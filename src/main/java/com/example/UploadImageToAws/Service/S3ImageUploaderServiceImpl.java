package com.example.UploadImageToAws.Service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.example.UploadImageToAws.Exception.ImageUploadException;
import org.apache.juli.logging.LogFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class S3ImageUploaderServiceImpl  implements ImageUploader {
    
    @Autowired
    private AmazonS3 client;
    
    @Value("${cloud.s3.bucket}")
    private String bucket;
    

    @Override
    public String uploadImage(MultipartFile image) {
        //actualFileName=abc.png
        String actualFileName=image.getOriginalFilename();
        
        
        String fileName = UUID.randomUUID().toString() + actualFileName.substring(actualFileName.lastIndexOf("."));

        //creating a metaData
        ObjectMetadata  metadata= new ObjectMetadata();
        metadata.setContentLength(image.getSize());

        try {
            PutObjectResult putObjectResult = client.putObject(new PutObjectRequest(bucket,fileName,image.getInputStream(),metadata));
            String imageUrl = this.presignedUrl(fileName);
            return imageUrl;
        } catch (IOException e) {
            throw new ImageUploadException("error in uploading image"+e.getMessage());
        }
    }

    @Override
    public List<String> allFiles() {
        //2nd do that
        ListObjectsV2Request listObjectRequest=new ListObjectsV2Request().withBucketName(bucket);

//1st do that
        ListObjectsV2Result listObjectsV2Result = client.listObjectsV2(listObjectRequest);
        List<S3ObjectSummary> objectSummaries = listObjectsV2Result.getObjectSummaries();
        List<String> list = objectSummaries.stream().map(item -> this.presignedUrl(item.getKey())).toList();
        return list;
    }

    @Override
    public String presignedUrl(String fileName) {

        //set the time for url will avaible for 2 hour
        Date expiretionDate=new Date();
        long time=expiretionDate.getTime();
        int hour=2;
        time=time+ hour*60*60*1000;
        expiretionDate.setTime(time);

        //here we are passing the expire date
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket,fileName)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiretionDate)
                ;

//getting a url of image or vedio
        URL url = client.generatePresignedUrl(generatePresignedUrlRequest);

        return url.toString();
    }
}

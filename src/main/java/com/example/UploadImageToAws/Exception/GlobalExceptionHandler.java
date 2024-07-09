package com.example.UploadImageToAws.Exception;

import com.example.UploadImageToAws.Model.Customeresponce;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler
//    public ResponseEntity<Customeresponce> handleImageUploadException(ImageUploadException imageUploadException){
//      //  return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Customeresponce.builder)
//        Customeresponce response = Customeresponce.bui
//                .message(imageUploadException.getMessage())
//                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                .build();
//
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//    }
}

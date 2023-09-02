package com.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
	
	
	@ExceptionHandler(CustomException.class)  // these exception is working from created class
	public ResponseEntity<CustomExceptionResponse> handleApiException(CustomException ex)
	{
	
		String a ="=========================================================================================================================";
		String message = ex.getMessage();
		String b ="=========================================================================================================================";
		
		CustomExceptionResponse  response = new CustomExceptionResponse(a,message,b);
		
		return new ResponseEntity<CustomExceptionResponse>(response,HttpStatus.BAD_REQUEST);
		
	}

}

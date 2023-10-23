package com.team2.Assessment1.controllers.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.http.HttpStatus;

import com.team2.Assessment1.dtos.ErrorDto;
import com.team2.Assessment1.exceptions.BadRequestException;
import com.team2.Assessment1.exceptions.NotAuthorizedException;
import com.team2.Assessment1.exceptions.NotFoundException;

@ControllerAdvice(basePackages = { "com.team2.Assessment1.controllers" })
@ResponseBody
public class Assessment1ControllerAdvice {
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ErrorDto handleBadRequestException(BadRequestException badRequestException) {
        return new ErrorDto(badRequestException.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorDto handleNotFoundException(NotFoundException notFoundException) {
        return new ErrorDto(notFoundException.getMessage());
    }
    
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotAuthorizedException.class)
    public ErrorDto handleNotAuthorizedException(NotAuthorizedException notAuthorizedException) {
    	return new ErrorDto(notAuthorizedException.getMessage());
    }
}

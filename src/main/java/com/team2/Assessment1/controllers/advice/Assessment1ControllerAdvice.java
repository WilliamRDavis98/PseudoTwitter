package com.team2.Assessment1.controllers.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackages = { "com.team2.Assessment1.controllers" })
@ResponseBody
public class Assessment1ControllerAdvice {
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(BadRequestException.class)
//    public ErrorDto handleBadRequestException(BadRequestException badRequestException) {
//        return new ErrorDto(badRequestException.getMessage());
//    }

//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(NotFoundException.class)
//    public ErrorDto handleNotFoundException(NotFoundException notFoundException) {
//        return new ErrorDto(notFoundException.getMessage());
//    }
}

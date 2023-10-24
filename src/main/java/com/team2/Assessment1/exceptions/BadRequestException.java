package com.team2.Assessment1.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BadRequestException extends RuntimeException{

	private static final long serialVersionUID = -2767348029949890761L;

	private String message;
}

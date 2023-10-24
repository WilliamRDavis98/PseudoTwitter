package com.team2.Assessment1.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class NotAuthorizedException extends RuntimeException {
	
	private static final long serialVersionUID = -8842816961905890161L;
	
	private String message;
}

package com.team2.Assessment1.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3790249904220376492L;

	private String message;
}

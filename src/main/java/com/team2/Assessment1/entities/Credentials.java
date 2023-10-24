package com.team2.Assessment1.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Data
public class Credentials {

	@Column(nullable = false)
	private String username;
	
	@Column(nullable = false)
	private String password;
}

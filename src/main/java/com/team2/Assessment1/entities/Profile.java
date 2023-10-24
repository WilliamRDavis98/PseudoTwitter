package com.team2.Assessment1.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Data
public class Profile {
	
	@Column(nullable = false)
	private String email;
	
	//Optional Fields
	private String phone;
	
	private String firstName;
	
	private String lastName;
}

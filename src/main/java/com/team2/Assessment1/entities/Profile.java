package com.team2.Assessment1.entities;

import java.sql.Timestamp;

import javax.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Data
public class Profile {
	
	// TODO: Determine if this needs to be a Date
	private Timestamp joined;
	
	private boolean deleted;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String phone;
}

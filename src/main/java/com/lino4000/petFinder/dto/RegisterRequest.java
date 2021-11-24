package com.lino4000.petFinder.dto;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.lino4000.petFinder.validation.ValidEmail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
@AllArgsConstructor
public class RegisterRequest {

	String username;

	@ValidEmail
	@NotNull
	@NotEmpty
	String email;
	String password;
	String passwordConfirmation;
	String info;
	String device;
}

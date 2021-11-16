package com.lino4000.petFinder.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class User implements Serializable{
	
	@Id @GeneratedValue
	private long id;
	@Column(unique=true)
	private String username;
	@NonNull
	private String password;
	@Column(unique=true) @NonNull
	private String email;
	private String info;
	@OneToMany(mappedBy="user")
	@JsonManagedReference
	private List<Device> devices;
}
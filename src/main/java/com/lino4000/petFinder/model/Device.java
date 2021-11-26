package com.lino4000.petFinder.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name="devices")
@SuppressWarnings("serial")
public class Device implements Serializable{

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(unique=true)
	private String serial;
	private String name;
	@ManyToOne @NonNull
	@JsonBackReference
	private User user;
	@OneToMany(mappedBy="device",cascade = CascadeType.REMOVE)
	@JsonManagedReference
	private List<Sensor> sensores;
}
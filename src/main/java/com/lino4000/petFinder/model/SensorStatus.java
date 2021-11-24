package com.lino4000.petFinder.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@SuppressWarnings("serial")
public class SensorStatus implements Serializable{
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Temporal(TemporalType.TIMESTAMP)
    private Date creationDateTime;
	@ManyToOne @NonNull
	@JsonBackReference
	private Sensor sensor;
	@NonNull
	private String value;
}

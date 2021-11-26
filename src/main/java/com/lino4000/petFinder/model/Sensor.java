package com.lino4000.petFinder.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
@Table(name="sensors", uniqueConstraints = { @UniqueConstraint(columnNames = {"device_id", "type"}) })
@SuppressWarnings("serial")
public class Sensor implements Serializable{
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne @NonNull
	@JsonBackReference
	private Device device;
	@NonNull
	private SensorType type;
	@OneToMany(mappedBy="sensor", cascade = CascadeType.REMOVE)
	@JsonManagedReference
	private List<SensorStatus> status;
	
	public enum SensorType {
		LATITUDE,
		LONGITUDE,
		ACCURACY,
		GYROSCOPE
	}
}

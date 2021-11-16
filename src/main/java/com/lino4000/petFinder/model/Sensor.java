package com.lino4000.petFinder.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
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
import lombok.RequiredArgsConstructor;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="sensors", uniqueConstraints = { @UniqueConstraint(columnNames = {"device_id", "type"}) })
public class Sensor implements Serializable{
	
	@Id @GeneratedValue
	private long id;
	@ManyToOne @NonNull
	@JsonBackReference
	private Device device;
	@NonNull
	private SensorType type;
	@OneToMany(mappedBy="sensor")
	@JsonManagedReference
	private List<SensorStatus> status;
	
	public enum SensorType {
		LATITUDE,
		LONGITUDE,
		ACCURACY,
		GYROSCOPE
	}
}

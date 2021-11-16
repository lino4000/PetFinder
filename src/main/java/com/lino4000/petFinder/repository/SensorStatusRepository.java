package com.lino4000.petFinder.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.lino4000.petFinder.model.Device;
import com.lino4000.petFinder.model.Sensor;
import com.lino4000.petFinder.model.SensorStatus;
import com.lino4000.petFinder.model.Sensor.SensorType;

public interface SensorStatusRepository extends CrudRepository<SensorStatus, Long>{
	
	public List<SensorStatus> findAllSensorStatusBySensor(Sensor sensor);
	public SensorStatus findById(long id);
}

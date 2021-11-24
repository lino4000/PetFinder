package com.lino4000.petFinder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.lino4000.petFinder.model.Sensor;
import com.lino4000.petFinder.model.SensorStatus;

public interface SensorStatusRepository extends CrudRepository<SensorStatus, Long>{
	
	public List<SensorStatus> findAllSensorStatusBySensor(Sensor sensor);
	public SensorStatus findById(long id);
	
	@Query("delete from sensor_status where sensor_id in(select * from sensors where device_id= :serial)")
	public void deleteAllBySerial(@Param("serial") String serial);

}

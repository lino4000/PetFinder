package com.lino4000.petFinder.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.lino4000.petFinder.model.Sensor;
import com.lino4000.petFinder.model.SensorStatus;

public interface SensorStatusRepository extends CrudRepository<SensorStatus, Long>{
	
	public List<SensorStatus> findAllSensorStatusBySensor(Sensor sensor);
	public SensorStatus findById(long id);
//	@Modifying @Transactional
//	@Query("delete from SensorStatus where sensor in(select * from Sensor where device= :id)")
//	public void deleteAllByDeviceId(@Param("id") long id);

}

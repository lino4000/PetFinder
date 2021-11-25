package com.lino4000.petFinder.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.lino4000.petFinder.model.Device;
import com.lino4000.petFinder.model.Sensor;
import com.lino4000.petFinder.model.Sensor.SensorType;

public interface SensorRepository extends CrudRepository<Sensor, Long>{
	
	public List<Sensor> findAllByDevice(Device device);
	public Sensor findById(int id);
	@Query(value = "SELECT s FROM Sensor s WHERE s.device = :device and s.type = :type")
	public Optional<Sensor> findByDeviceAndType(@Param("device") Device device, @Param("type") SensorType type);
//	@Modifying @Transactional
//	@Query("delete from Sensor where device = :id")
//	public void deleteAllBySerial(@Param("id") long id );

}

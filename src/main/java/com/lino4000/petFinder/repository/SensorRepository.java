package com.lino4000.petFinder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.lino4000.petFinder.model.Device;
import com.lino4000.petFinder.model.Sensor;
import com.lino4000.petFinder.model.Sensor.SensorType;
import com.lino4000.petFinder.model.SensorStatus;

public interface SensorRepository extends CrudRepository<Sensor, Long>{
	
	public List<Sensor> findAllByDevice(Device device);
	public Sensor findById(int id);
	@Query(value = "SELECT s FROM Sensor s WHERE s.device = :device and s.type = :type")
	public Sensor findByDeviceAndType(@Param("device") Device device, @Param("type") SensorType type);

}

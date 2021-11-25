package com.lino4000.petFinder.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.lino4000.petFinder.model.Device;

public interface DeviceRepository extends CrudRepository<Device, Long>{
	
	public Optional<Device> findBySerial(String serial);
//	public void deleteById(long id);

}

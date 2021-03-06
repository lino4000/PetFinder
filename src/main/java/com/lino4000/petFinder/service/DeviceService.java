package com.lino4000.petFinder.service;

import java.util.ArrayList;
import java.util.List;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lino4000.petFinder.dto.DeviceReceiveLocation;
import com.lino4000.petFinder.dto.DeviceResponse;
import com.lino4000.petFinder.dto.PathToGoogleMaps;
import com.lino4000.petFinder.model.Device;
import com.lino4000.petFinder.model.Sensor;
import com.lino4000.petFinder.model.Sensor.SensorType;
import com.lino4000.petFinder.model.SensorStatus;
import com.lino4000.petFinder.repository.DeviceRepository;
import com.lino4000.petFinder.repository.SensorRepository;
import com.lino4000.petFinder.repository.SensorStatusRepository;
import com.lino4000.petFinder.repository.UserRepository;

@Service
@Transactional
public class DeviceService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private SensorRepository sensorRepository;

	@Autowired
	private SensorStatusRepository sensorStatusRepository;

	public DeviceReceiveLocation newLocation(String serial, DeviceReceiveLocation location) {
		Device device = deviceRepository.findBySerial(serial).get();
		SensorStatus statusLat = SensorStatus.builder()
				.value(Double.toString(location.latitude))
				.sensor(sensorRepository.findByDeviceAndType(device, SensorType.LATITUDE).get())
				.build();

		SensorStatus statusLon = SensorStatus.builder()
				.value(Double.toString(location.longitude))
				.sensor(sensorRepository.findByDeviceAndType(device, SensorType.LONGITUDE).get())
				.build();

		SensorStatus statusAcc = SensorStatus.builder()
				.value(Double.toString(location.accuracy))
				.sensor(sensorRepository.findByDeviceAndType(device, SensorType.ACCURACY).get())
				.build();
		
		sensorStatusRepository.save(statusLat);
		sensorStatusRepository.save(statusLon);
		sensorStatusRepository.save(statusAcc);
		return location;
	}
	
	public List<PathToGoogleMaps> getPath(String serial) {
		List<SensorStatus> sensorsLat = sensorStatusRepository.findAllSensorStatusBySensor(
				sensorRepository.findByDeviceAndType(
						deviceRepository.findBySerial(serial).get(),
						SensorType.LATITUDE
					).get()
				);
		List<SensorStatus> sensorsLon = sensorStatusRepository.findAllSensorStatusBySensor(
				sensorRepository.findByDeviceAndType(
						deviceRepository.findBySerial(serial).get(),
						SensorType.LONGITUDE
						).get()
				);
		
		List<PathToGoogleMaps> locations = new ArrayList<>();

		for(int i=0; i < sensorsLat.size(); i++) {
			locations.add(
					PathToGoogleMaps.builder()
					.lat( Double.parseDouble(sensorsLat.get(i).getValue() ) )
					.lng( Double.parseDouble(sensorsLon.get(i).getValue() ) )
					.build()
					);
		}
		
		return locations;
		
	}
	
    public boolean deleteDevice(String serial) {
    	deviceRepository.deleteById(deviceRepository.findBySerial(serial).get().getId());
    	return true;
    }
    
    public boolean addDevice(DeviceResponse deviceResponse, String username) {
    	Device device = Device.builder()
    			.serial(deviceResponse.getSerial())
    			.name(deviceResponse.getName())
    			.user(userRepository.findByUsername(username).get())
    			.build();
    	
    	deviceRepository.save(device);
    	sensorRepository.save(Sensor.builder()
    			.device(device)
    			.type(SensorType.LATITUDE)
    			.build()
    			);
    	
    	deviceRepository.save(device);
    	sensorRepository.save(Sensor.builder()
    			.device(device)
    			.type(SensorType.LONGITUDE)
    			.build()
    			);
    	deviceRepository.save(device);
    	sensorRepository.save(Sensor.builder()
    			.device(device)
    			.type(SensorType.ACCURACY)
    			.build()
    			);
    	
    	return true;
    }
	
}

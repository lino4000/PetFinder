package com.lino4000.petFinder.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lino4000.petFinder.dto.DeviceReceiveLocation;
import com.lino4000.petFinder.model.Device;
import com.lino4000.petFinder.model.Sensor;
import com.lino4000.petFinder.model.Sensor.SensorType;
import com.lino4000.petFinder.model.SensorStatus;
import com.lino4000.petFinder.repository.DeviceRepository;
import com.lino4000.petFinder.repository.SensorRepository;
import com.lino4000.petFinder.repository.SensorStatusRepository;

@Service
@Transactional
public class DeviceService {

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private SensorRepository sensorRepository;

	@Autowired
	private SensorStatusRepository sensorStatusRepository;
	
	public DeviceReceiveLocation  newLocation(String serial, DeviceReceiveLocation location) {
		Device device = deviceRepository.findBySerial(serial);
		SensorStatus statusLat = SensorStatus.builder()
				.value(Double.toString(location.latitude))
				.sensor(sensorRepository.findByDeviceAndType(device, SensorType.LATITUDE))
				.build();

		SensorStatus statusLon = SensorStatus.builder()
				.value(Double.toString(location.longitude))
				.sensor(sensorRepository.findByDeviceAndType(device, SensorType.LONGITUDE))
				.build();

		SensorStatus statusAcc = SensorStatus.builder()
				.value(Double.toString(location.accuracy))
				.sensor(sensorRepository.findByDeviceAndType(device, SensorType.ACCURACY))
				.build();
		
		sensorStatusRepository.save(statusLat);
		sensorStatusRepository.save(statusLon);
		sensorStatusRepository.save(statusAcc);
		return location;
	}
	
	public List<DeviceReceiveLocation> getPath(String serial) {
		List<SensorStatus> sensorsLat = sensorStatusRepository.findAllSensorStatusBySensor(
				sensorRepository.findByDeviceAndType(
						deviceRepository.findBySerial(serial),
						SensorType.LATITUDE
					)
				);
		List<SensorStatus> sensorsLon = sensorStatusRepository.findAllSensorStatusBySensor(
				sensorRepository.findByDeviceAndType(
						deviceRepository.findBySerial(serial),
						SensorType.LONGITUDE
						)
				);
		List<SensorStatus> sensorsAcc = sensorStatusRepository.findAllSensorStatusBySensor(
				sensorRepository.findByDeviceAndType(
						deviceRepository.findBySerial(serial),
						SensorType.ACCURACY
						)
				);
		
		List<DeviceReceiveLocation> locations = new ArrayList<DeviceReceiveLocation>();

		for(int i=0; i < sensorsLat.size(); i++) {
			locations.add(
					DeviceReceiveLocation.builder()
					.latitude( Double.parseDouble(sensorsLat.get(i).getValue() ) )
					.longitude( Double.parseDouble(sensorsLon.get(i).getValue() ) )
					.accuracy( Double.parseDouble(sensorsAcc.get(i).getValue() ) )
					.build()
					);
		}
		
		return locations;
		
	}
}

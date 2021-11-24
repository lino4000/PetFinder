package com.lino4000.petFinder.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lino4000.petFinder.dto.DeviceReceiveLocation;
import com.lino4000.petFinder.dto.PathToGoogleMaps;
import com.lino4000.petFinder.service.DeviceService;


@Controller
@RequestMapping("/device")
public class DeviceController {
	
	@Autowired
	DeviceService deviceService;
	
	@PutMapping("{serial}/newlocation")
	@ResponseBody
    public DeviceReceiveLocation newLocation(@PathVariable("serial") String serial, @RequestBody @Valid final DeviceReceiveLocation locationReceived, @Param("nonce") long nonce) {
		
		return deviceService.newLocation(serial, locationReceived);
	}

	@GetMapping("{serial}/path")
	@ResponseBody
    public List<PathToGoogleMaps> getPath(@PathVariable("serial") String serial, @Param("nonce") long nonce) {
		return deviceService.getPath(serial);
	}

}

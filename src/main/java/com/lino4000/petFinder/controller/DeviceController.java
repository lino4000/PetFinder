package com.lino4000.petFinder.controller;

import javax.validation.Valid;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lino4000.petFinder.dto.DeviceReceiveLocation;


@Controller
@RequestMapping("/device")
public class DeviceController {
	
	@PostMapping("newlocation")
	@ResponseBody
    public String newLocation(@RequestBody @Valid final DeviceReceiveLocation locationReceived, @Param("nonce") long nonce) {
		return "Funcionou!";
	}

	@GetMapping("newlocation")
	@ResponseBody
    public String test(@RequestBody @Valid final DeviceReceiveLocation locationReceived, @Param("nonce") long nonce) {
		return "Funcionou!";
	}

}

package com.lino4000.petFinder.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lino4000.petFinder.dto.DeviceResponse;
import com.lino4000.petFinder.dto.GenericResponse;
import com.lino4000.petFinder.dto.RegisterRequest;
import com.lino4000.petFinder.model.Device;
import com.lino4000.petFinder.model.User;
import com.lino4000.petFinder.service.DeviceService;
import com.lino4000.petFinder.service.UserService;

@Controller
@RequestMapping("/dashboard")
public class PrivateController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private DeviceService deviceService;
	
	@Autowired
    private MessageSource messages;
	
	@GetMapping("/welcome")
	public String index(ModelMap map, Principal principal) {
		map.addAttribute("devices", userService.getDeviceList(principal.getName()));
		map.addAttribute("device", Device.builder().user(userService.getUser(principal)).build());
		return "welcome";
	}
	
	@GetMapping("/account")
	public String account(ModelMap map, Principal principal, RegisterRequest user) {
		User u = userService.getUser(principal);
		map.addAttribute("user", RegisterRequest.builder()
				.username(u.getUsername())
				.email(u.getEmail())
				.info(u.getInfo())
				.password("")
				.passwordConfirmation("")
				.device("")
				.build()
		);
		return "account";
	}

	@GetMapping("/view")
	public String viewDevice(Principal principal, ModelMap map)	{ 
		map.addAttribute("info", userService.getUser(principal).getInfo());
	    return "device";
	}
	
	@GetMapping("/device/add")
	public String deviceAdd(@RequestParam(required = false) String serial, ModelMap map, DeviceResponse device) {
		map.addAttribute("serial", serial);
		map.addAttribute("device", DeviceResponse.builder().build());
		return "device-add";
	}

	@PostMapping("/device/add")
	@ResponseBody
	public GenericResponse deviceAdding(@RequestBody DeviceResponse device, Principal principal, final HttpServletRequest request) {
        try {
            deviceService.addDevice(device, principal.getName());
        } catch (final IllegalArgumentException ex) {
        	
            return GenericResponse.builder()
            		.title( messages.getMessage("generic.title.failure", null, request.getLocale()) )
            		.message( ex.getMessage() )
            		.build();
	    } catch (final RuntimeException ex) {
	    	
	        return GenericResponse.builder()
	        		.title( messages.getMessage("generic.title.failure", null, request.getLocale()) )
	        		.message( messages.getMessage("generic.message.failure", null, request.getLocale()) )
	        		.build();
	    }
        
        return GenericResponse.builder()
        		.title( messages.getMessage("generic.title.success", null, request.getLocale()) )
        		.message( messages.getMessage("user.device.add.success", null, request.getLocale()))
        		.build();
	}

	@GetMapping("/device/{serial}/finder")
	public String getMapPage(@PathVariable("serial") String serial, ModelMap map) {
		map.addAttribute("path", deviceService.getPath(serial));
		return "finder";
	}
	
	@PostMapping("/device/{serial}/change-name")
	@ResponseBody
	public GenericResponse changeDeviceName(@PathVariable("serial") String serial, @RequestBody DeviceResponse device,final HttpServletRequest request) {
        try {
            userService.changeDeviceName(serial, device.getName());
        } catch (final IllegalArgumentException ex) {
        	
            return GenericResponse.builder()
            		.title( messages.getMessage("generic.title.failure", null, request.getLocale()) )
            		.message( ex.getMessage() )
            		.build();
        } catch (final RuntimeException ex) {
        	
            return GenericResponse.builder()
            		.title( messages.getMessage("generic.title.failure", null, request.getLocale()) )
            		.message( messages.getMessage("generic.message.failure", null, request.getLocale()) )
            		.build();
        }
        
        return GenericResponse.builder()
        		.title( messages.getMessage("generic.title.success", null, request.getLocale()) )
        		.message( messages.getMessage("user.device.changeName.success", null, request.getLocale()))
        		.build();
	}

	@PostMapping("/device/{serial}/delete")
	@ResponseBody
	public GenericResponse deleteDevice(@PathVariable("serial") String serial, final HttpServletRequest request) {
		
        try {
            deviceService.deleteDevice(serial);
        } catch (final IllegalArgumentException ex) {
        	
            return GenericResponse.builder()
            		.title( messages.getMessage("generic.title.failure", null, request.getLocale()) )
            		.message( ex.getMessage() )
            		.build();
        } catch (final RuntimeException ex) {
        	
            return GenericResponse.builder()
            		.title( messages.getMessage("generic.title.failure", null, request.getLocale()) )
            		.message( messages.getMessage("generic.message.failure", null, request.getLocale()) )
            		.build();
        }
        
        return GenericResponse.builder()
        		.title( messages.getMessage("generic.title.success", null, request.getLocale()) )
        		.message( messages.getMessage("user.device.delete.success", null, request.getLocale()))
        		.build();
	}
	
	@PostMapping("/user/change-username")
	@ResponseBody
	public GenericResponse changeUsername( @RequestBody RegisterRequest user, Principal principal, final HttpServletRequest request) {
        try {
            userService.changeUsername(principal.getName(), user.getUsername());
        } catch (final IllegalArgumentException ex) {
        	
            return GenericResponse.builder()
            		.title( messages.getMessage("generic.title.failure", null, request.getLocale()) )
            		.message( ex.getMessage() )
            		.build();
        } catch (final RuntimeException ex) {
        	
            return GenericResponse.builder()
            		.title( messages.getMessage("generic.title.failure", null, request.getLocale()) )
            		.message( messages.getMessage("generic.message.failure", null, request.getLocale()) )
            		.build();
        }
        
        return GenericResponse.builder()
        		.title( messages.getMessage("generic.title.success", null, request.getLocale()) )
        		.message( messages.getMessage("user.changeUsername.success", null, request.getLocale()))
        		.build();
		
	}

	@PostMapping("/user/change-email")
	@ResponseBody
	public GenericResponse changeEmail(@RequestBody RegisterRequest user, Principal principal, final HttpServletRequest request) {
        try {
            userService.changeEmail(principal.getName(), user.getEmail());
        } catch (final IllegalArgumentException ex) {
        	
            return GenericResponse.builder()
            		.title( messages.getMessage("generic.title.failure", null, request.getLocale()) )
            		.message( ex.getMessage() )
            		.build();
        } catch (final RuntimeException ex) {
        	
            return GenericResponse.builder()
            		.title( messages.getMessage("generic.title.failure", null, request.getLocale()) )
            		.message( messages.getMessage("generic.message.failure", null, request.getLocale()) )
            		.build();
        }
        
        return GenericResponse.builder()
        		.title( messages.getMessage("generic.title.success", null, request.getLocale()) )
        		.message( messages.getMessage("user.changeUsername.success", null, request.getLocale()))
        		.build();
		
	}
	@PostMapping("/user/change-info")
	@ResponseBody
	public GenericResponse changeInfo(@RequestBody User user, Principal principal, final HttpServletRequest request) {
        try {
            userService.changeInfo(principal.getName(), user.getInfo());
        } catch (final RuntimeException ex) {
        	
            return GenericResponse.builder()
            		.title( messages.getMessage("generic.title.failure", null, request.getLocale()) )
            		.message( messages.getMessage("generic.message.failure", null, request.getLocale()) )
            		.build();
        }
        
        return GenericResponse.builder()
        		.title( messages.getMessage("generic.title.success", null, request.getLocale()) )
        		.message( messages.getMessage("user.changeInfo.success", null, request.getLocale()))
        		.build();
		
	}

	@PostMapping("/user/change-password")
	@ResponseBody
	public GenericResponse changePassword(@RequestBody RegisterRequest user, Principal principal, final HttpServletRequest request) {
        try {
            userService.changePassword(principal.getName(), user.getPassword(),user.getPasswordConfirmation());
        } catch (final IllegalArgumentException ex) {
        	
            return GenericResponse.builder()
            		.title( messages.getMessage("generic.title.failure", null, request.getLocale()) )
            		.message( ex.getMessage() )
            		.build();
        } catch (final RuntimeException ex) {
        	
            return GenericResponse.builder()
            		.title( messages.getMessage("generic.title.failure", null, request.getLocale()) )
            		.message( messages.getMessage("generic.message.failure", null, request.getLocale()) )
            		.build();
        }
        
        return GenericResponse.builder()
        		.title( messages.getMessage("generic.title.success", null, request.getLocale()) )
        		.message( messages.getMessage("user.changeInfo.success", null, request.getLocale()))
        		.build();
		
	}
	
	@PostMapping("/user/delete")
	@ResponseBody
	public GenericResponse deleteDevice(@RequestBody RegisterRequest user, Principal principal, final HttpServletRequest request) {
		
        try {
            userService.deleteUser(principal.getName());
        } catch (final RuntimeException ex) {
        	
            return GenericResponse.builder()
            		.title( messages.getMessage("generic.title.failure", null, request.getLocale()) )
            		.message( messages.getMessage("generic.message.failure", null, request.getLocale()) )
            		.build();
        }
        
        return GenericResponse.builder()
        		.title( messages.getMessage("generic.title.success", null, request.getLocale()) )
        		.message( messages.getMessage("user.delete.success", null, request.getLocale()) )
        		.build();
	}

}

package com.lino4000.petFinder.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lino4000.petFinder.dto.GenericResponse;
import com.lino4000.petFinder.dto.RegisterRequest;
import com.lino4000.petFinder.error.EmailAlreadyExistException;
import com.lino4000.petFinder.error.UserAlreadyExistException;
import com.lino4000.petFinder.service.UserService;

@Controller
public class PublicController {
	
	@Autowired
	public UserService userService;

	@Autowired
    private MessageSource messages;
	
	@GetMapping("/login")
	public String loginPage(ModelMap map) {
		map.addAttribute("err", messages.getMessage("user.tryToLogin.failure", null, null ) );
		return "login";
	}
	
	@GetMapping("/register")
	public String registerPage(@ModelAttribute("user") RegisterRequest registerRequest)	{
	    return "register";
	}

	@PostMapping("/register")
	@ResponseBody
    public GenericResponse registering(@RequestBody @Valid final RegisterRequest registerRequest, final HttpServletRequest request, final Errors errors) {
		
        try {
        	
            userService.registerNewUser(registerRequest);
            
        } catch (final UserAlreadyExistException ex) {
        	
            return GenericResponse.builder()
            		.title( messages.getMessage("user.registration.failure", null, request.getLocale()) )
            		.message( ex.getMessage() )
            		.build();

        } catch (final EmailAlreadyExistException ex) {
        	
            return GenericResponse.builder()
            		.title( messages.getMessage("user.registration.failure", null, request.getLocale()) )
            		.message( ex.getMessage() )
            		.build();

        } catch (final RuntimeException ex) {
        	
            return GenericResponse.builder()
            		.title( messages.getMessage("user.registration.failure", null, request.getLocale()) )
            		.message( ex.getMessage() )
            		.build();
        }
        
        return GenericResponse.builder()
        		.title( messages.getMessage("user.registration.success.title", null, request.getLocale()) )
        		.message( messages.getMessage("user.registration.success.message", null, request.getLocale()) )
        		.build();
    }

}

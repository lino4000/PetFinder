package com.lino4000.petFinder.service;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lino4000.petFinder.dto.RegisterRequest;
import com.lino4000.petFinder.error.DeviceNotFoundException;
import com.lino4000.petFinder.error.EmailAlreadyExistException;
import com.lino4000.petFinder.error.PasswordMatchException;
import com.lino4000.petFinder.error.UserAlreadyExistException;
import com.lino4000.petFinder.model.Device;
import com.lino4000.petFinder.model.User;
import com.lino4000.petFinder.model.VerificationToken;
import com.lino4000.petFinder.repository.DeviceRepository;
import com.lino4000.petFinder.repository.UserRepository;
import com.lino4000.petFinder.repository.VerificationTokenRepository;

@Service
@Transactional
public class UserService implements UserDetailsService{
	
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeviceRepository deviceRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MessageSource messages;

    @Autowired
    private VerificationTokenRepository tokenRepository;
    
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Optional<User> user;
		if (login.contains("@")) {
			user = userRepository.findByEmail(login);
			user.orElseThrow(() -> new UsernameNotFoundException(messages.getMessage("user.email.notFound", null, null)));
		}
		else {
			user = userRepository.findByUsername(login);
			user.orElseThrow(() -> new UsernameNotFoundException(messages.getMessage("user.username.notFound", null, null)));
		}
	
		return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), true, true, true, true, new HashSet<>());
	}
	
	public boolean registerNewUser(RegisterRequest registerRequest) throws UserAlreadyExistException {
		
        if ( usernameExists( registerRequest.getUsername() ) ) {
            throw new UserAlreadyExistException(messages.getMessage("user.username.alreadExists",null,null));
        }

        if ( emailExists( registerRequest.getEmail() ) ) {
            throw new EmailAlreadyExistException(messages.getMessage("user.email.alreadExists",null,null));
        }
        
        userRepository.save(
    		User.builder()
        	.username(registerRequest.getUsername())
        	.password(passwordEncoder.encode(registerRequest.getPassword()))
        	.email(registerRequest.getEmail())
        	.build()
    	);
        
        return true;
    }

    
    public List<Device> getDeviceList(String username){
    	return userRepository.findByUsername(username).get().getDevices();
    }
    
    public boolean changeDeviceName(String serial, String name) {
    	Optional<Device> device = deviceRepository.findBySerial(serial);
    	device.orElseThrow(() -> new DeviceNotFoundException(messages.getMessage("user.device.notFound", null, null)));
    	device.get().setName(name);
    	deviceRepository.save(device.get());
    	return true;
    }
    
    
    public boolean changeUsername(String usernameOlder, String usernameNew) {
    	Optional<User> user = userRepository.findByUsername(usernameOlder);
    	user.orElseThrow(() -> new UsernameNotFoundException(messages.getMessage("user.username.notFound", null, null)));
    	user.get().setUsername(usernameNew);
    	userRepository.save(user.get());
    	return true;
    }
    
    public boolean changeEmail(String username, String email) {
    	Optional<User> user = userRepository.findByUsername(username);
    	user.orElseThrow(() -> new UsernameNotFoundException(messages.getMessage("user.username.notFound", null, null)));
    	user.get().setEmail(email);
    	userRepository.save(user.get());
    	return true;
    }

    public boolean changeInfo(String username, String info) {
    	Optional<User> user = userRepository.findByUsername(username);
    	user.orElseThrow(() -> new UsernameNotFoundException(messages.getMessage("user.username.notFound", null, null)));
    	user.get().setInfo(info);
    	userRepository.save(user.get());
    	return true;
    }

    public boolean changePassword(String username, String p1, String p2) throws PasswordMatchException {
    	if( ! p1.equals(p2)) {
    		throw new PasswordMatchException(messages.getMessage("user.password.failure",null,null));
    	}
    	Optional<User> user = userRepository.findByUsername(username);
    	user.orElseThrow(() -> new UsernameNotFoundException(messages.getMessage("user.username.notFound", null, null)));
    	user.get().setPassword(passwordEncoder.encode(p1));
    	userRepository.save(user.get());
    	return true;
    }
       
    public User getUser(Principal principal) {
		Optional<User> user = userRepository.findByUsername(principal.getName());
		user.orElseThrow(() -> new UsernameNotFoundException(messages.getMessage("user.username.notFound", null, null)));
		return user.get();
    }
    
    public void createVerificationTokenForUser(final User user, final String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    private boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
    
    private boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

}
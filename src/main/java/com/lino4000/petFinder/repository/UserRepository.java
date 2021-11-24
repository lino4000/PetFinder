package com.lino4000.petFinder.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.lino4000.petFinder.model.User;

public interface UserRepository extends CrudRepository<User, Long>{
	
	public boolean existsByEmail(String email);
	public boolean existsByUsername(String username);
	public Optional<User> findByEmail(String email);
	public Optional<User> findByUsername(String username);
}

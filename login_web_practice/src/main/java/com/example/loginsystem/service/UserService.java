package com.example.loginsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.loginsystem.model.NormalUser;
import com.example.loginsystem.model.PremiumUser;
import com.example.loginsystem.repository.UserRepository;
import com.example.loginsystem.repository.PremiumUserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PremiumUserRepository premiumUserRepository;
	
	@Transactional
	public NormalUser registerNormal(NormalUser user) {
		return userRepository.save(user);
	}
	
	@Transactional
	public PremiumUser registerPremium(PremiumUser user) {
		return premiumUserRepository.save(user);
	}
	
	public NormalUser loginNormal(String username, String password) {
		return userRepository.findByUsernameAndPassword(username, password);
	}
	
	public PremiumUser loginPremium(String username, String password) {
		return premiumUserRepository.findByUsernameAndPassword(username, password);
	}
	
	public boolean isUsernameExists(String username) {
		return userRepository.findByUsername(username) != null || 
			   premiumUserRepository.findByUsername(username) != null;
	}
}


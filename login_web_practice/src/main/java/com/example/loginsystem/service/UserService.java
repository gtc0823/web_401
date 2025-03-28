//package com.example.loginsystem.service;
//
//import com.example.loginsystem.model.User;
//import com.example.loginsystem.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    public User saveUser(User user) {
//        return userRepository.save(user);
//    }
//
//    public User findByUsername(String username) {
//        return userRepository.findByUsername(username);
//    }
//}

package com.example.loginsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.loginsystem.model.User;
import com.example.loginsystem.repository.UserRepository;


@Service
public class UserService {
	
	@Autowired
	UserRepository userrepository;
	public void register_normal(User user) {
		userrepository.register_normal(user);
	}
	public void register_premium(User user) {
		userrepository.register_premium(user);
	}
	public User login_normal(String username, String password) {
		return userrepository.login_normal(username, password);
	}
	public User login_premium(String username, String password) {
		return userrepository.login_premium(username, password);
	}
	

}


package com.apap.tutorial8.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.repository.UserRoleDb;

@Service
public class UserRoleServiceImpl implements UserRoleService{
	@Autowired
	private UserRoleDb userDb;

	@Override
	public UserRoleModel addUser(UserRoleModel user) {
		String pass = encrypt(user.getPassword());
		user.setPassword(pass);
		return userDb.save(user);
	}
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	@Override
	public String encrypt(String password){
		/*BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();*/
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}

	@Override
	public UserRoleModel findUserByUsername(String name) {
		return userDb.findByUsername(name);
	}

	@Override
	public boolean validatePassword(String oldPassword, String inputOldPassword) {
		/*String cekPassword = encrypt(inputOldPassword);*/
		if (passwordEncoder.matches(inputOldPassword, oldPassword)) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public void changePassword(UserRoleModel user, String newPassword) {
		String pass = encrypt(newPassword);
		user.setPassword(pass);
		userDb.save(user);
		
	}

	
	
/*	@Override
	public String validateNewPass(String pass1, String pass2) {
	    if (pass1 == null || pass2 == null) {
	        logger.info("Passwords = null");
	        return "One or both passwords are null";
	    }

	    StringBuilder retVal = new StringBuilder();

	    if (pass1.isEmpty() || pass2.isEmpty()) {
	        retVal.append("Empty fields <br>");
	    }else{
	        logger.info("Passwords = null");
	        retVal.append("Passwords Null <br>");
	    }

	    if (pass1.equals(pass2)) {
	        logger.info(pass1 + " = " + pass2);

	        if (pass1.length() < 11) {
	            logger.info(pass1 + " is length < 11");
	            retVal.append("Password is too short. Needs to have 11 characters <br>");
	        
	        }
	    }else{
            logger.info(pass1 + " != " + pass2);
            retVal.append("Passwords don't match<br>");
        }
    }
    if(retVal.length() == 0){
        logger.info("Password validates");
        retVal.append("Success");
    }

    return retVal.toString();
}*/
}

package com.apap.tutorial8.controller;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apap.tutorial8.model.PasswordModel;
import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.service.UserRoleService;

@Controller
@RequestMapping("/user")
public class UserRoleController {
	@Autowired
	private UserRoleService userService;

	@RequestMapping( value = "/addUser", method = RequestMethod.POST)
	private String addUserSubmit(@ModelAttribute UserRoleModel user, Model model) {
		Boolean passValidation= syaratPassword(user.getPassword());
		if(passValidation==true) {
			userService.addUser(user);
		}
		else {
			model.addAttribute("message", "Password teralu lemah");
		}		
		
		userService.addUser(user);
		return "home";
	}
	
	@RequestMapping(value ="/updatePassword")
	public String updatePassword() {
		return "update-password";
	}	
	
	@RequestMapping(value = "/updatePasswordSubmit", method = RequestMethod.POST)
	private String  updatePassword(@ModelAttribute PasswordModel password, Authentication authentication, Model model) {
		UserRoleModel currUser = userService.findUserByUsername(authentication.getName());
		if (password.getConPassword().equals(password.getNewPassword())) {
			System.out.println(authentication.getName());
			if (userService.validatePassword(currUser.getPassword(), password.getOldPassword())) {
				if (syaratPassword(password.getNewPassword())) {
					userService.changePassword(currUser, password.getNewPassword());
					model.addAttribute("message", "password telah diubah");
				}
				
			}
			else {
				model.addAttribute("message", "password lama anda salah");
			}
			
		}
		else {
			model.addAttribute("message", "password baru tidak sesuai");
		}
		System.out.println(password.getNewPassword());
		System.out.println(password.getOldPassword());
		System.out.println(currUser.getPassword());
		return "update-password";
	}
	
	public boolean syaratPassword(String password) {
		if (password.length()>=8 && Pattern.compile("[0-9]").matcher(password).find() &&  Pattern.compile("[a-zA-Z]").matcher(password).find())  {
			return true;
		}
		else {
			return false;
		}
	}
	
}
package com.apap.tutorial8.service;

import java.util.Optional;

import com.apap.tutorial8.model.UserRoleModel;

public interface UserRoleService {
	UserRoleModel addUser(UserRoleModel user);
	public String encrypt(String password);
	public UserRoleModel findUserByUsername(String name);
	public boolean validatePassword(String oldPassword, String inputOldPassword);
	void changePassword(UserRoleModel user, String newPassword);
	
}
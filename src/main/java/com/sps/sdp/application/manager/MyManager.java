package com.sps.sdp.application.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sps.sdp.application.dao.MyDAO;

@Service
public class MyManager {
	
	public MyDAO myDAO;
	
	@Autowired
	public MyManager(MyDAO myDAO) {
		this.myDAO = myDAO;
	}



	public String getUserDetails(String userName) {
		
		String password = null;
		
		password = myDAO.getUserDetails(userName);
		
		return password;
		
	}

}

package com.github.fwidder.freedoodle.config;

import com.github.fwidder.freedoodle.dao.UserDAOService;
import com.github.fwidder.freedoodle.util.UserNotFoundException;
import lombok.extern.flogger.Flogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Flogger
@Component
public class InitialConfig implements ApplicationListener<ApplicationReadyEvent> {
	private final String adminUsername;
	private final String adminPassword;
	private final Boolean adminForceCreate;
	private final UserDAOService userDAOService;
	
	@Autowired
	public InitialConfig(@Value( "${admin.username}" ) String adminUsername, @Value( "${admin.password}" ) String adminPassword, @Value( "${admin.forceCreate}" ) Boolean adminForceCreate, UserDAOService userDAOService) {
		this.adminUsername = adminUsername;
		this.adminPassword = adminPassword;
		this.adminForceCreate = adminForceCreate;
		this.userDAOService = userDAOService;
	}
	
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		log.atInfo().log("Starting Database Initialization ...");
		createAdminUser();
		log.atInfo().log("Finished Database Initialization ...");
	}
	
	private void createAdminUser() {
		try {
			userDAOService.findUserByName(adminUsername);
			if( adminForceCreate ) {
				log.atInfo().log("Admin User \"%s\" already exists but Force Recreate ist set.", adminUsername);
				log.atInfo().log("Deleting Admin User ...");
				userDAOService.deleteUser(adminUsername);
				log.atInfo().log("Recreating Admin User ...");
				userDAOService.addUser(adminUsername, adminPassword);
				return;
			}
			log.atInfo().log("Admin User \"%s\" already exists.", adminUsername);
			log.atInfo().log("Skip creation ...");
			return;
		} catch(UserNotFoundException userNotFoundException) {
			log.atInfo().log("Admin User \"%s\" does not exist.", adminUsername);
		}
		log.atInfo().log("Creating Admin User ...");
		userDAOService.addUser(adminUsername, adminPassword);
	}
}

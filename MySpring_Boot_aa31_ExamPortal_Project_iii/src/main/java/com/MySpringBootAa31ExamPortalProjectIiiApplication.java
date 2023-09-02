package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.model.Role;
import com.model.User;
import com.model.User_role;
import com.repo.RoleRepo;
import com.repo.UserRepo;
import com.repo.User_roleRepo;
import com.service.UserService;

@SpringBootApplication
public class MySpringBootAa31ExamPortalProjectIiiApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(MySpringBootAa31ExamPortalProjectIiiApplication.class, args);
	}

	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private User_roleRepo user_roleRepo;
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	@Override
	public void run(String... args) throws Exception {
		
		
		User local = this.userRepo.findByUsername("admin");
		 if(local == null)
		 {
		
		//======== creating user(admin) without api========================================	
		
		 
			User admin = new User();
			admin.setUsername("admin");
			admin.setFirstname("admin");
			admin.setLastname("admin");
			admin.setPassword(this.passwordEncoder.encode("admin"));
			admin.setEmail("admin@gmail.com");
			admin.setPhone("1234567891");
			admin.setProfile("profile.png");
			
			this.userRepo.save(admin);
		 
		
		//========  Role  ====================================================================================		
		
			Role role = new Role();
			role.setRid(1);
			role.setRolename("ROLE_ADMIN");
			
			this.roleRepo.save(role);
		
		//========= User_role ==================================================================================		
		
			User_role user_role = new User_role();
			user_role.setUrid(1);
			user_role.setRole(role);
			user_role.setUser(admin);
			
			this.user_roleRepo.save(user_role);
		
		
		 }
	}
}

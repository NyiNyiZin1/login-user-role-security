package com.nnz.user_role_security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnz.user_role_security.entity.User;
import com.nnz.user_role_security.repository.UserRepository;

@Service
@Transactional
public class UserService implements UserDetailsService{
	UserRepository userRepo;
	PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepo,PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.passwordEncoder=passwordEncoder;
		
		if(!userRepo.existsByEmail("lynnlynn@gmail.com")) {
			User user = new User();
			
			user.setName("LynnLynn");
			user.setEmail("lynnlynn@gmail.com");
			String encode = passwordEncoder.encode("lynnlynn");
			System.out.println("Encoded Password : "+encode);
			user.setPassword(encode);
			user.setRole("ROLE_ADMIN");
			
			userRepo.save(user);
		}
		if(!userRepo.existsByEmail("zawwinhtut@gmail.com")) {
			User user = new User();
			
			user.setName("ZawWinHtut");
			user.setEmail("zawwinhtut@gmail.com");
			String encode = passwordEncoder.encode("zawwinhtut");
			System.out.println("Encoded Password : "+encode);
			user.setPassword(encode);
			user.setRole("ROLE_EMPLOYEE");
			
			userRepo.save(user);
		}
		
	}
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		System.out.println("User email!!!!"+email);
		User user =  (User) userRepo.findByEmail(email);
		//System.out.println("existing user >>>>"+user.getName());
		if (user == null) {
            throw new BadCredentialsException("User don't exist.");
        }
		List<GrantedAuthority> authorityList = new ArrayList<>();
		authorityList.add(new SimpleGrantedAuthority(user.getRole()));
		return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), authorityList);
		
	}
}

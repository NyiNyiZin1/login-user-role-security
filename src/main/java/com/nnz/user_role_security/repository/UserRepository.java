package com.nnz.user_role_security.repository;



import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nnz.user_role_security.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
	User findByEmail(String email);

	boolean existsByEmail(String email);
	
	
	
}

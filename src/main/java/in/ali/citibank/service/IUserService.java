package in.ali.citibank.service;

import java.util.List;
import java.util.Optional;

import in.ali.citibank.entity.User;

public interface IUserService {
	
	User saveUser(User user); 
	List<User> getAllUsers();
	public User findByUserId(Integer userId);
	Optional<User> findByUserName(String username);
}

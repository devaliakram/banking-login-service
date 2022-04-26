package in.ali.citibank.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import in.ali.citibank.entity.User;
import in.ali.citibank.exception.UserNotFoundException;
import in.ali.citibank.repository.IUserRepository;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {
	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	/*
	 * @Autowired public UserServiceImpl(IUserRepository userRepository) {
	 * this.userRepository = userRepository; }
	 */
	@Override
	public User saveUser(User user) {
		// password encoded before storing to db
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User findByUserId(Integer userId) {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new UserNotFoundException("THIS USER IS NOT FOUND...!!");
		}

	}

	@Override
	public Optional<User> findByUserName(String username) {
		return userRepository.findByUserName(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> name = findByUserName(username);
		if (name.isEmpty())
			throw new UsernameNotFoundException("User IS NOT FOUND");

		User user = name.get();
		return new org.springframework.security.core.userdetails.User(username, user.getPassword(),
				user.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority(role))
				.collect(Collectors.toList()));
	}

}

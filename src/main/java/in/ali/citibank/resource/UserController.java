package in.ali.citibank.resource;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ali.citibank.dto.UserDTO;
import in.ali.citibank.entity.User;
import in.ali.citibank.exception.UserNotFoundException;
import in.ali.citibank.request.UserRequest;
import in.ali.citibank.response.UserResponse;
import in.ali.citibank.service.IUserService;
import in.ali.citibank.utility.JwtUtils;

@RestController
@RequestMapping("/user")
public class UserController {

	private IUserService userService;
	
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private AuthenticationManager authenticationManager;
	/*
	 * @Autowired public void setUserService(IUserService userService) {
	 * this.userService = userService; }
	 */

	// constructor injection is used here
	@Autowired
	public UserController(IUserService userService) {
		this.userService = userService;
	}

	@PostMapping("/save")
	public ResponseEntity<User> saveUser(@Valid @RequestBody UserDTO userDTO) {
		User user = new User();
		BeanUtils.copyProperties(userDTO, user);
		return new ResponseEntity<User>(userService.saveUser(user), HttpStatus.CREATED);
	}

	@GetMapping("/fetch")
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}

	@GetMapping("/one/{id}")
	public ResponseEntity<User> findByUserId(@PathVariable Integer id) throws UserNotFoundException {
		return ResponseEntity.ok(userService.findByUserId(id));

	}
	
	//validate the user and generate the token(Login)
	@PostMapping("/login")
	public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest userRequest){
		//TODO: VALIDATE THE UN/PWD WITH DB
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUserName(),
				userRequest.getPassword()));
		//generate the tokens,by calling utils class method 
		String token = jwtUtils.generateToken(userRequest.getUserName());
		return ResponseEntity.ok(new UserResponse(token," TOKEN IS GENERATED..!"));
		
	}
	
	
	@PostMapping("/welcome")
	public ResponseEntity<String> userWelcome(Principal principle){
		return ResponseEntity.ok("welcome to security in page "+principle);
	}

}

package in.ali.citibank.dto;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDTO {

	@NotBlank(message = "NAME PROPERTY IS NOT NULL OR WHITESPACE.")
	private String name;

	@NotNull
	@Size(min = 2, message = "user name should have at least 2 characters and not be empty")
	private String userName;

	@NotNull
	@Size(min = 8, message = "password should have at least 8 characters")
	private String password;
	@NotEmpty
	@Pattern(regexp = "^\\d{10}$")
	private String phoneNumber;

	@NotEmpty(message = "roles property is not null or empty; can be applied to String, Collection, Map, or Array values")
	private Set<String> roles;

	@Override
	public String toString() {
		return "UserDTO [name=" + name + ", userName=" + userName + ", password=" + password + ", phoneNumber="
				+ phoneNumber + ", roles=" + roles + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

}

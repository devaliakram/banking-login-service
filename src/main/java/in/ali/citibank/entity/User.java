package in.ali.citibank.entity;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="user")
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue
	private Integer userId;
	private String  name;
	private String userName;
	private String password;
	private String phoneNumber;
	
	
	@ElementCollection
	@CollectionTable(name="rolesTable",joinColumns = @JoinColumn(name="id"))
	@Column(name="roles")
	private Set<String> roles;

}

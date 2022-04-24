package in.ali.citibank.repository;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ali.citibank.entity.User;

public interface IUserRepository extends JpaRepository<User,Serializable>{
	
	   Optional<User> findByUserName(String username);

}

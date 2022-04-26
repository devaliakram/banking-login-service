package in.ali.citibank.utility;

import java.sql.Time;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtUtils {
	
	@Value("{app.secret}")
	private String secret;
	
	//1.generate the token
	public String generateToken(String subject) {
		return Jwts.builder().setSubject(subject)
				.setIssuer("xoriant").setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+TimeUnit.MINUTES.toMillis(15)))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
               
	}
	
	//2.READ THE TOKEN DATA
    public Claims getClaims(String token) {
    	return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
    }
    
    //3.validate the exp date
    public boolean isTokenExpired(String token) {
         Date expDate = getExpDate(token);
         return expDate.before(new Date(System.currentTimeMillis()));
    }
    
    //4.get exp date of token
    public Date getExpDate(String token) {
    	return getClaims(token).getExpiration();
    }
    
    //5.validate token
    public boolean validateToken(String token,String username) {
    	String tokenUserName = getClaims(token).getSubject();
    	return username.equals(tokenUserName) && !isTokenExpired(token);
    }
      
}

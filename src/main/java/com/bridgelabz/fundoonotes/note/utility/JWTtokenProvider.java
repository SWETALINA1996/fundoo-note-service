package com.bridgelabz.fundoonotes.note.utility;

import java.util.Date;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTtokenProvider {

@Value("${jwt.key}")
private String keys;
private final static String key = "Sweta";
	
	/**To generate the token
	 * @param user
	 * @return
	 */
	public String generator(String userId) 
	{
		
		//long time = System.currentTimeMillis();
		
		long nowMillis = System.currentTimeMillis();
		Date date = new Date(nowMillis);
		
		return Jwts.builder().setId(userId).setIssuedAt(date).signWith(SignatureAlgorithm.HS256, key).compact();
		
	}
	
	/**To claim token
	 * @param jwt
	 */
	public String parseJWT(String jwt)
	{

		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(key)).parseClaimsJws(jwt)
				.getBody();
	    String claimId = claims.getId();
		//claims.getSubject();
		return claimId;
	}
}

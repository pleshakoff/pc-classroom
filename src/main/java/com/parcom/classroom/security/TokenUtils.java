package com.parcom.classroom.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class TokenUtils
{

	@FunctionalInterface
	interface DurationSetter {
		Date getExpirationDate(Date now);
	}

	private static final String MAGIC_KEY = "MdDQkHksRA4Vq%A9fqVPT*Qnn9^dDyn8K";
	private static final long DEFAULT_TOKEN_DURATION = 30L;

	private static final String JWT_ID_USER = "idUser";
	private static final String JWT_AUTHORITIES = "authorities";


	public static String createToken(UserDetailsPC userDetails)
	{
		return createToken(userDetails,now -> new Date(now.getTime() + TimeUnit.MINUTES.toMillis(DEFAULT_TOKEN_DURATION)));
	}

	public static String createToken(UserDetailsPC userDetails, Integer duration) {
		int lduration = (duration!=null)?duration:1;
		return createToken(userDetails,now -> new Date(now.getTime() + TimeUnit.DAYS.toMillis(lduration)));
	}

	private static String createToken(UserDetailsPC userDetails, DurationSetter durationSetter) {
		Date now = new Date();
		Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
		claims.put(JWT_ID_USER, userDetails.getId());
			claims.put(JWT_AUTHORITIES, userDetails.getAuthoritiesStr());


		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date())
				.setExpiration(durationSetter.getExpirationDate(now))
				.signWith(SignatureAlgorithm.HS512, MAGIC_KEY)
				.compact();
	}

	public static UserDetails validateToken(String token)
	{

		DefaultClaims claims;
		try {
			claims = (DefaultClaims) Jwts.parser().setSigningKey(MAGIC_KEY).parse(token).getBody();
		}
		catch (ExpiredJwtException ex) {
			throw new SessionAuthenticationException("exception.token_expired_date_error");
		}
		catch (Exception ex) {
			throw new SessionAuthenticationException("exception.token_corrupted");
		}
		Date expiredDate = claims.getExpiration();
		if (expiredDate == null) {
			throw new SessionAuthenticationException("exception.token_invalid");
		}
		if (!expiredDate.after(new Date())) {
			throw new SessionAuthenticationException("exception.token_expired_date_error");
		}
		String userName = claims.getSubject();
		Long id = claims.get(JWT_ID_USER, Long.class);
		if (id == null) {
			throw new SessionAuthenticationException("exception.token_invalid");
		}
		Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get(JWT_AUTHORITIES));

		return new UserDetailsPC(userName,id,authorities);
	}

}
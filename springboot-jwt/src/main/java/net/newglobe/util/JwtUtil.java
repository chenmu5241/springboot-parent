package net.newglobe.util;

import java.security.Key;
import java.util.Date;

import com.alibaba.fastjson.JSON;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {
	private static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	public static String createJwt() {
		JwtBuilder builder = Jwts.builder();
//		String jws = builder11.setSubject("Joe").signWith(key).compact();
		String jws = builder
					.setHeaderParam("type", "jwt")
					.setSubject("Joezz")
					.claim("hello", "word")
					.claim("吕布", "貂蝉")
					.setId("用户id")
					.signWith(key)
					.setIssuedAt(new Date())
					.setExpiration(new Date(new Date().getTime()+1000*20))//过期时间20秒
					.compact();
		return jws;
	}
	
	
	public  static boolean checkJwt(String jwt) {
		Jwts.builder().setHeaderParam("111", "2");
		return false;
	}
	
	

	public static void main(String[] args) {
		String jwt = createJwt();
		//jwt = "eyJhbGciOiJub25lIn0.eyJoZWxsbyI6IndvcmQiLCLlkJXluIMiOiLosoLonYkiLCJqdGkiOiIxMjMiLCJpYXQiOjE1NTk4MTEwOTMsImV4cCI6MTU1OTgxMTExM30.";
		JwtParser parser = Jwts.parser().setSigningKey(key);
		Jwt parse = parser.parse(jwt);
		System.out.println(jwt);
		Claims claims = (Claims)parse.getBody();
		Header header = parse.getHeader();
		System.out.println("payload："+JSON.toJSONString(claims));
		System.out.println("header："+JSON.toJSONString(header));
		System.out.println("ContentType："+header.getContentType());
		System.out.println("CompressionAlgorithm："+header.getCompressionAlgorithm());
		System.out.println("subject："+claims.getSubject());
		System.out.println("alg："+header.get("alg"));
	}
}

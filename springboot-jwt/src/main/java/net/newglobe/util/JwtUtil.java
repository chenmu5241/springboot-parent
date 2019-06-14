package net.newglobe.util;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import net.newglobe.app.model.vo.DataResult;

public class JwtUtil {
	private static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	public static String createJwt(Map<String, Object> sub) {
		JwtBuilder builder = Jwts.builder();
		String jws = builder.setHeaderParam("type", "jwt")
				.setSubject(JSON.toJSONString(sub))
				.setId("10000")
				.signWith(key).setIssuedAt(new Date()).compact();
		// .setExpiration(new Date(new Date().getTime()+1000*20))//过期时间20秒
		return jws;
	}

	/**
	 * 验证JWT
	 * 
	 * @param jwtStr
	 * @return
	 */
	public static DataResult<Claims> getClaims(String jwtStr) {
		DataResult<Claims> checkResult = new DataResult<Claims>();
		Claims claims = null;
		try {
			claims = parseJWT(jwtStr);
			String userStr = claims.get("sub", String.class);
			Map<String, Object> parseObject = JSON.parseObject(userStr, Map.class);
			System.out.println(claims.getId());
			System.out.println(JSON.toJSONString(parseObject));
			checkResult.setSuccess(true);
			checkResult.setData(claims);
		} catch (ExpiredJwtException e) {
			checkResult.setMessage("token过期");
			checkResult.setSuccess(false);
		} catch (SignatureException e) {
			checkResult.setMessage("签名不正确");
			checkResult.setSuccess(false);
		} catch (Exception e) {
			checkResult.setMessage("token验证失败");
			checkResult.setSuccess(false);
		}
		return checkResult;
	}

	/**
	 * 
	 * 解析JWT字符串
	 * 
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	public static Claims parseJWT(String jwtStr) throws Exception {
		JwtParser parser = Jwts.parser().setSigningKey(key);
		Jwt jwt = parser.parse(jwtStr);
		return (Claims) jwt.getBody();
	}

	public static void main(String[] args) throws Exception {
		Map<String, Object> sub = new HashMap<String, Object>();
		sub.put("name", "张三");
		sub.put("age", 13);
		String jwt1 = createJwt(sub);
		getClaims(jwt1);
		
//		System.out.println(jwt1);
//		System.out.println(parseJWT(jwt1));

	}
}

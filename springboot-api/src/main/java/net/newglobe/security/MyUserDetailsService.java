package net.newglobe.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsService implements UserDetailsService{
	public Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info(username);
		if(!"root".equals(username)) {
			throw new UsernameNotFoundException("用户不存在");
		}
		//根据用户名查找用户信息
		User user = new User(username,passwordEncoder.encode("root"), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
		return user;
	}

}

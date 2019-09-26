package net.newglobe.security;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import net.newglobe.app.model.SysAccount;
import net.newglobe.app.service.SysAccountService;

@Component
public class MyUserDetailsService implements UserDetailsService{
	public Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private SysAccountService sysAccountService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SysAccount account = new SysAccount();
		account.setStatus(1);
		account.setUsername(username);
		SysAccount sysUser = sysAccountService.selectOne(account);
		if(sysUser==null) {
			throw new UsernameNotFoundException("用户不存在");
		}
		
		SysAccount user1 = new SysAccount();
		user1.setId(account.getId());
		user1.setUsername(username);
		user1.setPassword(passwordEncoder.encode("root"));
		user1.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList("read,ROLE_ADMIN"));
		
		//根据用户名查找用户信息
//		User user = new User(username,passwordEncoder.encode("root"), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
		return user1;
	}

}

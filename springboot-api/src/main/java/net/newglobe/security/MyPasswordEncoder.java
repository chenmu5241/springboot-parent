package net.newglobe.security;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by ASUS on 2018/6/30
 *
 * @Authod Grey Wolf
 */
@Component
public class MyPasswordEncoder implements PasswordEncoder {
	//数据库密码
    @Override
    public String encode(CharSequence charSequence) {
    	System.out.println(charSequence);
        return charSequence.toString();
    }

    /**
     * charSequence 用户输入的密码
     * s 数据库密码
     */
    @Override
    public boolean matches(CharSequence formPssword, String dataSourcePassword) {
        return dataSourcePassword.equals(formPssword.toString());
    }
}

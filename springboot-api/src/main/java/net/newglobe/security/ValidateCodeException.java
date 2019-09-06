package net.newglobe.security;
import org.springframework.security.core.AuthenticationException;

// AuthenticationException 是 spring security 身份验证过程中异常的基类  
public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = -7932793974645209799L;

    public ValidateCodeException(String msg) {
        super(msg);

    }

}

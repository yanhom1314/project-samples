package demo.security;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationCaptchaException extends AuthenticationException {
    public AuthenticationCaptchaException(String msg) {
        super(msg);
    }
}

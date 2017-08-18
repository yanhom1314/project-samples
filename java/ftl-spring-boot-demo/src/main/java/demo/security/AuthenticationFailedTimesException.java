package demo.security;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationFailedTimesException extends AuthenticationException {
    public AuthenticationFailedTimesException(String msg) {
        super(msg);
    }
}

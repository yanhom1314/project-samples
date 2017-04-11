package demo.spring;

import demo.entity.User;
import demo.repo.UserRepository;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyShiroRealm extends AuthorizingRealm {
    @Autowired
    private UserRepository userRepository;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //设置权限
        Object un = getAvailablePrincipal(principals);
        //通过un查找Roles
        //userRepository.findByUsername(un.toString).roles.map(_.roleName).foreach(info.addRole(_))

        info.addRole("ROLE_USER");
        info.addRole("ROLE_MANAGER");
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = token.getPrincipal().toString();
        String password = new String((byte[]) token.getCredentials());
        //User user = userRepository.findByUsername(username);
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        if (user != null && user.getPassword().equalsIgnoreCase(password)) return new SimpleAuthenticationInfo(username, password, username);
        else throw new IncorrectCredentialsException("密码错误！！！");
    }
}

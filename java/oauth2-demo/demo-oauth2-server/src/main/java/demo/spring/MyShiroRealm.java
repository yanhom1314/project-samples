package demo.spring;

import demo.entity.User;
import demo.entity.repo.UserRepository;
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

        info.addRole("guest");
        info.addRole("admin");
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (authenticationToken instanceof UsernamePasswordToken) {
            UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
            String username = token.getUsername();
            String password = new String(token.getPassword());
            User user = userRepository.findByUsername(username);
            System.out.println("username:" + user.getUsername() + " password:" + user.getPassword() + "][" + password +"]" + user.getPassword().equalsIgnoreCase(password));
            if (user != null && user.getPassword().equalsIgnoreCase(password)) return new SimpleAuthenticationInfo(username, password, username);
            else throw new IncorrectCredentialsException("密码错误！！！");
        } else throw new IncorrectCredentialsException("类型错误！！！");
    }
}

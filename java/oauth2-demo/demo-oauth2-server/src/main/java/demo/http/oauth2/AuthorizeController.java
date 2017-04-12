package demo.http.oauth2;

import demo.entity.Client;
import demo.entity.User;
import demo.oauth2.ClientService;
import demo.oauth2.OAuthService;
import demo.oauth2.UserService;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@Controller
@SessionAttributes(value = {"subject"})
public class AuthorizeController {
    @Autowired
    private OAuthService oAuthService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private UserService userService;

    @PostMapping("/confirm")
    public String confirm(HttpServletRequest request) throws OAuthProblemException, OAuthSystemException {
        OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
        Subject subject = SecurityUtils.getSubject();

        if (oAuthService.checkClientId(oauthRequest.getClientId())) {
            //第一次授权
            String username = (String) subject.getPrincipal();
            if (userService.isFirst(username, oauthRequest.getClientId())) {
                Client client = clientService.findByClientId(oauthRequest.getClientId());
                User user = userService.findByUsername(username);
                user.getClients().add(client);
                userService.updateUser(user);
            }
            return "forward:/authorize";
        } else return "login";
    }

    @RequestMapping("/authorize")
    public Object authorize(HttpServletRequest request, Model model) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        //登录
        if (!subject.isAuthenticated()) {
            if (!login(subject, request)) {
                return "forward:/login";
            }
        }
        String username = (String) subject.getPrincipal();
        //授权
        ResponseEntity entity = oauth2(request, username);
        if (entity != null) {
            switch (entity.getStatusCodeValue()) {
                case 201:
                    return "confirm";
                case 302:
                    return entity;
                default:
                    return entity;
            }
        } else {
            model.addAttribute("subject", subject);
            return "home";
        }
    }

    private boolean login(Subject subject, HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) return false;

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
            return true;
        } catch (Exception e) {
            request.setAttribute("error", "登录失败:" + e.getClass().getName());
            return false;
        }
    }

    private ResponseEntity oauth2(HttpServletRequest request, String username) {
        ResponseEntity entity = null;
        try {
            //构建OAuth 授权请求
            OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
            //检查传入的客户端id是否正确
            if (!oAuthService.checkClientId(oauthRequest.getClientId())) {
                OAuthResponse response = OAuthASResponse
                        .errorResponse(HttpStatus.BAD_REQUEST.value())
                        .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                        .buildJSONMessage();
                entity = new ResponseEntity<>(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
            }
            //第一次
            else if (userService.isFirst(username, oauthRequest.getClientId())) {
                entity = new ResponseEntity("", HttpStatus.CREATED);
            } else {
                String authorizationCode = null;
                //responseType目前仅支持CODE，另外还有TOKEN
                String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
                if (responseType.equals(ResponseType.CODE.toString())) {
                    OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
                    authorizationCode = oauthIssuerImpl.authorizationCode();
                    oAuthService.addAuthCode(authorizationCode, username);
                }
                //得到到客户端重定向地址
                String redirectURI = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);
                //进行OAuth响应构建
                OAuthResponse response = OAuthASResponse.authorizationResponse(request, HttpStatus.FOUND.value())
                        .setCode(authorizationCode)
                        .location(redirectURI)
                        .buildQueryMessage();
                //根据OAuthResponse返回ResponseEntity响应
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(new URI(response.getLocationUri()));
                return new ResponseEntity(headers, HttpStatus.valueOf(response.getResponseStatus()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }
}


package demo.controller;

import demo.oauth2.ClientService;
import demo.oauth2.OAuthService;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@RestController
public class Authorize {
    @Autowired
    private OAuthService oAuthService;
    @Autowired
    private ClientService clientService;

    @RequestMapping("/authorize")    
    public Object authorize(HttpServletRequest request, Model model) throws Exception {
        try {
            OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
            if (!oAuthService.checkClientId(oauthRequest.getClientId())) {
                OAuthResponse response = OAuthASResponse
                        .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                        .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                        .setErrorDescription("INVALID_CLIENT_DESCRIPTION")
                        .buildJSONMessage();
                return new ResponseEntity<String>(
                        response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
            }

            Subject subject = SecurityUtils.getSubject();
            if (!subject.isAuthenticated()) {
                if (!login(subject, request)) {
                    model.addAttribute("client",
                            clientService.findByClientId(oauthRequest.getClientId()));
                    return "oauth2login";
                }
            }

            String username = (String) subject.getPrincipal();
            //鐢熸垚鎺堟潈鐮�
            String authorizationCode = null;
            String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
            if (responseType.equals(ResponseType.CODE.toString())) {
                OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
                authorizationCode = oauthIssuerImpl.authorizationCode();
                oAuthService.addAuthCode(authorizationCode, username);
            }
            OAuthASResponse.OAuthAuthorizationResponseBuilder builder =
                    OAuthASResponse.authorizationResponse(request,
                            HttpServletResponse.SC_FOUND);
            builder.setCode(authorizationCode);
            String redirectURI = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);
            final OAuthResponse response = builder.location(redirectURI).buildQueryMessage();
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(new URI(response.getLocationUri()));
            return new ResponseEntity<String>(headers, HttpStatus.valueOf(response.getResponseStatus()));
        } catch (OAuthProblemException e) {
            String redirectUri = "/error/redirect/uri";
            if (OAuthUtils.isEmpty(redirectUri)) {
                return new ResponseEntity<String>(
                        "OAuth callback url needs to be provided by client!!!", HttpStatus.NOT_FOUND);
            }
            final OAuthResponse response =
                    OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND)
                            .error(e).location(redirectUri).buildQueryMessage();
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(new URI(response.getLocationUri()));
            return new ResponseEntity<String>(headers, HttpStatus.valueOf(response.getResponseStatus()));
        }
    }

    private boolean login(Subject subject, HttpServletRequest request) {
        if ("get".equalsIgnoreCase(request.getMethod())) {
            return false;
        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return false;
        }

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
            return true;
        } catch (Exception e) {
            request.setAttribute("error", "鐧诲綍澶辫触:" + e.getClass().getName());
            return false;
        }
    }
}

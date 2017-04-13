package demo.http.oauth2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.entity.User;
import demo.oauth2.OAuthService;
import demo.oauth2.OpenToken;
import demo.oauth2.UserService;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

@RestController
@RequestMapping("/v1/openapi")
public class OpenTokenController {

    @Autowired
    private OAuthService oAuthService;

    @Autowired
    private UserService userService;

    @RequestMapping("/me")
    public HttpEntity userInfo(HttpServletRequest request) throws OAuthSystemException, OAuthProblemException, JsonProcessingException {
        return checkAccessToken(request);
    }


    /**
     * 不校验accessToken
     *
     * @param request
     * @return
     * @throws OAuthSystemException
     * @throws OAuthProblemException
     */
    private HttpEntity nocheckAccessToken(HttpServletRequest request) throws OAuthSystemException, OAuthProblemException, JsonProcessingException {
        //构建OAuth资源请求
        OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);

        //获取Access Token
        String accessToken = oauthRequest.getAccessToken();

        //获取用户名
        String username = oAuthService.getUsernameByAccessToken(accessToken);
        User user = userService.findByUsername(username);
        ObjectMapper mapper = new ObjectMapper();

        return new ResponseEntity<>(mapper.writeValueAsString(user), HttpStatus.OK);
    }

    /**
     * 校验accessToken
     *
     * @param request
     * @return
     * @throws OAuthSystemException
     */
    private HttpEntity checkAccessToken(HttpServletRequest request) throws OAuthSystemException, JsonProcessingException {
        try {
            //构建OAuth资源请求
            OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
            //获取Access Token
            String accessToken = oauthRequest.getAccessToken();
            //验证Access Token
            if (!oAuthService.checkAccessToken(accessToken)) {
                // 如果不存在/过期了，返回未验证错误，需重新验证
                OAuthResponse oauthResponse = OAuthRSResponse
                        .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                        .setRealm("RESOURCE_SERVER_NAME")
                        .setError(OAuthError.ResourceResponse.INVALID_TOKEN)
                        .buildJSONMessage();
                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.add("Content-Type", "application/json; charset=utf-8");
                return new ResponseEntity<>(oauthResponse.getBody(), responseHeaders, HttpStatus.UNAUTHORIZED);
            }
            Base64.Encoder encoder = Base64.getEncoder();
            //获取用户名
            String username = oAuthService.getUsernameByAccessToken(accessToken);
            User user = userService.findByUsername(username);
            OpenToken token = new OpenToken();
            token.setOpenId(encoder.encodeToString((user.getId() + "_" + username.hashCode()).getBytes(StandardCharsets.UTF_8)));
            token.setRoles(Arrays.asList("guest", "admin", "hello"));
            ObjectMapper mapper = new ObjectMapper();
            return new ResponseEntity<>(mapper.writeValueAsString(token), HttpStatus.OK);
        } catch (OAuthProblemException e) {
            //检查是否设置了错误码
            String errorCode = e.getError();
            if (OAuthUtils.isEmpty(errorCode)) {
                OAuthResponse oauthResponse = OAuthRSResponse
                        .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                        .setRealm("RESOURCE_SERVER_NAME")
                        .buildHeaderMessage();
                HttpHeaders headers = new HttpHeaders();
                headers.add(OAuth.HeaderType.WWW_AUTHENTICATE, oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
                return new ResponseEntity<>(headers, HttpStatus.UNAUTHORIZED);
            }
            OAuthResponse oauthResponse = OAuthRSResponse
                    .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                    .setRealm("RESOURCE_SERVER_NAME")
                    .setError(e.getError())
                    .setErrorDescription(e.getDescription())
                    .setErrorUri(e.getUri())
                    .buildHeaderMessage();
            HttpHeaders headers = new HttpHeaders();
            headers.add(OAuth.HeaderType.WWW_AUTHENTICATE, oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
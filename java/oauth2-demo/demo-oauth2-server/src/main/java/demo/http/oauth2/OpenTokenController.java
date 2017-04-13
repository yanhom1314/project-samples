package demo.http.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.entity.User;
import demo.oauth2.OAuthService;
import demo.oauth2.OpenToken;
import demo.oauth2.UserService;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
    public HttpEntity me(HttpServletRequest request) {
        return checkAccessToken(request);
    }

    private HttpEntity checkAccessToken(HttpServletRequest request) {
        HttpEntity entity;
        try {
            //构建OAuth资源请求
            OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
            //获取Access Token
            String accessToken = oauthRequest.getAccessToken();
            //验证Access Token
            if (!oAuthService.checkAccessToken(accessToken)) {
                // 如果不存在/过期了，返回未验证错误，需重新验证
                OAuthResponse oauthResponse = OAuthRSResponse
                        .errorResponse(HttpStatus.UNAUTHORIZED.value())
                        .setError(OAuthError.ResourceResponse.INVALID_TOKEN)
                        .buildJSONMessage();
                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
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
            entity = new ResponseEntity<>(mapper.writeValueAsString(token), HttpStatus.OK);
        } catch (Exception e) {
            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }
}
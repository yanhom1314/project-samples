package demo;

import demo.config.MyConfig;
import demo.service.CaptchaFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(MyConfig.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CaptchaFilter captchaFilter;
    @Autowired
    private MyConfig mc;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/layout/**", "/captcha");
        web.ignoring().antMatchers("/index", "/greeting", "/hello", "/ws", "/ws/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/index", "/greeting", "/login", "/error/*").permitAll()
                .anyRequest().authenticated();
        //http.addFilterAfter(csrfHeaderFilter, CsrfFilter.class).csrf().csrfTokenRepository(csrfTokenRepository());
        http.formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
                .permitAll()
                .and()
                .rememberMe().key("greatbit@2017").tokenValiditySeconds(mc.getExpired())
                .and().addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
        auth.userDetailsService(userDetailsService);
    }
}

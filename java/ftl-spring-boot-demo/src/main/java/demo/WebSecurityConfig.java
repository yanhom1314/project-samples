package demo;

import demo.config.MyConfig;
import demo.security.CustomUsernamePasswordAuthenticationFilter;
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

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(MyConfig.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CaptchaFilter captchaFilter;
    @Autowired
    private MyConfig mc;
    @Autowired
    private CustomUsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter;
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
        http.headers()
                .contentTypeOptions()
                .and()
                .xssProtection()
                .and()
                .cacheControl();
        
        http.formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/admin")
                .failureUrl("/login?error")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .and()
                .rememberMe().key("greatbit@2017").tokenValiditySeconds(600000)
                .and()
                .addFilterAt(usernamePasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
        auth.userDetailsService(userDetailsService);
    }
}

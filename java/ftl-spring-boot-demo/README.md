## Spring Boot Demo Project

## i18n
+ 使用`java -jar executable=x.y.z.jar`运行时需要制定MesaageSource中的baseName，否则无法获取`key`内容

        @Bean
        public MessageSource messageSource() {
            final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
            messageSource.setBasename("classpath:/messages");
            messageSource.setFallbackToSystemLocale(false);
            messageSource.setCacheSeconds(0);
        
            return messageSource;
        }

## CSRF
+ 创建CsrfHeaderFilter：

        @Component
        @Order(Ordered.HIGHEST_PRECEDENCE)
        public class CsrfHeaderFilter implements Filter {
            @Override
            public void init(FilterConfig filterConfig) throws ServletException {
        
            }
        
            @Override
            public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                HttpServletRequest request = (HttpServletRequest) servletRequest;
        
                response.setHeader("Access-Control-Allow-Origin", "*");
                response.setHeader("Access-Control-Allow-Methods", "POST, PUT, PATCH, GET, OPTIONS, DELETE");
                response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
                response.setHeader("Access-Control-Max-Age", "3600");
                //response.setHeader("Allow", "POST, PUT, PATCH, GET, OPTIONS, DELETE");
                if (!request.getMethod().equals("OPTIONS")) {
                    try {
                        filterChain.doFilter(servletRequest, servletResponse);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        
            @Override
            public void destroy() {
        
            }
        }

+ WebSecurityConfig

        @Configuration
        @EnableWebMvcSecurity
        public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
            @Override
            protected void configure(HttpSecurity http) throws Exception {
                http.addFilterAfter(csrfHeaderFilter, CsrfFilter.class);        
            }
            ...
        }

## Captcha
+ 创建CaptchaFilter

        @Component
        @Order(Ordered.HIGHEST_PRECEDENCE)
        public class CaptchaFilter implements Filter {
            public static final Logger logger = LoggerFactory.getLogger(CaptchaFilter.class);
            private String captchaParam = "j_captcha";
            private String loginUrl = "/login";
        
            @Override
            public void init(FilterConfig filterConfig) throws ServletException {
        
            }
        
            @Override
            public void destroy() {
                this.captchaParam = null;
                this.loginUrl = null;
            }
        
            @Override
            public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
                try {
                    HttpServletRequest request = (HttpServletRequest) req;
                    HttpServletResponse response = (HttpServletResponse) res;
                    HttpSession session = request.getSession(false);
                    if (session != null && request.getRequestURI().endsWith(loginUrl) && "POST".equalsIgnoreCase(request.getMethod())) {
                        if (session.getAttribute(CaptchaController.SESSION_LOGIN_CAPTCHA_KEY) != null && request.getParameterMap().containsKey(captchaParam)) {
                            String sid = (String) session.getAttribute(CaptchaController.SESSION_LOGIN_CAPTCHA_KEY);
                            String cid = request.getParameter(captchaParam);
                            if (!sid.equalsIgnoreCase(cid)) {
                                response.sendRedirect(request.getContextPath() + loginUrl + "?captcha");
                                return;
                            }
                        }
                    }
                    chain.doFilter(req, res);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
+ WebSecurityConfig       
       
        @Configuration
        @EnableWebMvcSecurity
        public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        
            @Override
            protected void configure(HttpSecurity http) throws Exception {      
                http.formLogin()
                        .loginPage("/login")
                        .permitAll()
                        .and()
                        .logout()
                        .permitAll()
                        .and().addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class).authorizeRequests();
            }
            ...
        }

        
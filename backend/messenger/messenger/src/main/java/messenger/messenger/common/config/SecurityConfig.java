package messenger.messenger.common.config;

import lombok.RequiredArgsConstructor;
import messenger.messenger.auth.oauth.application.service.CustomOAuth2UserService;
import messenger.messenger.auth.oauth.application.service.CustomOidcUserService;
import messenger.messenger.auth.token.domain.TokenProviderImpl;
import messenger.messenger.auth.token.infra.repository.TokenRepositoryImpl;
import messenger.messenger.auth.token.presentation.filter.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOidcUserService customOidcUserService;
    private final CorsFilter corsFilter;
    private final TokenProviderImpl tokenProviderImpl;
    private final TokenRepositoryImpl tokenRepository;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers(
                "/static/**", "/static/js/**", "/static/images/**",
                "/static/css/**", "/static/scss/**",
                "/h2-console/**", "/favicon.ico", "/error"
        );
    }


    @Bean
    SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http,
                                                  TokenProviderImpl tokenProviderImpl) throws Exception {

        http
                .httpBasic().disable()
                .csrf().disable()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .headers()
                .xssProtection()
                .and()
                .contentSecurityPolicy("script-src 'self'")
                .and()
                .frameOptions()
                .sameOrigin()

                .and()
                .authorizeRequests((requests) -> requests
                .antMatchers(
                        "/resources/**",
                        "/api/v1/register",
                        "/api/v1/login",
                        "/"
                )
                .permitAll()
                .anyRequest().authenticated())

                .formLogin().disable()

                .oauth2Login(oauth2 -> oauth2.userInfoEndpoint(
                        userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOAuth2UserService)
                                .oidcUserService(customOidcUserService)))

                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);


        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(tokenProviderImpl, tokenRepository);
    }

}


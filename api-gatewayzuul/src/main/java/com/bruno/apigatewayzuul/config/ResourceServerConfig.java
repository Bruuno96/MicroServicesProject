package com.bruno.apigatewayzuul.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private JwtTokenStore jwtTokenStore;

    private static final String[] PUBLIC = {"/hr-oauth/oauth/token"};

    private static final String[] OPERATOR = {"/hr-worker/**"};

    private static final String[] ADMIN = {"/hr-payroll/**","/hr-user/**","/actuator","/hr-worker/actuator","/hr-oauth/actuator"};

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(jwtTokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(PUBLIC).permitAll() // TODOS PODEM ACESSAR ESSA ROTA
                .antMatchers(HttpMethod.GET, OPERATOR).hasAnyRole("OPERATOR,ADMIN") // ROTA DISPONÍVEL APENAS PARA MÉTODO GET PARA OPERADOR E ADMIN
                .antMatchers(ADMIN).hasRole("ADMIN")// ROTAS QUE SÓ PODEM SER ACESSADAS PELO ADMIN
                .anyRequest().authenticated(); // QUALQUER OUTRA ROTA DEVE EXIGIR QUE O USUÁRIO ESTEJA AUTENTICADO
        http.cors().configurationSource(corsConfigurationSource());
        }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("POST","GET","PUT","DELETE","PATCH"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization","Content-type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
    }

    public FilterRegistrationBean<CorsFilter>  corsFilter(){
        FilterRegistrationBean<CorsFilter>  bean
                = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}

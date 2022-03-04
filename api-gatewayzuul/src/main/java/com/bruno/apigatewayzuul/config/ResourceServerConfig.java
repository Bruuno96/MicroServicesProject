package com.bruno.apigatewayzuul.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

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
    }
}

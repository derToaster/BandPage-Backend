package com.example.bandproject.demo.configurations;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;


@Configuration
@EnableResourceServer

public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**");
//            }
//        };
//    }





    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
        .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/oauth/token").permitAll()
                .antMatchers("/mail/resetpassword", "/api/v1/users/approve",
                        "/api/v1/users/apoproved").hasRole("ADMIN")
                .antMatchers("/api/v1/bands/").permitAll()
                .antMatchers("/api/v1/users/add").permitAll()
                .antMatchers("/api/v1/users/user").hasRole("USER")
                .antMatchers("/api/v1/users/admin").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().formLogin().permitAll();
        http.cors();

    }




}

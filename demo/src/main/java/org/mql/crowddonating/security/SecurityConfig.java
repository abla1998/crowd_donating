package org.mql.crowddonating.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder())
                .rolePrefix("ROLE_");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeRequests()
                .antMatchers("/", "/cases", "/login", "/logout", "/register", "/confirm", "/events", "/files/association/", "/projects").permitAll()

                .antMatchers(HttpMethod.DELETE, "/cases/{^[\\d]$}", "/cases/files/{^[\\d]$}").hasRole("ASSOCIATION")
                .antMatchers(HttpMethod.PUT, "/cases").hasRole("ASSOCIATION")
                .antMatchers(HttpMethod.POST, "/cases", "/projects").hasRole("ASSOCIATION")
                .antMatchers(HttpMethod.GET, "/cases/add", "/cases/update", "/events/**", "/projects/add").hasRole("ASSOCIATION")

                .antMatchers("/cards/**", "/cases/{^[\\c]$}/donate", "/donations/{^[\\d]$}", "/projects/{^[\\c]$}/donate").hasRole("DONATOR")

                .antMatchers("/admin/**").hasRole("ADMIN")

                .and().formLogin().loginPage("/login")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").and()
                .exceptionHandling().accessDeniedPage("/forbidden");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

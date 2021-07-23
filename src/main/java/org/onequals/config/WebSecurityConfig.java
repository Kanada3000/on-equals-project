package org.onequals.config;

import org.onequals.domain.User;
import org.onequals.repo.UserRepo;
import org.onequals.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/resume/list").hasAnyAuthority("EMPLOYER", "ROLE_ADMIN")
                .antMatchers(HttpMethod.GET, "/seeker").hasAnyAuthority("USER")
                .antMatchers(HttpMethod.POST, "/seeker").hasAnyAuthority("USER")
                .antMatchers(HttpMethod.GET, "/employer").hasAnyAuthority("USER")
                .antMatchers(HttpMethod.POST, "/employer").hasAnyAuthority("USER")
                .antMatchers(HttpMethod.GET, "/resume/new").hasAnyAuthority("SEEKER", "ROLE_ADMIN")
                .antMatchers(HttpMethod.POST, "/resume/new").hasAnyAuthority("SEEKER", "ROLE_ADMIN")
                .antMatchers(HttpMethod.GET, "/vacancy/new").hasAnyAuthority("EMPLOYER", "ROLE_ADMIN")
                .antMatchers(HttpMethod.POST, "/vacancy/new").hasAnyAuthority("EMPLOYER", "ROLE_ADMIN")
                .antMatchers(HttpMethod.POST, "/admin").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.GET, "/admin").hasAuthority("ROLE_ADMIN")
                .anyRequest().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .permitAll()
                .and()
                .oauth2Login()
                .loginPage("/login/oauth2")
                .defaultSuccessUrl("/loginSuccess")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .permitAll()
                .and().csrf().ignoringAntMatchers("/admin/journals/**");
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/css/**",
                "/images/**",
                "/fonts/**",
                "/script/**",
                "/templates/**",
                "/ckeditor/**",
                "/ckfinder/**"
        );
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder);

        auth.inMemoryAuthentication().withUser("adminLog").password(passwordEncoder.encode("adminPass")).roles("ADMIN");
    }
}
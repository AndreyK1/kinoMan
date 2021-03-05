package ru.chuhan.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import ru.chuhan.demo.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        //РАСКОМЕНТИТЬ ДЛЯ ОТКЛЮЧЕНИЯ ВСЕЙ ЗАЩИТЫ
//        httpSecurity
//                .csrf()
//                .disable()
//
//                .headers().disable()
//                .authorizeRequests()
//                .antMatchers("/**").permitAll();


        httpSecurity
                .csrf()
                .disable()

                .headers().disable()        //TODO delete this is for h2-console
                .authorizeRequests()

                .antMatchers("/message/sendToGroup").permitAll()
                .antMatchers("/telbot/sendMessage").permitAll()
                .antMatchers("/telbot/whook").permitAll()


//                .antMatchers("/show").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/loginf").permitAll()
                .antMatchers("/tree/**").permitAll()
//                .antMatchers("/tree/").permitAll()
                .antMatchers("/theme/*").permitAll()
                .antMatchers("/registr").permitAll()
                .antMatchers("/getuserinfo").permitAll()
                .antMatchers("/js/**").permitAll()

                //Доступ только для не зарегистрированных пользователей
                .antMatchers("/registration").not().fullyAuthenticated()
                //Доступ только для пользователей с ролью Администратор
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/news").hasRole("USER")
                //Доступ разрешен всем пользователей
                .antMatchers("/", "/resources/**").permitAll()
                //Все остальные страницы требуют аутентификации
                .anyRequest().authenticated()
                .and()
                //Настройка для входа в систему
           .formLogin()
                .loginPage("/login")
                //Перенарпавление на главную страницу после успешного входа
//                .loginProcessingUrl("/show")   //SuccessUrl("/");
//                .defaultSuccessUrl("/show", true)
                .defaultSuccessUrl("/", true)
                .permitAll()
                .and()
           .logout()
                .permitAll()
//                .logoutSuccessUrl("/show");
                .logoutSuccessUrl("/");
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }



//    @Override
//    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//        //TODO            https://mkyong.com/spring-boot/spring-security-there-is-no-passwordencoder-mapped-for-the-id
//        // https://stackoverflow.com/questions/49654143/spring-security-5-there-is-no-passwordencoder-mapped-for-the-id-null
//        auth.inMemoryAuthentication()
//                .withUser("user1").password("{noop}user1Pass").roles("USER");
//
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
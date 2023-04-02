package assignment.chana.distributedsystem.config

import assignment.chana.distributedsystem.auth.AuthService
import assignment.chana.distributedsystem.auth.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userRepository: UserRepository
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http.authorizeHttpRequests {
            it.requestMatchers("/files")
                .authenticated()
                .anyRequest()
                .permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/files")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(false) // for test
                .and()
                .exceptionHandling()
        }
        http.userDetailsService(authService())
        return http.build()
    }

    @Bean
    fun authService() = AuthService(userRepository, passwordEncoder())
}
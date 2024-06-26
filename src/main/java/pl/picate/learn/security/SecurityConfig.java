package pl.picate.learn.security;

import java.util.ArrayList;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.vaadin.flow.spring.security.VaadinWebSecurity;

import net.bytebuddy.asm.Advice.Return;
import pl.picate.learn.login.user.BusinessUserService;
import pl.picate.learn.security.authentication.UserAuthenticationManager;
import pl.picate.learn.security.authentication.UserAuthenticationProvider;
import pl.picate.learn.views.LoginView;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class SecurityConfig extends VaadinWebSecurity {
	
	@Bean
	public UserAuthenticationManager userAuthManger() {
		ArrayList<AuthenticationProvider> listProvider = new ArrayList<>();
		listProvider.add(new UserAuthenticationProvider());
		UserAuthenticationManager manager = new UserAuthenticationManager(listProvider);
		return manager;
	}

	@Bean
	private static final PasswordEncoder passwordEncoderSecurity() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Authentication authen =
		// SecurityContextHolder.getContext().getAuthentication();
		http.authorizeHttpRequests(auth -> {
			auth.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/images/*.png")).permitAll();
			auth.requestMatchers((RequestMatcher) req -> {
				// req.getRequestURL().toString().split("/");
				AntPathRequestMatcher.antMatcher("/{name}/**");
				return false;
			}).access(new PolicyUserAuthorizationManager("#name == authentication.name"));
		});
		super.configure(http);
		setLoginView(http, LoginView.class);

	}

}
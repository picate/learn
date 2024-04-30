package pl.picate.learn.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.SpringComponent;

import pl.picate.learn.login.user.UserService;

@Route("registration") 
@PageTitle("Registration")
@AnonymousAllowed
public class RegistrationView extends VerticalLayout {

	public static final String VIEW_NAME = "registration";
	
	private RegistrationForm regis;
	
	
	private final UserService userService;
	private final BCryptPasswordEncoder passwordEncoder;
	
	public RegistrationView(UserService userService, BCryptPasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.setId("regis");
		regis = new RegistrationForm(userService, passwordEncoder);
		addViews();
	}

	private void addViews() {
		this.add(regis);
	}
}

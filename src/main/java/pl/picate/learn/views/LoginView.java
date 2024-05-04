package pl.picate.learn.views;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.AbstractLogin.LoginEvent;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import pl.picate.learn.security.authentication.UserAuthenticationProvider;

@Route("login") 
@PageTitle("Login In")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

	private final LoginForm login = new LoginForm();

	public LoginView(){
		this.setId("main");
		addClassName("login-view");
		setSizeFull();
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);
		setSignUp();
		setHComp();
		login.setAction("login");

	}

	private void setSignUp() {
		Button signUp = new Button("Sign-up");
		signUp.addSingleClickListener((b)->getUI().ifPresent(ui->ui.navigate("registration")));
		signUp.setId("sign-up");
		signUp.setClassName("sign");
		add(signUp);
		
	}

	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		// inform the user about an authentication error
		if(beforeEnterEvent.getLocation()  
        .getQueryParameters()
        .getParameters()
        .containsKey("error")) {
            login.setError(true);
        }
	}
	
	public void setHComp(){
		H1 pi = new H1("Picate");
		pi.setId("picate");
		pi.setClassName("sign-in-h");
		
		add(pi, login);
		this.login.addLoginListener((e)->auth(e));
	}

	private void auth(LoginEvent e) {	
		UI.getCurrent().navigateToClient("/"+e.getUsername());
	}
}
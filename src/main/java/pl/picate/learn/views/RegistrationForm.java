package pl.picate.learn.views;

import java.time.temporal.ChronoField;
import java.time.temporal.JulianFields;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

import lombok.Getter;
import lombok.Setter;
import pl.picate.learn.login.user.User;
import pl.picate.learn.login.user.UserService;

@Getter
@Setter
@Controller
@ComponentScan
public class RegistrationForm  extends FormLayout{

	private TextField firstName, lastName, userName, email;
	private DatePicker birth;
	private PasswordField password, conformPassword;
	private Button regisButton;
	private ArrayList<HorizontalLayout> horizontalL;
	private NativeLabel registration;
	private H1 pi;
	
	private final UserService userService;
	
	
	private BCryptPasswordEncoder passwordEncoder;
	
	public RegistrationForm(UserService userService, BCryptPasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder=passwordEncoder;
		this.getClassNames().add("regis-form");
		this.getElement().getStyle().set("max-width", "100vw");
		childSet();
		 // Max width of the Form
       setMaxWidth("300px");
       setColspan(pi, 2);
       
       // Allow the form layout to be responsive.
       // On device widths 0-490px we have one column.
       // Otherwise, we have two columns.
       setResponsiveSteps(
               new ResponsiveStep("0", 1, ResponsiveStep.LabelsPosition.TOP),
               new ResponsiveStep("750px", 2, ResponsiveStep.LabelsPosition.TOP));
	}

	private void childSet() {
		horizontalL = new ArrayList<>();
		registration = new NativeLabel("Registrasion");
		registration.setId("regis-label");
		HorizontalLayout regisHL = new HorizontalLayout(registration);
		regisHL.setId("regis-hl-label");
		firstName = new TextField();
		firstName.setLabel("First name");
		firstName.setClassName("data-regis");
		firstName.setId("first-name");
		lastName= new TextField();
		lastName.setLabel("Last name");
		lastName.setClassName("data-regis");
		lastName.setId("last-name");
		horizontalL.add(new HorizontalLayout(firstName,lastName));
		email = new TextField();
		email.setLabel("Email");
		email.setClassName("data-regis");
		email.setId("email");
		birth = new DatePicker();
		birth.setLabel("Date of birth");
		birth.setClassName("data-regis");
		birth.setId("birth");
		horizontalL.add(new HorizontalLayout(email,birth));
		userName= new TextField();
		userName.setLabel("User name");
		userName.setClassName("data-regis");
		userName.setId("user-name");
		horizontalL.add(new HorizontalLayout(userName));
		password = new PasswordField();
		password.setLabel("Password");
		password.setClassName("data-regis");
		password.setId("password");
		conformPassword = new PasswordField();
		conformPassword.setLabel("Repeat password");
		conformPassword.setClassName("data-regis");
		conformPassword.setId("conform-password");
		horizontalL.add(new HorizontalLayout(password,conformPassword));
		regisButton = new Button("Sing-up");
		regisButton.setId("regis-button");
		regisButton.setClassName("sign");
		horizontalL.add(new HorizontalLayout(regisButton));
		pi = new H1("Picate");
		pi.setId("picate");
		horizontalL.forEach(e->{setColspan(e, 2);
				e.setClassName("layout-data-regis");
			});
		regisButton.addSingleClickListener((e)->regisAction(e));
		add(pi,regisHL,horizontalL.get(0),horizontalL.get(1),horizontalL.get(2),horizontalL.get(3),horizontalL.get(4));
		
	}
	
	private void regisAction(ClickEvent<Button> e) {
		User user = new User(userService.findByUserID()+1, firstName.getValue(),lastName.getValue(),email.getValue(),java.sql.Date.valueOf(birth.getValue()),userName.getValue(),passwordEncoder.encode(password.getValue()));
		userService.getUserRespository().save(user);
	}

}

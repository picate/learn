package pl.picate.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;

/**
 * The entry point of the Spring Boot application.
 */
@Theme("my-theme")
@SpringBootApplication
@ComponentScan(basePackages = {"pl.picate.learn.login.user","pl.picate.learn.security","pl.picate.learn.login.token","pl.picate.learn.login","pl.picate.learn.configuration"})
@EntityScan(basePackages = {"pl.picate.learn.login.user","pl.picate.learn.login.token"})
public class Application extends SpringBootServletInitializer implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

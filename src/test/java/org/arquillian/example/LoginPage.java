package org.arquillian.example;

import static org.jboss.arquillian.graphene.Graphene.guardHttp;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Location("login.xhtml")
public class LoginPage {

	@FindBy(id = "userName")          // 1. injects an element by default location strategy ("idOrName")
	private WebElement userName;

	@FindBy(id = "password")
	private WebElement password;

	@FindBy(id = "login")
	private WebElement loginButton;
	
    @Drone
    private WebDriver browser;
	
//    @FindBy(css = "input[type=text]")
//    private WebElement userName;
//
//    @FindBy(css = "input[type=password]")
//    private WebElement password;
//
//    @FindBy(css = "input[type=submit]")
//    private WebElement loginButton;
    
    public void login(String userName, String password) {
        this.userName.sendKeys(userName);
        this.password.sendKeys(password);
        guardHttp(loginButton).click();
    }
}
package org.arquillian.example;

import static org.jboss.arquillian.graphene.Graphene.guardHttp;

import org.jboss.arquillian.graphene.fragment.Root;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginForm {

    @Root
    private WebElement loginForm;

    @FindBy(css = "input[type=text]")
    private WebElement userName;

    @FindBy(css = "input[type=password]")
    private WebElement password;

    @FindBy(css = "input[type=submit]")
    private WebElement loginButton;
    
    public void login(String userName, String password) {
        this.userName.sendKeys(userName);
        this.password.sendKeys(password);
        guardHttp(loginButton).click();
    }
}
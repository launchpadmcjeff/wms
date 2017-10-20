package org.arquillian.example;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.support.FindBy;

@Location("login.xhtml")
public class LoginPage2 {

    @FindBy(id = "loginForm")
    private LoginForm loginForm;        // locates the root of a page fragment on a particular page
    
    public LoginForm getLoginForm() {   // we can either manipulate with the login form or just expose it
       return loginForm;
    }
}
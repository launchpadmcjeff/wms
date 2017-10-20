package org.arquillian.example;

import java.io.File;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import static org.jboss.arquillian.graphene.Graphene.guardHttp;
import static org.jboss.arquillian.graphene.Graphene.waitAjax;
import static org.jboss.arquillian.graphene.Graphene.guardAjax;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.jboss.arquillian.graphene.Graphene.waitModel;

@RunWith(Arquillian.class)
public class LoginScreenGrapheneTest {
	private static final String WEBAPP_SRC = "src/main/webapp";

	@Deployment(testable = false)
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "login.war")
				.addClasses(Credentials.class, User.class, LoginController.class)
				.addAsWebResource(new File(WEBAPP_SRC, "login.xhtml"))
				.addAsWebResource(new File(WEBAPP_SRC, "home.xhtml"))
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource(new StringAsset("<faces-config version=\"2.0\"/>"), "faces-config.xml");
	}

	@Drone
	private WebDriver browser;
	
	@ArquillianResource
    private URL deploymentUrl;

	@FindBy(id = "userName")          // 1. injects an element by default location strategy ("idOrName")
	private WebElement userName;

	@FindBy(id = "password")
	private WebElement password;

	@FindBy(id = "login")
	private WebElement loginButton;

	@FindBy(tagName = "li")                     // 2. injects a first element with given tag name
	private WebElement facesMessage;

	@FindByJQuery("p:visible")                  // 3. injects an element using jQuery selector
	private WebElement signedAs;

	@FindBy(css = "input[type=submit]")
	private WebElement whoAmI;
	
	@Test
	public void should_login_successfully() {
	    browser.get(deploymentUrl.toExternalForm() + "login.xhtml");      // 1. open the tested page

	    userName.sendKeys("demo");                                      // 3. control the page
	    password.sendKeys("demo");

	    guardHttp(loginButton).click();

	    assertEquals("Welcome", facesMessage.getText().trim());
	    whoAmI.click();
	    waitAjax().until().element(signedAs).is().present();
	    assertTrue(signedAs.getText().contains("demo"));
	}
	
	@Test
	public void should_login_successfully_no_guards() {
	    browser.get(deploymentUrl.toExternalForm() + "login.xhtml");

	    userName.sendKeys("demo");
	    password.sendKeys("demo");

	    loginButton.click();
	    assertEquals("Welcome", facesMessage.getText().trim());

	    whoAmI.click();
	    assertTrue(signedAs.getText().contains("demo"));
	}
	
	@Test
	public void should_login_successfully_2() {
		browser.get(deploymentUrl.toExternalForm() + "login.xhtml");
		
		userName.sendKeys("demo");
		password.sendKeys("demo");
		loginButton.click();
		
		waitModel().until().element(facesMessage).is().present();     // once the element is present, page is loaded
		assertEquals("Welcome", facesMessage.getText().trim());

		guardAjax(whoAmI).click();
		waitAjax().until().element(signedAs).text().contains("demo"); // use condition as an assertion
		
		whoAmI.click();
		assertTrue(signedAs.getText().contains("demo"));
	}
}
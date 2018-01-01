package org.arquillian.example;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;
import static org.junit.Assert.assertEquals;

import org.jboss.arquillian.graphene.GrapheneElement;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Location("home.xhtml")
public class HomePage {

	@FindBy(tagName = "li")
	private WebElement facesMessage;

	@FindByJQuery("#whoami p:visible")
	private GrapheneElement signedAs;

	@FindBy(css = "input[type=submit]")
	private GrapheneElement whoAmI;

	public void assertOnHomePage() {
		assertEquals("We should be on home page", "Welcome", getMessage());
	}

	public String getMessage() {
		return facesMessage.getText().trim();
	}

	public String getUserName() {
		if (!signedAs.isPresent()) {
			whoAmI();
		}
		return signedAs.getText().substring("You are signed in as ".length(), signedAs.getText().length() - 1);
	}

	private void whoAmI() {
		guardAjax(whoAmI).click();
	}
}
package org.arquillian.example;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;
import static org.junit.Assert.assertEquals;

import org.jboss.arquillian.graphene.GrapheneElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage {

	@FindBy(tagName = "li")
	private WebElement facesMessage;

	@FindBy(using = "#whoami p:visible")
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
		return signedAs.getText();
	}

	private void whoAmI() {
		guardAjax(whoAmI).click();
	}
}
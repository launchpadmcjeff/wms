package com.robowebi.rest;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.UnknownExtensionTypeException;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.robowebi.model.Cosa;

@RunWith(Arquillian.class)
public class BasicClientTest {
    @Deployment(testable = false)
    public static WebArchive createDeployment() {
    	WebArchive webArchive = ShrinkWrap.create(WebArchive.class).addClasses(CosaEndpoint.class, Cosa.class)
    			.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
    			.addAsWebInfResource("arquillian-ds.xml").addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        try {
			WebArchive setWebXML = ShrinkWrap.create(WebArchive.class)
			    .addClasses(CajaEndpoint.class, Cosa.class)
			    .setWebXML("WEB-INF/web.xml");
		} catch (IllegalArgumentException e) {
		} catch (UnknownExtensionTypeException e) {
		}
		return webArchive;
    }
    
    @Test
    public void should_login_successfully() {
    }
}
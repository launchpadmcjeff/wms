package com.robowebi.rest;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.robowebi.model.Cosa;

@RunWith(Arquillian.class)
public class MixedRunModeTest {
    @Deployment
    public static Archive<?> createDeployment() {
    	WebArchive webArchive = ShrinkWrap.create(WebArchive.class).addClasses(CosaEndpoint.class, Cosa.class)
		.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
		.addAsWebInfResource("arquillian-ds.xml").addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        JavaArchive jarArchive = ShrinkWrap.create(JavaArchive.class)
        		.addClasses(CosaEndpoint.class, Cosa.class)
        		.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
		return webArchive;
    }
    
    @Inject
    CosaEndpoint bean;
 
    @Test
    public void should_run_in_container() {
        // executed from the server JVM
        Assert.assertNotNull(bean);
    }

    @Test
    @RunAsClient
    public void should_run_as_client() {
        // executed from the client JVM
    }
}
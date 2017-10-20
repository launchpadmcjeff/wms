/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.robowebi.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.robowebi.model.Caja;
import com.robowebi.model.Cosa;

import com.robowebi.util.Resources;

/**
 * Uses Arquilian to test the JAX-RS processing class for member registration.
 * 
 * @author balunasj
 */
@RunWith(Arquillian.class)
public class CajaEndpointTest {
    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap
                .create(WebArchive.class, "test.war")
                .addClasses(Caja.class, CajaEndpoint.class, Cosa.class,
                        Resources.class).addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("arquillian-ds.xml").addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    CajaEndpoint cajaEndpoint;

    @Inject
    Logger log;

    @Test
    @InSequence(1)
    public void createWithNewCajaShouldHave201ResponseCode() throws Exception {
        Caja member = new Caja();
        Response response = cajaEndpoint.create(member);

        assertEquals("Unexpected response status", 201, response.getStatus());
        log.info("New member was persisted and returned status " + response.getStatus());
    }

    @Test
    @InSequence(2)
    public void testInvalidRegister() throws Exception {
        Caja member = createMemberInstance(null, null, null, null, null, 0, null);
        Response response = cajaEndpoint.create(member);

        assertEquals("Unexpected response status", 201, response.getStatus());
        assertNull("response.getEntity() should not null", response.getEntity());
        log.info("New member was persisted and returned status " + response.getStatus());
    }

    @Test
    @InSequence(3)
    public void testFindById() throws Exception {
        Caja member = createMemberInstance(null, null, null, null, null, 0, null);
        Response response = cajaEndpoint.create(member);
        assertEquals("Unexpected response status", 201, response.getStatus());
        log.info("location" + response.getLocation().getPath());
    }

    private Caja createMemberInstance(Set<Cosa> cosas, Long id, String label, String location, Double siWeight, int version, Float weight) {
        Caja caja = new Caja();
        caja.setCosas(cosas);
        caja.setId(id);
        caja.setLabel(label);
        caja.setLocation(location);
        caja.setSiWeight(siWeight);
        caja.setVersion(version);
        caja.setWeight(weight);

        return caja;
    }
}

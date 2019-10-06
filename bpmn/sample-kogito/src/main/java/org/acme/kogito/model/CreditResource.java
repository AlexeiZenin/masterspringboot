package org.acme.kogito.model;

import org.kie.api.runtime.KieSession;
import org.kie.kogito.rules.KieRuntimeBuilder;

import javax.inject.Inject;
import javax.ws.rs.Consumes;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;



@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/credit")
public class CreditResource {


    @Inject
    KieRuntimeBuilder runtimeBuilder;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Boolean getCredit(Person p) {
        System.out.println(p);
        KieSession ksession = runtimeBuilder.newKieSession();
        ksession.insert(p);
        ksession.fireAllRules();

        return p.isApproved();

    }
}
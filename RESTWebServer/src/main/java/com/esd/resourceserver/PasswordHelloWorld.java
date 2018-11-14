package com.esd.resourceserver;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.QueryParam;
/**
 * Root resource (exposed at "passwordhelloworld" path)
 */
@Path("passwordhelloworld")
public class PasswordHelloWorld {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt( @QueryParam("user") String username, @QueryParam("password") String password ) {
        if(username.equals("user") && password.equals("password")){
            return "Hello " + username + "! I am secured with a username/password combo only!\n";
        }
        return "Wrong username/password combination.\n";
    }
}

package com.esd.resourceserver;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.QueryParam;
/**
 * Root resource (exposed at "helloworld" path)
 */
@Path("helloworld")
public class HelloWorld {
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt( @QueryParam("code") String code ) {

        if(code == null){
            return "It seems you are not authorized to access this page. To authenticate, go to https://slack.com/oauth/authorize?client_id=308511548535.486102684279&scope=identity.basic";
        }
        
        //Check token
        String token = SlackAuth.getSlackToken(code);

        if(token.startsWith("ERROR")){
            return "Could not get a valid OAuth2 token.\n";
        }

        String name = SlackAuth.getSlackIdentityName(token);

        if(!SlackAuth.isSlackAuthenticated(token)){
            return "Could not be authenticated.\n";
        }

        return
            "Welcome, " + name + ", and hello world!\n" + 
            "As you can see, you have authenticated using the OAuth provided by Slack.\n" +
            "The token that was returned by the Slack API is " + token + "\n";
    }
}

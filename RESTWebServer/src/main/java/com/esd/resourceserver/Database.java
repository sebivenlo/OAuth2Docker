package com.esd.resourceserver;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.QueryParam;
/**
 * Root resource (exposed at "database" path)
 */
@Path("database")
public class Database {

    @GET
    @Path("read")
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt( @QueryParam("token") String token, @QueryParam("table") String table ) {
        
        //Check token and connect to DB

        return "Your token: " + token + "\n" + "Reading from Table: " + table + "\n";
    }
    
    @GET
    @Path("write")
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt( @QueryParam("token") String token, @QueryParam("table") String table, @QueryParam("entry") String entry ) {
        
        //Check token and connect to DB

        return "Your token: " + token + "\n" + "Reading from Table: " + table + "\n" + "Entry to be written: " + entry + "\n";
    }
}

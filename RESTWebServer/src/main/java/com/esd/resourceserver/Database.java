package com.esd.resourceserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;

import javax.ws.rs.QueryParam;
/**
 * Root resource (exposed at "database" path)
 */
@Path("database")
public class Database {

    @GET
    @Path("read")
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt( @QueryParam("token") String token, @QueryParam("entry") String entry ) {

        if(token.startsWith("ERROR")){
            return "Could not get a valid OAuth2 token.\n";
        }

        if(!SlackAuth.isSlackAuthenticated(token)){
            return "Could not be authenticated.\n";
        }
        
        String url = "http://127.0.0.1:5984/workshop_db/" + entry;
        String json = "No result";
        try{
            json = readUrl(url);
        } catch (Exception ex){
            return "EXCEPTION: " + url + "\n" + ex;
        }

        return "Your token: " + token + "\n" + "Entry retrieved:\n" + json + "\n";
    }
    
    @GET
    @Path("write")
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt( @QueryParam("token") String token, @QueryParam("table") String table, @QueryParam("name") String name, @QueryParam("gender") String gender, @QueryParam("email") String email ) {
        
        if(token.startsWith("ERROR")){
            return "Could not get a valid OAuth2 token.\n";
        }

        if(!SlackAuth.isSlackAuthenticated(token)){
            return "Could not be authenticated.\n";
        }

        try{
            return sendPost("http://127.0.0.1:5984/workshop_db", name, gender, email);
        }catch(Exception ex){
            return "EXCEPTION: " + ex;
        }
    }

    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read); 
    
            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    private static String sendPost(String urlString, String name, String gender, String email) throws Exception {
		URL obj = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.setRequestProperty("Accept", "application/json; charset=UTF-8");
        
		con.setDoOutput(true);
        JsonObject newJson = new JsonObject();
        newJson.addProperty("name", name);
        newJson.addProperty("gender", gender);
        newJson.addProperty("email", email);

        OutputStreamWriter wr= new OutputStreamWriter(con.getOutputStream());
        wr.write(newJson.toString());
		wr.flush();
		wr.close();

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		return response.toString();

	}
}

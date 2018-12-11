package com.esd.resourceserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import com.google.gson.Gson;

public class SlackAuth {
    private static final String clientID = "308511548535.486102684279";
    private static final String clientSecret = "d10a2a77924e669c3daa436f396569d9";
    
    static class SlackAccess {
        boolean ok;
        String access_token;
        String scope;
        String team_id;
    }

    static class SlackIdentity {
        boolean ok;
        User user;
        Team team;
    }

    static class User {
        String name;
        String id;
    }

    static class Team {
        String id;
    }

    public static String getSlackToken ( String code ) {
        String url = String.format(
            "https://slack.com/api/oauth.access?client_id=%s&client_secret=%s&code=%s",
            clientID,
            clientSecret,
            code
        );
		try{
            String json = readUrl(url);
            Gson gson = new Gson();        
            SlackAccess slackAccess = gson.fromJson(json, SlackAccess.class);

            return slackAccess.access_token;
        }catch(Exception e){
            return "ERROR\n"+e;
        }
    }

    private static SlackIdentity getSlackIdentity ( String token ) throws Exception {
        String url = String.format(
            "https://slack.com/api/users.identity?token=%s",
            token
        );
        String json = readUrl(url);
        Gson gson = new Gson();        
        SlackIdentity slackIdentity = gson.fromJson(json, SlackIdentity.class);

        return slackIdentity;
    }

    public static boolean isSlackAuthenticated ( String token ) {
        try{
            return getSlackIdentity(token).ok;
        }catch(Exception e){
            return false;
        }
    }

    public static String getSlackIdentityName ( String token ) {
		try{
            return getSlackIdentity(token).user.name;
        }catch(Exception e){
            return "ERROR\n"+e;
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
}

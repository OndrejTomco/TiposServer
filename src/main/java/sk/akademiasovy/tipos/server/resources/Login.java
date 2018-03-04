package sk.akademiasovy.tipos.server.resources;


import com.fasterxml.jackson.annotation.JsonProperty;
import sk.akademiasovy.tipos.server.Credentials;
import sk.akademiasovy.tipos.server.Signup;
import sk.akademiasovy.tipos.server.User;
import sk.akademiasovy.tipos.server.db.MySQL;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/auth")
public class Login {

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)

    public String checkCredentials(Credentials credential){
        System.out.println(credential.getUsername());
        MySQL mySQL = new MySQL();
        User user = mySQL.getUser(credential.username, credential.password);
        if(user == null){
            System.out.println("wrong");
            return "{}";

        }

        else{

            String result;
            result="{\"firstname\":\""+user.getFirstName()+"\" , ";
            result+="\"lastname\":\""+user.getLastName()+"\" , ";
            result+="\"email\":\""+user.getEmail()+"\" , ";
            result+="\"login\":\""+user.getLogin()+"\" , ";
            result+="\"token\":\""+user.getToken()+"\"}";
            return result;

        }

    }

    @GET
    @Path("/logout/{token}")
    @Produces(MediaType.APPLICATION_JSON)

    public String logout(@PathParam("token") String token){
        MySQL mySQL = new MySQL();
        mySQL.logout(token);
        return "{}";
    }

    @POST
    @Path("/signup")
    @Produces(MediaType.APPLICATION_JSON)

    public String createNewUser(Signup signup){
        MySQL mySQL = new MySQL();
        boolean exist = mySQL.checkIfEmailOrLoginExist(signup.login.trim(),signup.email.trim());

        if(exist){
            return "{\"Error\":\"already exists\"}";
        }

        else{
            System.out.println("OK");
            mySQL.insertNewUser(signup);
        }

        return "{}";
    }

}

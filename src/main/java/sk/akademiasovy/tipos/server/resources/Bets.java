package sk.akademiasovy.tipos.server.resources;

import sk.akademiasovy.tipos.server.Ticket;
import sk.akademiasovy.tipos.server.db.MySQL;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/bets")
public class Bets {

    @POST
    @Path("/new")
    @Produces(MediaType.APPLICATION_JSON)
        public String newTicket(Ticket ticket){
        MySQL mySQL=new MySQL();
        boolean ret1 = mySQL.checkLogin(ticket.login);
        boolean ret2 = mySQL.checkToken(ticket.token, ticket.login);

        if(ret1 && ret2) {
            mySQL.insertBets(ticket);

        }

        else {
            System.out.println("Invalid");
        }

        return "{}";

        }

}

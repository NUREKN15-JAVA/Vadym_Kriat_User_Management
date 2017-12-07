package ua.nure.kn156.kriat.agent;

import jade.core.AID;
import jade.core.Agent;
import ua.nure.kn156.kriat.User;
import ua.nure.kn156.kriat.agent.exceptions.SearchException;
import ua.nure.kn156.kriat.db.DAOFactory;
import ua.nure.kn156.kriat.db.exceptions.DatabaseException;

import java.util.Collection;

public class SearchAgent extends Agent {
    private AID[] aids;

    @Override
    protected void setup() {
        super.setup();
        System.out.println("Agent " + getAID().getName() + " is working...");
    }

    @Override
    protected void takeDown() {
        super.takeDown();
        System.out.println("Agent " + getAID().getName() + " finished work.");
    }

    void search(String firstName, String lastName) throws SearchException {
        try {
            Collection<User> users = DAOFactory.getInstance().getUserDAO().find(firstName, lastName);
            if (!users.isEmpty()) {
                showUsers(users);
            } else {
                addBehaviour(new SearchRequestBehaviour(aids, firstName, lastName));
            }
        } catch (DatabaseException e) {
            throw new SearchException(e);
        }
    }

    public void showUsers(Collection users) {

    }
}

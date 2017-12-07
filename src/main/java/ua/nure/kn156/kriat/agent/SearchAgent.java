package ua.nure.kn156.kriat.agent;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import ua.nure.kn156.kriat.User;
import ua.nure.kn156.kriat.agent.exceptions.SearchException;
import ua.nure.kn156.kriat.db.DAOFactory;
import ua.nure.kn156.kriat.db.exceptions.DatabaseException;

import java.util.Collection;

public class SearchAgent extends Agent {
    private AID[] aids;
    private SearchGui gui;

    @Override
    protected void setup() {
        super.setup();
        System.out.println("Agent " + getAID().getName() + " is working...");

        gui = new SearchGui(this);
        gui.setVisible(true);

        DFAgentDescription description = new DFAgentDescription();
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setName("JADE-Searching");
        serviceDescription.setType("searching");
        description.setName(getAID());
        description.addServices(serviceDescription);
        try {
            DFService.register(this, description);
        } catch (FIPAException e) {
            e.printStackTrace();
        }

        addBehaviour(new TickerBehaviour(this, 60000) {
            @Override
            protected void onTick() {
                DFAgentDescription agentDescription = new DFAgentDescription();
                ServiceDescription sd = new ServiceDescription();
                sd.setType("searching");
                agentDescription.addServices(sd);
                try {
                    DFAgentDescription[] descriptions = DFService.search(myAgent, agentDescription);
                    aids = new AID[descriptions.length];
                    for (int i = 0; i < descriptions.length; i++) {
                        aids[i] = descriptions[i].getName();
                    }
                } catch (FIPAException e) {
                    e.printStackTrace();
                }
            }
        });
        addBehaviour(new RequestServer());
    }

    @Override
    protected void takeDown() {
        super.takeDown();
        System.out.println("Agent " + getAID().getName() + " finished work.");
        gui.setVisible(false);
        gui.dispose();
        try {
            DFService.deregister(this);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
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

    public void showUsers(Collection<User> users) {
        gui.addUsers(users);
    }
}

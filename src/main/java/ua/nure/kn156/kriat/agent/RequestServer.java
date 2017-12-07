package ua.nure.kn156.kriat.agent;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import ua.nure.kn156.kriat.User;
import ua.nure.kn156.kriat.db.DAOFactory;
import ua.nure.kn156.kriat.db.exceptions.DatabaseException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

public class RequestServer extends CyclicBehaviour {
    @Override
    public void action() {
        ACLMessage message = myAgent.receive();
        if (message != null) {
            if (ACLMessage.REQUEST == message.getPerformative()) {
                myAgent.send(createReply(message));
            } else {
                Collection<User> users = parseMessage(message);
                ((SearchAgent) myAgent).showUsers(users);
            }
        } else {
            block();
        }
    }

    private Collection<User> parseMessage(ACLMessage message) {
        Collection<User> users = new ArrayList<>();
        String content = message.getContent();
        if (content != null) {
            StringTokenizer tokenizer = new StringTokenizer(content, ";");
            while (tokenizer.hasMoreTokens()) {
                StringTokenizer subTokenizer = new StringTokenizer(tokenizer.nextToken(), ",");
                String id = subTokenizer.nextToken();
                String firstName = subTokenizer.nextToken();
                String lastName = subTokenizer.nextToken();
                users.add(new User(new Long(id), firstName, lastName, null));
            }
        }
        return users;
    }

    private ACLMessage createReply(ACLMessage message) {
        ACLMessage reply = message.createReply();
        String content = message.getContent();
        StringTokenizer tokenizer = new StringTokenizer(content, ",");
        if (tokenizer.countTokens() == 2) {
            String firstName = tokenizer.nextToken();
            String lastName = tokenizer.nextToken();
            Collection<User> users = null;
            try {
                users = DAOFactory.getInstance().getUserDAO().find(firstName, lastName);
            } catch (DatabaseException e) {
                e.printStackTrace();
                users = new ArrayList<>(0);
            }
            StringBuffer buffer = new StringBuffer();
            for (User user : users) {
                buffer.append(user.getId()).append(",");
                buffer.append(user.getFirstName()).append(",");
                buffer.append(user.getLastName()).append(";");
            }
            reply.setContent(buffer.toString());
        }
        return reply;
    }
}

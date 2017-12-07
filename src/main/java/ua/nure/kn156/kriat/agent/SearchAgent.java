package ua.nure.kn156.kriat.agent;

import jade.core.Agent;

public class SearchAgent extends Agent {

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
}

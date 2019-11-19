package main.services;

import main.Client;

import java.io.IOException;

public interface Service {

    void execute(Client client) throws IOException;
}

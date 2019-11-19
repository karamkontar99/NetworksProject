package main.services;

import main.Client;
import main.models.User;
import main.repos.UserRepository;

import java.io.IOException;

public class Login implements Service {
    private final UserRepository userRepository;

    public Login(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(Client client) throws IOException {

        client.send("USERNAME?");

        String username = client.receive();
        if (username.isEmpty() || username.equals("EXIT"))
            return;

        client.send("PASSWORD?");

        String password = client.receive();
        if (password.isEmpty() || password.equals("EXIT"))
            return;

        User user = userRepository.login(username, password);

        if (user == null) {
            client.send("FAIL");
            return;
        }

        client.send("SUCCESS");
        client.send("ID: " + user.getId());
        client.send("Name: " + user.getName());
        client.send("Address: " + user.getAddress());
        client.send("Email: " + user.getEmail());
        client.send("Username: " + user.getUsername());
    }

}

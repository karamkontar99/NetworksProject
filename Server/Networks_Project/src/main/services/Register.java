package main.services;

import main.Client;
import main.models.User;
import main.repos.UserRepository;

import java.io.IOException;

public class Register {
    private final UserRepository userRepository;

    public Register(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(Client client) throws IOException {
        User dummy = new User();
        dummy.setUsername("user");
        dummy.setPassword("pass");
        dummy.setName("Hackerman");
        dummy.setAddress("LAU Beirut");
        dummy.setEmail("hackerman@lau.edu.lb");
        userRepository.insert(dummy);

        client.send("NAME?");

        String name = client.receive();
        if (name.isEmpty() || name.equals("EXIT"))
            return;

        client.send("ADDRESS?");

        String address = client.receive();
        if (address.isEmpty() || address.equals("EXIT"))
            return;

        client.send("EMAIL?");

        String email = client.receive();
        if (email.isEmpty() || email.equals("EXIT"))
            return;

        String username;
        while (true) {
            client.send("USERNAME?");

            username = client.receive();

            if (username.isEmpty() || username.equals("EXIT"))
                return;
            else if (!userRepository.uniqueUsername(username))
                client.send("Username is not unique");
            else
                break;
        }

        client.send("PASSWORD?");

        String password = client.receive();
        if (password.isEmpty() || password.equals("EXIT"))
            return;

        User user = new User(name, address, email, username, password);
        userRepository.insert(user);

        client.send("SUCCESS");
        client.send("ID: " + user.getId());
        client.send("Name: " + user.getName());
        client.send("Address: " + user.getAddress());
        client.send("Email: " + user.getEmail());
        client.send("Username: " + user.getUsername());
    }
}

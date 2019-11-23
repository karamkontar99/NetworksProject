package edu.networks.project.services;


import edu.networks.project.messages.Message;

public interface Service <Request extends Message, Response extends Message> {

     Response execute(Request message);
}

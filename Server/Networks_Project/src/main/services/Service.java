package main.services;

import main.messages.Message;

public interface Service <Request extends Message, Response extends Message> {

     Response execute(Request message);
}

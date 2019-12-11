package main.services;

import main.messages.Message;
import main.messages.MessageInterface;

public interface Service <Request extends MessageInterface, Response extends MessageInterface> {

     Response execute(Request message);
}

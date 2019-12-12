package main;

import eu.lestard.easydi.EasyDI;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        EasyDI context = new EasyDI();

        int port = 5050;

        Application application = context.getInstance(Application.class);
        application.run(port);
    }

}

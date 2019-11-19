package main;
import com.mongodb.client.MongoDatabase;
import eu.lestard.easydi.EasyDI;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        EasyDI context = new EasyDI();

        int port = 5050;
        try {
            port = Integer.parseInt(args[1]);
        } catch (Exception ignored){}

        Application application = context.getInstance(Application.class);
        application.run(port);
    }

}

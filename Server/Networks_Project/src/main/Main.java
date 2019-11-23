package main;

import eu.lestard.easydi.EasyDI;
import org.modelmapper.ModelMapper;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        EasyDI context = new EasyDI();
        context.bindInstance(ModelMapper.class, configureModelMapper());

        int port = 5050;
        try {
            port = Integer.parseInt(args[1]);
        } catch (Exception ignored){}

        Application application = context.getInstance(Application.class);
        application.run(port);
    }

    private static ModelMapper configureModelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setFieldMatchingEnabled(true);
        return mapper;
    }

}

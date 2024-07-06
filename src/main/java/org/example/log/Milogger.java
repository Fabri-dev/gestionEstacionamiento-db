package org.example.log;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Milogger {
    //atributos
    private static Logger logger;
    private static FileHandler controladorDelLogger;
    //constructores

    public Milogger(){
        logger= Logger.getLogger("logEstacionamiento.log");
        controladorDelLogger=null;
    }

    //get y set

    //metodos

    public static void escribirLog(String mensaje){
       String texto;
        try {
            //instancio el controlador
            controladorDelLogger= new FileHandler(logger.getName(),true);

            //configuro el logger, el cual me va a permitir escribir mensajes en el log
            SimpleFormatter formatter = new SimpleFormatter();
            controladorDelLogger.setFormatter(formatter);

            //le agrego el controlador al logger
            logger.addHandler(controladorDelLogger);

            logger.setLevel(Level.ALL);

            logger.info(mensaje);


        } catch (IOException e) {
            System.out.println("Error con archivo");
        }finally {
            controladorDelLogger.close();
        }
    }

}

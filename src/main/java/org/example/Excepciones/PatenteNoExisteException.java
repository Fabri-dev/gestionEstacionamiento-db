package org.example.Excepciones;

public class PatenteNoExisteException extends Exception{
    //atributos

    //constructores

    public PatenteNoExisteException() {
        super("Error: La patente ingresada no existe en el estacionamiento");
    }

    public PatenteNoExisteException(String patente) {


        super("Error: La patente: "+ patente+ " no existe en el estacionamiento");
    }


    //get y set

    //metodos


    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}

package org.example.Excepciones;

public class PatenteYaExisteException extends Exception{

    //atributos

    //constructores

    public PatenteYaExisteException() {
        super("Error: La patente ingresada ya existe en el estacionamiento");
    }

    public PatenteYaExisteException(String patente) {


        super("Error: La patente: "+ patente+ " ya existe en el estacionamiento");
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

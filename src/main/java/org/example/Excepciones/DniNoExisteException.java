package org.example.Excepciones;

public class DniNoExisteException extends Exception{//atributos

    //constructores

    public DniNoExisteException() {
        super("Error: El dni ingresado no existe en el estacionamiento");
    }

    public DniNoExisteException(String dni) {


        super("Error: El dni: "+ dni+ " no existe en el estacionamiento");
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

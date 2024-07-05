package org.example.Excepciones;

public class DniYaExisteException extends Exception{
    //atributos

    //constructores

    public DniYaExisteException() {
        super("Error: El dni ingresado ya existe en el estacionamiento");
    }

    public DniYaExisteException(String dni) {


        super("Error: El dni: "+ dni+ " ya existe en el estacionamiento");
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

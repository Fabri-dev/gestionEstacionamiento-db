package org.example.Excepciones;

public class AutoYaExisteException extends Exception{
    //atributos

    //constructores

    public AutoYaExisteException() {
        super("Error: El auto ingresado ya existe en el estacionamiento, verifique si la patente o el dni del cliente ya existe.");
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

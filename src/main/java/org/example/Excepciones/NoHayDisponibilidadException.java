package org.example.Excepciones;

public class NoHayDisponibilidadException extends Exception{
    //atributos

    //constructores

    public NoHayDisponibilidadException() {
        super("Error: No hay disponibilidad en el estacionamiento por el momento");
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

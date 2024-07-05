package org.example.modelo;

import java.util.Objects;

public class Auto {
    //atributos
    private int idAuto;
    private String dniCliente;
    private int numeroDeEstacionamiento;
    private String patente;
    private String modelo;
    private String marca;
    private String color;
    private String tipoDeVehiculo; //sedán, SUV, camioneta, etc.
    private boolean estado;

    //constructores

    public Auto() {
        dniCliente=null;
        numeroDeEstacionamiento=0;
        patente=null;
        modelo=null;
        marca=null;
        color=null;
        tipoDeVehiculo=null;
        estado=true;
    }

    public Auto(String color, String dniCliente, String marca, String modelo, String patente, String tipoDeVehiculo) {
        this.color = color;
        this.dniCliente = dniCliente;
        this.marca = marca;
        this.modelo = modelo;
        this.patente = patente;
        this.tipoDeVehiculo = tipoDeVehiculo;
        numeroDeEstacionamiento=0;
        estado=true;
    }

    //get y set

    public String getColor() {
        return color;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getNumeroDeEstacionamiento() {
        return numeroDeEstacionamiento;
    }

    public String getPatente() {
        return patente;
    }

    public String getTipoDeVehiculo() {
        return tipoDeVehiculo;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setNumeroDeEstacionamiento(int numeroDeEstacionamiento) {
        this.numeroDeEstacionamiento = numeroDeEstacionamiento;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public void setTipoDeVehiculo(String tipoDeVehiculo) {
        this.tipoDeVehiculo = tipoDeVehiculo;
    }

    public void setIdAuto(int idAuto) {
        this.idAuto = idAuto;
    }

    //metodos


    @Override
    public boolean equals(Object o) {
       boolean flag=false;
        if (o != null)
        {
            if (o instanceof Auto autoAux)
            {
                if (autoAux.getPatente() == this.getPatente() && autoAux.getDniCliente() == this.getDniCliente())
                {
                    flag=true;
                }
            }
        }
        return flag;
    }

    @Override
    public int hashCode() {
        return 1;
    }
}

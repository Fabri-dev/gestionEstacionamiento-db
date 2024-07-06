package org.example.modelo;
import java.time.LocalDateTime;


public class Auto {
    //atributos
    private int idAuto;
    private String dniCliente;
    private int numeroDeEstacionamiento;
    private String patente;
    private LocalDateTime fechaDeEntrada;
    private LocalDateTime fechaDeSalida;
    private boolean estado;
    private String modelo;
    private String marca;
    private String color;
    private String tipoDeVehiculo; //sedán, SUV, camioneta, etc.

    //constructores

    public Auto() {
        dniCliente = null;
        numeroDeEstacionamiento = 0;
        patente = null;
        modelo = null;
        marca = null;
        color = null;
        tipoDeVehiculo = null;
        estado = true;
        fechaDeEntrada = LocalDateTime.now();
        fechaDeSalida = null;
    }

    public Auto(String color, String dniCliente, String marca, String modelo, String patente, String tipoDeVehiculo) {
        //me sirve para cuando creo un auto nuevo
        this.color = color;
        this.dniCliente = dniCliente;
        this.marca = marca;
        this.modelo = modelo;
        this.patente = patente;
        this.tipoDeVehiculo = tipoDeVehiculo;
        numeroDeEstacionamiento = 0;
        estado = true;
        fechaDeEntrada = LocalDateTime.now();
        fechaDeSalida = null;
    }

    public Auto(String color, String dniCliente, boolean estado, LocalDateTime fechaDeEntrada, LocalDateTime fechaDeSalida, int idAuto, String marca, String modelo, int numeroDeEstacionamiento, String patente, String tipoDeVehiculo) {
        //me sirve para cuando lo leo de la BD
        this.color = color;
        this.dniCliente = dniCliente;
        this.estado = estado;
        this.fechaDeEntrada = fechaDeEntrada;
        this.fechaDeSalida = fechaDeSalida;
        this.idAuto = idAuto;
        this.marca = marca;
        this.modelo = modelo;
        this.numeroDeEstacionamiento = numeroDeEstacionamiento;
        this.patente = patente;
        this.tipoDeVehiculo = tipoDeVehiculo;
    }

    //get y set

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaDeEntrada() {
        return fechaDeEntrada;
    }

    public void setFechaDeEntrada(LocalDateTime fechaDeEntrada) {
        this.fechaDeEntrada = fechaDeEntrada;
    }

    public LocalDateTime getFechaDeSalida() {
        return fechaDeSalida;
    }

    public void setFechaDeSalida(LocalDateTime fechaDeSalida) {
        this.fechaDeSalida = fechaDeSalida;
    }

    public int getIdAuto() {
        return idAuto;
    }

    public void setIdAuto(int idAuto) {
        this.idAuto = idAuto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getNumeroDeEstacionamiento() {
        return numeroDeEstacionamiento;
    }

    public void setNumeroDeEstacionamiento(int numeroDeEstacionamiento) {
        this.numeroDeEstacionamiento = numeroDeEstacionamiento;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente.toUpperCase();
    }

    public String getTipoDeVehiculo() {
        return tipoDeVehiculo;
    }

    public void setTipoDeVehiculo(String tipoDeVehiculo) {
        this.tipoDeVehiculo = tipoDeVehiculo;
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

    @Override
    public String toString() {
        return "Auto{" +
                "color='" + color + '\'' +
                ", idAuto=" + idAuto +
                ", dniCliente='" + dniCliente + '\'' +
                ", numeroDeEstacionamiento=" + numeroDeEstacionamiento +
                ", patente='" + patente + '\'' +
                ", fechaDeEntrada=" + fechaDeEntrada +
                ", fechaDeSalida=" + fechaDeSalida +
                ", estado=" + estado +
                ", modelo='" + modelo + '\'' +
                ", marca='" + marca + '\'' +
                ", tipoDeVehiculo='" + tipoDeVehiculo + '\'' +
                '}';
    }
}

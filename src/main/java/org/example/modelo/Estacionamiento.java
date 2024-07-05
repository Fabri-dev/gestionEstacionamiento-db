package org.example.modelo;

import org.example.Excepciones.AutoYaExisteException;
import org.example.Excepciones.NoHayDisponibilidadException;
import org.example.dbManager.DbManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Estacionamiento {
    //atributos
    private int disponibilidad;
    private int cantAutosEstacionados;
    private double precioXHora;
    private String nombreEstacionamiento;
    private String direccion;
    private HashMap<Integer,Auto> autos;
    private DbManager dbManager;


    //constructores

    public Estacionamiento()
    {
        cantAutosEstacionados = 0;
        direccion = null;
        disponibilidad = 0;
        nombreEstacionamiento = null;
        precioXHora = 0;
        iniciarListaDeAutos();
        dbManager= new DbManager();
    }

    public Estacionamiento(String direccion, int disponibilidad, String nombreEstacionamiento, double precioXHora) {
        this.cantAutosEstacionados = 0;
        this.direccion = direccion;
        this.disponibilidad = disponibilidad;
        this.nombreEstacionamiento = nombreEstacionamiento;
        this.precioXHora = precioXHora;
        iniciarListaDeAutos();
        dbManager= new DbManager();
    }

    //get y set

    public HashMap<Integer, Auto> getAutos() {
        return autos;
    }

    public int getCantAutosEstacionados() {
        return cantAutosEstacionados;
    }

    public String getDireccion() {
        return direccion;
    }

    public int getDisponibilidad() {
        return disponibilidad;
    }

    public String getNombreEstacionamiento() {
        return nombreEstacionamiento;
    }

    public double getPrecioXHora() {
        return precioXHora;
    }

    public void setPrecioXHora(double precioXHora) {
        this.precioXHora = precioXHora;
    }


    //metodos

    private void iniciarListaDeAutos()
    {
        autos= new HashMap<>();

        for(int i=1; i <= disponibilidad; i++)
        {
            autos.put(i,null);
        }
    }

    private void cargarAutosDeLaBD()
    {

    }


    public boolean agregarUnAuto(Auto nuevoAuto) throws NoHayDisponibilidadException, AutoYaExisteException, SQLException {
        //se agrega tanto al hashmap como a la DB
        boolean flag=false;
        int espacio=0;


        //primero tengo que buscar si tengo espacio en el estacionamiento

        System.out.println("Cant autos: "+ cantAutosEstacionados);
        System.out.println("Disponibilidad: "+ disponibilidad);

        if (cantAutosEstacionados>= disponibilidad)
        {
            System.out.println("No hay mas espacio");
            throw new NoHayDisponibilidadException();
        }

        //segundo tengo que buscar si el auto no es el mismo que alguno de los estacionados(patente y dnicliente)
        System.out.println(nuevoAuto);
        if (autoExiste(nuevoAuto))
        {
            System.out.println("El auto recibido ya existe");
            throw new AutoYaExisteException();
        }

        //tercero tengo que buscar un espacio vacio (un valor nulo en el mapa)

        espacio= retonarEspacioLibreEnEstacionamiento();

        if (espacio != -1)
        {

            nuevoAuto.setNumeroDeEstacionamiento(espacio);

            autos.put(nuevoAuto.getNumeroDeEstacionamiento(),nuevoAuto);

            cantAutosEstacionados++;

            //si tira excepcion se arroja sola
            dbManager.agregarUnAuto(nuevoAuto);

            flag=true;

        }


        return flag;
    }

    public int retonarEspacioLibreEnEstacionamiento(){
        int espacioLibre=-1;
        boolean flag=false;
        Iterator<Map.Entry<Integer,Auto>> iterator= autos.entrySet().iterator();

        //recorro todos los espacios del estacionamiento
        while (iterator.hasNext() && !flag)
        {
            Map.Entry<Integer,Auto> entry = iterator.next();

            //autoAux me determinara si hay espacio libre o no
            Auto autoAux=entry.getValue();

            //si hay un espacio libre, puedo guardar el auto
            if (autoAux == null)
            {
                //obtengo el nro de estacionamiento
                espacioLibre= entry.getKey();
                //corto el bucle
                flag=true;
            }

        }
        //si no hay un espacio libre, retorno -1
        return espacioLibre;
    }


    public boolean autoExiste(Auto autoAVerificar){
        boolean flag=false;
        if (autos.containsValue(autoAVerificar))
        {
            flag=true;
        }
        return flag;
    }



    public boolean eliminarUnAuto(String dniCliente, String patente)
    {
        //se elimina el auto del hashmap pero queda en la BD
        boolean flag=false;

        //primero tengo que verificar si existe el auto en el estacionamiento
        //if (autoExiste())





        return flag;
    }

    public boolean modificarUnAuto()
    {
        //se modifica de la DB
        boolean flag=false;

        return flag;
    }

    public ArrayList<Auto> listarEstacionamiento()
    {
        //muestra una lista de toddo el estacionamiento. Espacios vacios o llenos
        ArrayList<Auto> listaAutos=new ArrayList<>();

        for(Map.Entry<Integer,Auto> autoSet: autos.entrySet())
        {
            listaAutos.add(autoSet.getValue());
        }
        return listaAutos;
    }




    private void leerAutosDB()
    {
        //leo todos los autos desde la DB
    }



}

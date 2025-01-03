package org.example.modelo;

import org.example.Excepciones.*;
import org.example.log.Milogger;

import java.awt.image.AreaAveragingScaleFilter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    //atributos
    static Scanner scanner= new Scanner(System.in);
    Estacionamiento estacionamiento;
    //constructores

    public Menu(Estacionamiento estacionamiento) {
        this.estacionamiento = estacionamiento;
    }


    //get y set

    public Estacionamiento getEstacionamiento() {
        return estacionamiento;
    }

    //metodos

    public void menu()
    {
        String patenteAux,dniAux;
        char opMenu;
        int opSw=0,opIntAux,auxInt;
        boolean flag;
        ArrayList<Auto> listadoDeAutos;
        do {
            opcionesMenu();
            opSw= scanner.nextInt();
            switch (opSw)
            {
                case 1:
                    try {
                        auxInt= agregarUnAutoAEstacionamiento();
                        if (auxInt != -1){
                            System.out.println("Auto agregado con exito!, el numero de estacionamiento del auto es: " + auxInt);
                            Milogger.escribirLog("Auto agregado con exito!, el numero de estacionamiento del auto es: " + auxInt);
                        }else {
                            System.out.println("No se pudo agregar el auto al estacionamiento :(");
                            Milogger.escribirLog("Error: No se pudo agregar el auto al estacionamiento ");
                        }

                    } catch (SQLException e) {
                        Milogger.escribirLog("Error con la BD");
                        System.out.println(e.getMessage());

                    } catch (NoHayDisponibilidadException e) {
                        Milogger.escribirLog(e.getMessage());
                        System.out.println(e.getMessage());

                    } catch (AutoYaExisteException e) {
                        Milogger.escribirLog(e.getMessage());
                        System.out.println(e.getMessage());

                    } catch (PatenteYaExisteException e) {
                        Milogger.escribirLog(e.getMessage());
                        System.out.println(e.getMessage());

                    } catch (DniYaExisteException e) {
                        Milogger.escribirLog(e.getMessage());
                        System.out.println(e.getMessage());
                    }

                    break;
                case 2:
                    System.out.println("Sacar un auto del estacionamiento");

                    System.out.println("Ingrese el dni del cliente: ");
                    scanner.nextLine();
                    dniAux= scanner.nextLine();

                    System.out.println("Ingrese la patente del vehiculo: ");
                    scanner.nextLine();
                    patenteAux= scanner.nextLine();
                    patenteAux= patenteAux.toUpperCase();

                    try {

                        flag= estacionamiento.eliminarUnAuto(dniAux,patenteAux);
                        Milogger.escribirLog("Auto con patente: "+patenteAux+"y dni del cliente: "+dniAux+ " se fue del estacionamiento.");
                        System.out.println("se elimino: "+flag);
                    } catch (DniNoExisteException e) {

                        Milogger.escribirLog("Se ingreso un dni que no existe en el sistema para intentar sacar un auto del estacionamiento.");
                        System.out.println(e.getMessage());

                    } catch (PatenteNoExisteException e) {

                        Milogger.escribirLog("Se ingreso una patente que no existe en el sistema para intentar sacar un auto del estacionamiento.");
                        System.out.println(e.getMessage());
                    } catch (SQLException e) {

                        Milogger.escribirLog("Error con la BD al eliminar un auto");
                        System.out.println(e.getMessage());
                    }


                    break;
                case 3:
                    //Modificar un auto
                    System.out.println("Modificar un auto");

                    System.out.println("Ingrese la patente del auto que quiere modificar");



                    break;
                case 4:
                    //Ver registro de autos
                    System.out.println("Obtener un registro de autos");
                    System.out.println("""
                            1. Todos (Activos e inactivos)
                            2. Activos (Actualmente en el estacionamiento)
                            3. Inactivos (Se fueron del estacionamiento)
                            """);
                    opIntAux= scanner.nextInt();
                    listadoDeAutos= opcionesGenerarRegistro(opIntAux);

                    if (listadoDeAutos != null)
                    {
                        System.out.println("Listado de autos: \n"+listadoDeAutos);

                    }
                    else {
                        System.out.println("Lista vacia");
                    }
                    break;
                case 5:
                    System.out.println("Precio x hora anterior: "+ getEstacionamiento().getPrecioXHora());
                    System.out.println("Ingrese el nuevo precio x hora: ");
                    estacionamiento.setPrecioXHora(scanner.nextDouble());
                    break;
                case 6:
                    System.out.println("Hay: "+ estacionamiento.getCantAutosEstacionados()+" auto/s estacionado/s");
                    int espaciosLibres=estacionamiento.getDisponibilidad() - estacionamiento.getCantAutosEstacionados();
                    System.out.println("Tiene: "+ espaciosLibres +" espacio/s libre/s");
                    break;
                default:
                    System.out.println("Ingrese una opcion valida");
                    break;
            }

            System.out.println("Desea continuar? s/n");
            scanner.nextLine();
            opMenu= scanner.nextLine().charAt(0);

        }while (opMenu== 's');

    }

    private int agregarUnAutoAEstacionamiento() throws SQLException, NoHayDisponibilidadException, AutoYaExisteException, PatenteYaExisteException, DniYaExisteException {
        boolean flag=false;
        int nroDeEstacionamientoDelAutoNuevo=-1;
        Auto autoAux= cargarUnAuto();


        flag= estacionamiento.agregarUnAuto(autoAux);

        if (flag)
        {
            nroDeEstacionamientoDelAutoNuevo = autoAux.getNumeroDeEstacionamiento();
        }

        return nroDeEstacionamientoDelAutoNuevo;
    }

    public Auto cargarUnAuto(){
        Auto autoAux= new Auto();
        String dniAux,patenteAux;
        boolean flag;

        do {
            System.out.println("Ingrese el DNI del cliente: ");
            scanner.nextLine();
            dniAux= scanner.nextLine();
            flag=estacionamiento.autoExisteXDni(dniAux);
            if (flag)
            {
                System.out.println("Dni ya existe. Ingrese otro");
            }

        }while (flag);

        autoAux.setDniCliente(dniAux);

        do
        {
            System.out.println("Ingrese la patente del auto: ");
            scanner.nextLine();
            patenteAux= scanner.nextLine();
            flag=estacionamiento.autoExisteXPatente(patenteAux);
            if (flag)
            {
                System.out.println("Patente ya existe. Ingrese otra");
            }

        }while (flag);

        autoAux.setPatente(patenteAux);

        System.out.println("--Info adicional del vehiculo--");
        System.out.println("Ingrese el color: ");
        scanner.nextLine();
        autoAux.setColor(scanner.nextLine());
        System.out.println("Ingrese la marca: ");
        scanner.nextLine();
        autoAux.setMarca(scanner.nextLine());
        System.out.println("Ingrese el modelo: ");
        scanner.nextLine();
        autoAux.setModelo(scanner.nextLine());
        System.out.println("Ingrese el tipo de vehiculo(sedán, SUV, camioneta): ");
        scanner.nextLine();
        autoAux.setTipoDeVehiculo(scanner.nextLine());

        return autoAux;

    }

    public void opcionesMenu(){

        System.out.println("Estacionamiento: "+estacionamiento.getNombreEstacionamiento());

        System.out.println("---------------------------------------------------");
        System.out.println("""
                1. Entrar un auto\s
                2. Sacar un auto
                3. Modificar un auto
                4. Ver registro de autos
                5. Cambiar el precio x hora
                6. Espacio libre disponible en el estacionamiento""");
        System.out.println("---------------------------------------------------");
        System.out.println("Que funcion desea hacer?: ");

    }

    public ArrayList<Auto> opcionesGenerarRegistro(int opcionElegida){
        ArrayList<Auto> autoArrayList= new ArrayList<>();
        switch (opcionElegida)
        {
            case 1:
                //todos
                autoArrayList= estacionamiento.listarEstacionamiento();
                try {
                    autoArrayList.addAll(estacionamiento.listarAutosInactivosDB());
                } catch (SQLException e) {
                    Milogger.escribirLog("Error con BD al intentar hacer un listado de todos los autos");
                    System.out.println("Error con BD");
                    e.printStackTrace();
                }
                Milogger.escribirLog("Se creo un listado de todos los autos.");
                break;
            case 2:
                //activos
                autoArrayList= estacionamiento.listarEstacionamiento();
                Milogger.escribirLog("Se creo un listado de los autos activos.");
                break;
            case 3:
                //inactivos
                try {
                    autoArrayList= estacionamiento.listarAutosInactivosDB();
                    Milogger.escribirLog("Se creo un listado de los autos inactivos.");
                } catch (SQLException e) {
                    System.out.println("Error con BD");
                    Milogger.escribirLog("Error con BD al intentar hacer un listado de los autos inactivos");
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("Elija una opcion valida");
                autoArrayList= null;
                break;
        }
    return autoArrayList;

    }


}

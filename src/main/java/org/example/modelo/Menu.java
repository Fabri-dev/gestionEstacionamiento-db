package org.example.modelo;

import org.example.Excepciones.AutoYaExisteException;
import org.example.Excepciones.NoHayDisponibilidadException;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
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

        char opMenu = 0;
        int opSw=0;

        do {
            opcionesMenu();
            opSw= scanner.nextInt();
            switch (opSw)
            {
                case 1:

                    try {
                        agregarUnAutoAEstacionamiento();



                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    } catch (NoHayDisponibilidadException e) {
                        System.out.println(e.getMessage());
                    } catch (AutoYaExisteException e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                case 2:
                    System.out.println("Sacar un auto del estacionamiento");


                    System.out.println("Ingrese la patente del vehiculo: ");
                    System.out.println("Ingrese el dni del cliente: ");

                    //estacionamiento.eliminarUnAuto()


                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    System.out.println("Precio x hora anterior: "+ getEstacionamiento().getPrecioXHora());
                    System.out.println("Ingrese el nuevo precio x hora: ");
                    estacionamiento.setPrecioXHora(scanner.nextDouble());
                    break;
                default:

                    break;
            }

            System.out.println("Desea continuar? s/n");
            opMenu= scanner.nextLine().charAt(0);

        }while (opMenu== 's');

    }

    private void agregarUnAutoAEstacionamiento() throws SQLException, NoHayDisponibilidadException, AutoYaExisteException {
        Auto autoAux= new Auto();
        boolean flag;
        String dniAux,patenteAux;
        do {
            System.out.println("Ingrese el DNI del cliente: ");
            scanner.nextLine();
            dniAux= scanner.nextLine();

        }while (dniExiste(dniAux));

        autoAux.setDniCliente(dniAux);

        do
        {
            System.out.println("Ingrese la patente del auto: ");
            scanner.nextLine();
            patenteAux= scanner.nextLine();

        }while (patenteExiste(patenteAux));

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


        flag= estacionamiento.agregarUnAuto(autoAux);

        if (flag)
        {
            System.out.println("Auto agregado con exito!, el numero de estacionamiento del auto es: "+ autoAux.getNumeroDeEstacionamiento());
        }
        else {
            System.out.println("No se pudo agregar el auto al estacionamiento :(");
        }

    }

    public boolean dniExiste(String dniRecibido){
        boolean flag=false;
        Iterator<Map.Entry<Integer,Auto>>iterator= estacionamiento.getAutos().entrySet().iterator();
        Auto autoAux;
        while (iterator.hasNext() && !flag){
            autoAux= iterator.next().getValue();
            //si el lugar no esta vacio
            if (autoAux != null)
            {
                if (autoAux.getDniCliente() == dniRecibido)
                {
                    flag=true;
                }
            }
        }
        return flag;
    }

    public boolean patenteExiste(String patenteRecibida){
        boolean flag=false;
        Iterator<Map.Entry<Integer,Auto>>iterator= estacionamiento.getAutos().entrySet().iterator();
        Auto autoAux;
        while (iterator.hasNext() && !flag){
            autoAux= iterator.next().getValue();
            //si el lugar no esta vacio
            if (autoAux != null)
            {
                if (autoAux.getPatente() == patenteRecibida)
                {
                    flag=true;
                }

            }
        }
        return flag;
    }



    public void opcionesMenu(){

        System.out.println("Estacionamiento: "+estacionamiento.getNombreEstacionamiento());

        System.out.println("---------------------------------------------------");
        System.out.println("""
                1. Entrar un auto\s
                2. Sacar un auto
                3. Modificar un auto
                4. Ver registro de autos
                5. Cambiar el precio x hora""");
        System.out.println("---------------------------------------------------");
        System.out.println("Que funcion desea hacer?: ");

    }




}

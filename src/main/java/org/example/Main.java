package org.example;

import org.example.Excepciones.AutoYaExisteException;
import org.example.Excepciones.NoHayDisponibilidadException;
import org.example.dbManager.DbManager;
import org.example.modelo.Auto;
import org.example.modelo.Estacionamiento;
import org.example.modelo.Menu;


import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws SQLException {
        Estacionamiento estacionamiento= new Estacionamiento("Olazabal",20,"Pepito",3000);
        Menu menu= new Menu(estacionamiento);

        //menu
        menu.menu();
        DbManager dbManager= new DbManager();

//
//        dbManager.realizarUnaQuery("ALTER TABLE autos ADD estado BOOLEAN;");

//        Auto autoPrueba= new Auto("Rojo","46737845","Chevrolet","Onix","AA234BB","Coupe");
//
//        System.out.println(dbManager.obtenerMapaDeAutosActivos());


//        System.out.println(autoPrueba);
//
//        try {
//            estacionamiento.agregarUnAuto(autoPrueba);
//        } catch (NoHayDisponibilidadException e) {
//            throw new RuntimeException(e);
//        } catch (AutoYaExisteException e) {
//            throw new RuntimeException(e);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }


    }
}
package org.example;

import org.example.dbManager.DbManager;
import org.example.modelo.Auto;
import org.example.modelo.Estacionamiento;
import org.example.modelo.Menu;


import java.sql.SQLException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws SQLException {
        Estacionamiento estacionamiento= new Estacionamiento("Olazabal",20,"Pepito",3000);
        Menu menu= new Menu(estacionamiento);

        //menu
        //menu.menu();
        DbManager dbManager= new DbManager();

        dbManager.realizarUnaQuery("ALTER TABLE autos ADD estado BOOLEAN;");





    }
}
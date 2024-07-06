package org.example;

import org.example.Excepciones.AutoYaExisteException;
import org.example.Excepciones.NoHayDisponibilidadException;
import org.example.dbManager.DbManager;
import org.example.log.Milogger;
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


        menu.menu();




    }
}
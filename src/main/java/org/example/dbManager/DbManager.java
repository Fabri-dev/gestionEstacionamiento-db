package org.example.dbManager;

import org.example.modelo.Auto;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

//todo: EL DB MANAGER SIRVE PARA GUARDAR UN HISTORIAL DE TODOS LOS AUTOS QUE ESTUVIERON EN EL ESTACIONAMIENTO

public class DbManager {
    //atributos
    private String url= "jdbc:mysql://localhost:3306/bd_gestionestacionamiento";
    private String usuario="root";
    private String contrasenia="";

    //constructores

    public DbManager() {

    }


    //get y set
    public String getUrl() {
        return url;
    }

    //metodos

    public Connection retornarConexionConBD() throws SQLException{
        //retorna una conexion con la BD, recordar cerrar esta conexion
        Connection conexion=null;

        conexion = DriverManager.getConnection(url,usuario,contrasenia);

        return conexion;
    }

    public boolean agregarUnAutoActivoDB(Auto nuevoAuto) throws SQLException {
        //subo un auto a la BD
        boolean flag=false;
        //hago un try-with-resources, esto signfica que se cerraran de forma automatica si hay algun error
        String statePreparada;
        try(Connection conexion= retornarConexionConBD()) {


                statePreparada= "INSERT INTO autos_activos (dniCliente, numeroDeEstacionamiento, patente, fechaDeEntrada, fechaDeSalida, estado, modelo, marca, color, tipoDeVehiculo) VALUES (?,?,?,?,?,?,?,?,?,?)";


                //Creo el state preparado, con opcion para que retorne los IDs generados(las KEYS, que son los valores por los que se identifican)
                try (PreparedStatement preparedStatement= conexion.prepareStatement(statePreparada,PreparedStatement.RETURN_GENERATED_KEYS)){


                    //agrego los valores a insertar
                    preparedStatement.setString(1, nuevoAuto.getDniCliente());
                    preparedStatement.setInt(2,nuevoAuto.getNumeroDeEstacionamiento());
                    preparedStatement.setString(3, nuevoAuto.getPatente());
                    preparedStatement.setTimestamp(4, Timestamp.valueOf(nuevoAuto.getFechaDeEntrada())); //LocalDateTime se debe traducir a TimeStamp
                    preparedStatement.setTimestamp(5,null); //cuando entra el auto no tiene fecha de salida, por lo tanto esta en null
                    preparedStatement.setBoolean(6,nuevoAuto.isEstado());
                    preparedStatement.setString(7, nuevoAuto.getModelo());
                    preparedStatement.setString(8, nuevoAuto.getMarca());
                    preparedStatement.setString(9, nuevoAuto.getColor());
                    preparedStatement.setString(10, nuevoAuto.getTipoDeVehiculo());


                    //ejecuto la query
                    int dato= preparedStatement.executeUpdate();
                    System.out.println("Filas agregadas: "+dato);


                    // Obtener el último ID insertado si hubo una insercion exitosa
                    if (dato > 0) {
                        ResultSet resultSet = preparedStatement.getGeneratedKeys();
                        if (resultSet.next()) {
                            int ultimodId = resultSet.getInt(1);
                            System.out.println("Último ID insertado: " + ultimodId);
                            nuevoAuto.setIdAuto(ultimodId);
                        }
                    }
                    //la query se ejecuto correctamente sin problemas
                    flag= true;
                }
        }
        return flag;
    }

    public boolean agregarUnAutoInactivoDB(Auto autoRecibido) throws SQLException {
        //subo un auto a la BD
        boolean flag=false;
        //hago un try-with-resources, esto signfica que se cerraran de forma automatica si hay algun error
        String statePreparada;
        try(Connection conexion= retornarConexionConBD()) {


            statePreparada= "INSERT INTO autos_inactivos (idAuto,dniCliente, numeroDeEstacionamiento, patente, fechaDeEntrada, fechaDeSalida, estado, modelo, marca, color, tipoDeVehiculo) VALUES (?,?,?,?,?,?,?,?,?,?,?)";


            //Creo el state preparado, con opcion para que retorne los IDs generados(las KEYS, que son los valores por los que se identifican)
            try (PreparedStatement preparedStatement= conexion.prepareStatement(statePreparada)){


                //agrego los valores a insertar
                preparedStatement.setInt(1,autoRecibido.getIdAuto());
                preparedStatement.setString(2, autoRecibido.getDniCliente());
                preparedStatement.setInt(3,autoRecibido.getNumeroDeEstacionamiento());
                preparedStatement.setString(4, autoRecibido.getPatente());
                preparedStatement.setTimestamp(5, Timestamp.valueOf(autoRecibido.getFechaDeEntrada())); //LocalDateTime se debe traducir a TimeStamp
                preparedStatement.setTimestamp(6,Timestamp.valueOf(autoRecibido.getFechaDeSalida())); //cuando entra el auto no tiene fecha de salida, por lo tanto esta en null
                preparedStatement.setBoolean(7,autoRecibido.isEstado());
                preparedStatement.setString(8, autoRecibido.getModelo());
                preparedStatement.setString(9, autoRecibido.getMarca());
                preparedStatement.setString(10, autoRecibido.getColor());
                preparedStatement.setString(11, autoRecibido.getTipoDeVehiculo());


                //ejecuto la query
                int dato= preparedStatement.executeUpdate();
                System.out.println("Filas agregadas: "+dato);

                //la query se ejecuto correctamente sin problemas
                flag= true;
            }
        }
        return flag;
    }


    public boolean moverUnAutoAInactivoDB(Auto autoRecibido) throws SQLException {
        boolean flag;

        //pongo el auto inactivo
        autoRecibido.setEstado(false);
        //anoto la fecha de salida
        autoRecibido.setFechaDeSalida(LocalDateTime.now());

        flag=agregarUnAutoInactivoDB(autoRecibido);

        if (flag)
        {
            //si el auto se pudo agregar al registro historico con exito, borro el auto de la tabla de activos
            flag=eliminarUnAutoXIdBD(autoRecibido.getIdAuto());
        }


        return flag;
    }

    private boolean eliminarUnAutoXIdBD(int idRecibido) throws SQLException {
        boolean flag=false;
        try(Connection connection= retornarConexionConBD()){

            String statePreparada= "DELETE FROM autos_activos WHERE idAuto = ?";

            try(PreparedStatement preparedStatement= connection.prepareStatement(statePreparada))
            {
                preparedStatement.setInt(1,idRecibido);

                int result= preparedStatement.executeUpdate();

                System.out.println("Filas afectadas: "+ result);
                flag=true;

            }
        }
        return flag;
    }


    public HashMap<Integer,Auto> obtenerMapaDeAutosActivos() throws SQLException {
        HashMap<Integer,Auto> mapaARetornar=new HashMap<>();

        Auto autoAux=null;

        try(Connection connection= retornarConexionConBD()) {
            try(Statement statement= connection.createStatement()){
                ResultSet resultSet= statement.executeQuery("SELECT * FROM `autos_activos`");
                while (resultSet.next())
                {
                    //el metodo .toLocalDateTime traduce el tipo de dato TimeStamp a LocalDateTime, de todas formas es el mismo pero en distinto lenguaje
                    autoAux=new Auto(resultSet.getString("color"),
                            resultSet.getString("dniCliente"),
                            resultSet.getBoolean("estado"),
                            resultSet.getTimestamp("fechaDeEntrada").toLocalDateTime(),
                            null,
                            resultSet.getInt("idAuto"),
                            resultSet.getString("marca"),
                            resultSet.getString("modelo"),
                            resultSet.getInt("numeroDeEstacionamiento"),
                            resultSet.getString("patente"),
                            resultSet.getString("tipoDeVehiculo")
                            );

                    mapaARetornar.put(autoAux.getNumeroDeEstacionamiento(),autoAux);

                }

            }
        }
        return mapaARetornar;
    }

    public HashMap<Integer,Auto> obtenerMapaDeAutosActivos(HashMap<Integer,Auto> mapaInicializado) throws SQLException {
        Auto autoAux=null;

        try(Connection connection= retornarConexionConBD()) {
            try(Statement statement= connection.createStatement()){
                ResultSet resultSet= statement.executeQuery("SELECT * FROM `autos_activos`");
                while (resultSet.next())
                {
                    //el metodo .toLocalDateTime traduce el tipo de dato TimeStamp a LocalDateTime, de todas formas es el mismo pero en distinto lenguaje
                    autoAux=new Auto(resultSet.getString("color"),
                            resultSet.getString("dniCliente"),
                            resultSet.getBoolean("estado"),
                            resultSet.getTimestamp("fechaDeEntrada").toLocalDateTime(),
                            null,
                            resultSet.getInt("idAuto"),
                            resultSet.getString("marca"),
                            resultSet.getString("modelo"),
                            resultSet.getInt("numeroDeEstacionamiento"),
                            resultSet.getString("patente"),
                            resultSet.getString("tipoDeVehiculo")
                    );

                    mapaInicializado.put(autoAux.getNumeroDeEstacionamiento(),autoAux);

                }
            }
        }


        return mapaInicializado;
    }

    public ArrayList<Auto> obtenerListadoDeAutosInactivos() throws SQLException {
        ArrayList<Auto> listadoDeAutos=new ArrayList<>();
        Auto autoAux=null;

        try(Connection connection= retornarConexionConBD()) {
            try(Statement statement= connection.createStatement()){
                ResultSet resultSet= statement.executeQuery("SELECT * FROM `autos_inactivos`");
                while (resultSet.next())
                {
                    //el metodo .toLocalDateTime traduce el tipo de dato TimeStamp a LocalDateTime, de todas formas es el mismo pero en distinto lenguaje
                    autoAux=new Auto(resultSet.getString("color"),
                            resultSet.getString("dniCliente"),
                            resultSet.getBoolean("estado"),
                            resultSet.getTimestamp("fechaDeEntrada").toLocalDateTime(),
                            resultSet.getTimestamp("fechaDeSalida").toLocalDateTime(),
                            resultSet.getInt("idAuto"),
                            resultSet.getString("marca"),
                            resultSet.getString("modelo"),
                            resultSet.getInt("numeroDeEstacionamiento"),
                            resultSet.getString("patente"),
                            resultSet.getString("tipoDeVehiculo")
                    );

                    listadoDeAutos.add(autoAux);

                }
            }
        }

        return listadoDeAutos;
    }


    public void realizarUnaQuery(String sqlQuery)throws SQLException{

        //todo: recordar borrar este metodo debido a que es peligroso

        try (Connection conexion= retornarConexionConBD()){
            try(Statement statement= conexion.createStatement()){
                statement.execute(sqlQuery);
                System.out.println("query realizada: ");
            }
        }

    }

}

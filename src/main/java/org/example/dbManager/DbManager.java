package org.example.dbManager;

import org.example.modelo.Auto;

import java.sql.*;

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

    public void agregarUnAuto(Auto nuevoAuto) throws SQLException {
        //subo un auto a la BD

        //hago un try-with-resources, esto signfica que se cerraran de forma automatica si hay algun error
        try(Connection conexion= retornarConexionConBD()) {



                String statePreparada= "INSERT INTO autos ( dniCliente, numeroDeEstacionamiento, patente, modelo, marca, color, tipoDeVehiculo) VALUES (?,?,?,?,?,?,?)";


                //Creo el state preparado, con opcion para que retorne los IDs generados(las KEYS, que son los valores por los que se identifican)
                try (PreparedStatement preparedStatement= conexion.prepareStatement(statePreparada,PreparedStatement.RETURN_GENERATED_KEYS)){


                    //agrego los valores a insertar
                    preparedStatement.setString(1, nuevoAuto.getDniCliente());
                    preparedStatement.setInt(2,nuevoAuto.getNumeroDeEstacionamiento());
                    preparedStatement.setString(3, nuevoAuto.getPatente());
                    preparedStatement.setString(4, nuevoAuto.getModelo());
                    preparedStatement.setString(5, nuevoAuto.getMarca());
                    preparedStatement.setString(6, nuevoAuto.getColor());
                    preparedStatement.setString(7, nuevoAuto.getTipoDeVehiculo());


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

                }
        }
    }

    public void crearTablaAutos() throws SQLException {
        try (Connection conexion= retornarConexionConBD()){

            try (Statement statement= conexion.createStatement()){

                //creo la tabla si no existe
                statement.execute("CREATE TABLE IF NOT EXISTS autos (" +
                        "  idAuto INT PRIMARY KEY AUTO_INCREMENT," +
                        "  dniCliente TEXT," +
                        "  numeroDeEstacionamiento INTEGER," +
                        "  patente TEXT," +
                        "  modelo TEXT,"+
                        "  marca TEXT,"+
                        "  color TEXT,"+
                        "  tipoDeVehiculo TEXT,"+
                        ") ENGINE=InnoDB;");

            } catch (SQLException e) {
                throw new SQLException("Error con la creacion de la tabla de autos");
            }
        }
    }

    public void realizarUnaQuery(String sqlQuery)throws SQLException{

        try (Connection conexion= retornarConexionConBD()){
            try(Statement statement= conexion.createStatement()){
                boolean result= statement.execute(sqlQuery);
                System.out.println("query realizada: "+result);
            }
        }

    }

}

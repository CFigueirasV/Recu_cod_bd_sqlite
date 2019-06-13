package bd_sqlite;

import java.sql.*;
import javax.swing.JOptionPane;

public class bd {

    public static String bd_sqlite;


    public static void createNewDatabase(String fileName) {
 
        /* 
           Método para crear una nueva Base de datos
           El parámetro: fileName , el nombre de la bd
        */
        bd_sqlite = fileName;

        String url = "jdbc:sqlite:/home/local/carli/IESdanielcastelao/NetBeansProjects/COD/DATABASE_COD/" + bd_sqlite;

        try (Connection conn = DriverManager.getConnection(url)) {

            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                JOptionPane.showMessageDialog(null, "BD Creada");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "ERROR al crear la BD, ver execepciones");
        }
    }

    public static String baseCon;

    /*
       Método para conectarse a una BD de SQLite
       Con el parametro: nombreConn pondremos el nombre de la BD a la que nos vamos a conectar.
     */
    public static void connect(String nombreBDCon) {

        baseCon = nombreBDCon;

        Connection conn = null;

        try {

            String url = "jdbc:sqlite:/home/local/carli/IESdanielcastelao/NetBeansProjects/COD/DATABASE_COD/" + baseCon;

            conn = DriverManager.getConnection(url);

            JOptionPane.showMessageDialog(null, "Conectado a la BD");

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Fallo en la conexión, pon bien el nombre de la BD");

        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /*
     Método para crear una tabla.
     Con el parámetro: nombre, nombre de las tablas que creará
     */
    public static void crearTablaPer1(String nombre) {

        String url = "jdbc:sqlite:/home/local/carli/IESdanielcastelao/NetBeansProjects/COD/DATABASE_COD/" + baseCon;

        String sql = "CREATE TABLE IF NOT EXISTS " + nombre + " (\n"
                + "	dni text NOT NULL PRIMARY KEY,\n"
                + "	nombre text NOT NULL,\n"
                + "	apellido1 text NOT NULL\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {

            stmt.execute(sql);

            JOptionPane.showMessageDialog(null, "Tabla creada");

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            JOptionPane.showConfirmDialog(null, "ERROR EN TABLA 1, ver excepción");
        }
    }

    /*
       Método de conexión a la BD      
       return conn usado por los métodos que accedan a la BD.
     */
    private Connection connect() {

        String url = "jdbc:sqlite:/home/local/carli/IESdanielcastelao/NetBeansProjects/COD/DATABASE_COD/" + baseCon;

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /*
       Método para insertar datos en una tabla.
       parametro dni
       parametro nombre
       parametro apellido1
       parametro nombreT para el nombre de la tabla.
     */
    public void insertFila1(String dni, String nombre, String apellido1, String nombreT) {

        String sql = "INSERT INTO " + nombreT + "(dni,nombre,apellido1) ";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dni);
            pstmt.setString(2, nombre);
            pstmt.setString(3, apellido1);

            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Datos insertados");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "ERROR, inserta bien los datos, por favor");
        }
    }

    /*
       Método para actualizar los datos de una tabla de la BD
       parametro dni
       parametro nombre
       parametro apellido1
       parametro nombreT para el nombre de la tabla.
    */
    public void update(String dni, String nombre, String apellido1, String tabla) {

        String sql = "UPDATE " + tabla + " SET nombre = ? , "
                + "apellido1 = ? "
                + "WHERE dni = ?";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido1);
            pstmt.setString(3, dni);

            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Datos modificados");

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "ERROR, inserta bien los datos, por favor");
        }
    }

    /*
       Método para eliminar campos de una tabla
       parametro dni
       parametro tabla para el nombre de la tabla
     */
    public void delete(String dni, String tabla) {

        String sql = "DELETE FROM " + tabla + " WHERE dni = ?";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dni);

            JOptionPane.showMessageDialog(null, "Datos borrados");

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "ERROR, asegúrate de que has insertado los datos correctamente");
        }
    }

    /*
       Método para buscar campos en una tabla de la BD.
       parametro dni
       parametro tabla para el nombre de la tabla
     */
    public void selectDni(String dni, String tabla) {

        String sql = "SELECT nombre,apellido1 FROM " + tabla + " WHERE dni = ?";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dni);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                JOptionPane.showMessageDialog(null, rs.getString("dni") + "\t" + rs.getString("nombre") + "\t"
                        + rs.getString("apellido1"));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "ERROR, asegúrate de que has insertado los datos correctamente");
        }
    }

}

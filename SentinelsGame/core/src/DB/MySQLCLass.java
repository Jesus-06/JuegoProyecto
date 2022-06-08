package DB;


import java.sql.*;

public class MySQLCLass {

    private Statement stmt= null;
    private static Connection conn;
    private static final String user= "root";
    private static final String password="admin";
    private static final String url="jdbc:mysql://localhost:3306/sentinels";

    public MySQLCLass()  {
        conn =null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn= DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
            if(conn!=null){
                System.out.println("Conexion establecida...");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
    public Connection getCOnnection(){
        return conn;
    }
    public void desconectar(){
        conn=null;
        if(conn == null){
            System.out.println("Conexion terminada...");
        }
    }
    public void insertaDatos(String perfil,float personaje_x,float personaje_y, float barra_vida,float nivel_juego, float corazones, float puntuacion){
        try {
            stmt.executeUpdate( "INSERT INTO jugadores VALUES('"+ perfil + "'," + personaje_x + ", " + personaje_y + ", " + barra_vida + ", " + nivel_juego + ", " + corazones + ", " + puntuacion + ")");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void eliminarDatos(String perfil){

        try{
        stmt.executeUpdate("DELETE FROM jugadores WHERE idJugadores = '" + perfil +"'");
    } catch (SQLException e) {
        e.printStackTrace();
    }

    }
    public ResultSet Consulta(String nombre) {
        ResultSet resultado = null;

        try {

            resultado = stmt.executeQuery("SELECT * FROM jugadores WHERE idJugadores = '" + nombre +"'");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public ResultSet Consulta2() {
        ResultSet resultado = null;

        try {

            resultado = stmt.executeQuery("SELECT id Jugadores FROM jugadores ");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }
}

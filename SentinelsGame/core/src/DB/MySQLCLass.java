package DB;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLCLass {

    private Statement stmt= null;
    private static Connection conn;
    private static final String user= "root";
    private static final String password="admin";
    private static final String url="jdbc:mysql://localhost:3306/darksentinels";

    public MySQLCLass()  {
        conn =null;
        try {
            conn= DriverManager.getConnection(url,user,password);
            if(conn!=null){
                System.out.println("Conexion establecida...");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
    public void insertaDatos(int id,String perfil,String personaje_name,String personaje_vida,String personaje_puntuacion,String personaje_x,String personaje_y){
        try {
            stmt = conn.createStatement();

            stmt.executeUpdate( "INSERT INTO perfil VALUES("+id+",'2')" );
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void recogerDatos(){

    }
    public void eliminarDatos(){

    }
}

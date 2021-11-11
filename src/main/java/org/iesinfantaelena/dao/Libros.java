package org.iesinfantaelena.dao;

import org.iesinfantaelena.modelo.AccesoDatosException;
import org.iesinfantaelena.modelo.Libro;
import org.iesinfantaelena.utils.Utilidades;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Libros {

    // Consultas a realizar en BD

    private static final String CREATE_LIBROS=" create table libros (isbn integer not null, titulo varchar(50) not null, autor varchar(50) not null, " +
            "editorial varchar(25) not null, paginas integer not null, copias integer not null, constraint isbn_pk primary key (isbn));";

    private static final String INSERT_LIBRO_QUERY="INSERT INTO libros VALUES (?,?,?,?,?,?)";
    private static final String SEARCH_LIBROS_EDITORIAL = "SELECT * FROM libros WHERE libros.editorial= ?";

    private Connection con;
    private Statement stmt;
    private ResultSet rs;
    private PreparedStatement pstmt;

    /**
     * Constructor: inicializa conexión
     */

    public Libros() throws AccesoDatosException {
        try {
            // Obtenemos la conexión
            this.con = new Utilidades().getConnection();
            this.stmt = null;
            this.rs = null;
            this.pstmt = null;
        } catch (IOException e) {
            // Error al leer propiedades
            // En una aplicación real, escribo en el log y delego
            System.err.println(e.getMessage());
            throw new AccesoDatosException(
                    "Ocurrió un error al acceder a los datos");
        } catch (SQLException sqle) {
            // En una aplicación real, escribo en el log y delego
            // System.err.println(sqle.getMessage());
            Utilidades.printSQLException(sqle);
            throw new AccesoDatosException(
                    "Ocurrió un error al acceder a los datos");
        }
    }


    /**
     * Método para cerrar la conexión
     *
     * @throws AccesoDatosException
     */
    public void cerrar() {

        if (con != null) {
            Utilidades.closeConnection(con);
        }

    }


    /**
     * Método para liberar recursos
     *
     * @throws AccesoDatosException
     */
    private void liberar() {
        try {
            // Liberamos todos los recursos pase lo que pase
            //Al cerrar un stmt se cierran los resultset asociados. Podíamos omitir el primer if. Lo dejamos por claridad.
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException sqle) {
            // En una aplicación real, escribo en el log, no delego porque
            // es error al liberar recursos
            Utilidades.printSQLException(sqle);
        }
    }

    /**
     * Metodo que muestra por pantalla los datos de la tabla cafes
     *
     * @param
     * @throws SQLException
     */

    public List<Libro> verCatalogo() throws AccesoDatosException {
        String sentencia= "SELECT * FROM LIBROS;";
        ArrayList<Libro> listalibros= new ArrayList<Libro>();

        try {
            if (stmt == null)
                stmt = con.createStatement();
                rs = stmt.executeQuery(sentencia);

            while(rs.next()){
                int isbn= rs.getInt("isbn");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                String editorial = rs.getString("editorial");
                int paginas = rs.getInt("paginas");
                int copias = rs.getInt("copias");
                listalibros.add(new Libro(isbn,titulo,autor,editorial,paginas,copias));
            }

            } catch (SQLException e) {
              e.printStackTrace();
            }

        return listalibros;
    }

    /**
     * Añade un nuevo libro a la BD
     * @throws AccesoDatosException
     */

    public void anadirLibro(Libro libro) throws AccesoDatosException {

        PreparedStatement stmt=null;
        try {
            stmt= con.prepareStatement(INSERT_LIBRO_QUERY);
            stmt.setInt(1,libro.getISBN());
            stmt.setString(2,libro.getTitulo());
            stmt.setString(3,libro.getAutor());
            stmt.setString(4,libro.getEditorial());
            stmt.setInt(5,libro.getPaginas());
            stmt.setInt(6,libro.getCopias());
            stmt.executeUpdate();
        } catch (SQLException sqle) {
            // En una aplicación real, escribo en el log y delego
            Utilidades.printSQLException(sqle);
            throw new AccesoDatosException(
                    "Ocurrió un error al acceder a los datos");

        } finally {
            try {
                // Liberamos todos los recursos pase lo que pase
                if (stmt != null) {
                    stmt.close();
                }

            } catch (SQLException sqle) {
                // En una aplicación real, escribo en el log, no delego porque
                // es error al liberar recursos
                Utilidades.printSQLException(sqle);
            }
        }


    }

    /**
     * Actualiza el numero de copias para un libro
     * @param
     * @param
     * @throws AccesoDatosException
     */

    public void actualizarCopias(Libro libro) throws AccesoDatosException {

    }

    /**
     * Borra un libro por ISBN
     * @throws AccesoDatosException
     */

    public void borrar(Libro libro) throws AccesoDatosException {


    }

    /**
     * Devulve los nombres de los campos de BD
     * @return
     * @throws AccesoDatosException
     */

    public String[] getCamposLibro() throws AccesoDatosException {

        return null;
    }


    public void obtenerLibro(int ISBN) throws AccesoDatosException {

    }

    public void librosporEditorial(String editorial) throws AccesoDatosException{
        //Sentencia SQL
        PreparedStatement stmnt=null;
        //Resultados a obtener de la sentencia SQL
        ResultSet rs=null;
        try {
            con=new Utilidades().getConnection();
            //Creacion de la sentencia
            stmnt=con.prepareStatement(SEARCH_LIBROS_EDITORIAL);
            stmnt.setString(1,editorial);
            //Ejecución de la consulta y obtencion de resultados en un ResultSet
            rs=stmnt.executeQuery();
            while (rs.next()){
                int isbn=rs.getInt("isbn");
                String titulo=rs.getString("titulo");
                String autor=rs.getString("autor");
                String editor=rs.getString("editorial");
                int paginas=rs.getInt("paginas");
                int copias=rs.getInt("copias");
                System.out.println(isbn+", "+titulo+", "+autor+", "+editor+", "+paginas+", "+copias);
            }
        } catch (IOException e) {
            // Error al leer propiedades
            // En una aplicación real, escribo en el log y delego
            System.err.println(e.getMessage());
            throw new AccesoDatosException(
                    "Ocurrió un error al acceder a los datos");
        } catch (SQLException sqle) {
            // En una aplicación real, escribo en el log y delego
            Utilidades.printSQLException(sqle);
            throw new AccesoDatosException(
                    "Ocurrió un error al acceder a los datos");
        } finally {
            try {
                // Liberamos todos los recursos pase lo que pase
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }

            } catch (SQLException sqle) {
                // En una aplicación real, escribo en el log, no delego porque
                // es error al liberar recursos
                Utilidades.printSQLException(sqle);
            }
        }

    }



}

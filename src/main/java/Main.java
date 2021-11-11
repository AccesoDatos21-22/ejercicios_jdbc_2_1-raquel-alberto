import org.iesinfantaelena.dao.Cafes;
import org.iesinfantaelena.dao.Libros;
import org.iesinfantaelena.modelo.AccesoDatosException;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {


        try {
            Libros libro1 = new Libros();
            libro1.crearTablaLibros();

        } catch (AccesoDatosException | SQLException e) {
            e.printStackTrace();
        }
    }

}
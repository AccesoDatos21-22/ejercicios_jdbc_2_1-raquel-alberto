import org.iesinfantaelena.dao.Libros;
import org.iesinfantaelena.modelo.AccesoDatosException;
import org.iesinfantaelena.modelo.Libro;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            Libros libroprueba = new Libros();
            Libro libro1 = new Libro(1111,"El principito","Antoine de Saint-Exupéry","Éditions Gallimard",100,6000000);

            List listalibros = libroprueba.verCatalogo();
            listalibros.add(libro1);

            libroprueba.anadirLibro(libro1);
            libroprueba.borrar(libro1);
            libroprueba.obtenerLibro(1111);
            libroprueba.librosporEditorial("Éditions Gallimard");
            libroprueba.actualizarCopias(libro1);

        } catch (AccesoDatosException e) {
            e.printStackTrace();
        }
    }
}
import org.iesinfantaelena.dao.Cafes;
import org.iesinfantaelena.dao.Libros;
import org.iesinfantaelena.modelo.AccesoDatosException;
import org.iesinfantaelena.modelo.Libro;

public class Main {

    public static void main(String[] args) {


        try {
            Libros libroprueba=new Libros();
            Libro libro1= new Libro(1,"El principito","Antoine de Saint-Exupéry","Éditions Gallimard",100,6000000);
            libroprueba.anadirLibro(libro1);
            System.out.println(libroprueba);




        } catch (AccesoDatosException e) {
            e.printStackTrace();
        }
    }

}
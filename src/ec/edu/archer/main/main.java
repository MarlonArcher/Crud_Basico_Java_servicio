package ec.edu.archer.main;
import ec.edu.archer.entidades.Estudiante;
import ec.edu.archer.servicio.API;
import java.util.ArrayList;

public class main {

    public static void main(String[] args) {
        API p = new API();
        ArrayList<Estudiante> estudintes = p.GETDEBER();
        for (Estudiante estudinte : estudintes) {
            System.out.println(estudinte.getNombre());
        }
    }
}

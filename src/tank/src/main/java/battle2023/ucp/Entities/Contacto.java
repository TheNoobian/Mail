package battle2023.ucp.Entities;
import java.util.List;
import java.util.ArrayList;

public class Contacto {
    private String nombre;
    private String email;
    private BandejaEntrada bandejaEntrada = new BandejaEntrada();
    private BandejaEnviados bandejaEnviados = new BandejaEnviados();


    public Contacto() {
        //constructor vacio
    }

    public Contacto(String nombre, String email) {
        this.nombre = nombre;
        setEmail(email);
    }
    
   /* public Contacto(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    } */

    public BandejaEntrada getBandejaEntrada() {
        return bandejaEntrada;
    }

    public BandejaEnviados getBandejaEnviados() {
        return bandejaEnviados;
    }

    // Getters y setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email.contains("@")) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("El email debe contener '@'");
        }
    }

}

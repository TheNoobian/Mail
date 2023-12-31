package battle2023.ucp.Entities;
import java.util.List;
import java.util.ArrayList;

public class EmailManager {
    private List<Contacto> contactos = new ArrayList<>();
    private List<Contacto> contactosFavoritos = new ArrayList<>();

    public EmailManager() {
        // Constructor vacío
    }

    public void agregarContacto(Contacto contacto) {
        contactos.add(contacto);
    }

    public void quitarContacto(Contacto contacto) {
        contactos.remove(contacto);
    }

    public void agregarContactoFavorito(Contacto contactoFavorito) {
        contactosFavoritos.add(contactoFavorito);
    }

    public List<Contacto> getContactosFavoritos() {
        return contactosFavoritos;
    }

    public void enviarEmail(Email email, Contacto remitente, List<Contacto> destinatarios) {
        remitente.getBandejaEnviados().agregarEmail(email);

        for (Contacto destinatario : destinatarios) {
            destinatario.getBandejaEntrada().agregarEmail(email);
        }
    }

    public List<Contacto> getContactos() {
        return contactos;
    }
}

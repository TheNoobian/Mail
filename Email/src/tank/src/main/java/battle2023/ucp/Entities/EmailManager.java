package battle2023.ucp.Entities;
import java.util.List;
import java.util.ArrayList;

public class EmailManager {
    private BandejaEntrada bandejaEntrada;
    private BandejaEnviados bandejaEnviados;
    private List<Contacto> contactos = new ArrayList<>();

    public EmailManager() {
        // Constructor vacío
    }

    public EmailManager(BandejaEntrada bandejaEntrada, BandejaEnviados bandejaEnviados) {
        this.bandejaEntrada = bandejaEntrada;
        this.bandejaEnviados = bandejaEnviados;
    }

    public void agregarContacto(Contacto contacto) {
        contactos.add(contacto);
    }

    public void enviarEmail(Email email, Contacto remitente, List<Contacto> destinatarios) {
        remitente.getBandejaEnviados().agregarEmail(email);

        for (Contacto destinatario : destinatarios) {
            destinatario.getBandejaEntrada().agregarEmail(email);
        }
    }

    public void recibirEmail(Email email) {
        bandejaEntrada.agregarEmail(email);
    }

    public List<Contacto> getContactos() {
        return contactos;
    }
}

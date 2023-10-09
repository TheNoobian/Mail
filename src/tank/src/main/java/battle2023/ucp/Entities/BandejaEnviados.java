package battle2023.ucp.Entities;
import java.util.List;
import java.util.ArrayList;

public class BandejaEnviados {
    private List<Email> emails = new ArrayList<>();

    public void agregarEmail(Email email) {
        emails.add(email);
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void quitarEmail(Email email) {
        emails.remove(email);
    }

    // Puedes agregar otros métodos según sea necesario
}

package battle2023.ucp.Entities;
import java.util.List;
import java.util.ArrayList;


public class BandejaEntrada {
    private List<Email> emails = new ArrayList<>();

    public void agregarEmail(Email email) {
        emails.add(email);
    }

    public List<Email> getEmails() {
        return emails;
    }

    // Puedes agregar otros métodos según sea necesario
}


package battle2023.ucp.Entities;

import battle2023.ucp.interfaces.FiltroCorreo;

public class FiltroRemitente implements FiltroCorreo {
    private Contacto filtroRemitente;

    public FiltroRemitente(Contacto filtroRemitente) {
        this.filtroRemitente = filtroRemitente;
    }

    @Override
    public boolean cumpleFiltro(Email email) {
        return email.getRemitente().equals(filtroRemitente);
    }
}

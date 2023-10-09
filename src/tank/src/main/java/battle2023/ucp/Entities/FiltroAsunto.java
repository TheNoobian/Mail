package battle2023.ucp.Entities;

import battle2023.ucp.interfaces.FiltroCorreo;

public class FiltroAsunto implements FiltroCorreo {
    private String filtroAsunto;

    public FiltroAsunto(String filtroAsunto) {
        this.filtroAsunto = filtroAsunto;
    }

    @Override
    public boolean cumpleFiltro(Email email) {
        return email.getAsunto().contains(filtroAsunto);
    }
}

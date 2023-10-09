package battle2023.ucp.Entities;

import battle2023.ucp.interfaces.FiltroCorreo;

public class FiltroContenido implements FiltroCorreo {
    private String filtroContenido;

    public FiltroContenido(String filtroContenido) {
        this.filtroContenido = filtroContenido;
    }

    @Override
    public boolean cumpleFiltro(Email email) {
        return email.getContenido().contains(filtroContenido);
    }
}

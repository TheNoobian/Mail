package battle2023.ucp.Entities;

import battle2023.ucp.interfaces.FiltroCorreo;

public class FiltroAdjunto implements FiltroCorreo {
    @Override
    public boolean cumpleFiltro(Email email) {
        return email.tieneAdjunto();
    }
}
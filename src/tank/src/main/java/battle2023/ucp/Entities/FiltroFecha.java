package battle2023.ucp.Entities;

import battle2023.ucp.interfaces.FiltroCorreo;
import java.time.LocalDateTime;

public class FiltroFecha implements FiltroCorreo {
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    public FiltroFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    @Override
    public boolean cumpleFiltro(Email email) {
        LocalDateTime fecha = email.getFechaEnvio();
        return fecha.isAfter(fechaInicio) && fecha.isBefore(fechaFin);
    }
}
package battle2023.ucp.Entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Filtro {
    private String nombre;
    private List<Email> mailsEncontrados = new ArrayList<>();
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    public Filtro(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void filter(List<Email> correos, TipoFiltro tipoFiltro, Object filtro) {
        mailsEncontrados = correos.stream()
                .filter(email -> cumpleFiltro(email, tipoFiltro, filtro))
                .collect(Collectors.toList());
    }

    public List<Email> getMailsEncontrados() {
        return mailsEncontrados;
    }

    private boolean cumpleFiltro(Email email, TipoFiltro tipoFiltro, Object filtro) {
        switch (tipoFiltro) {
            case ASUNTO:
                return email.getAsunto().contains((String) filtro);
            case CONTENIDO:
                return email.getContenido().contains((String) filtro);
            case FECHA:
                return estaDentroDelRango(email.getFechaEnvio());
            case REMITENTE:
                return email.getRemitente().equals((Contacto) filtro);
            default:
                return false;
        }
    }
    

    private boolean estaDentroDelRango(LocalDateTime fecha) {
        if (fechaInicio != null && fechaFin != null) {
            return fecha.isAfter(fechaInicio) && fecha.isBefore(fechaFin);
        }
        return true; // Si no se establece un rango, no se aplica el filtro de fecha
    }


    public enum TipoFiltro {
        ASUNTO, CONTENIDO, FECHA, REMITENTE
    }
}
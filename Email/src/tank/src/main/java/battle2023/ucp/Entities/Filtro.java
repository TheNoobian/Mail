package battle2023.ucp.Entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Filtro {
    private String nombre;
    private Predicate<Email> predicado;
    private List<Email> mailsEncontrados = new ArrayList<>();
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    public Filtro(String nombre, Predicate<Email> predicado) {
        this.nombre = nombre;
        this.predicado = predicado;
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

    public void aplicarFiltro(Contacto contacto) {
        for (Email email : contacto.getBandejaEntrada().getEmails()) {
            if (predicado.test(email) && estaDentroDelRango(email.getFechaEnvio())) {
                mailsEncontrados.add(email);
            }
        }
    }

    public List<Email> getMailsEncontrados() {
        return mailsEncontrados;
    }

    private boolean estaDentroDelRango(LocalDateTime fecha) {
        if (fechaInicio != null && fechaFin != null) {
            return fecha.isAfter(fechaInicio) && fecha.isBefore(fechaFin);
        }
        return true; // Si no se establece un rango, no se aplica el filtro de fecha
    }
}
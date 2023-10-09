package battle2023.ucp.Entities;
import java.util.List;
import java.util.ArrayList;
import java.lang.String;
import java.time.LocalDateTime;

public class Email {
    private String asunto;
    private String contenido;
    private Contacto remitente;
    private LocalDateTime fechaEnvio;
    private List<Contacto> para = new ArrayList<>();
    private boolean tieneAdjunto;

    public Email() {
        // Constructor vacío
    }

    public Email(String asunto, String contenido, Contacto remitente, List<Contacto> para) {
        this.asunto = asunto;
        this.contenido = contenido;
        this.remitente = remitente;
        this.para = para;
        this.fechaEnvio = LocalDateTime.now();
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public boolean tieneAdjunto() {
        return tieneAdjunto;
    }

    public void setTieneAdjunto(boolean tieneAdjunto) {
        this.tieneAdjunto = tieneAdjunto;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Contacto getRemitente() {
        return remitente;
    }

    public void setRemitente(Contacto remitente) {
        this.remitente = remitente;
    }

    public List<Contacto> getPara() {
        return para;
    }

    public void agregarDestinatario(Contacto contacto) {
        para.add(contacto);
    }
}

package battle2023.ucp.Entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import battle2023.ucp.interfaces.FiltroCorreo;

public class Filtro {
    private String nombre;
    private List<Email> mailsEncontrados = new ArrayList<>();


    public Filtro(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void filter(List<Email> correos, FiltroCorreo filtroCorreo) {
        mailsEncontrados = correos.stream()
                .filter(email -> filtroCorreo.cumpleFiltro(email))
                .collect(Collectors.toList());
    }

    public List<Email> getMailsEncontrados() {
        return mailsEncontrados;
    }

}
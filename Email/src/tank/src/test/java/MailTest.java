import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;

import battle2023.ucp.Entities.BandejaEntrada;
import battle2023.ucp.Entities.BandejaEnviados;
import battle2023.ucp.Entities.Contacto;
import battle2023.ucp.Entities.Email;
import battle2023.ucp.Entities.EmailManager;
import battle2023.ucp.Entities.Filtro;

import java.util.function.Predicate;

public class MailTest {
    private EmailManager emailManager;
    private Contacto remitente;
    private Contacto destinatario1;
    private Contacto destinatario2;
    private Contacto contacto;
    private Filtro filtroAsunto;
    private Filtro filtroRemitente;
    private Filtro filtroContenido;


    @Before
    public void setUp() {
        emailManager = new EmailManager();
        remitente = new Contacto("Nombre Remitente", "remitente@demo.com");
        destinatario1 = new Contacto("Nombre Destinatario1", "destinatario1@demo.com");
        destinatario2 = new Contacto("Nombre Destinatario2", "destinatario2@demo.com");
        emailManager.agregarContacto(destinatario1);
        emailManager.agregarContacto(destinatario2);
    }


    @Test
        public void existeEmailManager()
    {
        assertNotNull(emailManager);
    }

    @Test
        public void existeMail()
    {
        Email Email = new Email();
        assertNotNull(Email);
    }

    @Test
        public void existeMailConstructor()
    {
        Email Email = new Email("Prueba", "Esto es una prueba", remitente, emailManager.getContactos());
        assertNotNull(Email);
    }

    


    @Test
    public void testLlegaEmail() {
        Email email = new Email();
        email.setAsunto("Prueba");
        email.setContenido("Este es un correo de prueba");
        email.setRemitente(remitente);

        emailManager.enviarEmail(email, remitente, emailManager.getContactos());

        assertEquals(1, remitente.getBandejaEnviados().getEmails().size());
        assertEquals(1, destinatario1.getBandejaEntrada().getEmails().size());
        assertEquals(1, destinatario2.getBandejaEntrada().getEmails().size());
    }


    @Test
    public void testBandejaEntradaVacia() {
        assertTrue(remitente.getBandejaEntrada().getEmails().isEmpty());
        assertTrue(destinatario1.getBandejaEntrada().getEmails().isEmpty());
        assertTrue(destinatario2.getBandejaEntrada().getEmails().isEmpty());
    }

    @Test
    public void testBandejaEnviadosVacia() {
        assertTrue(remitente.getBandejaEnviados().getEmails().isEmpty());
        assertTrue(destinatario1.getBandejaEnviados().getEmails().isEmpty());
        assertTrue(destinatario2.getBandejaEnviados().getEmails().isEmpty());
    }

    @Test
    public void testAsuntoCorrectoEmail() {
        Email email = new Email();
        email.setAsunto("Prueba");
        email.setContenido("Este es un correo de prueba");
        email.setRemitente(remitente);

        emailManager.enviarEmail(email, remitente, emailManager.getContactos());


        // Verificar el asunto en las bandejas de entrada
        assertEquals("Prueba", destinatario1.getBandejaEntrada().getEmails().get(0).getAsunto());

        assertEquals("Prueba", destinatario2.getBandejaEntrada().getEmails().get(0).getAsunto());
    }

    @Test
    public void testContenidoCorrectoEmail() {
        Email email = new Email();
        email.setAsunto("Prueba");
        email.setContenido("Este es un correo de prueba");
        email.setRemitente(remitente);

        emailManager.enviarEmail(email, remitente, emailManager.getContactos());

        // Verificar el contenido de los correos en las bandejas de entrada
        assertEquals("Este es un correo de prueba", destinatario1.getBandejaEntrada().getEmails().get(0).getContenido());

        assertEquals("Este es un correo de prueba", destinatario2.getBandejaEntrada().getEmails().get(0).getContenido());
    }

    @Test
    public void testListaDeContactosAgregados() {
        List<Contacto> contactosAgregados = emailManager.getContactos();

        // Verificar que la lista de contactos tenga los contactos correctos
        assertTrue(contactosAgregados.contains(destinatario1));
        assertTrue(contactosAgregados.contains(destinatario2));
    }

    @Test
    public void testCantidadListaDeContactosAgregados() {
        List<Contacto> contactosAgregados = emailManager.getContactos();

        // Verificar que la lista de contactos tenga los contactos correctos
        assertEquals(2, contactosAgregados.size()); // Debería haber dos contactos
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMailSinArroba(){

        Contacto contacto = new Contacto("Prueba", "emailsinarroba.com");

        assertNull(contacto);
    }

    @Test
    public void testFiltroAsunto() {

         // Crear un contacto de ejemplo
         contacto = new Contacto("Nombre Contacto", "contacto@ejemplo.com");

         // Crear filtros para asunto, remitente y contenido
         Predicate<Email> filtroAsuntoPredicate = email -> email.getAsunto().contains("Importante");

         filtroAsunto = new Filtro("Filtrar por Asunto", filtroAsuntoPredicate);
 
         // Crear correos de ejemplo
         Email correo1 = new Email();
         correo1.setAsunto("Correo Importante");
         correo1.setRemitente(remitente);
 
         Email correo2 = new Email();
         correo2.setAsunto("Pedidos Ya");
         correo2.setRemitente(remitente);
 
         // Agregar los correos al contacto
         contacto.getBandejaEntrada().agregarEmail(correo1);
         contacto.getBandejaEntrada().agregarEmail(correo2);
 
         // Aplicar el filtro de asunto al contacto
         filtroAsunto.aplicarFiltro(contacto);
 
         // Verificar que solo se encuentra el correo con asunto "Correo Importante"
         assertEquals(1, filtroAsunto.getMailsEncontrados().size());
         assertTrue(filtroAsunto.getMailsEncontrados().contains(correo1));
         assertFalse(filtroAsunto.getMailsEncontrados().contains(correo2));
     }

    @Test
    public void testFiltroRemitente() {

        // Crear un contacto de ejemplo
         contacto = new Contacto("Nombre Contacto", "contacto@ejemplo.com");

         // Crear filtros para asunto, remitente y contenido
         Predicate<Email> filtroRemitentePredicate = email -> email.getRemitente().equals(remitente);

         filtroRemitente = new Filtro("Filtrar por Remitente", filtroRemitentePredicate);


        // Crear correos de ejemplo
        Email correo1 = new Email();
        correo1.setAsunto("Correo 1");
        correo1.setRemitente(remitente);

        Email correo2 = new Email();
        correo2.setAsunto("Correo 2");
        correo2.setRemitente(destinatario2);

        // Agregar los correos al contacto
        contacto.getBandejaEntrada().agregarEmail(correo1);
        contacto.getBandejaEntrada().agregarEmail(correo2);

        // Aplicar el filtro de remitente al contacto
        filtroRemitente.aplicarFiltro(contacto);

        // Verificar que solo se encuentra el correo con remitente "remitente@dominio.com"
        assertEquals(1, filtroRemitente.getMailsEncontrados().size());
        assertTrue(filtroRemitente.getMailsEncontrados().contains(correo1));
        assertFalse(filtroRemitente.getMailsEncontrados().contains(correo2));
    }

    @Test
    public void testFiltroContenido() {

        // Crear un contacto de ejemplo
         contacto = new Contacto("Nombre Contacto", "contacto@ejemplo.com");

         // Crea filtros para contenido
         Predicate<Email> filtroContenidoPredicate = email -> email.getContenido().contains("palabra clave");
 
         filtroContenido = new Filtro("Filtrar por Contenido", filtroContenidoPredicate);


        // Crear correos de ejemplo
        Email correo1 = new Email();
        correo1.setAsunto("Correo 1");
        correo1.setContenido("Este es un correo con una +++ palabra clave +++");

        Email correo2 = new Email();
        correo2.setAsunto("Correo 2");
        correo2.setContenido("Animal Bar con Fuego Pedido");

        // Agregar los correos al contacto
        contacto.getBandejaEntrada().agregarEmail(correo1);
        contacto.getBandejaEntrada().agregarEmail(correo2);

        // Aplicar el filtro de contenido al contacto
        filtroContenido.aplicarFiltro(contacto);

        // Verificar que solo se encuentra el correo con contenido que contiene la palabra clave
        assertEquals(1, filtroContenido.getMailsEncontrados().size());
        assertTrue(filtroContenido.getMailsEncontrados().contains(correo1));
        assertFalse(filtroContenido.getMailsEncontrados().contains(correo2));
    }


    @Test
    public void testFiltroFechaEnvio() {
        // Crear un contacto de ejemplo
        Contacto contactoPrueba = new Contacto("Nombre Contacto", "contacto@ejemplo.com");

        // Crear un filtro de fecha de envío para correos enviados entre marzo y abril de 2023

        Predicate<Email> filtroFechaEnvioPredicate = email ->
            email.getFechaEnvio().isAfter(LocalDateTime.of(2023, 3, 1, 0, 0))
            && email.getFechaEnvio().isBefore(LocalDateTime.of(2023, 4, 30, 23, 59));

        Filtro filtroFechaEnvio = new Filtro("Filtrar por Fecha de Envío", filtroFechaEnvioPredicate);
        filtroFechaEnvio.setFechaInicio(LocalDateTime.of(2023, 3, 1, 0, 0));
        filtroFechaEnvio.setFechaFin(LocalDateTime.of(2023, 4, 30, 23, 59));

        // Crear correos de ejemplo con fechas de envío dentro y fuera del rango
        Email correo1 = new Email("Asunto 1", "Contenido 1", null, null);
        correo1.setFechaEnvio(LocalDateTime.of(2023, 3, 15, 10, 0)); // Fecha dentro del rango

        Email correo2 = new Email("Asunto 2", "Contenido 2", null, null);
        correo2.setFechaEnvio(LocalDateTime.of(2023, 5, 20, 14, 30)); // Fecha fuera del rango

        // Agregar los correos al contacto
        contactoPrueba.getBandejaEntrada().agregarEmail(correo1);
        contactoPrueba.getBandejaEntrada().agregarEmail(correo2);

        // Aplicar el filtro de fecha de envío al contacto
        filtroFechaEnvio.aplicarFiltro(contactoPrueba);

        // Verificar que solo se encuentra el correo1 dentro del rango de fechas
        List<Email> correosFiltrados = filtroFechaEnvio.getMailsEncontrados();
        assertEquals(1, correosFiltrados.size());
        assertTrue(correosFiltrados.contains(correo1));
        assertFalse(correosFiltrados.contains(correo2));
    }


}





    


    




 

























    

    

    









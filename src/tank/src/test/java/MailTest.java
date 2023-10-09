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
        // Crear un filtro para Asunto
        Filtro filtroAsunto = new Filtro("Filtrar por Asunto");

        //crea un contacto
        contacto = new Contacto("Nombre Contacto", "contacto@ejemplo.com");

        // Crear correos de ejemplo con diferentes asuntos
        Email correo1 = new Email("Importante: Reunión", "Contenido 1", null, null);
        Email correo2 = new Email("Notificación", "Contenido 2", null, null);
        Email correo3 = new Email("Reunión de Equipo", "Contenido 3", null, null);

        // Agregar los correos a las bandejas
        
        contacto.getBandejaEntrada().agregarEmail(correo1);
        contacto.getBandejaEntrada().agregarEmail(correo2);
        contacto.getBandejaEntrada().agregarEmail(correo3);

        // Aplicar el filtro de Asunto para buscar correos con "Importante"
        filtroAsunto.filter(contacto.getBandejaEntrada().getEmails(), Filtro.TipoFiltro.ASUNTO, "Importante");

        // Verificar que solo se encuentra el correo1 con asunto "Importante"
        List<Email> correosFiltrados = filtroAsunto.getMailsEncontrados();
        assertEquals(1, correosFiltrados.size());
        assertTrue(correosFiltrados.contains(correo1));
        assertFalse(correosFiltrados.contains(correo2));
        assertFalse(correosFiltrados.contains(correo3));
    }


    @Test
    public void testFiltroContenido() {
        // Crear un filtro para Contenido
        Filtro filtroContenido = new Filtro("Filtrar por Contenido");

        contacto = new Contacto("Nombre Contacto", "contacto@ejemplo.com");

        // Crear correos de ejemplo con diferentes contenidos
        Email correo1 = new Email("Asunto 1", "Este es un correo importante.", null, null);
        Email correo2 = new Email("Asunto 2", "Contenido sin relevancia.", null, null);
        Email correo3 = new Email("Asunto 3", "Correo con información clave.", null, null);

        // Agregar los correos a las bandejas
        contacto.getBandejaEntrada().agregarEmail(correo1);
        contacto.getBandejaEntrada().agregarEmail(correo2);
        contacto.getBandejaEntrada().agregarEmail(correo3);

        // Aplicar el filtro de Contenido para buscar correos con "importante"
        filtroContenido.filter(contacto.getBandejaEntrada().getEmails(), Filtro.TipoFiltro.CONTENIDO, "importante");

        // Verificar que solo se encuentra el correo1 con contenido "importante"
        List<Email> correosFiltrados = filtroContenido.getMailsEncontrados();
        assertEquals(1, correosFiltrados.size());
        assertTrue(correosFiltrados.contains(correo1));
        assertFalse(correosFiltrados.contains(correo2));
        assertFalse(correosFiltrados.contains(correo3));
    }

    @Test
    public void testFiltroFecha() {
        // Crear un filtro para Fecha
        Filtro filtroFecha = new Filtro("Filtrar por Fecha");

        contacto = new Contacto("Nombre Contacto", "contacto@ejemplo.com");

        // Crear correos de ejemplo con diferentes fechas de envío
        Email correo1 = new Email("Asunto 1", "Contenido 1", null, null);
        correo1.setFechaEnvio(LocalDateTime.of(2023, 3, 15, 10, 0)); // Fecha dentro del rango

        Email correo2 = new Email("Asunto 2", "Contenido 2", null, null);
        correo2.setFechaEnvio(LocalDateTime.of(2023, 6, 20, 14, 30)); // Fecha fuera del rango

        // Agregar los correos a las bandejas
        contacto.getBandejaEntrada().agregarEmail(correo1);
        contacto.getBandejaEntrada().agregarEmail(correo2);

        // Definir un rango de fechas para el filtro (marzo a mayo de 2023)
        filtroFecha.setFechaInicio(LocalDateTime.of(2023, 3, 1, 0, 0));
        filtroFecha.setFechaFin(LocalDateTime.of(2023, 5, 31, 23, 59));

        // Aplicar el filtro de Fecha para buscar correos dentro del rango
        filtroFecha.filter(contacto.getBandejaEntrada().getEmails(), Filtro.TipoFiltro.FECHA, null);

        // Verificar que solo se encuentra el correo1 dentro del rango de fechas
        List<Email> correosFiltrados = filtroFecha.getMailsEncontrados();
        assertEquals(1, correosFiltrados.size());
        assertTrue(correosFiltrados.contains(correo1));
        assertFalse(correosFiltrados.contains(correo2));
    }

    @Test
    public void testFiltroRemitente() {
        // Crear un filtro para Remitente
        Filtro filtroRemitente = new Filtro("Filtrar por Remitente");

        contacto = new Contacto("Nombre Contacto", "contacto@ejemplo.com");

        // Crear correos de ejemplo con diferentes remitentes
        Contacto remitente1 = new Contacto("Remitente 1", "remitente1@ejemplo.com");
        Contacto remitente2 = new Contacto("Remitente 2", "remitente2@ejemplo.com");
        Contacto remitente3 = new Contacto("Remitente 3", "remitente3@ejemplo.com");

        Email correo1 = new Email("Asunto 1", "Contenido 1", remitente1, null);
        Email correo2 = new Email("Asunto 2", "Contenido 2", remitente2, null);
        Email correo3 = new Email("Asunto 3", "Contenido 3", remitente3, null);

        // Agregar los correos a las bandejas
        contacto.getBandejaEntrada().agregarEmail(correo1);
        contacto.getBandejaEntrada().agregarEmail(correo2);
        contacto.getBandejaEntrada().agregarEmail(correo3);

        // Definir el remitente para el filtro
        filtroRemitente.filter(contacto.getBandejaEntrada().getEmails(), Filtro.TipoFiltro.REMITENTE, remitente2);

        // Verificar que solo se encuentra el correo2 con remitente2
        List<Email> correosFiltrados = filtroRemitente.getMailsEncontrados();
        assertEquals(1, correosFiltrados.size());
        assertTrue(correosFiltrados.contains(correo2));
        assertFalse(correosFiltrados.contains(correo1));
        assertFalse(correosFiltrados.contains(correo3));
    }


    @Test
    public void TestTieneAdjunto(){
        
    Filtro filtroAdjunto = new Filtro("Filtrar por Adjunto");

    Contacto contacto = new Contacto("pedro", "pedro@gmail.com");

    // Crear correos de ejemplo con y sin adjuntos
    Email correo1 = new Email("Correo 1", "Contenido 1", null, null);
    correo1.setTieneAdjunto(true);
    
    Email correo2 = new Email("Correo 2", "Contenido 2", null, null);
    correo2.setTieneAdjunto(false);
    
    // Agregar los correos a las bandejas
        contacto.getBandejaEntrada().agregarEmail(correo1);
        contacto.getBandejaEntrada().agregarEmail(correo2);
    
    // Aplicar el filtro de Adjunto para buscar correos con adjunto
    filtroAdjunto.filter(contacto.getBandejaEntrada().getEmails(), Filtro.TipoFiltro.ADJUNTO, null);
    
    // Verificar que solo se encuentra el correo1 con adjunto
    List<Email> correosFiltrados = filtroAdjunto.getMailsEncontrados();
    assertEquals(1, correosFiltrados.size());
    assertTrue(correosFiltrados.contains(correo1));
    assertFalse(correosFiltrados.contains(correo2));  
    }


    @Test
    public void testQuitarEmailDeBandejaDeEntrada() {
        // Crear un contacto y una bandeja de entrada
        Contacto contacto = new Contacto("Nombre Contacto", "contacto@ejemplo.com");

        // Crear un correo de ejemplo y agregarlo a la bandeja de entrada
        Email correo = new Email("Asunto 1", "Contenido 1", null, null);
        contacto.getBandejaEntrada().agregarEmail(correo);

        // Verificar que el correo esté en la bandeja de entrada
        assertTrue(contacto.getBandejaEntrada().getEmails().contains(correo));

        // Queremos quitar el correo de la bandeja de entrada
        contacto.getBandejaEntrada().quitarEmail(correo);

        // Verificar que el correo haya sido eliminado de la bandeja de entrada
        assertFalse(contacto.getBandejaEntrada().getEmails().contains(correo));
        assertEquals(0, contacto.getBandejaEntrada().getEmails().size());
    }


    @Test
    public void testQuitarEmailDeBandejaDeSalida() {
        // Crear un contacto y una bandeja de enviados
        Contacto contacto = new Contacto("Nombre Contacto", "contacto@ejemplo.com");

        // Crear un correo de ejemplo y agregarlo a la bandeja de enviados
        Email correo = new Email("Asunto 1", "Contenido 1", null, null);
        contacto.getBandejaEnviados().agregarEmail(correo);

        // Verificar que el correo esté en la bandeja de enviados
        assertTrue(contacto.getBandejaEnviados().getEmails().contains(correo));

        // Queremos quitar el correo de la bandeja de enviados
        contacto.getBandejaEnviados().quitarEmail(correo);

        // Verificar que el correo haya sido eliminado de la bandeja de enviados
        assertFalse(contacto.getBandejaEnviados().getEmails().contains(correo));
        assertEquals(0, contacto.getBandejaEnviados().getEmails().size());
    }











    /*@Test
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
    } */


}





    


    




 

























    

    

    









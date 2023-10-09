import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import battle2023.ucp.Entities.Contacto;
import battle2023.ucp.Entities.Email;
import battle2023.ucp.Entities.EmailManager;
import battle2023.ucp.Entities.Filtro;
import battle2023.ucp.Entities.FiltroAdjunto;
import battle2023.ucp.Entities.FiltroAsunto;
import battle2023.ucp.Entities.FiltroContenido;
import battle2023.ucp.Entities.FiltroFecha;
import battle2023.ucp.Entities.FiltroRemitente;
import battle2023.ucp.interfaces.FiltroCorreo;



public class MailTest {
    private EmailManager emailManager;
    private Contacto remitente;
    private Contacto destinatario1;
    private Contacto destinatario2;


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
    public void testQuitarEmailDeBandejaDeEnviados() {
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

    @Test
    public void testQuitarContactoDeEmailManager() {
        // Crear una instancia de EmailManager
        EmailManager emailManager = new EmailManager();

        // Crear un contacto de ejemplo y agregarlo a la lista de contactos
        Contacto contacto = new Contacto("Nombre Contacto", "contacto@ejemplo.com");
        emailManager.agregarContacto(contacto);

        // Verificar que el contacto esté en la lista de contactos
        assertTrue(emailManager.getContactos().contains(contacto));

        // Queremos quitar el contacto de la lista de contactos
        emailManager.quitarContacto(contacto);

        // Verificar que el contacto haya sido eliminado de la lista de contactos
        assertFalse(emailManager.getContactos().contains(contacto));
        assertEquals(0, emailManager.getContactos().size());
    }

    @Test
    public void testAgregarYObtenerContactosFavoritos() {
        // Crear una instancia de EmailManager
        EmailManager emailManager = new EmailManager();

        // Crear un contacto favorito y agregarlo a la lista de contactos favoritos
        Contacto favorito1 = new Contacto("Nombre Favorito 1", "favorito1@ejemplo.com");
        emailManager.agregarContactoFavorito(favorito1);

        // Obtener la lista de contactos favoritos
        List<Contacto> favoritos = emailManager.getContactosFavoritos();

        // Verificar que el contacto favorito esté en la lista de contactos favoritos
        assertTrue(favoritos.contains(favorito1));

        // Crear otro contacto favorito y agregarlo
        Contacto favorito2 = new Contacto("Nombre Favorito 2", "favorito2@ejemplo.com");
        emailManager.agregarContactoFavorito(favorito2);

        // Obtener la lista de contactos favoritos nuevamente
        favoritos = emailManager.getContactosFavoritos();

        // Verificar que ambos contactos favoritos estén en la lista de contactos favoritos
        assertTrue(favoritos.contains(favorito1));
        assertTrue(favoritos.contains(favorito2));

        // Verificar el tamaño de la lista
        assertEquals(2, favoritos.size());
    }


    @Test
    public void testFiltroAsunto2() {
        // Crear varios correos de ejemplo
        Email correo1 = new Email("Asunto importante", "Contenido del correo 1", null, null);
        Email correo2 = new Email("Correo sin importancia", "Contenido del correo 2", null, null);
        Email correo3 = new Email("Asunto importante", "Contenido del correo 3", null, null);

        // Crear un filtro de Asunto
        FiltroCorreo filtroAsunto = new FiltroAsunto("importante");

        Contacto contacto = new Contacto("pedro", "pedro@gmail.com");

        // Aplicar el filtro a los correos
        contacto.getBandejaEntrada().agregarEmail(correo1);
        contacto.getBandejaEntrada().agregarEmail(correo2);
        contacto.getBandejaEntrada().agregarEmail(correo3);

        Filtro filtro = new Filtro("Filtro Asunto");
        filtro.filter(contacto.getBandejaEntrada().getEmails(), filtroAsunto);

        // Verificar que los correos correctos estén en los correos encontrados
        assertTrue(filtro.getMailsEncontrados().contains(correo1));
        assertTrue(filtro.getMailsEncontrados().contains(correo3));
        assertFalse(filtro.getMailsEncontrados().contains(correo2));

        // Verificar el número de correos encontrados
        assertEquals(2, filtro.getMailsEncontrados().size());
    }

    @Test
    public void testFiltroRemitente2() {
        // Crear varios correos de ejemplo
        Contacto remitente1 = new Contacto("Remitente 1", "remitente1@ejemplo.com");
        Contacto remitente2 = new Contacto("Remitente 2", "remitente2@ejemplo.com");
        Contacto remitente3 = new Contacto("Remitente 3", "remitente3@ejemplo.com");

        Email correo1 = new Email("Asunto 1", "Contenido del correo 1", remitente1, null);
        Email correo2 = new Email("Asunto 2", "Contenido del correo 2", remitente2, null);
        Email correo3 = new Email("Asunto 3", "Contenido del correo 3", remitente3, null);

        // Crear un filtro de Remitente
        FiltroCorreo filtroRemitente = new FiltroRemitente(remitente1);

        Contacto contacto = new Contacto("pedro", "pedro@gmail.com");

        // Aplicar el filtro a los correos
        contacto.getBandejaEntrada().agregarEmail(correo1);
        contacto.getBandejaEntrada().agregarEmail(correo2);
        contacto.getBandejaEntrada().agregarEmail(correo3);

        Filtro filtro = new Filtro("Filtro Remitente");
        filtro.filter(contacto.getBandejaEntrada().getEmails(), filtroRemitente);

        // Verificar que los correos correctos estén en los correos encontrados
        assertTrue(filtro.getMailsEncontrados().contains(correo1));
        assertFalse(filtro.getMailsEncontrados().contains(correo2));
        assertFalse(filtro.getMailsEncontrados().contains(correo3));

        // Verificar el número de correos encontrados
        assertEquals(1, filtro.getMailsEncontrados().size());
    }


    @Test
    public void testFiltroFecha2() {
        // Crear varios correos de ejemplo con fechas diferentes

        Contacto contacto = new Contacto("pedro", "pedro@gmail.com");

        Email correo1 = new Email("Asunto 1", "Contenido del correo 1", new Contacto("Remitente 1", "remitente1@ejemplo.com"), new ArrayList<>());
        Email correo2 = new Email("Asunto 2", "Contenido del correo 2", new Contacto("Remitente 2", "remitente2@ejemplo.com"), new ArrayList<>());
        Email correo3 = new Email("Asunto 3", "Contenido del correo 3", new Contacto("Remitente 3", "remitente3@ejemplo.com"), new ArrayList<>());

        correo1.setFechaEnvio(LocalDateTime.of(2023, 1, 1, 10, 0)); // Fecha dentro del rango
        correo2.setFechaEnvio(LocalDateTime.of(2023, 2, 1, 12, 0)); // Fecha dentro del rango
        correo3.setFechaEnvio(LocalDateTime.of(2023, 3, 1, 14, 0)); // Fecha dentro del rango


        // Crear un filtro de Fecha que incluye un rango de fechas
        LocalDateTime fechaInicio = LocalDateTime.of(2023, 1, 15, 0, 0);
        LocalDateTime fechaFin = LocalDateTime.of(2023, 2, 28, 23, 59);
        FiltroCorreo filtroFecha = new FiltroFecha(fechaInicio, fechaFin);

        // Aplicar el filtro a los correos
        contacto.getBandejaEntrada().agregarEmail(correo1);
        contacto.getBandejaEntrada().agregarEmail(correo2);
        contacto.getBandejaEntrada().agregarEmail(correo3);

        Filtro filtro = new Filtro("Filtro Fecha");
        filtro.filter(contacto.getBandejaEntrada().getEmails(), filtroFecha);

        // Verificar que los correos correctos estén en los correos encontrados
        assertFalse(filtro.getMailsEncontrados().contains(correo1));
        assertTrue(filtro.getMailsEncontrados().contains(correo2));
        assertFalse(filtro.getMailsEncontrados().contains(correo3));

        // Verificar el número de correos encontrados
        assertEquals(1, filtro.getMailsEncontrados().size());
    }

    @Test
    public void testFiltroAdjunto() {
        // Crear varios correos de ejemplo con y sin adjunto
        Email correo1 = new Email("Asunto 1", "Contenido del correo 1", new Contacto("Remitente 1", "remitente1@ejemplo.com"), new ArrayList<>());
        Email correo2 = new Email("Asunto 2", "Contenido del correo 2", new Contacto("Remitente 2", "remitente2@ejemplo.com"), new ArrayList<>());
        Email correo3 = new Email("Asunto 3", "Contenido del correo 3", new Contacto("Remitente 3", "remitente3@ejemplo.com"), new ArrayList<>());
        correo3.setTieneAdjunto(true);

        // Crear un filtro de Adjunto
        FiltroCorreo filtroAdjunto = new FiltroAdjunto(); // Buscar correos con adjunto

        Contacto contacto = new Contacto("pedro", "pedro@gmail.com");

        // Aplicar el filtro a los correos
        contacto.getBandejaEntrada().agregarEmail(correo1);
        contacto.getBandejaEntrada().agregarEmail(correo2);
        contacto.getBandejaEntrada().agregarEmail(correo3);

        Filtro filtro = new Filtro("Filtro Adjunto");
        filtro.filter(contacto.getBandejaEntrada().getEmails(), filtroAdjunto);

        // Verificar que los correos correctos estén en los correos encontrados
        assertFalse(filtro.getMailsEncontrados().contains(correo1));
        assertFalse(filtro.getMailsEncontrados().contains(correo2));
        assertTrue(filtro.getMailsEncontrados().contains(correo3));

        // Verificar el número de correos encontrados
        assertEquals(1, filtro.getMailsEncontrados().size());
    }


    @Test
    public void testFiltroContenido2() {
        // Crear varios correos de ejemplo con diferentes contenidos
        Email correo1 = new Email("Asunto 1", "Este es un contenido de prueba", new Contacto("Remitente 1", "remitente1@ejemplo.com"), new ArrayList<>());
        Email correo2 = new Email("Asunto 2", "Contenido importante", new Contacto("Remitente 2", "remitente2@ejemplo.com"), new ArrayList<>());
        Email correo3 = new Email("Asunto 3", "Texto sin relevancia", new Contacto("Remitente 3", "remitente3@ejemplo.com"), new ArrayList<>());

        // Crear un filtro de Contenido
        FiltroCorreo filtroContenido = new FiltroContenido("importante");

        Contacto contacto = new Contacto("pedro", "pedro@gmail.com");

        // Aplicar el filtro a los correos
        contacto.getBandejaEntrada().agregarEmail(correo1);
        contacto.getBandejaEntrada().agregarEmail(correo2);
        contacto.getBandejaEntrada().agregarEmail(correo3);

        Filtro filtro = new Filtro("Filtro Contenido");
        filtro.filter(contacto.getBandejaEntrada().getEmails(), filtroContenido);

        // Verificar que los correos correctos estén en los correos encontrados
        assertFalse(filtro.getMailsEncontrados().contains(correo1));
        assertTrue(filtro.getMailsEncontrados().contains(correo2));
        assertFalse(filtro.getMailsEncontrados().contains(correo3));

        // Verificar el número de correos encontrados
        assertEquals(1, filtro.getMailsEncontrados().size());
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





    


    




 

























    

    

    









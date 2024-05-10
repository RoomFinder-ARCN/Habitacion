package arcn.roomfinder.habitacion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import arcn.roomfinder.habitacion.application.HabitacionServicioImpl;
import arcn.roomfinder.habitacion.domain.exception.RoomFinderException;
import arcn.roomfinder.habitacion.domain.model.EstadoHabitacion;
import arcn.roomfinder.habitacion.domain.model.Habitacion;
import arcn.roomfinder.habitacion.domain.model.Servicio;
import arcn.roomfinder.habitacion.domain.model.TipoHabitacion;
import arcn.roomfinder.habitacion.domain.repository.HabitacionRepositorio;

@RunWith(MockitoJUnitRunner.class)
public class HabitacionServicioTest {

    @Mock
    private HabitacionRepositorio habitacionRepositorio;

    @InjectMocks
    private HabitacionServicioImpl habitacionServicio;

    private Habitacion habitacionCorrecta;

    public HabitacionServicioTest() {
        init();
    }
    
    private void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Before
    public void setUp() {
        Set<Servicio> servicios = Set.of(
            Servicio.valueOf("RESTAURANTE"),
            Servicio.valueOf("CINE"),
            Servicio.valueOf("PISCINA"),
            Servicio.valueOf("WIFI"),
            Servicio.valueOf("PARQUEADERO"),
            Servicio.valueOf("TELEVISION")
        );

        habitacionCorrecta = new Habitacion(
            TipoHabitacion.valueOf("FAMILIAR"), 
            "101", 
            EstadoHabitacion.valueOf("DISPONIBLE"), 
            800000.0, 
            4, 
            "Habitacion Familiar", 
            servicios
        );
    }

    @Test
    public void deberiaAgregarHabitacion() throws RoomFinderException{
        doReturn(habitacionCorrecta).when(habitacionRepositorio).agregarHabitacion(any(Habitacion.class));
        Habitacion habitacionRespuesta = habitacionServicio.agregarHabitacion(habitacionCorrecta);

        assertNotNull(habitacionRespuesta);
        assertEquals(habitacionCorrecta, habitacionRespuesta);
    }

    @Test
    public void noDeberiaAgregarHabitacionSiNoTieneInfoHabitacion() throws RoomFinderException{
        assertThrows(RoomFinderException.class, () -> habitacionServicio.agregarHabitacion(null));
        assertThrows(NullPointerException.class, () -> new Habitacion(null, null, null, null,null));
    }

    @Test
    public void noDeberiaPermitirServicioCuandoNoExisteElValorDelServicio(){
        assertThrows(IllegalArgumentException.class, () -> Servicio.valueOf("PLANTAS"));
    }

    @Test
    public void noDeberiaPermitirServicioCuandoElValorDelServicioEsNulo(){
        assertThrows(NullPointerException.class, () -> Servicio.valueOf(null));
    }

    @Test
    public void noDeberiaPermitirServicioCuandoElValorDelServicioEsVacio(){
        assertThrows(IllegalArgumentException.class, () -> Servicio.valueOf(""));
    }

    @Test
    public void deberiaConsultarTodasLasHabitaciones(){
        Set<Servicio> serviciosTest = Set.of(
            Servicio.valueOf("RESTAURANTE"),
            Servicio.valueOf("PISCINA"),
            Servicio.valueOf("TELEVISION")
        );

        Habitacion habitacionTest = new Habitacion(
            TipoHabitacion.valueOf("DOBLE"), 
            "102", 
            EstadoHabitacion.valueOf("DISPONIBLE"), 
            950000.0, 
            2, 
            "Habitacion con vista al exterior", 
            serviciosTest
        );
        
        List<Habitacion> habitaciones = new ArrayList<>();
        habitaciones.add(habitacionTest);
        habitaciones.add(habitacionCorrecta);

        doReturn(habitaciones).when(habitacionRepositorio).consultarTodasLasHabitaciones();
        assertEquals(habitaciones, habitacionServicio.consultarTodasLasHabitaciones());
    }

    @Test
    public void deberiaConsultarHabitacionPorNumero() throws RoomFinderException{
        doReturn(habitacionCorrecta).when(habitacionRepositorio).consultarHabitacionPorNumero(anyString());
        var habitacionRespuesta = habitacionServicio.consultarHabitacionPorNumero("101");
        assertEquals(habitacionCorrecta, habitacionRespuesta);
    }

    @Test
    public void noDeberiaConsultarHabitacionPorNumeroSiNoExiste() throws RoomFinderException{
        doThrow(RoomFinderException.class).when(habitacionRepositorio).consultarHabitacionPorNumero(anyString());
        assertThrows(RoomFinderException.class, () -> habitacionServicio.consultarHabitacionPorNumero("300"));
    }

    @Test
    public void noDeberiaConsultarHabitacionPorNumeroCuandoEsNulo() throws RoomFinderException{
        assertThrows(RoomFinderException.class, () -> habitacionServicio.consultarHabitacionPorNumero(null));
    }

    @Test
    public void noDeberiaConsultarHabitacionPorNumeroCuandoEsVacio() throws RoomFinderException{
        assertThrows(RoomFinderException.class, () -> habitacionServicio.consultarHabitacionPorNumero(""));
    }

    @Test
    public void deberiaConsultarHabitacionesPorTipo() throws RoomFinderException{
        List<Habitacion> habitaciones = new ArrayList<>();
        habitaciones.add(habitacionCorrecta);

        doReturn(habitaciones).when(habitacionRepositorio).consultarHabitacionesPorTipo(any(TipoHabitacion.class));
        List<Habitacion> habitacionRespuesta = habitacionServicio.consultarHabitacionesPorTipo(TipoHabitacion.valueOf("FAMILIAR"));

        assertEquals(habitaciones.get(0).getNumeroHabitacion(), habitacionRespuesta.get(0).getNumeroHabitacion());
    }

    @Test
    public void noDeberiaConsultarHabitacionPorTipoCuandoElValorEsNulo() throws RoomFinderException{
        assertThrows(RoomFinderException.class, () -> habitacionServicio.consultarHabitacionesPorTipo(null));
    }

    @Test
    public void deberiaConsultarHabitacionesPorPrecio() throws RoomFinderException{
        List<Habitacion> habitaciones = new ArrayList<>();
        habitaciones.add(habitacionCorrecta);

        doReturn(habitaciones).when(habitacionRepositorio).consultarHabitacionesPorPrecio(anyDouble());
        List<Habitacion> habitacionRespuesta = habitacionServicio.consultarHabitacionesPorPrecio(800000.0);

        assertEquals(habitaciones.get(0).getNumeroHabitacion(), habitacionRespuesta.get(0).getNumeroHabitacion());
    }

    @Test
    public void noDeberiaConsultarHabitacionPorPrecioCuandoElValorEsNulo() throws RoomFinderException{
        assertThrows(RoomFinderException.class, () -> habitacionServicio.consultarHabitacionesPorPrecio(null));
    }

    @Test
    public void noDeberiaConsultarHabitacionPorPrecioCuandoNoEsMayorACero() throws RoomFinderException{
        assertThrows(RoomFinderException.class, () -> habitacionServicio.consultarHabitacionesPorPrecio(-100000.0));
        assertThrows(RoomFinderException.class, () -> habitacionServicio.consultarHabitacionesPorPrecio(0.0));
    }

    @Test
    public void deberiaModificarEstadoHabitacion() throws RoomFinderException{
        doReturn(habitacionCorrecta).when(habitacionRepositorio).modificarEstadoHabitacion(anyString(), any(EstadoHabitacion.class));
        var habitacionRespuesta = habitacionServicio.modificarEstadoHabitacion("101", EstadoHabitacion.valueOf("RESERVADA"));
        assertEquals(habitacionCorrecta, habitacionRespuesta);
    }

    @Test
    public void noDeberiaModificarEstadoHabitacionSiNoExisteLaHabitacion() throws RoomFinderException{
        doThrow(RoomFinderException.class).when(habitacionRepositorio).modificarEstadoHabitacion(anyString(), any(EstadoHabitacion.class));
        assertThrows(RoomFinderException.class, () -> habitacionServicio.modificarEstadoHabitacion("300", EstadoHabitacion.valueOf("RESERVADA")));
    }

    @Test
    public void noDeberiaModificarEstadoHabitacioCuandoEsNulo() throws RoomFinderException{
        assertThrows(RoomFinderException.class, () -> habitacionServicio.modificarEstadoHabitacion(habitacionCorrecta.getNumeroHabitacion(), null));
    }

    @Test
    public void noDeberiaModificarEstadoHabitacioCuandoNumHabitacionEsNulo() throws RoomFinderException{
        assertThrows(RoomFinderException.class, () -> habitacionServicio.modificarEstadoHabitacion(null, EstadoHabitacion.valueOf("RESERVADA")));
    }

    @Test
    public void noDeberiaModificarEstadoHabitacioCuandoNumHabitacionEsVacio() throws RoomFinderException{
        assertThrows(RoomFinderException.class, () -> habitacionServicio.modificarEstadoHabitacion("", EstadoHabitacion.valueOf("RESERVADA")));
    }

    @Test
    public void deberiaModificarPrecioHabitacion() throws RoomFinderException{
        doReturn(habitacionCorrecta).when(habitacionRepositorio).modificarPrecioHabitacion(anyString(), anyDouble());
        var habitacionRespuesta = habitacionServicio.modificarPrecioHabitacion("101", 9850000.0 );
        assertEquals(habitacionCorrecta, habitacionRespuesta);
    }

    @Test
    public void noDeberiaModificarPrecioHabitacionSiNoExisteLaHabitacion() throws RoomFinderException{
        doThrow(RoomFinderException.class).when(habitacionRepositorio).modificarPrecioHabitacion(anyString(), anyDouble());
        assertThrows(RoomFinderException.class, () -> habitacionServicio.modificarPrecioHabitacion("300", 9850000.0));
    }



    @Test
    public void noDeberiaModificarPrecioHabitacioCuandoNumHabitacionEsNulo() throws RoomFinderException{
        assertThrows(RoomFinderException.class, () -> habitacionServicio.modificarPrecioHabitacion(null, 9850000.0));
    }

    @Test
    public void noDeberiaModificarPrecioHabitacioCuandoNumHabitacionEsVacio() throws RoomFinderException{
        assertThrows(RoomFinderException.class, () -> habitacionServicio.modificarPrecioHabitacion("", 9850000.0));
    }

    @Test
    public void noDeberiaModificarPrecioHabitacioCuandoPrecioEsNulo() throws RoomFinderException{
        assertThrows(RoomFinderException.class, () -> habitacionServicio.modificarPrecioHabitacion(habitacionCorrecta.getNumeroHabitacion(), null));
    }

    @Test
    public void noDeberiaModificarPrecioHabitacioCuandoPrecioNoEsMayorACero() throws RoomFinderException{
        assertThrows(RoomFinderException.class, () -> habitacionServicio.modificarPrecioHabitacion(habitacionCorrecta.getNumeroHabitacion(), -52560000.0));
        assertThrows(RoomFinderException.class, () -> habitacionServicio.modificarPrecioHabitacion(habitacionCorrecta.getNumeroHabitacion(), 0.0));
    }

    @Test
    public void deberiaModificarDescripcionHabitacion() throws RoomFinderException{
        doReturn(habitacionCorrecta).when(habitacionRepositorio).modificarDescripcionHabitacion(anyString(), anyString());
        var habitacionRespuesta = habitacionServicio.modificarDescripcionHabitacion("101", "Habitacion con ambientacion infantil");
        assertEquals(habitacionCorrecta, habitacionRespuesta);
    }

    @Test
    public void noDeberiaModificarDescripcionHabitacionSiNoExisteLaHabitacion() throws RoomFinderException{
        doThrow(RoomFinderException.class).when(habitacionRepositorio).modificarDescripcionHabitacion(anyString(), anyString());
        assertThrows(RoomFinderException.class, () -> habitacionServicio.modificarDescripcionHabitacion("300", "Habitacion con ambientacion infantil"));
    }

    @Test
    public void noDeberiaModificarDescripcionHabitacioCuandoNumHabitacionEsNulo() throws RoomFinderException{
        assertThrows(RoomFinderException.class, () -> habitacionServicio.modificarDescripcionHabitacion(null, "Habitacion con ambientacion infantil"));
    }

    @Test
    public void noDeberiaModificarDescripcionHabitacioCuandoNumHabitacionEsVacio() throws RoomFinderException{
        assertThrows(RoomFinderException.class, () -> habitacionServicio.modificarDescripcionHabitacion("", "Habitacion con ambientacion infantil"));
    }

   @Test
   public void deberiaAgregarServiciosHabitacion() throws RoomFinderException {
        Habitacion habitacionTest = new Habitacion(
            TipoHabitacion.valueOf("DOBLE"), 
            "102", 
            EstadoHabitacion.valueOf("DISPONIBLE"), 
            950000.0, 
            2, 
            "Habitacion con vista al exterior", 
            Collections.emptySet()
        );

        Set<Servicio> serviciosTest = Set.of(
            Servicio.valueOf("RESTAURANTE"),
            Servicio.valueOf("PISCINA"),
            Servicio.valueOf("TELEVISION")
        );

        Habitacion habitacionServicios = new Habitacion(
            TipoHabitacion.valueOf("DOBLE"), 
            "102", 
            EstadoHabitacion.valueOf("DISPONIBLE"), 
            950000.0, 
            2, 
            "Habitacion con vista al exterior", 
            serviciosTest
        );

        doNothing().when(habitacionRepositorio).agregarServiciosHabitacion(anyString(), anySet());
        doReturn(habitacionServicios).when(habitacionRepositorio).consultarHabitacionPorNumero(anyString());
        habitacionServicio.agregarServiciosHabitacion(habitacionTest.getNumeroHabitacion(), serviciosTest);
        var habitacionResult = habitacionServicio.consultarHabitacionPorNumero(habitacionTest.getNumeroHabitacion());
        assertEquals(serviciosTest, habitacionResult.getServicios());
        assertEquals(habitacionServicios.getNumeroHabitacion(), habitacionResult.getNumeroHabitacion());
        assertEquals(habitacionServicios, habitacionResult);
    }

    @Test
    public void noDeberiaAgregarServiciosHabitacionSiNoExisteLaHabitacion() throws RoomFinderException{
        Set<Servicio> serviciosTest = Set.of(
            Servicio.valueOf("PLATAFORMA_VIDEO"),
            Servicio.valueOf("TELEVISION")
        );

        doThrow(RoomFinderException.class).when(habitacionRepositorio).agregarServiciosHabitacion(anyString(), anySet());
        assertThrows(RoomFinderException.class, () -> habitacionServicio.agregarServiciosHabitacion("300", serviciosTest));
    }

    @Test
    public void noDeberiaAgregarServiciosHabitacionSiNumHabitacionEsNulo() throws RoomFinderException{
        Set<Servicio> serviciosTest = Set.of(
            Servicio.valueOf("PLATAFORMA_VIDEO"),
            Servicio.valueOf("TELEVISION")
        );

        assertThrows(RoomFinderException.class, () -> habitacionServicio.agregarServiciosHabitacion(null, serviciosTest));
    }

    @Test
    public void noDeberiaAgregarServiciosHabitacionSiNumHabitacionEsVacio() throws RoomFinderException{
        Set<Servicio> serviciosTest = Set.of(
            Servicio.valueOf("PLATAFORMA_VIDEO"),
            Servicio.valueOf("TELEVISION")
        );

        assertThrows(RoomFinderException.class, () -> habitacionServicio.agregarServiciosHabitacion("", serviciosTest));
    }

    @Test
    public void noDeberiaAgregarServiciosHabitacionSiServiciosEsNulo() throws RoomFinderException{
        Habitacion habitacionTest = new Habitacion(
            TipoHabitacion.valueOf("DOBLE"), 
            "102", 
            EstadoHabitacion.valueOf("DISPONIBLE"), 
            950000.0, 
            2, 
            "Habitacion con vista al exterior", 
            Collections.emptySet()
        );

        assertThrows(RoomFinderException.class, () -> habitacionServicio.agregarServiciosHabitacion(habitacionTest.getNumeroHabitacion(), null));
    }

    @Test
    public void noDeberiaAgregarServiciosHabitacionSiServiciosEsVacio() throws RoomFinderException{
        Habitacion habitacionTest = new Habitacion(
            TipoHabitacion.valueOf("DOBLE"), 
            "102", 
            EstadoHabitacion.valueOf("DISPONIBLE"), 
            950000.0, 
            2, 
            "Habitacion con vista al exterior", 
            Collections.emptySet()
        );

        assertThrows(RoomFinderException.class, () -> habitacionServicio.agregarServiciosHabitacion(habitacionTest.getNumeroHabitacion(), Collections.emptySet()));
    }
    
    @Test
    public void deberiaModificarServiciosHabitacion() throws RoomFinderException {
        Set<Servicio> serviciosModificar = Set.of(
            Servicio.valueOf("BAR"),
            Servicio.valueOf("GYM")
        );

        Set<Servicio> serviciosEsperados = new HashSet<>();
        serviciosEsperados.addAll(habitacionCorrecta.getServicios());
        serviciosEsperados.addAll(serviciosModificar);

        Habitacion habitacionServicios = new Habitacion(
            TipoHabitacion.valueOf("FAMILIAR"), 
            "101", 
            EstadoHabitacion.valueOf("DISPONIBLE"), 
            800000.0, 
            4, 
            "Habitacion Familiar", 
            serviciosEsperados
        );

        doReturn(habitacionServicios).when(habitacionRepositorio).consultarHabitacionPorNumero(anyString());
        habitacionServicio.modificarServiciosHabitacion(habitacionCorrecta.getNumeroHabitacion(), serviciosModificar);
        var habitacionResult = habitacionServicio.consultarHabitacionPorNumero(habitacionCorrecta.getNumeroHabitacion());
        assertEquals(serviciosEsperados, habitacionResult.getServicios());
        assertEquals(habitacionServicios.getNumeroHabitacion(), habitacionResult.getNumeroHabitacion());
        assertEquals(habitacionServicios, habitacionResult);
    }

    @Test
    public void noDeberiaModificarServiciosHabitacionSiNoExisteLaHabitacion() throws RoomFinderException{
        Set<Servicio> serviciosTest = Set.of(
            Servicio.valueOf("PLATAFORMA_VIDEO"),
            Servicio.valueOf("TELEVISION")
        );

        doThrow(RoomFinderException.class).when(habitacionRepositorio).modificarServiciosHabitacion(anyString(), anySet());
        assertThrows(RoomFinderException.class, () -> habitacionServicio.modificarServiciosHabitacion("300", serviciosTest));
    }

    @Test
    public void noDeberiaModificarServiciosHabitacionSiNumHabitacionEsNulo() throws RoomFinderException{
        Set<Servicio> serviciosTest = Set.of(
            Servicio.valueOf("PLATAFORMA_VIDEO"),
            Servicio.valueOf("TELEVISION")
        );

        assertThrows(RoomFinderException.class, () -> habitacionServicio.modificarServiciosHabitacion(null, serviciosTest));
    }

    @Test
    public void noDeberiaModificarServiciosHabitacionSiNumHabitacionEsVacio() throws RoomFinderException{
        Set<Servicio> serviciosTest = Set.of(
            Servicio.valueOf("PLATAFORMA_VIDEO"),
            Servicio.valueOf("TELEVISION")
        );

        assertThrows(RoomFinderException.class, () -> habitacionServicio.modificarServiciosHabitacion("", serviciosTest));
    }

    @Test
    public void noDeberiaModificarServiciosHabitacionSiServiciosEsNulo() throws RoomFinderException{
        Habitacion habitacionTest = new Habitacion(
            TipoHabitacion.valueOf("DOBLE"), 
            "102", 
            EstadoHabitacion.valueOf("DISPONIBLE"), 
            950000.0, 
            2, 
            "Habitacion con vista al exterior", 
            Collections.emptySet()
        );

        assertThrows(RoomFinderException.class, () -> habitacionServicio.modificarServiciosHabitacion(habitacionTest.getNumeroHabitacion(), null));
    }

    @Test
    public void noDeberiaModificarServiciosHabitacionSiServiciosEsVacio() throws RoomFinderException{
        Habitacion habitacionTest = new Habitacion(
            TipoHabitacion.valueOf("DOBLE"), 
            "102", 
            EstadoHabitacion.valueOf("DISPONIBLE"), 
            950000.0, 
            2, 
            "Habitacion con vista al exterior", 
            Collections.emptySet()
        );

        assertThrows(RoomFinderException.class, () -> habitacionServicio.modificarServiciosHabitacion(habitacionTest.getNumeroHabitacion(), Collections.emptySet()));
    }

    @Test
    public void deberiaEliminarServiciosHabitacion() throws RoomFinderException {

        Habitacion habitacionServicios = new Habitacion(
            TipoHabitacion.valueOf("FAMILIAR"), 
            "101", 
            EstadoHabitacion.valueOf("DISPONIBLE"), 
            800000.0, 
            4, 
            "Habitacion Familiar", 
            Collections.emptySet()
        );

        doReturn(habitacionServicios).when(habitacionRepositorio).consultarHabitacionPorNumero(anyString());
        habitacionServicio.eliminarServiciosHabitacion(habitacionCorrecta.getNumeroHabitacion(), habitacionCorrecta.getServicios());
        var habitacionResult = habitacionServicio.consultarHabitacionPorNumero(habitacionCorrecta.getNumeroHabitacion());
        assertEquals(habitacionServicios.getServicios(), habitacionResult.getServicios());
        assertEquals(habitacionServicios.getNumeroHabitacion(), habitacionResult.getNumeroHabitacion());
        assertEquals(habitacionServicios, habitacionResult);
    }

    @Test
    public void noDeberiaEliminarServiciosHabitacionSiNoExisteLaHabitacion() throws RoomFinderException{
        Set<Servicio> serviciosTest = Set.of(
            Servicio.valueOf("PLATAFORMA_VIDEO"),
            Servicio.valueOf("TELEVISION")
        );

        doThrow(RoomFinderException.class).when(habitacionRepositorio).eliminarServiciosHabitacion(anyString(), anySet());
        assertThrows(RoomFinderException.class, () -> habitacionServicio.eliminarServiciosHabitacion("300", serviciosTest));
    }

    @Test
    public void noDeberiaEliminarServiciosHabitacionSiNumHabitacionEsNulo() throws RoomFinderException{
        Set<Servicio> serviciosTest = Set.of(
            Servicio.valueOf("PLATAFORMA_VIDEO"),
            Servicio.valueOf("TELEVISION")
        );

        assertThrows(RoomFinderException.class, () -> habitacionServicio.eliminarServiciosHabitacion(null, serviciosTest));
    }

    @Test
    public void noDeberiaEliminarServiciosHabitacionSiNumHabitacionEsVacio() throws RoomFinderException{
        Set<Servicio> serviciosTest = Set.of(
            Servicio.valueOf("PLATAFORMA_VIDEO"),
            Servicio.valueOf("TELEVISION")
        );

        assertThrows(RoomFinderException.class, () -> habitacionServicio.eliminarServiciosHabitacion("", serviciosTest));
    }

    @Test
    public void noDeberiaEliminarServiciosHabitacionSiServiciosEsNulo() throws RoomFinderException{
        Habitacion habitacionTest = new Habitacion(
            TipoHabitacion.valueOf("DOBLE"), 
            "102", 
            EstadoHabitacion.valueOf("DISPONIBLE"), 
            950000.0, 
            2, 
            "Habitacion con vista al exterior", 
            Collections.emptySet()
        );

        assertThrows(RoomFinderException.class, () -> habitacionServicio.eliminarServiciosHabitacion(habitacionTest.getNumeroHabitacion(), null));
    }

    @Test
    public void noDeberiaEliminarServiciosHabitacionSiServiciosEsVacio() throws RoomFinderException{
        Habitacion habitacionTest = new Habitacion(
            TipoHabitacion.valueOf("DOBLE"), 
            "102", 
            EstadoHabitacion.valueOf("DISPONIBLE"), 
            950000.0, 
            2, 
            "Habitacion con vista al exterior", 
            Collections.emptySet()
        );

        assertThrows(RoomFinderException.class, () -> habitacionServicio.eliminarServiciosHabitacion(habitacionTest.getNumeroHabitacion(), Collections.emptySet()));
    }

    
}

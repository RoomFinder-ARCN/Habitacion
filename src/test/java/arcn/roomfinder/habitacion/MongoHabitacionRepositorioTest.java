package arcn.roomfinder.habitacion;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import arcn.roomfinder.habitacion.domain.entity.HabitacionEntidad;
import arcn.roomfinder.habitacion.domain.exception.RoomFinderException;
import arcn.roomfinder.habitacion.domain.model.EstadoHabitacion;
import arcn.roomfinder.habitacion.domain.model.Habitacion;
import arcn.roomfinder.habitacion.domain.model.Servicio;
import arcn.roomfinder.habitacion.domain.model.TipoHabitacion;
import arcn.roomfinder.habitacion.domain.repository.mongorepo.MongoHabitacionInterface;
import arcn.roomfinder.habitacion.domain.repository.mongorepo.MongoHabitacionRepositorio;

@RunWith(MockitoJUnitRunner.class)
public class MongoHabitacionRepositorioTest {

    @Mock
    MongoHabitacionInterface mongoHabitacionInterface;

    @InjectMocks
    MongoHabitacionRepositorio mongoHabitacionRepositorio;

    private Habitacion habitacionCorrecta;
    private Set<Servicio> servicios;
    private HabitacionEntidad habitacionCorrectaEntidad;


    @Before
    public void setUp() {
        habitacionCorrecta = new Habitacion(
            TipoHabitacion.valueOf("FAMILIAR"), 
            "101", 
            EstadoHabitacion.valueOf("DISPONIBLE"), 
            800000.0, 
            4, 
            "Habitacion Familiar", 
            Collections.emptySet()
        );

        servicios = Set.of(
            Servicio.valueOf("RESTAURANTE"),
            Servicio.valueOf("CINE"),
            Servicio.valueOf("PISCINA"),
            Servicio.valueOf("WIFI"),
            Servicio.valueOf("PARQUEADERO"),
            Servicio.valueOf("TELEVISION")
        );

        habitacionCorrectaEntidad = new HabitacionEntidad(
            TipoHabitacion.valueOf("FAMILIAR"), 
            "101", 
            EstadoHabitacion.valueOf("DISPONIBLE"), 
            800000.0, 
            4, 
            "Habitacion Familiar", 
            Collections.emptySet()
        );
    }

    @Test
    public void deberiaAgregarHabitacion() throws RoomFinderException {
        doReturn(habitacionCorrectaEntidad).when(mongoHabitacionInterface).save(any(HabitacionEntidad.class));
        Habitacion resultado = mongoHabitacionRepositorio.agregarHabitacion(habitacionCorrecta);
        assertNotNull(resultado);
        assertEquals(habitacionCorrecta.getNumeroHabitacion(), resultado.getNumeroHabitacion());
    }

    @Test
    public void noDeberiaAgregarHabitacionCuandoLaHabitacionEsNula() throws RoomFinderException {
        assertThrows(RoomFinderException.class, () -> mongoHabitacionRepositorio.agregarHabitacion(null));
    }

    @Test
    public void deberiaConsultarTodasLasHabitaciones(){
        doReturn(List.of(habitacionCorrectaEntidad)).when(mongoHabitacionInterface).findAll();
        List<Habitacion> habitaciones = mongoHabitacionRepositorio.consultarTodasLasHabitaciones();
        assertEquals(1, habitaciones.size());
        assertEquals(habitacionCorrecta.getNumeroHabitacion(), habitaciones.get(0).getNumeroHabitacion());
    }

    @Test
    public void deberiaConsultarHabitacionPorNumero() throws RoomFinderException {
        doReturn(Optional.of(habitacionCorrectaEntidad)).when(mongoHabitacionInterface).findById(any(String.class));
        Habitacion habitacion = mongoHabitacionRepositorio.consultarHabitacionPorNumero(habitacionCorrecta.getNumeroHabitacion());
        assertNotNull(habitacion);
        assertEquals(habitacionCorrecta.getNumeroHabitacion(), habitacion.getNumeroHabitacion());
    }

    @Test
    public void noDeberiaConsultarHabitacionPorNumeroCuandoEsNulo() throws RoomFinderException {
        assertThrows(RoomFinderException.class, () -> mongoHabitacionRepositorio.consultarHabitacionPorNumero(null));
    }

    @Test
    public void noDeberiaConsultarHabitacionPorNumeroCuandoNoExisteLaHabitacion() throws RoomFinderException {
        assertThrows(RoomFinderException.class, () -> mongoHabitacionRepositorio.consultarHabitacionPorNumero("300"));
    }

    @Test
    public void deberiaConsultarHanitacionPorTipo() throws RoomFinderException {
        doReturn(List.of(habitacionCorrectaEntidad)).when(mongoHabitacionInterface).findByTipoHabitacion(any(TipoHabitacion.class));
        List<Habitacion> habitaciones = mongoHabitacionRepositorio.consultarHabitacionesPorTipo(habitacionCorrecta.getTipoHabitacion());
        assertEquals(1, habitaciones.size());
        assertEquals(habitacionCorrecta.getNumeroHabitacion(), habitaciones.get(0).getNumeroHabitacion());
        assertEquals(habitacionCorrecta.getTipoHabitacion(), habitaciones.get(0).getTipoHabitacion());
    }

    @Test
    public void deberiaConsultarHanitacionPorPrecio() throws RoomFinderException {
        doReturn(List.of(habitacionCorrectaEntidad)).when(mongoHabitacionInterface).findByPrecio(any(Double.class));
        List<Habitacion> habitaciones = mongoHabitacionRepositorio.consultarHabitacionesPorPrecio(habitacionCorrecta.getPrecio());
        assertEquals(1, habitaciones.size());
        assertEquals(habitacionCorrecta.getNumeroHabitacion(), habitaciones.get(0).getNumeroHabitacion());
        assertEquals(habitacionCorrecta.getPrecio(), habitaciones.get(0).getPrecio());
    }

    @Test
    public void deberiaModificarEstadoHabitacion() throws RoomFinderException {
        doReturn(Optional.of(habitacionCorrectaEntidad)).when(mongoHabitacionInterface).findById(any(String.class));
        doReturn(habitacionCorrectaEntidad).when(mongoHabitacionInterface).save(any(HabitacionEntidad.class));
        Habitacion resultado = mongoHabitacionRepositorio.modificarEstadoHabitacion(habitacionCorrecta.getNumeroHabitacion(), EstadoHabitacion.valueOf("RESERVADA"));
        assertEquals(habitacionCorrecta.getNumeroHabitacion(), resultado.getNumeroHabitacion());
        assertEquals(EstadoHabitacion.valueOf("RESERVADA"), resultado.getEstadoHabitacion());
    }

    @Test
    public void noDeberiaModificarEstadoHabitacionCuandoNumHabitacionEsNulo() throws RoomFinderException {
        assertThrows(RoomFinderException.class, () -> mongoHabitacionRepositorio.modificarEstadoHabitacion(null, EstadoHabitacion.valueOf("RESERVADA")));
    }

    @Test
    public void noDeberiaModificarEstadoHabitacionCuandoNoExisteLaHabitacion() throws RoomFinderException {
        assertThrows(RoomFinderException.class, () -> mongoHabitacionRepositorio.modificarEstadoHabitacion("300", EstadoHabitacion.valueOf("RESERVADA")));
    }

    @Test
    public void deberiaModificarPrecioHabitacion() throws RoomFinderException {
        doReturn(Optional.of(habitacionCorrectaEntidad)).when(mongoHabitacionInterface).findById(any(String.class));
        doReturn(habitacionCorrectaEntidad).when(mongoHabitacionInterface).save(any(HabitacionEntidad.class));
        Habitacion resultado = mongoHabitacionRepositorio.modificarPrecioHabitacion(habitacionCorrecta.getNumeroHabitacion(), 359500.0);
        assertEquals(habitacionCorrecta.getNumeroHabitacion(), resultado.getNumeroHabitacion());
        assertEquals(359500.0, resultado.getPrecio(), 0.001);
    }

    @Test
    public void noDeberiaModificarPrecioHabitacionCuandoNumHabitacionEsNulo() throws RoomFinderException {
        assertThrows(RoomFinderException.class, () -> mongoHabitacionRepositorio.modificarPrecioHabitacion(null, 359500.0));
    }

    @Test
    public void noDeberiaModificarPrecioHabitacionCuandoNoExisteLaHabitacion() throws RoomFinderException {
        assertThrows(RoomFinderException.class, () -> mongoHabitacionRepositorio.modificarPrecioHabitacion("300", 359500.0));
    }

    @Test
    public void deberiaModificarDescripcionHabitacion() throws RoomFinderException {
        doReturn(Optional.of(habitacionCorrectaEntidad)).when(mongoHabitacionInterface).findById(any(String.class));
        doReturn(habitacionCorrectaEntidad).when(mongoHabitacionInterface).save(any(HabitacionEntidad.class));
        Habitacion resultado = mongoHabitacionRepositorio.modificarDescripcionHabitacion(habitacionCorrecta.getDescripcion(), "Habitacion de prueba");
        assertEquals(habitacionCorrecta.getNumeroHabitacion(), resultado.getNumeroHabitacion());
        assertEquals("Habitacion de prueba", resultado.getDescripcion());
    }

    @Test
    public void noDeberiaModificarDescripcionHabitacionCuandoDescripcionEsNula() throws RoomFinderException {
        assertThrows(RoomFinderException.class, () -> mongoHabitacionRepositorio.modificarDescripcionHabitacion(null, "Habitacion de prueba"));
    }

    @Test
    public void noDeberiaModificarDescripcionHabitacionCuandoNoExisteLaHabitacion() throws RoomFinderException {
        assertThrows(RoomFinderException.class, () -> mongoHabitacionRepositorio.modificarDescripcionHabitacion("300", "Habitacion de prueba"));
    }

    @Test
    public void deberiaAgregarServiciosHabitacion() throws RoomFinderException {
        doReturn(Optional.of(habitacionCorrectaEntidad)).when(mongoHabitacionInterface).findById(any(String.class));
        doReturn(habitacionCorrectaEntidad).when(mongoHabitacionInterface).save(any(HabitacionEntidad.class));
        mongoHabitacionRepositorio.agregarServiciosHabitacion(habitacionCorrecta.getNumeroHabitacion(), servicios);
        assertEquals(servicios, mongoHabitacionRepositorio.consultarHabitacionPorNumero(habitacionCorrecta.getNumeroHabitacion()).getServicios());
    }

    @Test
    public void noDeberiaAgregarServiciosHabitacionCuandoNumHabitacionEsNulo() throws RoomFinderException {
        assertThrows(RoomFinderException.class, () -> mongoHabitacionRepositorio.agregarServiciosHabitacion(null, servicios));
    }

    @Test
    public void noDeberiaAgregarServiciosHabitacionCuandoSonNulos() throws RoomFinderException {
        assertThrows(RoomFinderException.class, () -> mongoHabitacionRepositorio.agregarServiciosHabitacion(habitacionCorrecta.getNumeroHabitacion(), null));
    }

    @Test
    public void noDeberiaAgregarServiciosHabitacionCuandoLaInfoEsNula() throws RoomFinderException {
        assertThrows(RoomFinderException.class, () -> mongoHabitacionRepositorio.agregarServiciosHabitacion(null, null));
    }

    @Test
    public void noDeberiaAgregarServiciosHabitacionCuandoNoExisteLaHabitacion() throws RoomFinderException {  
        assertThrows(RoomFinderException.class, () -> mongoHabitacionRepositorio.agregarServiciosHabitacion("300", servicios));
    }

    /*@Test
    public void deberiaModificarServiciosHabitacion() throws RoomFinderException {
        doReturn(Optional.of(habitacionCorrectaEntidad)).when(mongoHabitacionInterface).findById(any(String.class));
        doReturn(habitacionCorrectaEntidad).when(mongoHabitacionInterface).save(any(HabitacionEntidad.class));
        mongoHabitacionRepositorio.modificarServiciosHabitacion(habitacionCorrecta.getNumeroHabitacion(), servicios);
        assertEquals(servicios, mongoHabitacionRepositorio.consultarHabitacionPorNumero(habitacionCorrecta.getNumeroHabitacion()).getServicios());
    }*/

    @Test
    public void noDeberiaModificarServiciosHabitacionCuandoNumHabitacionEsNulo() throws RoomFinderException {
        assertThrows(RoomFinderException.class, () -> mongoHabitacionRepositorio.modificarServiciosHabitacion(null, servicios));
    }

    @Test
    public void noDeberiaModificarServiciosHabitacionCundoSonNulos() throws RoomFinderException {
        assertThrows(RoomFinderException.class, () -> mongoHabitacionRepositorio.modificarServiciosHabitacion(habitacionCorrecta.getNumeroHabitacion(), null));
    }

    @Test
    public void noDeberiaModificarServiciosHabitacionCuandoLaInfoEsNula() throws RoomFinderException {
        assertThrows(RoomFinderException.class, () -> mongoHabitacionRepositorio.modificarServiciosHabitacion(null, null));
    }

    @Test
    public void noDeberiaModificarServiciosHabitacionCuandoNoExisteLaHabitacion() throws RoomFinderException {
        assertThrows(RoomFinderException.class, () -> mongoHabitacionRepositorio.modificarServiciosHabitacion("300", servicios));
    }

    @Test
    public void deberiaEliminarServiciosHabitacion() throws RoomFinderException {
        doReturn(Optional.of(habitacionCorrectaEntidad)).when(mongoHabitacionInterface).findById(any(String.class));
        doReturn(habitacionCorrectaEntidad).when(mongoHabitacionInterface).save(any(HabitacionEntidad.class));
        mongoHabitacionRepositorio.eliminarServiciosHabitacion(habitacionCorrecta.getNumeroHabitacion(), servicios);
        assertEquals(0, mongoHabitacionRepositorio.consultarHabitacionPorNumero(habitacionCorrecta.getNumeroHabitacion()).getServicios().size());
        assertEquals(Collections.emptySet(), mongoHabitacionRepositorio.consultarHabitacionPorNumero(habitacionCorrecta.getNumeroHabitacion()).getServicios());
    }

    @Test
    public void noDeberiaEliminarServiciosHabitacionCuandoNumHabitacionEsNulo() throws RoomFinderException {
        assertThrows(RoomFinderException.class, () -> mongoHabitacionRepositorio.eliminarServiciosHabitacion(null, servicios));
    }

    @Test
    public void noDeberiaEliminarServiciosHabitacionCuandoSonNulos() throws RoomFinderException {
        assertThrows(RoomFinderException.class, () -> mongoHabitacionRepositorio.eliminarServiciosHabitacion(habitacionCorrecta.getNumeroHabitacion(), null));
    }

    @Test
    public void noDeberiaEliminarServiciosHabitacionCuandoLaInfoEsNula() throws RoomFinderException {
        assertThrows(RoomFinderException.class, () -> mongoHabitacionRepositorio.eliminarServiciosHabitacion(null, null));
    }

    @Test
    public void noDeberiaEliminarServiciosHabitacionCuandoNoExisteLaHabitacion() throws RoomFinderException {
        assertThrows(RoomFinderException.class, () -> mongoHabitacionRepositorio.eliminarServiciosHabitacion("300", servicios));
    }


}

package arcn.roomfinder.habitacion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.util.Arrays;
import java.util.Collection;

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
        Collection<Servicio> servicios = Arrays.asList(
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
    public void deberiaLanzarExcepcionSiNoTieneInfoHabitacion() throws RoomFinderException{
        doThrow(RoomFinderException.class).when(habitacionRepositorio).agregarHabitacion(nullable(Habitacion.class));
        assertThrows(RoomFinderException.class, () -> habitacionServicio.agregarHabitacion(null));
        assertThrows(NullPointerException.class, () -> habitacionServicio.agregarHabitacion(new Habitacion(null, null, null, null)));
    }
}

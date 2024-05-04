package arcn.roomfinder.habitacion.application;

import java.util.List;
import java.util.Set;

import arcn.roomfinder.habitacion.domain.exception.RoomFinderException;
import arcn.roomfinder.habitacion.domain.model.EstadoHabitacion;
import arcn.roomfinder.habitacion.domain.model.Habitacion;
import arcn.roomfinder.habitacion.domain.model.Servicio;

public interface HabitacionServicio {

    Habitacion agregarHabitacion(Habitacion habitacion) throws RoomFinderException;
    List<Habitacion> consultarTodasLasHabitaciones();
    Habitacion consultarHabitacionPorNumero(String numeroHabitacion) throws RoomFinderException;
    Habitacion modificarEstadoHabitacion(String numeroHabitacion, EstadoHabitacion estadoHabitacion) throws RoomFinderException;
    Habitacion modificarPrecioHabitacion(String numeroHabitacion, Double precio) throws RoomFinderException;
    Habitacion modificarDescripcionHabitacion(String numeroHabitacion, String descripcion) throws RoomFinderException;
    void agregarServiciosHabitacion(String numeroHabitacion, Set<Servicio> servicios) throws RoomFinderException;
    void eliminarServiciosHabitacion(String numeroHabitacion, Set<Servicio> servicios) throws RoomFinderException;
    void modificarServiciosHabitacion(String numeroHabitacion, Set<Servicio> servicios) throws RoomFinderException;

}

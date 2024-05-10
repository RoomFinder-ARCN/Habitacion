package arcn.roomfinder.habitacion.application;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import arcn.roomfinder.habitacion.domain.exception.RoomFinderException;
import arcn.roomfinder.habitacion.domain.model.EstadoHabitacion;
import arcn.roomfinder.habitacion.domain.model.Habitacion;
import arcn.roomfinder.habitacion.domain.model.Servicio;
import arcn.roomfinder.habitacion.domain.model.TipoHabitacion;
import arcn.roomfinder.habitacion.domain.repository.HabitacionRepositorio;

@Service
public class HabitacionServicioImpl implements HabitacionServicio {

    private HabitacionRepositorio habitacionRepositorio;

    private static final String HABITACION_NO_NULA = "La habitacion no puede ser null";
    private static final String MENSAJE_NUM_HABITACION = "El numero de habitacion no puede ser nulo o vacio";
    private static final String MENSAJE_DATOS = "Los datos no pueden ser nulos o vacios";
    private static final String MENSAJE_PRECIO = "El precio debe ser mayor a 0";

    @Autowired
    public HabitacionServicioImpl(HabitacionRepositorio habitacionRepositorio) {
        this.habitacionRepositorio = habitacionRepositorio;
    }

    @Override
    public Habitacion agregarHabitacion(Habitacion habitacion) throws RoomFinderException {
        if(habitacion == null) throw new RoomFinderException(HABITACION_NO_NULA);
        return habitacionRepositorio.agregarHabitacion(habitacion);  
    }

    @Override
    public List<Habitacion> consultarTodasLasHabitaciones() {
        return habitacionRepositorio.consultarTodasLasHabitaciones();
    }

    @Override
    public Habitacion consultarHabitacionPorNumero(String numeroHabitacion) throws RoomFinderException {
        if(numeroHabitacion == null || numeroHabitacion.equals("")) throw new RoomFinderException(MENSAJE_NUM_HABITACION);
        return habitacionRepositorio.consultarHabitacionPorNumero(numeroHabitacion);
    }

    @Override
    public List<Habitacion> consultarHabitacionesPorTipo(TipoHabitacion tipoHabitacion) throws RoomFinderException {
        if(tipoHabitacion == null) throw new RoomFinderException(MENSAJE_DATOS);
        return habitacionRepositorio.consultarHabitacionesPorTipo(tipoHabitacion);
    }

    @Override
    public List<Habitacion> consultarHabitacionesPorPrecio(Double precio) throws RoomFinderException {
        if(precio == null) throw new RoomFinderException(MENSAJE_DATOS);
        if(precio <= 0) throw new RoomFinderException(MENSAJE_PRECIO);
        return habitacionRepositorio.consultarHabitacionesPorPrecio(precio);
    }

    @Override
    public Habitacion modificarEstadoHabitacion(String numeroHabitacion, EstadoHabitacion estadoHabitacion) throws RoomFinderException {
        if(estadoHabitacion == null || numeroHabitacion == null || numeroHabitacion.equals("")) throw new RoomFinderException(MENSAJE_DATOS);
        return habitacionRepositorio.modificarEstadoHabitacion(numeroHabitacion, estadoHabitacion);
    }

    @Override
    public Habitacion modificarPrecioHabitacion(String numeroHabitacion, Double precio) throws RoomFinderException {
        if(numeroHabitacion == null || numeroHabitacion.equals("") || precio == null) throw new RoomFinderException(MENSAJE_DATOS);
        if(precio <= 0) throw new RoomFinderException(MENSAJE_PRECIO);
        return habitacionRepositorio.modificarPrecioHabitacion(numeroHabitacion, precio);  
    }

    @Override
    public Habitacion modificarDescripcionHabitacion(String numeroHabitacion, String descripcion) throws RoomFinderException {
        if(numeroHabitacion == null || numeroHabitacion.equals("")) throw new RoomFinderException(MENSAJE_DATOS);
        return habitacionRepositorio.modificarDescripcionHabitacion(numeroHabitacion, descripcion);  
    }

    @Override
    public void agregarServiciosHabitacion(String numeroHabitacion, Set<Servicio> servicios) throws RoomFinderException {
        if(numeroHabitacion == null || numeroHabitacion.equals("") || servicios == null || servicios.isEmpty()) throw new RoomFinderException(MENSAJE_DATOS);
        habitacionRepositorio.agregarServiciosHabitacion(numeroHabitacion, servicios); 
    }

    @Override
    public void modificarServiciosHabitacion(String numeroHabitacion, Set<Servicio> servicios) throws RoomFinderException {
        if(numeroHabitacion == null || numeroHabitacion.equals("") || servicios == null || servicios.isEmpty()) throw new RoomFinderException(MENSAJE_DATOS);
        habitacionRepositorio.modificarServiciosHabitacion(numeroHabitacion, servicios);
    }


    @Override
    public void eliminarServiciosHabitacion(String numeroHabitacion, Set<Servicio> servicios) throws RoomFinderException {
        if(numeroHabitacion == null || numeroHabitacion.equals("") || servicios == null || servicios.isEmpty()) throw new RoomFinderException(MENSAJE_DATOS);
        habitacionRepositorio.eliminarServiciosHabitacion(numeroHabitacion, servicios);
    }
    
}

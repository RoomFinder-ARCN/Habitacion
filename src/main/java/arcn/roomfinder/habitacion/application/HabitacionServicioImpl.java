package arcn.roomfinder.habitacion.application;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import arcn.roomfinder.habitacion.domain.exception.RoomFinderException;
import arcn.roomfinder.habitacion.domain.model.EstadoHabitacion;
import arcn.roomfinder.habitacion.domain.model.Habitacion;
import arcn.roomfinder.habitacion.domain.model.Servicio;
import arcn.roomfinder.habitacion.domain.repository.HabitacionRepositorio;

@Service
public class HabitacionServicioImpl implements HabitacionServicio {

    private HabitacionRepositorio habitacionRepositorio;

    @Autowired
    public HabitacionServicioImpl(HabitacionRepositorio habitacionRepositorio) {
        this.habitacionRepositorio = habitacionRepositorio;
    }

    @Override
    public Habitacion agregarHabitacion(Habitacion habitacion) throws RoomFinderException {
        if(habitacion == null) throw new RoomFinderException("La habitacion no puede ser null");
        return habitacionRepositorio.agregarHabitacion(habitacion);  
    }

    @Override
    public List<Habitacion> consultarTodasLasHabitaciones() {
        return habitacionRepositorio.consultarTodasLasHabitaciones();
    }

    @Override
    public Habitacion consultarHabitacionPorNumero(String numeroHabitacion) throws RoomFinderException {
        if(numeroHabitacion == null || numeroHabitacion.equals("")) throw new RoomFinderException("El numero de habitacion no puede ser nulo o vacio");
        return habitacionRepositorio.consultarHabitacionPorNumero(numeroHabitacion);
    }

    @Override
    public Habitacion modificarEstadoHabitacion(String numeroHabitacion, EstadoHabitacion estadoHabitacion) throws RoomFinderException {
        if(estadoHabitacion == null || numeroHabitacion == null || numeroHabitacion.equals("")) throw new RoomFinderException("Los datos no pueden ser nulos o vacios");
        return habitacionRepositorio.modificarEstadoHabitacion(numeroHabitacion, estadoHabitacion);
    }

    @Override
    public Habitacion modificarPrecioHabitacion(String numeroHabitacion, Double precio) throws RoomFinderException {
        if(numeroHabitacion == null || numeroHabitacion.equals("") || precio == null) throw new RoomFinderException("Los datos no pueden ser nulos o vacios");
        if(precio <= 0) throw new RoomFinderException("El precio debe ser mayor a 0");
        return habitacionRepositorio.modificarPrecioHabitacion(numeroHabitacion, precio);  
    }

    @Override
    public Habitacion modificarDescripcionHabitacion(String numeroHabitacion, String descripcion) throws RoomFinderException {
        if(numeroHabitacion == null || numeroHabitacion.equals("")) throw new RoomFinderException("El numero de habitacion no puede ser nulo o vacio");
        return habitacionRepositorio.modificarDescripcionHabitacion(numeroHabitacion, descripcion);  
    }

    @Override
    public void agregarServiciosHabitacion(String numeroHabitacion, Set<Servicio> servicios) throws RoomFinderException {
        if(numeroHabitacion == null || numeroHabitacion.equals("") || servicios == null || servicios.isEmpty()) 
        throw new RoomFinderException("Los datos no pueden ser nulos o vacios");

        habitacionRepositorio.agregarServiciosHabitacion(numeroHabitacion, servicios); 
    }

    @Override
    public void modificarServiciosHabitacion(String numeroHabitacion, Set<Servicio> servicios) throws RoomFinderException {
        if(numeroHabitacion == null || numeroHabitacion.equals("") || servicios == null || servicios.isEmpty()) 
        throw new RoomFinderException("Los datos no pueden ser nulos o vacios");
        habitacionRepositorio.modificarServiciosHabitacion(numeroHabitacion, servicios);
    }


    @Override
    public void eliminarServiciosHabitacion(String numeroHabitacion, Set<Servicio> servicios) throws RoomFinderException {
        if(numeroHabitacion == null || numeroHabitacion.equals("") || servicios == null || servicios.isEmpty()) 
        throw new RoomFinderException("Los datos no pueden ser nulos o vacios");
        habitacionRepositorio.eliminarServiciosHabitacion(numeroHabitacion, servicios);
    }
    
}

package arcn.roomfinder.habitacion.domain.repository.mongoRepository;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import arcn.roomfinder.habitacion.domain.entity.HabitacionEntidad;
import arcn.roomfinder.habitacion.domain.exception.RoomFinderException;
import arcn.roomfinder.habitacion.domain.model.EstadoHabitacion;
import arcn.roomfinder.habitacion.domain.model.Habitacion;
import arcn.roomfinder.habitacion.domain.model.Servicio;
import arcn.roomfinder.habitacion.domain.repository.HabitacionRepositorio;

@Repository
public class MongoHabitacionRepositorio implements HabitacionRepositorio{

    private MongoHabitacionInterface mongoHabitacionInterface;

    @Autowired
    public MongoHabitacionRepositorio(MongoHabitacionInterface mongoHabitacionInterface) {
        this.mongoHabitacionInterface = mongoHabitacionInterface;
    }

    @Override
    public Habitacion agregarHabitacion(Habitacion habitacion) throws RoomFinderException {
        if(habitacion == null) throw new RoomFinderException("La habitacion no puede ser null");
        
        HabitacionEntidad habitacionEntidad = new HabitacionEntidad();
        habitacionEntidad.setTipoHabitacion(habitacion.getTipoHabitacion());
        habitacionEntidad.setNumeroHabitacion(habitacion.getNumeroHabitacion());
        habitacionEntidad.setEstadoHabitacion(habitacion.getEstadoHabitacion());
        habitacionEntidad.setPrecio(habitacion.getPrecio());
        habitacionEntidad.setCapacidad(habitacion.getCapacidad());
        habitacionEntidad.setDescripcion(habitacion.getDescripcion());
        habitacionEntidad.setServicios(habitacion.getServicios());

        HabitacionEntidad habitacionResult = mongoHabitacionInterface.save(habitacionEntidad);

        return crearHabitacion(habitacionResult);

    }

    @Override
    public List<Habitacion> consultarTodasLasHabitaciones() {
       return mongoHabitacionInterface.findAll().parallelStream().map(habitacion->
            {
                try{
                    return this.consultarHabitacionPorNumero(habitacion.getNumeroHabitacion());
                }catch(RoomFinderException e){
                    return null;
                }
            }
       ).toList();
    }

    @Override
    public Habitacion consultarHabitacionPorNumero(String numeroHabitacion) throws RoomFinderException {
        if(numeroHabitacion == null) throw new RoomFinderException("El numero de habitacion no puede ser null");
        HabitacionEntidad habitacionEntidad = mongoHabitacionInterface.findById(numeroHabitacion)
                                            .orElseThrow(() -> new RoomFinderException("No se encontró la habitación con el número: " + numeroHabitacion));

        return crearHabitacion(habitacionEntidad);
    }

    @Override
    public Habitacion modificarEstadoHabitacion(String numeroHabitacion, EstadoHabitacion estadoHabitacion) throws RoomFinderException{
        if(numeroHabitacion == null) throw new RoomFinderException("El numero de habitacion no puede ser null");
        HabitacionEntidad habitacionEntidad = mongoHabitacionInterface.findById(numeroHabitacion)
                                            .orElseThrow(() -> new RoomFinderException("No se encontró la habitación con el número: " + numeroHabitacion));
        
        habitacionEntidad.setEstadoHabitacion(estadoHabitacion);

        HabitacionEntidad habitacionResult = mongoHabitacionInterface.save(habitacionEntidad);

        return crearHabitacion(habitacionResult);
    }

    @Override
    public Habitacion modificarPrecioHabitacion(String numeroHabitacion, Double precio) throws RoomFinderException {
        if(numeroHabitacion == null) throw new RoomFinderException("El numero de habitacion no puede ser null");
        HabitacionEntidad habitacionEntidad = mongoHabitacionInterface.findById(numeroHabitacion)
                                            .orElseThrow(() -> new RoomFinderException("No se encontró la habitación con el número: " + numeroHabitacion));
        
        habitacionEntidad.setPrecio(precio);

        HabitacionEntidad habitacionResult = mongoHabitacionInterface.save(habitacionEntidad);

        return crearHabitacion(habitacionResult);
    }

    @Override
    public Habitacion modificarDescripcionHabitacion(String numeroHabitacion, String descripcion) throws RoomFinderException {
        if(numeroHabitacion == null) throw new RoomFinderException("El numero de habitacion no puede ser null");
        HabitacionEntidad habitacionEntidad = mongoHabitacionInterface.findById(numeroHabitacion)
                                            .orElseThrow(() -> new RoomFinderException("No se encontró la habitación con el número: " + numeroHabitacion));
        
        habitacionEntidad.setDescripcion(descripcion);

        HabitacionEntidad habitacionResult = mongoHabitacionInterface.save(habitacionEntidad);

        return crearHabitacion(habitacionResult);
    }

    @Override
    public void agregarServiciosHabitacion(String numeroHabitacion, Set<Servicio> servicios) throws RoomFinderException {
        if(numeroHabitacion == null || servicios == null) throw new RoomFinderException("El numero o servicios de la habitacion no pueden ser null");
        
        HabitacionEntidad habitacionEntidad = mongoHabitacionInterface.findById(numeroHabitacion)
                                            .orElseThrow(() -> new RoomFinderException("No se encontró la habitación con el número: " + numeroHabitacion));
        
        habitacionEntidad.setServicios(servicios);

        mongoHabitacionInterface.save(habitacionEntidad);
    }

    @Override
    public void modificarServiciosHabitacion(String numeroHabitacion, Set<Servicio> servicios) throws RoomFinderException {
        if(numeroHabitacion == null || servicios == null) throw new RoomFinderException("El numero o servicios de la habitacion no pueden ser null");
        
        HabitacionEntidad habitacionEntidad = mongoHabitacionInterface.findById(numeroHabitacion)
                                            .orElseThrow(() -> new RoomFinderException("No se encontró la habitación con el número: " + numeroHabitacion));
        
        servicios.addAll(habitacionEntidad.getServicios());
        habitacionEntidad.setServicios(servicios);

        mongoHabitacionInterface.save(habitacionEntidad);
    }

    @Override
    public void eliminarServiciosHabitacion(String numeroHabitacion, Set<Servicio> servicios) throws RoomFinderException {
        if(numeroHabitacion == null || servicios == null) throw new RoomFinderException("El numero o servicios de la habitacion no pueden ser null");
        
        HabitacionEntidad habitacionEntidad = mongoHabitacionInterface.findById(numeroHabitacion)
                                            .orElseThrow(() -> new RoomFinderException("No se encontró la habitación con el número: " + numeroHabitacion));
        
        servicios.removeAll(habitacionEntidad.getServicios());
        habitacionEntidad.setServicios(servicios);

        mongoHabitacionInterface.save(habitacionEntidad);
    }

    private Habitacion crearHabitacion(HabitacionEntidad habitacionResult){
        return new Habitacion(
            habitacionResult.getTipoHabitacion(),
            habitacionResult.getNumeroHabitacion(),
            habitacionResult.getEstadoHabitacion(),
            habitacionResult.getPrecio(),
            habitacionResult.getCapacidad(),
            habitacionResult.getDescripcion(),
            habitacionResult.getServicios()
        );
    }
    
}

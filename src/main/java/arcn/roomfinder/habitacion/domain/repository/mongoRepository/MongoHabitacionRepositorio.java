package arcn.roomfinder.habitacion.domain.repository.mongoRepository;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import arcn.roomfinder.habitacion.domain.entity.HabitacionEntidad;
import arcn.roomfinder.habitacion.domain.exception.RoomFinderException;
import arcn.roomfinder.habitacion.domain.model.Habitacion;
import arcn.roomfinder.habitacion.domain.repository.HabitacionRepositorio;

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

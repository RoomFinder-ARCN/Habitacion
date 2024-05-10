package arcn.roomfinder.habitacion.domain.repository.mongorepo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import arcn.roomfinder.habitacion.domain.entity.HabitacionEntidad;
import arcn.roomfinder.habitacion.domain.model.TipoHabitacion;

public interface MongoHabitacionInterface extends MongoRepository<HabitacionEntidad, String>{
    List<HabitacionEntidad> findByTipoHabitacion(TipoHabitacion tipoHabitacion);
    List<HabitacionEntidad> findByPrecio(Double precio);
}

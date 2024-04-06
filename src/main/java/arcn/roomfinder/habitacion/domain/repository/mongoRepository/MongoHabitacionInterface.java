package arcn.roomfinder.habitacion.domain.repository.mongoRepository;

import org.springframework.data.mongodb.repository.MongoRepository;

import arcn.roomfinder.habitacion.domain.entity.HabitacionEntidad;

public interface MongoHabitacionInterface extends MongoRepository<HabitacionEntidad, String>{
    
}

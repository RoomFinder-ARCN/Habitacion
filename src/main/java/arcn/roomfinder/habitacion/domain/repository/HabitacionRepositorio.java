package arcn.roomfinder.habitacion.domain.repository;

import arcn.roomfinder.habitacion.domain.exception.RoomFinderException;
import arcn.roomfinder.habitacion.domain.model.Habitacion;

public interface HabitacionRepositorio {

    Habitacion agregarHabitacion(Habitacion habitacion) throws RoomFinderException;
    
}

package arcn.roomfinder.habitacion.application;

import arcn.roomfinder.habitacion.domain.exception.RoomFinderException;
import arcn.roomfinder.habitacion.domain.model.Habitacion;

public interface HabitacionServicio {

    Habitacion agregarHabitacion(Habitacion habitacion) throws RoomFinderException;
    
}

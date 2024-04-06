package arcn.roomfinder.habitacion.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import arcn.roomfinder.habitacion.domain.exception.RoomFinderException;
import arcn.roomfinder.habitacion.domain.model.Habitacion;
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
        return habitacionRepositorio.agregarHabitacion(habitacion);  
    }

    


    
}

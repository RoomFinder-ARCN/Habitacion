package arcn.roomfinder.habitacion.infraestructure.inbound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import arcn.roomfinder.habitacion.application.HabitacionServicio;
import arcn.roomfinder.habitacion.domain.model.Habitacion;

@RestController
@RequestMapping(value = "/habitacion")
public class HabitacionController {

    private HabitacionServicio habitacionServicio;

    @Autowired
    public HabitacionController(HabitacionServicio habitacionServicio) {
        this.habitacionServicio = habitacionServicio;
    }

    @PostMapping(value = "")
    public ResponseEntity<?> agregarHabitacion(@RequestBody Habitacion habitacion){
        try{
            var habitacionAgregada= habitacionServicio.agregarHabitacion(habitacion);
            return ResponseEntity.status(201).body("Habitacion: " + habitacionAgregada.getNumeroHabitacion() +" agregada");

        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(403).body(e.getMessage());

        }
    }
    
}

package arcn.roomfinder.habitacion.infraestructure.inbound;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import arcn.roomfinder.habitacion.application.HabitacionServicio;
import arcn.roomfinder.habitacion.domain.model.EstadoHabitacion;
import arcn.roomfinder.habitacion.domain.model.Habitacion;
import arcn.roomfinder.habitacion.domain.model.Servicio;

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
            return ResponseEntity.status(500).body(e.getMessage());

        }
    }

    @GetMapping(value = "")
    public ResponseEntity<?> consultarTodasLasHabitaciones(){
        try{
            var habitaciones= habitacionServicio.consultarTodasLasHabitaciones();
            return ResponseEntity.status(200).body(habitaciones);
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());

        }
    }    

    @GetMapping(value = "/{numeroHabitacion}")
    public ResponseEntity<?> consultarHabitacionPorNumero(@PathVariable("numeroHabitacion") String numeroHabitacion){
        try{
            var habitacion= habitacionServicio.consultarHabitacionPorNumero(numeroHabitacion);
            return ResponseEntity.status(200).body(habitacion);
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping(value = "/{numeroHabitacion}/estadoHabitacion")
    public ResponseEntity<?> modificarEstadoHabitacion(@PathVariable String numeroHabitacion, @RequestParam("estadoHabitacion") EstadoHabitacion estadoHabitacion){
        try{
            habitacionServicio.modificarEstadoHabitacion(numeroHabitacion, estadoHabitacion);
            return ResponseEntity.status(200).body("Estado de la habitacion: " + numeroHabitacion + " cambiado a: " + estadoHabitacion);
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping(value = "/{numeroHabitacion}/precio")
    public ResponseEntity<?> modificarPrecioHabitacion(@PathVariable String numeroHabitacion, @RequestParam("precio") Double precio){
        try{
            habitacionServicio.modificarPrecioHabitacion(numeroHabitacion, precio);
            return ResponseEntity.status(200).body("Precio de la habitacion: " + numeroHabitacion + " cambiado a: " + precio);
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping(value = "/{numeroHabitacion}/descripcion")
    public ResponseEntity<?> modificarDescripcionHabitacion(@PathVariable String numeroHabitacion, @RequestParam("descripcion") String descripcion){
        try{
            habitacionServicio.modificarDescripcionHabitacion(numeroHabitacion, descripcion);
            return ResponseEntity.status(200).body("Descripcion de la habitacion: " + numeroHabitacion + " cambiada ");
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping(value = "/{numeroHabitacion}/servicios")
    public ResponseEntity<?> agregarServiciosHabitacion(@PathVariable String numeroHabitacion, @RequestBody Set<Servicio> servicios){
        try{
            habitacionServicio.agregarServiciosHabitacion(numeroHabitacion, servicios);
            return ResponseEntity.status(200).body("Servicios agregados a la habitacion: " + numeroHabitacion);
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PatchMapping(value = "/{numeroHabitacion}/servicios")
    public ResponseEntity<?> modificarServiciosHabitacion(@PathVariable String numeroHabitacion, @RequestBody Set<Servicio> servicios){
        try{
            habitacionServicio.modificarServiciosHabitacion(numeroHabitacion, servicios);
            return ResponseEntity.status(200).body("Servicios modificados a la habitacion: " + numeroHabitacion);
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{numeroHabitacion}/servicios")
    public ResponseEntity<?> eliminarServiciosHabitacion(@PathVariable String numeroHabitacion,  @RequestBody Set<Servicio> servicios){
        try{
            habitacionServicio.eliminarServiciosHabitacion(numeroHabitacion, servicios);
            return ResponseEntity.status(200).body("Servicios eliminados de la habitacion: " + numeroHabitacion);
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}

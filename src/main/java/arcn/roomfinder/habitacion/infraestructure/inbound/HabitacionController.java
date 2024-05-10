package arcn.roomfinder.habitacion.infraestructure.inbound;

import java.util.List;
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
import arcn.roomfinder.habitacion.domain.model.TipoHabitacion;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/habitacion")
public class HabitacionController {

    private HabitacionServicio habitacionServicio;

    @Autowired
    public HabitacionController(HabitacionServicio habitacionServicio) {
        this.habitacionServicio = habitacionServicio;
    }

    @Operation(summary = "Agregar habitacion")
    @PostMapping(value = "")
    public ResponseEntity<String> agregarHabitacion(@RequestBody Habitacion habitacion){
        try{
            var habitacionAgregada= habitacionServicio.agregarHabitacion(habitacion);
            return ResponseEntity.status(201).body("Habitacion: " + habitacionAgregada.getNumeroHabitacion() +" agregada");

        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());

        }
    }

    @Operation(summary = "Consultar todas las habitaciones")
    @GetMapping(value = "")
    public ResponseEntity<List<Habitacion>> consultarTodasLasHabitaciones(){
        try{
            var habitaciones= habitacionServicio.consultarTodasLasHabitaciones();
            return ResponseEntity.status(200).body(habitaciones);
        }catch(Exception e){
            return ResponseEntity.status(500).body(null);

        }
    }    

    @Operation(summary = "Consultar habitacion por numero")
    @GetMapping(value = "/numero/{numeroHabitacion}")
    public ResponseEntity<Habitacion> consultarHabitacionPorNumero(@PathVariable("numeroHabitacion") String numeroHabitacion){
        try{
            var habitacion= habitacionServicio.consultarHabitacionPorNumero(numeroHabitacion);
            return ResponseEntity.status(200).body(habitacion);
        }catch(Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "Consultar habitacion por tipo")
    @GetMapping(value = "/tipo/{tipoHabitacion}")
    public ResponseEntity<List<Habitacion>> consultarHabitacionesPorTipo(@PathVariable("tipoHabitacion") TipoHabitacion tipoHabitacion){
        try{
            var habitacion= habitacionServicio.consultarHabitacionesPorTipo(tipoHabitacion);
            return ResponseEntity.status(200).body(habitacion);
        }catch(Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "Consultar habitacion por precio")
    @GetMapping(value = "/precio/{precio}")
    public ResponseEntity<List<Habitacion>> consultarHabitacionesPorPrecio(@PathVariable("precio") Double precio){
        try{
            var habitacion= habitacionServicio.consultarHabitacionesPorPrecio(precio);
            return ResponseEntity.status(200).body(habitacion);
        }catch(Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "Modificar el estado de la habitacion")
    @PutMapping(value = "/{numeroHabitacion}/estadoHabitacion")
    public ResponseEntity<String> modificarEstadoHabitacion(@PathVariable String numeroHabitacion, @RequestParam("estadoHabitacion") EstadoHabitacion estadoHabitacion){
        try{
            habitacionServicio.modificarEstadoHabitacion(numeroHabitacion, estadoHabitacion);
            return ResponseEntity.status(200).body("Estado de la habitacion: " + numeroHabitacion + " cambiado a: " + estadoHabitacion);
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @Operation(summary = "Modificar el precio de la habitacion")
    @PutMapping(value = "/{numeroHabitacion}/precio")
    public ResponseEntity<String> modificarPrecioHabitacion(@PathVariable String numeroHabitacion, @RequestParam("precio") Double precio){
        try{
            habitacionServicio.modificarPrecioHabitacion(numeroHabitacion, precio);
            return ResponseEntity.status(200).body("Precio de la habitacion: " + numeroHabitacion + " cambiado a: " + precio);
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @Operation(summary = "Modificar la descripcion de la habitacion")
    @PutMapping(value = "/{numeroHabitacion}/descripcion")
    public ResponseEntity<String> modificarDescripcionHabitacion(@PathVariable String numeroHabitacion, @RequestParam("descripcion") String descripcion){
        try{
            habitacionServicio.modificarDescripcionHabitacion(numeroHabitacion, descripcion);
            return ResponseEntity.status(200).body("Descripcion de la habitacion: " + numeroHabitacion + " cambiada ");
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @Operation(summary = "Agregar servicios a la habitacion")
    @PutMapping(value = "/{numeroHabitacion}/servicios")
    public ResponseEntity<String> agregarServiciosHabitacion(@PathVariable String numeroHabitacion, @RequestBody Set<Servicio> servicios){
        try{
            habitacionServicio.agregarServiciosHabitacion(numeroHabitacion, servicios);
            return ResponseEntity.status(200).body("Servicios agregados a la habitacion: " + numeroHabitacion);
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @Operation(summary = "Modificar servicios de la habitacion")
    @PatchMapping(value = "/{numeroHabitacion}/servicios")
    public ResponseEntity<String> modificarServiciosHabitacion(@PathVariable String numeroHabitacion, @RequestBody Set<Servicio> servicios){
        try{
            habitacionServicio.modificarServiciosHabitacion(numeroHabitacion, servicios);
            return ResponseEntity.status(200).body("Servicios modificados a la habitacion: " + numeroHabitacion);
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar servicios de la habitacion")
    @DeleteMapping(value = "/{numeroHabitacion}/servicios")
    public ResponseEntity<String> eliminarServiciosHabitacion(@PathVariable String numeroHabitacion,  @RequestBody Set<Servicio> servicios){
        try{
            habitacionServicio.eliminarServiciosHabitacion(numeroHabitacion, servicios);
            return ResponseEntity.status(200).body("Servicios eliminados de la habitacion: " + numeroHabitacion);
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}

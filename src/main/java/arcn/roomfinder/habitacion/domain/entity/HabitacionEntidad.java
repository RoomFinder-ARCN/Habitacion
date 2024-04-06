package arcn.roomfinder.habitacion.domain.entity;

import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import arcn.roomfinder.habitacion.domain.model.EstadoHabitacion;
import arcn.roomfinder.habitacion.domain.model.Servicio;
import arcn.roomfinder.habitacion.domain.model.TipoHabitacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "habitaciones")
public class HabitacionEntidad {

    private TipoHabitacion tipoHabitacion;

    @Id
    private String numeroHabitacion;

    private EstadoHabitacion estadoHabitacion;
    private Double precio;
    private int capacidad;
    private String descripcion;
    private Collection<Servicio> servicios;
    
}

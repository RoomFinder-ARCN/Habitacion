package arcn.roomfinder.habitacion.domain.model;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Habitacion {

    @NonNull
    private TipoHabitacion tipoHabitacion;

    @NonNull
    private String numeroHabitacion;

    @NonNull
    private EstadoHabitacion estadoHabitacion;

    @NonNull
    private Double precio;

    private int capacidad;

    private String descripcion;

    @NonNull
    private Set<Servicio> servicios;
    
}

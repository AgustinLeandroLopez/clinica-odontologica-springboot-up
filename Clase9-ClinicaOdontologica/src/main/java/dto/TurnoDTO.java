package dto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TurnoDTO {

    private Integer id;
    private LocalDate fecha;
    private Integer pacienteId;
    private Integer odontologoId;
}

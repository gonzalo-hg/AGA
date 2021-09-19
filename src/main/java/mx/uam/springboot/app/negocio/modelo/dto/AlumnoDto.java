package mx.uam.springboot.app.negocio.modelo.dto;
import lombok.Data;
import mx.uam.springboot.app.negocio.modelo.Alumno;

/**
* DTO de alumnos
*
*/
@Data
public class AlumnoDto {
	private String matricula;
	private String plan; // No vacío, no numérico
	private String edad; // No vacío
	private String nombre; // Rango entre 1 - 120
	private String ape_pat; // No vacío
	private String ape_mat; // No vacío
	
	public static AlumnoDto creaDto(Alumno alumno) {
		AlumnoDto dto = new AlumnoDto();
		dto.setMatricula(alumno.getMAT());
		dto.setPlan(alumno.getPLA());
		dto.setEdad(alumno.getEDAD());
		dto.setNombre(alumno.getNOM());
		dto.setApe_pat(alumno.getPATE());
		dto.setApe_mat(alumno.getMATE());
		return dto;
	}
}
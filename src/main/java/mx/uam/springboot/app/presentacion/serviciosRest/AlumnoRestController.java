package mx.uam.springboot.app.presentacion.serviciosRest;


import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.uam.springboot.app.negocio.AlumnoService;
import mx.uam.springboot.app.negocio.modelo.Alumno;
import mx.uam.springboot.app.negocio.modelo.dto.AlumnoDto;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/api")
public class AlumnoRestController {
	
	@Autowired
	private AlumnoService alumnoService;
	
	
	//PETICIONES GET
	
	/**
	 * Endpoint para realizar una petición GET
	 * @param alumnoId El id asignado en la BD
	 * @return El alumno cuyo id=alumnoId si existe, null en caso contrario
	 */
	@GetMapping("/alumnos/{alumnoId}")
	public Alumno findById(@PathVariable final String alumnoId) {
		return alumnoService.findById(alumnoId);
	}
	
	/**
	 * Endpoint para realizar una petición GET
	 * @param matricula La matrícula del alumno a consultar
	 * @return El alumno cuya MAT=matricula si existe, null en caso contrario
	 */
	@GetMapping("/alumnos/matricula/{matricula}")
	public Alumno findByMatricula(@PathVariable final String matricula) {
		return alumnoService.findByMatricula(matricula);
		//return alumnoRepository.findByMAT(matricula);
	}
	
	/**
	 * Endpoint para realizar una petición GET
	 * @return Una lista con todos los alumnos existentes
	 */
	@GetMapping("/alumnos")
	public List<Alumno> mostrarProductos(){
		return alumnoService.findAll();	
	}	
	
	/**
	 * Endpoint para realizar una petición GET
	 * @param plan El plan de estudios de los alumnos a consultar
	 * @param trimestre El último trimestre de reinscripción de los alumnos a consultar
	 * @return Una lista con todos los alumnos cuyo atributo PLA=plan y UT_RE=trimestre
	 */
	@GetMapping("/alumnos?plan={plan}&trimestre=20P&sexo=M")
	public List<AlumnoDto> findByPlanAndTrimestre(@PathVariable String plan, @PathVariable String trimestre){
		return alumnoService.findByPlanAndTrimestre(plan, trimestre);
	}
	
	/*
	@GetMapping(path = "/hotels/{hotelCode}/prices", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResultListDto <HotelPrices>> retrievePrices(@PathVariable(
			"hotelCode") String hotelCode,
			@ApiParam(name = "startDate", value = "Date yyyy-mm-dd", defaultValue = "2020-01-01", required=true)
			@RequestParam(required = true) String startDate,
			@ApiParam(name = "endDate", value = "Date yyyy-mm-dd", defaultValue = "2020-01-01", required=false)
			@RequestParam(required = fa*/
	
	/**
	 * Endpoint para realizar una petición GET
	 * @param plan El plan de estudios de los alumnos a consultar
	 * @param sexo El sexo de los alumnos a consultar
	 * @param trimestre El último trimestre de reinscripción de los alumnos a consultar
	 * @return Una lista con todos los alumnos cuyos atributos PLA=plan, SEXO=sexo y UT_RE=trimestre
	 */
	@GetMapping("/alumnos/plan/{plan}/trimestre/{trimestre}/sexo/{sexo}")
	public List<Alumno> findByPlanAndSexoAndTrimestre(@PathVariable final String plan,
			@PathVariable final String sexo,@PathVariable final String trimestre){
				return alumnoService.findByPlanAndSexoAndTrimestre(plan,sexo,trimestre);
	}

	
	//PETICIONES POST

	/**
	 * Metodo para agregar alumnos a la BD
	 * */
	@PostMapping("/alumnos")
	public void agregarAlumno(@RequestBody final List<Alumno> alumno){
		alumnoService.saveAll(alumno);
	}
	
	
	//PETICIONES PATCH
	
	/**
	 * Metodo para actulizar un registro en la BD de manera
	 * basado en su ID y es parcialmente
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * */
	@PatchMapping("/alumnos/{alumnoId}")
	public void UpdateAlumno(@PathVariable final String alumnoId,
			@RequestBody final Alumno alumno) throws Exception {
		for(final Field campo : Alumno.class.getDeclaredFields()) {
			final String fieldname = campo.getName();
			
			if(fieldname.equals("id")) {
				continue;
			}
			
			final java.lang.reflect.Method getter = Alumno.class.getDeclaredMethod( "get"+StringUtils.capitalize(fieldname));
			final  Object valorCampo = getter.invoke(alumno);
			
			if(Objects.nonNull(valorCampo)) {
				
				alumnoService.UpdateAlumno(alumnoId, fieldname, valorCampo);
			}
		}
	}
	
	@GetMapping("/alumnos/solo")
	public List<Alumno> buscaUno() {
		return alumnoService.consultaAlumno();
	}
	
	//OTROS
	
	/**
	 * Metodo para renombrar las fotografias
	 * 
	@GetMapping("/alumnos/fotos/cambio-nombre")
	public void findByMatricula() {
		alumnoService.cambiaNombreFotos();
		//return alumnoRepository.findByMAT(matricula);
	}
	*/
	
}

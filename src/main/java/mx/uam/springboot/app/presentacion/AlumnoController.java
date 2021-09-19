package mx.uam.springboot.app.presentacion;


import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import mx.uam.springboot.app.datos.AlumnoRepository;
import mx.uam.springboot.app.negocio.ServicioAlumno;
import mx.uam.springboot.app.negocio.modelo.Alumno;
import mx.uam.springboot.app.negocio.modelo.dto.AlumnoDto;

@RestController
public class AlumnoController {
	
	@Autowired
	private ServicioAlumno servicioAlumno;

	/**
	 * Metodo para agregar alumnos a la BD
	 * */
	@PostMapping("/alumnos")
	public void agregarAlumno(@RequestBody final List<Alumno> alumno){
		servicioAlumno.saveAll(alumno);
	}

	/**
	 * Metodo para consultar todos los alumnos en la BD
	 * */
	@GetMapping("/alumnos")
	public List<Alumno> mostrarProductos(){
		return servicioAlumno.findAll();	
	}
	
	/**
	 * Metodo para consultar un alumno por ID en la DB
	 * */
	@GetMapping("/alumnos/{alumnoId}")
	public Alumno findAlumno(@PathVariable final String alumnoId) {
		return servicioAlumno.findById(alumnoId);
	}
	
	/**
	 * Metodo para consultar un alumno por MAtricula
	 * */
	@GetMapping("/alumnos/fotos/{matricula}")
	public Alumno findByMatricula(@PathVariable final String matricula) {
		return servicioAlumno.consultaPorMatricula(matricula);
		//return alumnoRepository.findByMAT(matricula);
	}
	
	/**
	 * Metodo para renombrar las fotografias
	 * */
	@GetMapping("/alumnos/fotos/cambio-nombre")
	public void findByMatricula() {
		System.out.println("Controlador");
		servicioAlumno.cambiaNombreFotos();
		//return alumnoRepository.findByMAT(matricula);
	}
	
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
				
				servicioAlumno.UpdateAlumno(alumnoId, fieldname, valorCampo);
			}
		}
	}
	
	@GetMapping("/alumnos/lista/{plan}/{sexo}/{trimestre}")
	public List<Alumno> clasificacionEdadSexoCarrera(@PathVariable final String plan,
			@PathVariable final String sexo,@PathVariable final String trimestre){
		System.out.println(servicioAlumno.clasificacionSexo(plan,sexo,trimestre));
				return servicioAlumno.clasificacionSexo(plan,sexo,trimestre);
	}
	
	@GetMapping(path = "/alumnos/lista/{plan}")
	public List<Alumno> clasificacionEdadSexoCarrera2(@PathVariable final String plan,
			@RequestBody final String sexo,@RequestBody final String trimestre ){
		System.out.println(servicioAlumno.clasificacionSexo(plan,sexo,trimestre));
				return servicioAlumno.clasificacionSexo(plan,sexo,trimestre);
	}
	
	@GetMapping("/alumnos/lista/sexo-edad-lic/{plan}/{trimestre}")
	public List<AlumnoDto> listaSexoEdadLic(@PathVariable String plan, @PathVariable String trimestre){
		System.out.println(servicioAlumno.consultaPorCarreraTrimestre(plan, trimestre));
		return servicioAlumno.consultaPorCarreraTrimestre(plan, trimestre);
	}
	
}

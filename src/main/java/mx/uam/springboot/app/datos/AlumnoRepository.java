package mx.uam.springboot.app.datos;


import org.springframework.data.mongodb.repository.MongoRepository;

import mx.uam.springboot.app.negocio.modelo.Alumno;

public interface AlumnoRepository extends MongoRepository<Alumno, String>{
	
	public Alumno findByMAT(String matricula);
}

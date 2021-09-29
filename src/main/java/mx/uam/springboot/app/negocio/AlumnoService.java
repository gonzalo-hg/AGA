package mx.uam.springboot.app.negocio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.BasicUpdate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import mx.uam.springboot.app.negocio.modelo.Alumno;
import mx.uam.springboot.app.negocio.modelo.dto.AlumnoDto;

@Service
public class AlumnoService {

	@Autowired
	private MongoTemplate mongoTemplate;
	

	//METODOS GET
	
	
	/**
	 * Consulta a un alumno por id
	 * @param alumnoId El id asignado en la BD
	 * @return El alumno cuyo id=alumnoId si existe, null en caso contrario
	 */
	public Alumno findById(@PathVariable final String alumnoId) {
		return mongoTemplate.findById(alumnoId,Alumno.class);
	}
	
	
	/**
	 * Consulta a un alumno por matricula
	 * @param matricula La matrícula del alumno a consultar
	 * @return El alumno cuya MAT=matricula si existe, null en caso contrario
	 */
	public Alumno findByMatricula(long matricula) {
		Query query = new Query();
		query.addCriteria(Criteria.where("MAT").is(matricula));
		query.fields().include("MAT","PATE","MATE","NOM");
		Alumno alumonosConsulta =  mongoTemplate.findOne(query,Alumno.class);
		return alumonosConsulta;
	}
	
	/**
	 * Consulta a todos los alumnos existentes en la BD
	 * @return Una lista con todos los alumnos
	 */
	public List<Alumno> findAll(){
		return mongoTemplate.findAll(Alumno.class);
	}
	
	/**
	 * Consulta a los alumnos de una carrera dada
	 * @param plan El plan de estudios de los alumnos a consultar
	 * @return Una lista con todos los alumnos cuyo atributo PLA=plan
	 */
	public List<Alumno> findByPlan(String plan) {
		Query query = new Query();
		query.addCriteria(Criteria.where("PLA").is(plan));
		query.fields().include("NOM","PLA","MAT","PATE","MATE");
		List<Alumno> alumonosCoincidentes = mongoTemplate.find(query,Alumno.class);
		return alumonosCoincidentes;
	}
	
	/**
	 * Consulta a los alumnos filtrados por plan de estudios y último trimestre de reinscripción
	 * @param plan El plan de estudios de los alumnos a consultar
	 * @param trimestre El último trimestre de reinscripción de los alumnos a consultar
	 * @return Una lista con todos los alumnos cuyos atributos PLA=plan y UT_RE=trimestre
	 */
	public List<AlumnoDto> findByPlanAndTrimestre(String plan, String trimestre) {
		Query query = new Query();
		query.addCriteria(new Criteria().andOperator(
				Criteria.where("PLA").is(plan),
				Criteria.where("UT_RE").is(trimestre)));
		query.fields().include("MAT","PLA","EDAD","PATE","MATE","NOM");
		//query.fields().elemMatch(plan,  Criteria.where("online").is(true));
		List<AlumnoDto> alumnosConsulta = new ArrayList<AlumnoDto>();
		
		for(Alumno a: mongoTemplate.find(query,Alumno.class)) {
			alumnosConsulta.add(AlumnoDto.creaDto(a));
		}
		
		return alumnosConsulta;
	}
	
	/**
	 * Consulta a los alumnos filtrados por plan de estudios, sexo y último trimestre de reinscripción
	 * @param plan El plan de estudios de los alumnos a consultar
	 * @param sexo El sexo de los alumnos a consultar
	 * @param trimestre El último trimestre de reinscripción de los alumnos a consultar
	 * @return Una lista con todos los alumnos cuyos atributos PLA=plan, SEXO=sexo y UT_RE=trimestre
	 */
	public List<Alumno> findByPlanAndSexoAndTrimestre(long plan,String sexo,String trimestre) {
		Query query = new Query();
		//query.addCriteria(Criteria.where("PLA").is(plan).and("SEXO").is(sexo));
		//query = BasicQuery.query(Criteria.where("PLA").is(plan).and("SEXO").is(sexo));
		query.addCriteria(new Criteria().andOperator(
				Criteria.where("PLA").is(plan),
				Criteria.where("SEXO").is(sexo),
				Criteria.where("UT_RE").is(trimestre)
				));
		//Query query = new BasicQuery("{$and: [{'SEXO': 'M'},{'PLA':'52'}]}");
		query.fields().include("MAT","PLA","EDAD","PATE","MATE","NOM","SEXO");
		List<Alumno> alumonosCoincidentes = mongoTemplate.find(query,Alumno.class);
		System.out.println("Tamaño de la consulta: " + alumonosCoincidentes.size());
		return alumonosCoincidentes;
	}
	
	
	//METODOS POST
	
	/**
	 * Guarda en la BD a los alumnos recibidos como parámetro
	 * @param alumnos La lista de alumnos que se quiere guardar
	 */
	public void saveAll(final List<Alumno> alumnos) {
		mongoTemplate.insertAll(alumnos);
	}
	
	public List<Alumno> consultaAlumno() {
		Query query = new Query();
		query.addCriteria(new Criteria().andOperator(
				Criteria.where("PLA").is("57"),
				Criteria.where("UT_RE").is("20P")));
		query.fields().include("MAT","PLA","EDAD","PATE","MATE","NOM"); 
		List<Alumno> alumno = mongoTemplate.find(query,Alumno.class);
		return alumno;
	}
	
	

	//METODOS UPDATE/PATCH
	
	/**
	 * Actualiza el valor de un atributo de un alumno dado
	 * @param alumnoId El id del alumno en cuestión
	 * @param fieldname El nombre del campo que se quiere actualizar
	 * @param fieldValue El nuevo valor del campo
	 */
	public void UpdateAlumno(String alumnoId, String fieldname, Object fieldValue) {
		mongoTemplate.findAndModify(BasicQuery.query(Criteria.where("id").is(alumnoId)),
				BasicUpdate.update(fieldname, fieldValue),
				FindAndModifyOptions.none(),Alumno.class);
	}
	

	/*public void cambiaNombreFotos() {

	public void cambiaNombreFotos() {
		System.out.println("Entro cambioNombreFotos");
		Query query = new Query();
		File carpetaFotos = new File("C:\\Users\\gonza\\OneDrive\\Imágenes\\Saved Pictures\\FOTOGRAFIAS EGRESADOS 20P");//Carpeta donde se almacenan las fotos
		File[] listaFotos = carpetaFotos.listFiles();//Lista de fotos 
		//File[] listaFotosAux //Lista de fotos auxiliar
		String[] nombreFotos = new String[listaFotos.length];//Arreglo que guarda los nombres de las fotos
		String extensionFoto = null;
		//Verificamos que los archivos contenidos sean jpg
		for (int i = 0; i < listaFotos.length; i++) {
			System.out.println(FilenameUtils.getBaseName(listaFotos[i].getName()));

			nombreFotos[i] = FilenameUtils.getBaseName(listaFotos[i].getName());//Se guarda el nombre de la foto
			extensionFoto = FilenameUtils.getExtension(listaFotos[i].getName());//Guardamos la extension de la foto
			if(extensionFoto.contains("jpg")) {//Si es una foto cambiamos el nombre
				query.addCriteria(Criteria.where("MAT").is(nombreFotos[i]));
				//query2 = new BasicQuery("MAT : " + nombreFotos[i]);
				//query.fields().include("PATE","MATE","NOM");
				Alumno alumonosConsulta =  mongoTemplate.findOne(query,Alumno.class);
				
				if(alumonosConsulta != null) {
					System.out.println(alumonosConsulta.getNOM());
					File fotoRenombrada = new File("C:\\Users\\gonza\\OneDrive\\Escritorio\\renombrados"+"\\"+alumonosConsulta.getNOM()+"_"+alumonosConsulta.getPATE()+"_"+alumonosConsulta.getMATE()+"."+extensionFoto);
					listaFotos[i].renameTo(fotoRenombrada);
				}else {
					System.out.println(nombreFotos[i]+" es nulo");
				}
					
				query = new Query();
			}
			//System.out.println(listaFotos[i].getName());
		}
		
	}*/

}

package mx.uam.springboot.app.negocio;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.BasicUpdate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import mx.uam.springboot.app.datos.AlumnoRepository;
import mx.uam.springboot.app.negocio.modelo.Alumno;
import mx.uam.springboot.app.negocio.modelo.dto.AlumnoDto;

@Service
public class ServicioAlumno {
	
	@Autowired
	private AlumnoRepository alumnoRepository;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	public void saveAll(final List<Alumno> alumnos) {
		mongoTemplate.insertAll(alumnos);
	}
	
	public List<Alumno> findAll(){
		return mongoTemplate.findAll(Alumno.class);
	}
	
	public Alumno findById(@PathVariable final String alumnoId) {
		return mongoTemplate.findById(alumnoId,Alumno.class);
	}
	
	public void UpdateAlumno(String alumnoId, String fieldname, Object fieldValue) {
		mongoTemplate.findAndModify(BasicQuery.query(Criteria.where("id").is(alumnoId)),
				BasicUpdate.update(fieldname, fieldValue),
				FindAndModifyOptions.none(),Alumno.class);
	}
	
	public List<Alumno> findByCarrera2(String carrera) {
		Query query = new Query();
		query.addCriteria(Criteria.where("carrera").is(carrera));
		query.fields().include("nombre","carrera","matricula","apellidoP","apellidoM");
		List<Alumno> alumonosCoincidentes = mongoTemplate.find(query,Alumno.class);
		return alumonosCoincidentes;
	}
	
	public List<Alumno> clasificacionSexo(String plan,String sexo,String trimestre) {
		Query query = new Query();
		//query.addCriteria(Criteria.where("PLA").is(plan).and("SEXO").is(sexo));
		//query = BasicQuery.query(Criteria.where("PLA").is(plan).and("SEXO").is(sexo));
		query.addCriteria(new Criteria().andOperator(
				Criteria.where("PLA").is(plan),
				Criteria.where("SEXO").is(sexo),
				Criteria.where("UT_RE").is(trimestre)
				));
		//Query query = new BasicQuery("{$and: [{'SEXO': 'M'},{'PLA':'52'}]}");
		query.fields().include("NOM","MAT","EDAD");
		List<Alumno> alumonosCoincidentes = mongoTemplate.find(query,Alumno.class);
		for (Alumno alumno : alumonosCoincidentes) {
			System.out.println(alumno.getMAT() + " " + alumno.getEDAD()+ " " +alumno.getNOM());
		}
		System.out.println("Tamaño de la consulta: " + alumonosCoincidentes.size());
		return alumonosCoincidentes;
	}
	
	public List<AlumnoDto> consultaPorCarreraTrimestre(String plan, String trimestre) {
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
	
	public Alumno consultaPorMatricula(String matricula) {
		Query query = new Query();
		query.addCriteria(Criteria.where("MAT").is(matricula));
		query.fields().include("MAT","PATE","MATE","NOM");
		Alumno alumonosConsulta =  mongoTemplate.findOne(query,Alumno.class);
		return alumonosConsulta;
	}
	
	public void cambiaNombreFotos() {
		System.out.println("Entro cambioNombreFotos");
		Query query = new Query();
		File carpetaFotos = new File("C:\\Users\\gonza\\OneDrive\\Imágenes\\Saved Pictures\\FOTOGRAFIAS EGRESADOS 20P");//Carpeta donde se almacenan las fotos
		File[] listaFotos = carpetaFotos.listFiles();//Lista de fotos 
		//File[] listaFotosAux //Lista de fotos auxiliar
		String[] nombreFotos = new String[listaFotos.length];//Arreglo que guarda los nombres de las fotos
		String extensionFoto = null,extensionAux=null;
		
		int contadorFotos=0;
		//Verificamos que los archivos contenidos sean jpg
		for (int i = 0; i < listaFotos.length; i++) {
			System.out.println(FilenameUtils.getBaseName(listaFotos[i].getName()));

			nombreFotos[i] = FilenameUtils.getBaseName(listaFotos[i].getName());//Se guarda el nombre de la foto
			extensionFoto = FilenameUtils.getExtension(listaFotos[i].getName());//Guardamos la extension de la foto
			if(extensionFoto.contains("jpg")) {//Si es una foto cambiamos el nombre
				contadorFotos++;//Contamos las fotos en la carpeta
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
		
	}
	
	
}
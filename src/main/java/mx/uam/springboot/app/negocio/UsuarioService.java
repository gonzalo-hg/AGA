package mx.uam.springboot.app.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import mx.uam.springboot.app.negocio.modelo.Usuario;
import mx.uam.springboot.app.negocio.modelo.dto.UsuarioDto;

@Service
public class UsuarioService {
	
	@Autowired
	private MongoTemplate mongoTemplate; 

	/**
	 * Método que permite localizar al usuario en la DB
	 * @author gonzalo
	 * @param 
	 * 
	 * @return Usuario
	 * */
	public Usuario iniciarSesion(final String email, final String password) {
		Query query = new Query();
		query.addCriteria(new Criteria().andOperator(
				Criteria.where("email").is(email),
				Criteria.where("password").is(password)));
		query.fields().include("email","password","rol");
		Usuario usuario = null;
		try {
			usuario = mongoTemplate.findOne(query, Usuario.class);
			return usuario;
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usuario;
	}

	
}

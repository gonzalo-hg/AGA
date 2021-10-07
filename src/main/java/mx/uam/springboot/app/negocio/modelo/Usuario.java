package mx.uam.springboot.app.negocio.modelo;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collation = "Usuarios")
public class Usuario implements Serializable{

	@Id
	private String id;
	
	private String nombre;
	private String apellidoP;
	private String apellidoM;
	private String email;
	private String password;
	private String rol;
	
	private static final long serialVersionUID = 1L;

	
}

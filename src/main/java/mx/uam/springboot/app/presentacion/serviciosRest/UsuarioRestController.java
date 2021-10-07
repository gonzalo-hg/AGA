package mx.uam.springboot.app.presentacion.serviciosRest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import mx.uam.springboot.app.negocio.UsuarioService;
import mx.uam.springboot.app.negocio.modelo.Usuario;
import mx.uam.springboot.app.negocio.modelo.dto.UsuarioDto;

@Controller
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class UsuarioRestController {

	@Autowired
	private UsuarioService usuarioService;
	
	
	@GetMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UsuarioDto> userLogin(@RequestBody UsuarioDto usuarioDto){
		Usuario usuario = usuarioService.iniciarSesion(usuarioDto.getEmail(), usuarioDto.getPassword());
		UsuarioDto usuarioDtoNuevo = UsuarioDto.creaDto(usuario);
		if(usuarioDto.equals(usuarioDtoNuevo)) {
			return ResponseEntity.status(HttpStatus.OK).body(usuarioDtoNuevo);
		}
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(usuarioDtoNuevo);
	}
	
}

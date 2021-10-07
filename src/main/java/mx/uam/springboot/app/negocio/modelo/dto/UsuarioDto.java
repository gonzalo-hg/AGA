package mx.uam.springboot.app.negocio.modelo.dto;

import lombok.Data;
import mx.uam.springboot.app.negocio.modelo.Usuario;

@Data
public class UsuarioDto {

	private String email;
	
	private String password;
	
	private String rol;
	
	public static UsuarioDto creaDto(Usuario usuario) {
		UsuarioDto usuarioDto = new UsuarioDto();
		usuarioDto.setEmail(usuario.getEmail());
		usuarioDto.setPassword(usuario.getPassword());
		usuarioDto.setRol(usuario.getRol());
		return usuarioDto;
	}
}

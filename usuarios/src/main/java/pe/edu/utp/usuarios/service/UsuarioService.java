package pe.edu.utp.usuarios.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import pe.edu.utp.usuarios.dto.LoginRequestDTO;
import pe.edu.utp.usuarios.dto.LoginResponseDTO;
import pe.edu.utp.usuarios.entity.Usuario;

public interface UsuarioService {
	public List<Usuario> findAll(Pageable page);
	public List<Usuario> findByEmail(String email, Pageable page);
	public Usuario findById(int id);
	public Usuario save(Usuario usuario);
	public Usuario update(Usuario usuario);
	public boolean delete(int id);
	public LoginResponseDTO login(LoginRequestDTO request);
}

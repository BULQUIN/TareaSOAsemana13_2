package pe.edu.utp.usuarios.soap;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import pe.edu.utp.usuarios.AddUsuarioRequest;
import pe.edu.utp.usuarios.AddUsuarioResponse;
import pe.edu.utp.usuarios.UsuarioDetalle;
import pe.edu.utp.usuarios.dto.LoginRequestDTO;
import pe.edu.utp.usuarios.dto.LoginResponseDTO;
import pe.edu.utp.usuarios.DeleteUsuarioRequest;
import pe.edu.utp.usuarios.DeleteUsuarioResponse;
import pe.edu.utp.usuarios.GetAllUsuariosRequest;
import pe.edu.utp.usuarios.GetAllUsuariosResponse;
import pe.edu.utp.usuarios.GetUsuarioRequest;
import pe.edu.utp.usuarios.GetUsuarioResponse;
import pe.edu.utp.usuarios.LoginUsuarioResponse;
import pe.edu.utp.usuarios.ServiceStatus;
import pe.edu.utp.usuarios.UpdateUsuarioRequest;
import pe.edu.utp.usuarios.UpdateUsuarioResponse;
import pe.edu.utp.usuarios.entity.Usuario;

import pe.edu.utp.usuarios.service.impl.UsuarioServiceImpl;

@Endpoint
public class UsuarioEndpoint {
	@Autowired
	private UsuarioServiceImpl service;
	
	@PayloadRoot(namespace = "http://utp.edu.pe/usuarios", localPart = "GetAllUsuariosRequest")
	@ResponsePayload
	public GetAllUsuariosResponse findAll (@RequestPayload GetAllUsuariosRequest request) {
		GetAllUsuariosResponse response = new GetAllUsuariosResponse();
		
		Pageable page = PageRequest.of(request.getOffset(), request.getLimit());
		List<Usuario> usuarios;
		if(request.getTexto()==null) {
			usuarios = service.findAll(page);
		}else {
			usuarios=service.findByEmail(request.getTexto(), page);
		}
		
		List<UsuarioDetalle> usuariosResponse=new ArrayList<>();
		for (int i = 0; i < usuarios.size(); i++) {
		     UsuarioDetalle ob = new UsuarioDetalle();
		     BeanUtils.copyProperties(usuarios.get(i), ob);
		     usuariosResponse.add(ob);    
		}
		response.getUsuarioDetalle().addAll(usuariosResponse);
		return response;
	}
	
	@PayloadRoot(namespace = "http://utp.edu.pe/usuarios", localPart = "GetUsuarioRequest")
	@ResponsePayload
	public GetUsuarioResponse findById (@RequestPayload GetUsuarioRequest request) {
		GetUsuarioResponse response = new GetUsuarioResponse();
		Usuario entity=service.findById(request.getId());
		UsuarioDetalle usuario=new UsuarioDetalle();
		usuario.setId(entity.getId());
		usuario.setEmail(entity.getEmail());
		usuario.setPassword(entity.getPassword());
		usuario.setActivo(entity.isActivo());
		usuario.setRol(entity.getRol().toString());
		response.setUsuarioDetalle(usuario);		
		return response;
	}
	
	@PayloadRoot(namespace = "http://utp.edu.pe/usuarios", localPart = "AddUsuarioRequest")
	@ResponsePayload
	public AddUsuarioResponse create (@RequestPayload AddUsuarioRequest request) {
		ServiceStatus serviceStatus=new ServiceStatus();
		AddUsuarioResponse response = new AddUsuarioResponse();
		Usuario entity = new Usuario();
		entity.setEmail(request.getEmail());
		entity.setPassword(request.getPassword());
		entity.setActivo(true);
		entity.setRol(request.getRol());
		entity=service.save(entity);
		if(entity!=null) {
			UsuarioDetalle usuario=new UsuarioDetalle();
			BeanUtils.copyProperties(entity, usuario);
			response.setUsuarioDetalle(usuario);
			serviceStatus.setStatusCode("SUCCESS");
			serviceStatus.setMessage("Content Added Successfully");
			response.setServiceStatus(serviceStatus);
		}else {
			serviceStatus.setStatusCode("CONFLICT");
			serviceStatus.setMessage("Content Already Available");
			response.setServiceStatus(serviceStatus);
		}
		return response;
	}
	@PayloadRoot(namespace = "http://utp.edu.pe/usuarios", localPart = "UpdateUsuarioRequest")
	@ResponsePayload
	public UpdateUsuarioResponse update (@RequestPayload UpdateUsuarioRequest request) {
		ServiceStatus serviceStatus = new ServiceStatus();
		UpdateUsuarioResponse response= new UpdateUsuarioResponse();
		Usuario entity = service.findById(request.getId());
		entity.setEmail(request.getEmail());
		entity.setPassword(request.getPassword());
		entity.setRol(request.getRol());
		entity=service.update(entity);		
		if (entity!=null) {
			UsuarioDetalle usuario= new UsuarioDetalle();
			BeanUtils.copyProperties(entity, usuario);
			response.setUsuarioDetalle(usuario);
			serviceStatus.setStatusCode("SUCCESS");
     	    serviceStatus.setMessage("Content Updated Successfully");
     	    response.setServiceStatus(serviceStatus);			
		}else {
			serviceStatus.setStatusCode("CONFLICT");
	     	serviceStatus.setMessage("Content Not Updated");
	     	response.setServiceStatus(serviceStatus);
		}
		return response;
	}
	
	@PayloadRoot(namespace = "http://utp.edu.pe/usuarios", localPart = "DeleteUsuarioRequest")
	@ResponsePayload
	public DeleteUsuarioResponse create (@RequestPayload DeleteUsuarioRequest request) {
		ServiceStatus serviceStatus=new ServiceStatus();
		DeleteUsuarioResponse response = new DeleteUsuarioResponse();
		boolean resp=service.delete(request.getId());
		if(resp) {
			serviceStatus.setStatusCode("SUCCESS");
			serviceStatus.setMessage("Content Deleted Successfully");
			response.setServiceStatus(serviceStatus);
		}else {
			serviceStatus.setStatusCode("CONFLICT");
			serviceStatus.setMessage("Content Not Deleted");
			response.setServiceStatus(serviceStatus);
		}
		return response;
	}
	
	@PayloadRoot(namespace = "http://utp.edu.pe/usuarios", localPart = "LoginUsuarioRequest")
	@ResponsePayload
	public LoginResponseDTO login (@RequestPayload LoginRequestDTO request) {
		ServiceStatus serviceStatus = new ServiceStatus();
		LoginUsuarioResponse responseMensaje= new LoginUsuarioResponse();
		LoginResponseDTO response=service.login(request);	
		if (response!=null) {
			serviceStatus.setStatusCode("SUCCESS");
     	    serviceStatus.setMessage("Logeado Correctamente");
     	    responseMensaje.setServiceStatus(serviceStatus);	
     	    return response;
		}else {
			serviceStatus.setStatusCode("CONFLICT");
	     	serviceStatus.setMessage("Credenciales Incorrectas");
	     	responseMensaje.setServiceStatus(serviceStatus);
		}
		return response;
	}
}

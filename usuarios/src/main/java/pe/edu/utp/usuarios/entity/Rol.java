package pe.edu.utp.usuarios.entity;

public enum Rol {
	USER,ADMIN;
	
	public static Rol fromString(String rol) {
        return Rol.valueOf(rol.toUpperCase());
    }
}

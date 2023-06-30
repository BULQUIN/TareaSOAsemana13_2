package pe.edu.utp.usuarios.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;
import pe.edu.utp.usuarios.exception.GeneralServiceException;
import pe.edu.utp.usuarios.util.WrapperResponse;

@Slf4j
@ControllerAdvice
public class ErrorHandlerConfig extends ResponseEntityExceptionHandler{
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> all(Exception e,WebRequest request){
		log.error(e.getMessage(),e);
		WrapperResponse<?> response=new WrapperResponse<>(false,"Internal Server Error",null);
		return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(pe.edu.utp.usuarios.exception.ValidateServiceException.class)
	public ResponseEntity<?> validateService(pe.edu.utp.usuarios.exception.ValidateServiceException e, WebRequest request){
		log.info(e.getMessage(),e);
		WrapperResponse<?> response=new WrapperResponse<>(false,e.getMessage(),null);
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(pe.edu.utp.usuarios.exception.NoDataFoundException.class)
	public ResponseEntity<?> noData(pe.edu.utp.usuarios.exception.NoDataFoundException e, WebRequest request){
		log.info(e.getMessage(),e);
		WrapperResponse<?> response=new WrapperResponse<>(false,e.getMessage(),null);
		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}
	
	public ResponseEntity<?> generalService(GeneralServiceException e, WebRequest request){
		log.info(e.getMessage(),e);
		WrapperResponse<?> response=new WrapperResponse<>(false,"Internal Server Error",null);
		return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

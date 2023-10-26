package com.uam.projetoN1.exceptions;

import com.uam.projetoN1.dto.erro.ApiErroDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({DesafioException.class})
    public ResponseEntity<ApiErroDTO> handlerDesafioException(DesafioException exception, WebRequest request){
        String erro = "Erro no sistema";

        ApiErroDTO apiErroDTO = new ApiErroDTO(exception.getMenssagem(), erro, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<ApiErroDTO>(apiErroDTO,new HttpHeaders(),apiErroDTO.getStatus());
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ApiErroDTO> handlerEntityNotFoundException(EntityNotFoundException exception,WebRequest request){

        String erro = "Recurso não encontrado";

        ApiErroDTO apiErroDTO = new ApiErroDTO(exception.getMenssagem(), erro, HttpStatus.NOT_FOUND);

        return new ResponseEntity<ApiErroDTO>(apiErroDTO,new HttpHeaders(),apiErroDTO.getStatus());
    }

    @ExceptionHandler({EtiquetaJaExisteException.class})
    public ResponseEntity<ApiErroDTO> handlerAlreadyExistException(EtiquetaJaExisteException exception, WebRequest request){

        String erro = "Recurso já existe";

        ApiErroDTO apiErroDTO = new ApiErroDTO(exception.getMenssagem(), erro, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<ApiErroDTO>(apiErroDTO,new HttpHeaders(),apiErroDTO.getStatus());
    }

    @ExceptionHandler({UsuarioNaoPossuiEtiquetaException.class})
    public ResponseEntity<ApiErroDTO> handlerUsuarioNaoPossuiEtiquetaException(UsuarioNaoPossuiEtiquetaException exception, WebRequest request){

        String erro = "Recurso não existe";

        ApiErroDTO apiErroDTO = new ApiErroDTO(exception.getMenssagem(), erro, HttpStatus.NOT_FOUND);

        return new ResponseEntity<ApiErroDTO>(apiErroDTO,new HttpHeaders(),apiErroDTO.getStatus());
    }


    @ExceptionHandler({AdminNaoPodeTerEtiquetaException.class})
    public ResponseEntity<ApiErroDTO> HandlerAdminNaoPodeTerEtiquetaException(AdminNaoPodeTerEtiquetaException exception, WebRequest request){

        String erro = "Requisição não pode ser concluida";

        ApiErroDTO apiErroDTO = new ApiErroDTO(exception.getMenssagem(), erro, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<ApiErroDTO>(apiErroDTO,new HttpHeaders(),apiErroDTO.getStatus());
    }

    @ExceptionHandler({UsuarioComEmailJaExisteException.class})
    public ResponseEntity<ApiErroDTO> HandlerUsuarioComEmailJaExisteException(UsuarioComEmailJaExisteException exception, WebRequest request){

        String erro = "Email Já existe";

        ApiErroDTO apiErroDTO = new ApiErroDTO(exception.getMenssagem(), erro, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<ApiErroDTO>(apiErroDTO,new HttpHeaders(),apiErroDTO.getStatus());
    }



}

package com.example.algamoneyapi.exceptionhandler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class AlgamoneyExceptionHandler  extends ResponseEntityExceptionHandler {

   @Autowired
   private MessageSource messageSource;


   protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, WebRequest request,
                                                              HttpHeaders headers, HttpStatus httpStatus){

         String mensagemUsuario = messageSource.getMessage("mensagem invalida" ,
                 null, LocaleContextHolder.getLocale());
         String mensagemDesenvolvedor = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
         List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario,mensagemDesenvolvedor));
         return  handleExceptionInternal(ex,erros,headers,httpStatus.BAD_REQUEST,request);
   }
   @Override
   protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,HttpHeaders headers,HttpStatus httpStatus,
                                                                WebRequest request ){

       List<Erro> erros = criarListaDeErros(ex.getBindingResult());
       return handleExceptionInternal(ex,erros,headers,HttpStatus.BAD_REQUEST,request);

   }
   @ExceptionHandler({EmptyResultDataAccessException.class})
   public ResponseEntity<Object>handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request){
      String mensagemUsuario =messageSource.getMessage("recurso.não-encontrado", null, LocaleContextHolder.getLocale());
      String mensagemDesenvolvedor = ex.toString();
      List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario,mensagemDesenvolvedor));
      return  handleExceptionInternal(ex, erros, new HttpHeaders(),HttpStatus.NOT_FOUND,request);
   }
   @ExceptionHandler({DataIntegrityViolationException.class})
   public ResponseEntity<Object> handleDataIntegrityViolationExceptio(DataIntegrityViolationException ex ,WebRequest request){
        String mensagemUsuario = messageSource.getMessage("recurso.operacao-nao-permitida", null, LocaleContextHolder.getLocale());
        String mensageDesenvolver = ex.toString();
        List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensageDesenvolver));
        return  handleExceptionInternal(ex,erros, new HttpHeaders(),HttpStatus.BAD_REQUEST,request);
   }

    private List<Erro> criarListaDeErros(BindingResult bindingResult) {
       List<Erro> erros = new ArrayList<>();
       for(FieldError fieldError : bindingResult.getFieldErrors()){
           String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
           String mensagemDesenvolvedor = fieldError.toString();
           erros.add(new Erro(mensagemUsuario,mensagemDesenvolvedor));

       }

       return erros;
    }



    public static class Erro{
       private String mensagemUsuario;
       private  String mensagemDesenvolvedor;


       public Erro(String mensageUsuario, String mensagemDesenvolvedor) {
           this.mensagemUsuario = mensageUsuario;
           this.mensagemDesenvolvedor = mensagemDesenvolvedor;
       }

       public String getMensageUsuario() {
           return mensagemUsuario;

       }

       public void setMensageUsuario(String mensageUsuario) {
           this.mensagemUsuario = mensageUsuario;
       }

       public String getMensagemDesenvolvedor() {
           return mensagemDesenvolvedor;
       }

       public void setMensagemDesenvolvedor(String mensagemDesenvolvedor) {
           this.mensagemDesenvolvedor = mensagemDesenvolvedor;
       }
   }


}

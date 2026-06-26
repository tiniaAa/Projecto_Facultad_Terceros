package com.utn.prog3.tp_Final.Exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Atajamos el error genérico (Cualquier cosa inesperada que rompa Java)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> manejarErrorInesperado(Exception ex) {
        
        // Armamos nuestra caja con el mensaje prolijo
        ErrorDTO error = new ErrorDTO(
            "Ocurrió un error interno en el servidor. Detalles: " + ex.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(), // 500
            new Date()
        );

        // Devolvemos el error en formato JSON
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    // 2. Atajamos el famoso error de las listas desfasadas que nos pasó antes
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<ErrorDTO> manejarErrorDeListas(IndexOutOfBoundsException ex) {
        
        ErrorDTO error = new ErrorDTO(
            "Error procesando la lista de detalles: La cantidad de ítems no coincide.",
            HttpStatus.BAD_REQUEST.value(), // 400
            new Date()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    
    // (¡Acá en el futuro podés agregar más @ExceptionHandler para errores específicos!)
}

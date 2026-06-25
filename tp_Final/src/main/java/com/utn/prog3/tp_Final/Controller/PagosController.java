package com.utn.prog3.tp_Final.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utn.prog3.tp_Final.Dto.PagosDTO;
import com.utn.prog3.tp_Final.Service.iservices.IPagosService;

@RestController
@RequestMapping("/api/pagos")
public class PagosController {

    @Autowired
    private IPagosService pagosService;

    @PostMapping
    public ResponseEntity<PagosDTO> crearPago(@RequestBody PagosDTO dto) {
        PagosDTO pagoCreado = this.pagosService.crear(dto);
        
        if (pagoCreado == null) {
            return ResponseEntity.badRequest().build(); 
        }
        
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoCreado);
    }

    
    @GetMapping
    public ResponseEntity<List<PagosDTO>> listarTodos() {
        List<PagosDTO> lista = this.pagosService.listarTodas();
        return ResponseEntity.ok(lista);
    }

    // 
    @PutMapping("/{id}")
    public ResponseEntity<PagosDTO> actualizarPago(@PathVariable Long id, @RequestBody PagosDTO dto) {
        PagosDTO pagoActualizado = this.pagosService.actualizar(id, dto);
        
        if (pagoActualizado == null) {
            return ResponseEntity.notFound().build(); 
        }
        
        return ResponseEntity.ok(pagoActualizado); 
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id) {
        boolean seBorro = this.pagosService.eliminar(id);
        
        if (!seBorro) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.noContent().build(); 
    }
}
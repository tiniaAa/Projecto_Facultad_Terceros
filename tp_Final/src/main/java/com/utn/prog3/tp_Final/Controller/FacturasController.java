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

import com.utn.prog3.tp_Final.Dto.FacturasDTO;
import com.utn.prog3.tp_Final.Service.iservices.IFacturarsService;

@RestController
@RequestMapping("api/facturas")
public class FacturasController {
	
	@Autowired
    private IFacturarsService facturasService;
	
	@PostMapping
    public ResponseEntity<FacturasDTO> crearFactura(@RequestBody FacturasDTO dto) {
        
        FacturasDTO facturaCreada = this.facturasService.crear(dto);

        if (facturaCreada == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(facturaCreada);
    }
	@GetMapping
    public ResponseEntity<List<FacturasDTO>> listarFacturas() {
        List<FacturasDTO> facturas = this.facturasService.listarTodas();
        return ResponseEntity.ok(facturas); 
    }
	@PutMapping
	public ResponseEntity<FacturasDTO> actualizarFactura(@PathVariable Long id,@RequestBody FacturasDTO dto){
		return null;
	}
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFactura(@PathVariable Long id) {
        boolean seBorro = this.facturasService.eliminar(id);

        if (!seBorro) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
	
}

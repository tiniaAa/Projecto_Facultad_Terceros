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

import com.utn.prog3.tp_Final.Dto.TercerosDTO;
import com.utn.prog3.tp_Final.Service.iservices.ITercerosService;



@RestController
@RequestMapping("/api/terceros")
public class TercerosController {
	
	@Autowired
	private ITercerosService tercerosService;
	
	@PostMapping
	public ResponseEntity<TercerosDTO> crearTercero(@RequestBody TercerosDTO tercerosDTO){
		TercerosDTO terceroCreado = this.tercerosService.crear(tercerosDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(terceroCreado);
	}
	@GetMapping
	public ResponseEntity<List<TercerosDTO>> listarTerceros(){
		List<TercerosDTO> listaTerceros = this.tercerosService.listarTerceros();
		return ResponseEntity.status(HttpStatus.OK).body(listaTerceros);
	}
	@PutMapping
	public ResponseEntity<TercerosDTO> actualizarTercero(@PathVariable Long id,@RequestBody TercerosDTO dto){
		TercerosDTO terceroActualizado = this.tercerosService.actualizar(id, dto);
		if(terceroActualizado == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(terceroActualizado);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarTercero(@PathVariable Long id){
		boolean seBorro = this.tercerosService.eliminar(id);
		if(!seBorro) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}
}

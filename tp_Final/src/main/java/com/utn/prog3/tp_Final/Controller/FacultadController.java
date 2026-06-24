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

import com.utn.prog3.tp_Final.Dto.FacultadDTO;
import com.utn.prog3.tp_Final.Service.iservices.IFacultadService;

@RestController
@RequestMapping("/api/facultades")
public class FacultadController {

	@Autowired
    private IFacultadService facultadService;
    
	@PostMapping
    public ResponseEntity<FacultadDTO> crearFacultad(@RequestBody FacultadDTO facultadDTO) {
        FacultadDTO facultadCreada = this.facultadService.crear(facultadDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(facultadCreada);
    }
	@GetMapping
	public ResponseEntity <List<FacultadDTO>> listarFacultades(){
		List<FacultadDTO> listaFacultades = this.facultadService.listarTodas();
		return ResponseEntity.status(HttpStatus.OK).body(listaFacultades);
	}
	@PutMapping
	public ResponseEntity<FacultadDTO> actualizarFacultad(@PathVariable Long id,@RequestBody FacultadDTO dto){
		FacultadDTO facultadActualizada = this.facultadService.actualizar(id, dto);
	    if (facultadActualizada == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok(facultadActualizada);
	}
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFacultad(@PathVariable Long id) {
        boolean seBorro = this.facultadService.eliminar(id);
        if (!seBorro) {
            return ResponseEntity.notFound().build(); 
        }
        return ResponseEntity.noContent().build();
    }
}

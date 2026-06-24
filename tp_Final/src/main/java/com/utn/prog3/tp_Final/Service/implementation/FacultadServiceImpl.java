package com.utn.prog3.tp_Final.Service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utn.prog3.tp_Final.Dto.FacultadDTO;
import com.utn.prog3.tp_Final.Model.Facultad;
import com.utn.prog3.tp_Final.Repository.FacultadRepository;
import com.utn.prog3.tp_Final.Service.iservices.IFacultadService;


@Service
public class FacultadServiceImpl implements IFacultadService {
	
	
	
	@Autowired
	private FacultadRepository facultadRepository;
	
	
	@Override
	public FacultadDTO crear(FacultadDTO dto) {
		Facultad entidad = new Facultad();
		entidad.setNombre(dto.getNombre());
		entidad.setNombre(dto.getNombre());
        entidad.setDireccion(dto.getDireccion());
        entidad.setCuit(dto.getCuit());
        entidad.setSucursal(dto.getSucursal());
        entidad.setTelefonos(dto.getTelefono()); 
        entidad.setCorreo(dto.getEmail());
        entidad.setDefecto(false);
		
        
        Facultad entidadGuardada = this.facultadRepository.save(entidad);
        
        FacultadDTO respuestaDTO= new FacultadDTO();
        respuestaDTO.setId(entidadGuardada.getId());
        respuestaDTO.setNombre(entidadGuardada.getNombre());
        respuestaDTO.setDireccion(entidadGuardada.getDireccion());
        respuestaDTO.setCuit(entidadGuardada.getCuit());
        respuestaDTO.setSucursal(entidadGuardada.getSucursal());
        respuestaDTO.setTelefono(entidadGuardada.getTelefonos());
        respuestaDTO.setEmail(entidadGuardada.getCorreo());
        
        
		return respuestaDTO;
	}

	@Override
	public List<FacultadDTO> listarTodas() {
		List<Facultad>facultades = this.facultadRepository.findAll();
		
		List<FacultadDTO> listaDeRespuesta = new ArrayList<>();
		
		for (Facultad entidad : facultades) {
			FacultadDTO dto = new FacultadDTO();
			
			dto.setId(entidad.getId());
		    dto.setCuit(entidad.getCuit());
		    dto.setDireccion(entidad.getDireccion());
		    dto.setEmail(entidad.getDireccion());
		    dto.setNombre(entidad.getNombre());
		    dto.setSucursal(entidad.getSucursal());
		    dto.setTelefono(entidad.getTelefonos());
		    
		    listaDeRespuesta.add(dto);
        }

		return listaDeRespuesta;
	}
	public FacultadDTO actualizar(Long id, FacultadDTO dto) {
	    Optional<Facultad> resultado = this.facultadRepository.findById(id);

	    if (resultado.isEmpty()) {
	        return null; 
	    }

	    Facultad entidadExistente = resultado.get();

	    entidadExistente.setNombre(dto.getNombre());
	    entidadExistente.setDireccion(dto.getDireccion());
	    entidadExistente.setCuit(dto.getCuit());
	    entidadExistente.setSucursal(dto.getSucursal());
	    entidadExistente.setTelefonos(dto.getTelefono());
	    entidadExistente.setCorreo(dto.getEmail());

	    this.facultadRepository.save(entidadExistente);

	    dto.setId(id); 
	    return dto;
	}

	@Override
	public boolean eliminar(Long id) {
		Optional<Facultad> resultado = this.facultadRepository.findById(id);
        if (resultado.isEmpty()) {
            return false; 
        }
        this.facultadRepository.delete(resultado.get());
        return true;
	}

}

package com.utn.prog3.tp_Final.Service.implementation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utn.prog3.tp_Final.Dto.TercerosDTO;
import com.utn.prog3.tp_Final.Model.Terceros;
import com.utn.prog3.tp_Final.Repository.TercerosRepository;
import com.utn.prog3.tp_Final.Service.iservices.ITercerosService;


@Service
public class TercerosServiceImpl implements ITercerosService {
	
	@Autowired
	private TercerosRepository tercerosRepository;
	@Override
	public TercerosDTO crear(TercerosDTO dto) {
		
		Terceros entidad = new Terceros();
		
		entidad.setCuilt(dto.getCuilt());
		entidad.setDireccion(dto.getDireccion());
		entidad.setLocalidad(dto.getLocalidad());
		entidad.setNombre(dto.getNombre());
		entidad.setProvincia(dto.getProvincia());
		entidad.setSaldo_apertura(dto.getSaldo_apertura());
		entidad.setSitiva(dto.getSitiva());
		entidad.setTelefonos(dto.getTelefonos());
		entidad.setTipo_saldo(dto.getTipo_saldo());
		
		Terceros entidadGuardada = this.tercerosRepository.save(entidad);
		
		TercerosDTO respuestaDTO = new TercerosDTO();
		
		respuestaDTO.setCuilt(entidadGuardada.getCuilt());
		respuestaDTO.setDireccion(entidadGuardada.getDireccion());
		respuestaDTO.setLocalidad(entidadGuardada.getLocalidad());
		respuestaDTO.setNombre(entidadGuardada.getNombre());
		respuestaDTO.setProvincia(entidadGuardada.getProvincia());
		respuestaDTO.setSaldo_apertura(entidadGuardada.getSaldo_apertura());
		respuestaDTO.setSitiva(entidadGuardada.getSitiva());
		respuestaDTO.setTelefonos(entidadGuardada.getTelefonos());
		respuestaDTO.setTipo_saldo(entidadGuardada.getTipo_saldo());
		
		dto.setId(entidadGuardada.getId());
		return respuestaDTO;
	}

	@Override
	public List<TercerosDTO> listarTerceros() {
		List<Terceros> terceros = this.tercerosRepository.findAll();
		List<TercerosDTO> listaDeRespuesta= new ArrayList<>();
		
		for(Terceros entidad: terceros) {
			TercerosDTO dto = new TercerosDTO();
			dto.setCuilt(entidad.getCuilt());
			dto.setDireccion(entidad.getDireccion());
			dto.setLocalidad(entidad.getLocalidad());
			dto.setNombre(entidad.getNombre());
			dto.setProvincia(entidad.getProvincia());
			dto.setSaldo_apertura(entidad.getSaldo_apertura());
			dto.setSitiva(entidad.getSitiva());
			dto.setTelefonos(entidad.getTelefonos());
			dto.setTipo_saldo(entidad.getTipo_saldo());
			dto.setId(entidad.getId());
			
		}
		return listaDeRespuesta;
	}

	@Override
	public TercerosDTO actualizar(Long id, TercerosDTO dto) {
	    Optional<Terceros> resultado = this.tercerosRepository.findById(id);

	    if (resultado.isEmpty()) {
	        return null; 
	    }

	    Terceros entidadExistente = resultado.get();
	    
	    entidadExistente.setCuilt(dto.getCuilt());
	    entidadExistente.setDireccion(dto.getDireccion());
	    entidadExistente.setLocalidad(dto.getLocalidad());
	    entidadExistente.setNombre(dto.getNombre());
	    entidadExistente.setProvincia(dto.getProvincia());
	    entidadExistente.setSaldo_apertura(dto.getSaldo_apertura());
	    entidadExistente.setSitiva(dto.getSitiva());
	    entidadExistente.setTelefonos(dto.getTelefonos());
	    entidadExistente.setTipo_saldo(dto.getTelefonos());
	    entidadExistente.setId(dto.getId());
	    
	    this.tercerosRepository.save(entidadExistente);
	    dto.setId(id); 
	    return dto;
	}

	@Override
	public boolean eliminar(Long id) {
		Optional<Terceros> resultado = this.tercerosRepository.findById(id);
        if (resultado.isEmpty()) {
            return false; 
        }
        this.tercerosRepository.delete(resultado.get());
        return true;
	}
}

package com.utn.prog3.tp_Final.Service.iservices;

import java.util.List;

import com.utn.prog3.tp_Final.Dto.FacultadDTO;

public interface IFacultadService {
	boolean eliminar(Long id);
	
	FacultadDTO crear(FacultadDTO dto);
	
	List<FacultadDTO> listarTodas();
	
	FacultadDTO actualizar(Long id, FacultadDTO dto);
}

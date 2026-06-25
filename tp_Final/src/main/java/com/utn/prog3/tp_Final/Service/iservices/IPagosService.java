package com.utn.prog3.tp_Final.Service.iservices;

import java.util.List;

import com.utn.prog3.tp_Final.Dto.PagosDTO;

public interface IPagosService {
	boolean eliminar(Long id);
	PagosDTO crear(PagosDTO dto);
	List<PagosDTO> listarTodas();
	PagosDTO actualizar(Long id, PagosDTO dto);
	
}

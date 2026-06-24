package com.utn.prog3.tp_Final.Service.iservices;

import java.util.List;

import com.utn.prog3.tp_Final.Dto.TercerosDTO;

public interface ITercerosService {
	boolean eliminar(Long id);
	
	TercerosDTO crear(TercerosDTO dto);
		
	List<TercerosDTO > listarTerceros();
		
	TercerosDTO  actualizar(Long id, TercerosDTO dto);
}

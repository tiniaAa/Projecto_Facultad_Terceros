package com.utn.prog3.tp_Final.Service.iservices;

import java.util.List;

import com.utn.prog3.tp_Final.Dto.FacturasDTO;


public interface IFacturarsService {
	boolean eliminar(Long id);
	
	FacturasDTO crear(FacturasDTO dto);
	
	List<FacturasDTO> listarTodas();
	
}

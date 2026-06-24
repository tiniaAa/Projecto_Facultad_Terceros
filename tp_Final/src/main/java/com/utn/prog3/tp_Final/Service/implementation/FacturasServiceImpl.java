package com.utn.prog3.tp_Final.Service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utn.prog3.tp_Final.Dto.FacturasDTO;
import com.utn.prog3.tp_Final.Dto.Facturas_ItemsDTO;
import com.utn.prog3.tp_Final.Dto.TercerosDTO;
import com.utn.prog3.tp_Final.Model.Facturas;
import com.utn.prog3.tp_Final.Model.Facturas_items;
import com.utn.prog3.tp_Final.Model.Terceros;
import com.utn.prog3.tp_Final.Repository.FacturasRepository;
import com.utn.prog3.tp_Final.Repository.TercerosRepository;
import com.utn.prog3.tp_Final.Service.iservices.IFacturarsService;


@Service
public class FacturasServiceImpl implements IFacturarsService{
	
	@Autowired
    private FacturasRepository facturasRepository;

    @Autowired
    private TercerosRepository tercerosRepository;
	
	
	@Override
	public boolean eliminar(Long id) {
		Optional<Facturas> resultado = this.facturasRepository.findById(id);

        if (resultado.isEmpty()) {
            return false; 
        }
        this.facturasRepository.delete(resultado.get());
        return true;
	}

	@Override
	public FacturasDTO crear(FacturasDTO dto) {
		Optional<Terceros> terceroEncontrado = this.tercerosRepository.findById(dto.getTercero().getId());
        if (terceroEncontrado.isEmpty()) {
            return null;
        }
        Facturas nuevaFactura = new Facturas();
        nuevaFactura.setFecha_factura(dto.getFecha_factura());
        nuevaFactura.setNumero(dto.getNumero());
        nuevaFactura.setTercero(terceroEncontrado.get());
        
        
        for (Facturas_ItemsDTO itemDto : dto.getItems()) {
        	Facturas_items nuevoItem = new Facturas_items();
            nuevoItem.setCantidad(itemDto.getCantidad());
            nuevoItem.setMonto(itemDto.getMonto());
            nuevoItem.setDetalle(itemDto.getDetalle());
            
            
            nuevoItem.setFactura(nuevaFactura); 
            
            nuevaFactura.getItems().add(nuevoItem);
        }
        Facturas facturaGuardada = this.facturasRepository.save(nuevaFactura);
        dto.setId(facturaGuardada.getId());
        
        for (int i = 0; i < facturaGuardada.getItems().size(); i++) {
        
            dto.getItems().get(i).setId(facturaGuardada.getItems().get(i).getId()); 
        }
        return dto;
	}

	@Override
	public List<FacturasDTO> listarTodas() {
		List<Facturas> facturasBD = this.facturasRepository.findAll();
		List<FacturasDTO> listaRespuesta = new ArrayList<>();
		
		for (Facturas factura : facturasBD) {
            FacturasDTO dto = new FacturasDTO();
            dto.setId(factura.getId());
            dto.setFecha_factura(factura.getFecha_factura());
            dto.setNumero(factura.getNumero());

            TercerosDTO terceroDto = new TercerosDTO();
            terceroDto.setId(factura.getTercero().getId());
            terceroDto.setNombre(factura.getTercero().getNombre());
            dto.setTercero(terceroDto);

            for (Facturas_items item : factura.getItems()) {
                Facturas_ItemsDTO itemDto = new Facturas_ItemsDTO();
                itemDto.setId(item.getId());
                itemDto.setCantidad(item.getCantidad());
                itemDto.setMonto(item.getMonto());
                itemDto.setDetalle(item.getDetalle());
                
                dto.getItems().add(itemDto);
            }

            listaRespuesta.add(dto);
        }
		
		return listaRespuesta;
	}

	
	//public FacturasDTO actualizar(Long id, FacturasDTO dto) no se actualizan las facturas
	
	
	
}

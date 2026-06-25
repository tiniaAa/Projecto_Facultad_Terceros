package com.utn.prog3.tp_Final.Service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utn.prog3.tp_Final.Dto.PagosDTO;
import com.utn.prog3.tp_Final.Dto.Pagos_detalleDTO;
import com.utn.prog3.tp_Final.Dto.TercerosDTO;
import com.utn.prog3.tp_Final.Model.Pagos;
import com.utn.prog3.tp_Final.Model.Pagos_detalle;
import com.utn.prog3.tp_Final.Model.Terceros;
import com.utn.prog3.tp_Final.Repository.PagosRepository;
import com.utn.prog3.tp_Final.Repository.TercerosRepository;
import com.utn.prog3.tp_Final.Service.iservices.IPagosService;

@Service
public class PagosServiceImpl implements IPagosService{
	
	@Autowired
	private PagosRepository pagosRepository;
	@Autowired
	private TercerosRepository tercerosRepository;
	
	@Override
	public PagosDTO crear(PagosDTO dto) {
		Optional<Terceros> tercerosOpt = this.tercerosRepository.findById(dto.getTercero().getId());
		if(tercerosOpt.isEmpty()) {
			return null; //El tercero asignado no existe
		}
		
		Pagos pago = new Pagos();
		pago.setFecha_pago(dto.getFecha_pago());
		pago.setMonto_pago(dto.getMonto_pago());
		pago.setModo_pago(dto.getModo_pago());
		pago.setTercero(tercerosOpt.get());
		
		for(Pagos_detalleDTO detalleDto: dto.getDetalles()) {
			Pagos_detalle nuevoDetalle = new Pagos_detalle();
			
			nuevoDetalle.setInstrument_Date(detalleDto.getInstrument_Date());
			nuevoDetalle.setIntrument_Number(detalleDto.getInstrument_Number());
			nuevoDetalle.setBanco(detalleDto.getBanco());
			nuevoDetalle.setPago_Ralizado(detalleDto.getPago_Realizado());
			
			nuevoDetalle.setPago(pago);
			pago.getDetalles().add(nuevoDetalle);
		}
		Pagos pagoGuardado = this.pagosRepository.save(pago);
		
		dto.setId(pagoGuardado.getId());
		
		for(int i=0;i<pagoGuardado.getDetalles().size();i++) {
			dto.getDetalles().get(i).setId(pagoGuardado.getDetalles().get(i).getId());
		}
		
		
		return dto;
	}
	@Override
	public List<PagosDTO> listarTodas() {
		List<Pagos> pagosDB = this.pagosRepository.findAll();
		List<PagosDTO> listaRespuesta= new ArrayList<>();
		
		for(Pagos pago: pagosDB) {
			PagosDTO dto = new PagosDTO();
			dto.setId(pago.getId());
			dto.setFecha_pago(pago.getFecha_pago());
			dto.setMonto_pago(dto.getMonto_pago());
			dto.setModo_pago(pago.getModo_pago());
			
			
			//Mapeo del tercero
			TercerosDTO terceroDto = new TercerosDTO();
			terceroDto.setId(pago.getTercero().getId());
			terceroDto.setNombre(pago.getTercero().getNombre());
            dto.setTercero(terceroDto);
            
            
            for(Pagos_detalle detalle : pago.getDetalles()) {
            	Pagos_detalleDTO detalleDto = new Pagos_detalleDTO();
            	detalleDto.setId(detalle.getId());
                detalleDto.setInstrument_Number(detalle.getIntrument_Number());
                detalleDto.setInstrument_Date(detalle.getInstrument_Date());
                detalleDto.setBanco(detalle.getBanco());
                detalleDto.setPago_Realizado(detalle.getPago_Ralizado());
                
                dto.getDetalles().add(detalleDto);
            }
            listaRespuesta.add(dto);
		}
		
		return listaRespuesta;
	}
	@Override
    public boolean eliminar(Long id) {
        Optional<Pagos> resultado = this.pagosRepository.findById(id);

        if (resultado.isEmpty()) {
            return false;
        }
        this.pagosRepository.delete(resultado.get());
        return true;
    }
	@Override
    public PagosDTO actualizar(Long id, PagosDTO dto) {
        
		Pagos pagoBD = this.pagosRepository.findById(id).orElse(null);
        if (pagoBD == null) return null;

        pagoBD.setFecha_pago(dto.getFecha_pago());
        pagoBD.setMonto_pago(dto.getMonto_pago());
        pagoBD.setModo_pago(dto.getModo_pago());

        this.tercerosRepository.findById(dto.getTercero().getId())
                               .ifPresent(pagoBD::setTercero);


        // PASO A: Extraer los IDs que vienen del DTO 
        List<Long> idsQueSobreviven = dto.getDetalles().stream()
                .map(Pagos_detalleDTO::getId)
                .filter(detalleId -> detalleId != 0) // Filtramos los que no tienen ID 
                .toList();

        // PASO B: El DELETE Quirúrgico en 1 sola línea 
        pagoBD.getDetalles().removeIf(detalleViejo -> !idsQueSobreviven.contains(detalleViejo.getId()));

        // PASO C: Actualizar o Insertar usando Lambda
        dto.getDetalles().forEach(detalleDto -> {
            
            if (detalleDto.getId() == 0) {
                // ES NUEVO -> Lo creamos y lo metemos en la caja principal
                Pagos_detalle nuevoDetalle = new Pagos_detalle();
                nuevoDetalle.setIntrument_Number(detalleDto.getInstrument_Number());
                nuevoDetalle.setInstrument_Date(detalleDto.getInstrument_Date());
                nuevoDetalle.setBanco(detalleDto.getBanco());
                nuevoDetalle.setPago_Ralizado(detalleDto.getPago_Realizado());
                nuevoDetalle.setPago(pagoBD); 
                
                pagoBD.getDetalles().add(nuevoDetalle);
            
            } else {
                // YA EXISTE -> Lo buscamos al vuelo 
                pagoBD.getDetalles().stream()
                    .filter(detalleViejo -> detalleViejo.getId() == detalleDto.getId())
                    .findFirst()
                    .ifPresent(detalleViejo -> {
                        detalleViejo.setIntrument_Number(detalleDto.getInstrument_Number());
                        detalleViejo.setInstrument_Date(detalleDto.getInstrument_Date());
                        detalleViejo.setBanco(detalleDto.getBanco());
                        detalleViejo.setPago_Ralizado(detalleDto.getPago_Realizado());
                    });
            }
        });

        // 4. Guardamos todo. Hibernate se encarga de los INSERT, UPDATE y DELETE automáticamente.
        Pagos pagoGuardado = this.pagosRepository.save(pagoBD);
        
        // 5. Devolvemos los IDs al DTO
        dto.setId(pagoGuardado.getId());
        for (int i = 0; i < pagoGuardado.getDetalles().size(); i++) {
            dto.getDetalles().get(i).setId(pagoGuardado.getDetalles().get(i).getId());
        }

        return dto;
    }
	

	

}

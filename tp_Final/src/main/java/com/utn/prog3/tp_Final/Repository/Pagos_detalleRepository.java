package com.utn.prog3.tp_Final.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utn.prog3.tp_Final.Model.Pagos_detalle;

@Repository
public interface Pagos_detalleRepository extends JpaRepository<Pagos_detalle, Long> {

}

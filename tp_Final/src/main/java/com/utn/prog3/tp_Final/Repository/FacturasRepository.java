package com.utn.prog3.tp_Final.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utn.prog3.tp_Final.Model.Facturas;

@Repository
public interface FacturasRepository extends JpaRepository<Facturas, Long> {

}

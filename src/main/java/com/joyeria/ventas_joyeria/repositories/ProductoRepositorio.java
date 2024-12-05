/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.joyeria.ventas_joyeria.repositories;

import com.joyeria.ventas_joyeria.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author GabrielPanta
 */
public interface ProductoRepositorio extends JpaRepository<Producto, Integer>{
    
}

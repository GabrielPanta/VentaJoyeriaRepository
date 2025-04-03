/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.joyeria.ventas_joyeria.repositories;
import com.joyeria.ventas_joyeria.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 *
 * @author GabrielPanta
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}

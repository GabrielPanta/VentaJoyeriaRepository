/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joyeria.ventas_joyeria.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.joyeria.ventas_joyeria.models.Role;
import com.joyeria.ventas_joyeria.repositories.RoleRepository;
import java.util.List;
import org.springframework.stereotype.Service;
/**
 *
 * @author GabrielPanta
 */
@Service
public class RoleService {
     @Autowired
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }
    public Role getRoleById(Integer id) {
    return roleRepository.findById(id).orElse(null); // Devuelve null si no se encuentra el rol
}

}

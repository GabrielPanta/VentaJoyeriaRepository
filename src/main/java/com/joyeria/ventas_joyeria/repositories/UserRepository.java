/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joyeria.ventas_joyeria.repositories;

import com.joyeria.ventas_joyeria.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author GabrielPanta
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}

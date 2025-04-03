/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joyeria.ventas_joyeria.service;
import com.joyeria.ventas_joyeria.models.User;
import com.joyeria.ventas_joyeria.repositories.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author GabrielPanta
 */
@Service
public class UsuarioService {
     @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsuarios() {
        return userRepository.findAll();
    }

    public User getUsuarioById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public void saveUsuario(User usuario) {
        userRepository.save(usuario);
    }

    public void deleteUsuario(Integer id) {
        userRepository.deleteById(id);
    }
}

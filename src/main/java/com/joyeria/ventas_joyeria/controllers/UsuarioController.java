/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joyeria.ventas_joyeria.controllers;

import com.joyeria.ventas_joyeria.models.Role;
import com.joyeria.ventas_joyeria.models.User;
import com.joyeria.ventas_joyeria.service.RoleService;
import com.joyeria.ventas_joyeria.service.UsuarioService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author GabrielPanta
 */
@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

     @Autowired
    private UsuarioService usuarioService;
     
      @Autowired
    private RoleService roleService;  // Inyectar RoleService
      @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String listarUsuarios(Model model) {
        List<User> usuarios = usuarioService.getAllUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "list"; // Vista: templates/list.html
    }

    @GetMapping("/crear")
    public String formularioCrearUsuario(Model model) {
        model.addAttribute("usuario", new User());
        model.addAttribute("role", roleService.getAllRoles());  // Pasamos todos los roles disponibles
        return "usuarioForm"; // Vista: templates/form.html
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute User usuario) {
        // Encriptar la contraseña antes de guardarla
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        // Crear la lista de roles seleccionados
        List<Role> selectedRoles = new ArrayList<>();
        if (usuario.getRoleList() != null) {
            for (Role role : usuario.getRoleList()) {
                if (role != null) {
                    selectedRoles.add(role); // Agregar roles válidos a la lista
                }
            }
        }

        usuario.setRoleList(selectedRoles); // Asignar los roles seleccionados al usuario
        usuarioService.saveUsuario(usuario);
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditarUsuario(@PathVariable Integer id, Model model) {
        User usuario = usuarioService.getUsuarioById(id);
        model.addAttribute("usuario", usuario);
         model.addAttribute("role", roleService.getAllRoles());  // Pasamos todos los roles disponibles
        return "usuarioForm"; // Vista: templates/form.html
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Integer id) {
        usuarioService.deleteUsuario(id);
        return "redirect:/usuarios";
    }
    

}

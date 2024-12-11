/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joyeria.ventas_joyeria.controllers;

import com.joyeria.ventas_joyeria.models.Producto;
import com.joyeria.ventas_joyeria.models.Role;
import com.joyeria.ventas_joyeria.models.User;
import com.joyeria.ventas_joyeria.repositories.ProductoRepositorio;
import com.joyeria.ventas_joyeria.service.RoleService;
import com.joyeria.ventas_joyeria.service.UsuarioService;
import java.security.Principal;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author GabrielPanta
 */
@Controller
//@RestController
@RequestMapping("")
public class DashboardController {

    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private RoleService roleService;
    
    @GetMapping("/login")
    public String login(@RequestParam(value = "logout", required = false) String logout, Model model) {
        if (logout != null) {
            model.addAttribute("message", "Sesión cerrada exitosamente.");
        }
        return "login"; // Renderiza la plantilla de login
    }

    /* @GetMapping("/login")
    public String login() {
        return "login";
    }*/
    
     @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("usuario", new User());
        return "register";
    }

   @PostMapping("/register")
public String registerUser(@ModelAttribute User usuario) {
    // Encriptar la contraseña antes de guardarla
    usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));

    // Asignar el rol "usuario común" por defecto
    List<Role> roles = new ArrayList<>();
    Role userRole = roleService.getRoleByName("comun");  // Asegúrate de que el rol exista en la base de datos
    if (userRole != null) {
        roles.add(userRole);  // Agregar el rol de "usuario común"
    } else {
        // Si el rol "ROLE_USER" no existe, crearlo (opcional si necesitas agregarlo dinámicamente)
        userRole = new Role();
        userRole.setName("comun");
        usuario.setRoleList(roles);
        roles.add(userRole);
    }

    // Asignar los roles al usuario
    usuario.setRoleList(roles);

    // Guardar el usuario con el rol asignado
    usuarioService.saveUsuario(usuario);

    return "redirect:/login?success";  // Redirigir al login con un mensaje de éxito
}

    @PostMapping("/login")
    public String login(User user, RedirectAttributes redirectAttributes) {
        return "redirect:/index";
    }


    /* @GetMapping("/")
     @ResponseBody
    public String dashboard() {
        return "Bienvenido al Panel";
    }*/
    @GetMapping("")
    public ModelAndView verPaginaDeInicio() {
        List<Producto> ultimosProductos = productoRepositorio.findAll(PageRequest.of(0, 4, Sort.by("id").descending())).toList();
        // Crear la vista con productos
        ModelAndView modelAndView = new ModelAndView("index")
                .addObject("ultimosProductos", ultimosProductos);

        // Obtener la autenticación del usuario actual
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            // Agregar el nombre del usuario al modelo
            modelAndView.addObject("username", auth.getName());

            // Determinar si el usuario tiene el rol "admin"
            boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("admin"));

            modelAndView.addObject("admin", isAdmin); // Pasar atributo "admin" para Thymeleaf
        }

        return modelAndView;
    }
    

    /*@GetMapping("/admin")
    @ResponseBody
    public String admin() {
        return "Puedes ver esto solo si eres administrador";
    }*/
    @ModelAttribute
    public void addAttributes(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
    }

    @GetMapping("/productos")
    public ModelAndView listarProductos(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Producto> productos = productoRepositorio.findAll(pageable);
        return new ModelAndView("productos")
                .addObject("productos", productos);
    }

    @GetMapping("/productos/{id}")
    public ModelAndView mostrarDetallesDeProducto(@PathVariable Integer id) {
        Producto producto = productoRepositorio.getOne(id);
        return new ModelAndView("producto").addObject("producto", producto);
    }

    @GetMapping("/soporte")
    @ResponseBody
    public String soporte() {
        return "Si eres administrador o soporte, puedes acceder a esta página";
    }
    
    
}

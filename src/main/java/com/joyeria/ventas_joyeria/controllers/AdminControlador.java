package com.joyeria.ventas_joyeria.controllers;

import com.joyeria.ventas_joyeria.models.Categoria;
import com.joyeria.ventas_joyeria.models.Producto;
import com.joyeria.ventas_joyeria.repositories.CategoriaRepositorio;
import com.joyeria.ventas_joyeria.repositories.ProductoRepositorio;
import com.joyeria.ventas_joyeria.service.AlmacenServicioImpl;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")

public class AdminControlador {

    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    @Autowired
    private AlmacenServicioImpl servicio;

    @GetMapping("")
    public ModelAndView verPaginaDeInicio(@PageableDefault(sort = "nombre", size = 5) Pageable pageable) {
        Page<Producto> productos = productoRepositorio.findAll(pageable);
        return new ModelAndView("adminindex").addObject("productos", productos);
    }

    @ModelAttribute
    public void addAttributes(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
    }

    @GetMapping("/productos/nuevo")
    public ModelAndView mostrarFormularioDeNuevoProducto() {
        List<Categoria> categorias = categoriaRepositorio.findAll(Sort.by("nombre"));
        return new ModelAndView("nuevo-producto")
                .addObject("producto", new Producto())
                .addObject("categorias", categorias);
    }

    @PostMapping("/productos/nuevo")
    public ModelAndView registrarProducto(@Validated Producto producto, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || producto.getPortada().isEmpty()) {
            if (producto.getPortada().isEmpty()) {
                bindingResult.rejectValue("portada", "MultipartNotEmpty");
            }

            List<Categoria> categorias = categoriaRepositorio.findAll(Sort.by("nombre"));
            return new ModelAndView("nuevo-producto")
                    .addObject("producto", producto)
                    .addObject("categorias", categorias);
        }

        String rutaPortada = servicio.almacenarArchivo(producto.getPortada());
        producto.setRutaPortada(rutaPortada);

        productoRepositorio.save(producto);
        return new ModelAndView("redirect:/admin");
    }

    @GetMapping("/productos/{id}/editar")
    public ModelAndView mostrarFormilarioDeEditarProducto(@PathVariable Integer id) {
        Producto producto = productoRepositorio.getOne(id);
        List<Categoria> categorias = categoriaRepositorio.findAll(Sort.by("nombre"));

        return new ModelAndView("editar-producto")
                .addObject("producto", producto)
                .addObject("categorias", categorias);
    }

    @PostMapping("/productos/{id}/editar")
    public ModelAndView actualizarProducto(@PathVariable Integer id, @Validated Producto producto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<Categoria> categorias = categoriaRepositorio.findAll(Sort.by("nombre"));
            return new ModelAndView("editar-producto")
                    .addObject("producto", producto)
                    .addObject("categorias", categorias);
        }

        Producto productoDB = productoRepositorio.getOne(id);
        productoDB.setNombre(producto.getNombre());

        productoDB.setDescripcion(producto.getDescripcion());

        productoDB.setCategorias(producto.getCategorias());

        productoDB.setPrecio(producto.getPrecio());

        productoDB.setStock(producto.getStock());

        if (!producto.getPortada().isEmpty()) {
            servicio.eliminarArchivo(productoDB.getRutaPortada());
            String rutaPortada = servicio.almacenarArchivo(producto.getPortada());
            productoDB.setRutaPortada(rutaPortada);
        }

        productoRepositorio.save(productoDB);
        return new ModelAndView("redirect:/admin");
    }

    @PostMapping("/productos/{id}/eliminar")
public String eliminarProducto(@PathVariable Integer id) {
    // Usamos findById() para obtener el producto de manera segura.
    Optional<Producto> productoOpt = productoRepositorio.findById(id);
    
    if (productoOpt.isPresent()) {
        Producto producto = productoOpt.get();
        productoRepositorio.delete(producto);
        servicio.eliminarArchivo(producto.getRutaPortada());
    } else {
        // Manejar el caso cuando el producto no se encuentra.
        // Puedes redirigir a una página de error o mostrar un mensaje adecuado.
        return "redirect:/error"; // O cualquier otra vista que desees mostrar.
    }

    return "redirect:/admin"; // Redirigir a la página de administración.
}

}

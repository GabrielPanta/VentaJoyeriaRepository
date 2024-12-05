/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joyeria.ventas_joyeria.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author GabrielPanta
 */
@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer id;
    
    @NotBlank
    private String nombre;
    
    @NotBlank
    @Column(length = 255)
    private String descripcion;
    
    @NotEmpty // campo no puede estar vacío.
    @ManyToMany(fetch = FetchType.LAZY) //define una relación de tipo "muchos a muchos"
    @JoinTable(name = "categoria_producto", joinColumns = @JoinColumn(name = "id_producto"), 
            inverseJoinColumns = @JoinColumn(name = "id_categoria"))
    private List<Categoria> categorias;
    
    @NotEmpty
    private Double precio;
    
    @NotEmpty
    private Integer stock;
    
    private String rutaPortada;

    @Transient //campo presente en la clase y su valor no se guardará en la base de datos
	private MultipartFile portada;
    // Getters y Setters



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getRutaPortada() {
        return rutaPortada;
    }

    public void setRutaPortada(String rutaPortada) {
        this.rutaPortada = rutaPortada;
    }

    public MultipartFile getPortada() {
        return portada;
    }

    public void setPortada(MultipartFile portada) {
        this.portada = portada;
    }

    public Producto() {
		super();
	}
    public Producto(Integer id,@NotBlank String nombre, @NotBlank String descripcion, @NotEmpty List<Categoria> categorias, 
             @NotEmpty Double precio,  @NotEmpty Integer stock, String rutaPortada, MultipartFile portada) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categorias = categorias;
        this.precio = precio;
        this.stock = stock;
        this.rutaPortada = rutaPortada;
        this.portada = portada;
    }

    public Producto(@NotBlank String nombre, @NotBlank String descripcion, @NotEmpty List<Categoria> categorias, 
             @NotEmpty Double precio,  @NotEmpty Integer stock, String rutaPortada, MultipartFile portada) {
        super();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categorias = categorias;
        this.precio = precio;
        this.stock = stock;
        this.rutaPortada = rutaPortada;
        this.portada = portada;
    }
       
}

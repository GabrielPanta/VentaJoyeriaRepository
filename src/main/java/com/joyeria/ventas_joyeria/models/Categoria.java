/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joyeria.ventas_joyeria.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 *
 * @author GabrielPanta
 */
@Entity
public class Categoria {
    @Id
    @Column(name = "id_categoria")
    private Integer id;

    private String nombre;
    
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

    public Categoria(Integer id, String nombre) {
        super();
        this.id = id;
        this.nombre = nombre;
    }
    
    public Categoria() {
		super();
	}
    public Categoria(String nombre) {
		super();
		this.nombre = nombre;
	}

	public Categoria(Integer id) {
		super();
		this.id = id;
	}
}

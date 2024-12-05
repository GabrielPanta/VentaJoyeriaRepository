/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joyeria.ventas_joyeria.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author GabrielPanta
 */
@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logoutSuccess() {
        return "login"; // Devuelve una vista personalizada
    }
}


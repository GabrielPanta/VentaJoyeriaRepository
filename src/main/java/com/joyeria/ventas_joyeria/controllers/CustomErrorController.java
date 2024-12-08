/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.joyeria.ventas_joyeria.controllers;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 *
 * @author GabrielPanta
 */
@Controller
public class CustomErrorController implements ErrorController {

  @RequestMapping("/error")
    public String handleError() {
        // Este método maneja los errores y redirige a la página correspondiente
        return "403"; // Asegúrate de que este sea el nombre del archivo que creaste (por ejemplo, "403.html")
    }

    //@Override
    public String getErrorPath() {
        return "/error";
    }
}

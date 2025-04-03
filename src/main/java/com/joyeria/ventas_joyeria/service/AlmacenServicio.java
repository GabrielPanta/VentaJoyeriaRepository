package com.joyeria.ventas_joyeria.service;

import java.nio.file.Path;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface AlmacenServicio {

    public void iniciarAlmacenDeArchivos();

    public String almacenarArchivo(MultipartFile archivo);

    public Path cargarArchivo(String nombreArchivo);

    public Resource cargarComoRecurso(String nombreArchivo);

    public void eliminarArchivo(String nombreArchivo);

}

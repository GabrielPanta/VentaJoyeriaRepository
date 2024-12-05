package com.joyeria.ventas_joyeria.service;

import com.joyeria.ventas_joyeria.excepciones.AlmacenExcepcion;
import com.joyeria.ventas_joyeria.excepciones.FileNotFoundException;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AlmacenServicioImpl implements AlmacenServicio {

    @Value("${storage.location}")
    private String storageLocation;

    //esta sirve para indicar que este metodo se va a ejecutar cada vez que halla una nueva instancia de esta esta clase
    @PostConstruct
    @Override
    public void iniciarAlmacenDeArchivos() {
        try {
            Files.createDirectories(Paths.get(storageLocation));
        } catch (IOException excepcion) {
            throw new AlmacenExcepcion("Error al inicializar la ubicación en el almacen de archivos");
        }
    }

  /*  @Override
    public synchronized String almacenarArchivo(MultipartFile archivo) {
        String nombreArchivo = archivo.getOriginalFilename();
        if (archivo.isEmpty()) {
            throw new AlmacenExcepcion("No se puede almacenar un archivo vacio");
        }
        try {
            InputStream inputStream = archivo.getInputStream();
            Files.copy(inputStream, Paths.get(storageLocation).resolve(nombreArchivo), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException excepcion) {
            throw new AlmacenExcepcion("Error al almacenar el archivo " + nombreArchivo, excepcion);
        }
        return nombreArchivo;
    }*/

@Override
public synchronized String almacenarArchivo(MultipartFile archivo) {
    String nombreArchivo = archivo.getOriginalFilename();
    if (archivo.isEmpty()) {
        throw new AlmacenExcepcion("No se puede almacenar un archivo vacio");
    }

    Path destino = Paths.get(storageLocation).resolve(nombreArchivo);

    try {
        // Intentar abrir el archivo en modo escritura para verificar si está bloqueado
        try (FileChannel channel = FileChannel.open(destino, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            // Si no se lanza ninguna excepción, el archivo no está bloqueado
        } catch (IOException e) {
            throw new AlmacenExcepcion("El archivo está siendo utilizado por otro proceso", e);
        }

        // Copiar el archivo con la opción de reemplazo
        try (InputStream inputStream = archivo.getInputStream()) {
            Files.copy(inputStream, destino, StandardCopyOption.REPLACE_EXISTING);
        }

    } catch (IOException excepcion) {
        throw new AlmacenExcepcion("Error al almacenar el archivo " + nombreArchivo, excepcion);
    }

    return nombreArchivo;
}


    /*
    @Override
public String almacenarArchivo(MultipartFile archivo) {
    String nombreArchivo = archivo.getOriginalFilename();
    if (archivo.isEmpty()) {
        throw new AlmacenExcepcion("No se puede almacenar un archivo vacío");
    }

    // Usar try-with-resources para garantizar que el InputStream se cierre
    try (InputStream inputStream = archivo.getInputStream()) {
        Path destino = Paths.get(storageLocation).resolve(nombreArchivo);
        
        // Verificar si el archivo ya existe y eliminarlo si es necesario
        if (Files.exists(destino)) {
            try {
                Files.delete(destino);
            } catch (IOException e) {
                throw new AlmacenExcepcion("No se pudo eliminar el archivo existente", e);
            }
        }
        
        // Copiar el archivo al destino
        Files.copy(inputStream, destino, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException excepcion) {
        throw new AlmacenExcepcion("Error al almacenar el archivo " + nombreArchivo, excepcion);
    }
    
    return nombreArchivo;
}*/



    @Override
    public Path cargarArchivo(String nombreArchivo) {
        return Paths.get(storageLocation).resolve(nombreArchivo);
    }

    @Override
    public Resource cargarComoRecurso(String nombreArchivo) {
        try {
            Path archivo = cargarArchivo(nombreArchivo);
            Resource recurso = new UrlResource(archivo.toUri());

            if (recurso.exists() || recurso.isReadable()) {
                return recurso;
            } else {
                throw new FileNotFoundException("No se pudo encontrar el archivo " + nombreArchivo);
            }

        } catch (MalformedURLException excepcion) {
            throw new FileNotFoundException("No se pudo encontrar el archivo " + nombreArchivo, excepcion);
        }
    }

    @Override
    public void eliminarArchivo(String nombreArchivo) {
        Path archivo = cargarArchivo(nombreArchivo);
        try {
            FileSystemUtils.deleteRecursively(archivo);
        } catch (Exception excepcion) {
            System.out.println(excepcion);
        }
    }

}

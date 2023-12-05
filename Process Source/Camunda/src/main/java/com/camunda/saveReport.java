package com.camunda;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class saveReport implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        // Obtener la información del modelo BPMN
        String nombre = (String) execution.getVariable("Nombre");
        Long telefono = (Long) execution.getVariable("Telefono");
        String  visita = (String) execution.getVariable("Visita");
        String domicilio = (String) execution.getVariable("Domicilio");
        Long valorTotal = (Long) execution.getVariable("ValorTotal");
        String queja = (String) execution.getVariable("Queja");

        // Crear un mapa para almacenar la información de la visita técnica
        Map<String, Object> visitaTecnica = new HashMap<>();
        visitaTecnica.put("Nombre", nombre);
        visitaTecnica.put("Telefono", telefono);
        visitaTecnica.put("Visita", visita);
        visitaTecnica.put("Domicilio", domicilio);
        visitaTecnica.put("ValorTotal", valorTotal);
        visitaTecnica.put("Queja", queja);

        // Convertir el mapa a formato JSON
        ObjectMapper objectMapper = new ObjectMapper();
        File archivoJSON = new File("visita_tecnica.json");

        try {
            List<Map<String, Object>> listaDeRegistros = new ArrayList<>();

            if (archivoJSON.exists()) {
                // Si el archivo existe, leer su contenido como una lista de mapas
                listaDeRegistros = objectMapper.readValue(archivoJSON, new TypeReference<List<Map<String, Object>>>() {});
            }

            // Agregar el nuevo registro a la lista
            listaDeRegistros.add(visitaTecnica);

            // Escribir el archivo con la lista actualizada de registros
            objectMapper.writeValue(archivoJSON, listaDeRegistros);
            System.out.println("Información de visita técnica agregada al archivo visita_tecnica.json");
        } catch (IOException e) {
            System.out.println("Error al guardar o actualizar la información en el archivo JSON: " + e.getMessage());
        }
    }
}

package com.aninfo.proyectos.service;

import java.util.ArrayList;
import java.util.stream.Collectors;
import com.aninfo.proyectos.model.Tarea;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import com.aninfo.proyectos.repository.TareaRepository;
import com.aninfo.proyectos.exception.NoExisteTareaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

@Service
public class TareaService {

    @Autowired
    private TareaRepository tareaRepository;

    public void addTarea(Tarea tarea) {
        if (!tareaRepository.findById(tarea.getId()).isPresent()){
            tareaRepository.save(tarea);
        }
    }

    public Tarea getTarea(int id) throws NoExisteTareaException {
        if (tareaRepository.findById(id).isPresent()){
            return tareaRepository.findById(id).get();
        }
        throw new NoExisteTareaException();
    }

    public ArrayList<Tarea> getAllTareas() {
        return (ArrayList<Tarea>) tareaRepository.findAll();
    }

    public void updateTarea(int id, Tarea tarea) {
        if (tareaRepository.findById(id).isPresent()){
            Tarea t = tareaRepository.getReferenceById(id);
            t.setNombre(tarea.getNombre());
            t.setEstado(tarea.getEstado());
            t.setDescripcion(tarea.getDescripcion());
            t.setIdTicket(tarea.getIdTicket());
            t.setFechaCreacion(tarea.getFechaCreacion());
            tareaRepository.save(t);
        }
    }

    public void deleteTarea(int id){
        tareaRepository.deleteById(id);
    }

    public ArrayList<Tarea> getAllTareasFromTicket(int id) {
        ArrayList<Tarea> tareas = (ArrayList<Tarea>) tareaRepository.findAll();
        return (ArrayList<Tarea>) tareas.stream().filter(tarea -> tarea.getIdTicket() == id).collect(Collectors.toList());
    }

    public void addEmpleadoToTarea(int id_tarea, long legajo) {
        if (tareaRepository.findById(id_tarea).isPresent()){
            Tarea tarea = tareaRepository.findById(id_tarea).get();
            tarea.addEmpleado(legajo);
            tareaRepository.save(tarea);
        }
    }

    public void deleteEmpleadoFromTarea(int id_tarea, long legajo){
        if (tareaRepository.findById(id_tarea).isPresent()){
            Tarea t = tareaRepository.getReferenceById(id_tarea);
            t.deleteEmpleado(legajo);
            tareaRepository.save(t);
        }
    }

    public void setTicketOfTarea(int id_tarea, int id_ticket) {
        if (tareaRepository.findById(id_tarea).isPresent()){
            Tarea t = tareaRepository.getReferenceById(id_tarea);
            t.setIdTicket(id_ticket);
            tareaRepository.save(t);
        }
    }

    public JSONObject getTicketFromTarea(int id_tarea) throws ParseException {
        RestTemplate restTemplate = new RestTemplate();
        JSONParser parser = new JSONParser();
        Tarea t = tareaRepository.getReferenceById(id_tarea);
        String request = "https://psa-soporte-mvp.herokuapp.com/soporte/ticket/"+String.valueOf(t.getIdTicket());
        return (JSONObject) parser.parse(restTemplate.getForObject(request, String.class));
    }

    public JSONArray getTickets() throws ParseException {
        RestTemplate restTemplate = new RestTemplate();
        JSONParser parser = new JSONParser();
        String request = "https://psa-soporte-mvp.herokuapp.com/soporte/tickets";
        return (JSONArray) parser.parse(restTemplate.getForObject(request, String.class));
    }
}

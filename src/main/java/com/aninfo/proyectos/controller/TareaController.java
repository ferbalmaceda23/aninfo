package com.aninfo.proyectos.controller;

import java.util.ArrayList;
import com.aninfo.proyectos.model.Tarea;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;
import com.aninfo.proyectos.service.TareaService;
import com.aninfo.proyectos.exception.NoExisteTareaException;
import org.springframework.beans.factory.annotation.Autowired;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RestController
public class TareaController {

    @Autowired
    private final TareaService tareaService = new TareaService();

    /*
     * GET REQUESTS /////////////////////////////////////////////////////////////////////////////////
     * */

    @RequestMapping(method=RequestMethod.GET, value="/tareas")
    public ArrayList<Tarea> getAllTareas(){
        return tareaService.getAllTareas();
    }

    @RequestMapping(method=RequestMethod.GET, value="/tareas/{id}")
    public Tarea getTarea(@PathVariable("id") int id) throws NoExisteTareaException {
        return tareaService.getTarea(id);
    }

    @RequestMapping(method=RequestMethod.GET, value="/tickets/{id}/tareas")
    public ArrayList<Tarea> getTareasFromTicket(@PathVariable("id") int id){
        return tareaService.getAllTareasFromTicket(id);
    }

    @RequestMapping(method=RequestMethod.GET, value="/tareas/{id}/tickets")
    public JSONObject getTicketFromTarea(@PathVariable("id")int id_tarea) throws ParseException {
        return tareaService.getTicketFromTarea(id_tarea);
    }

    @RequestMapping(method=RequestMethod.GET, value="/tickets")
    public JSONArray getTickets() throws ParseException {
        return tareaService.getTickets();
    }
}
package com.aninfo.proyectos.controller;

import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.aninfo.proyectos.model.Tarea;
import com.aninfo.proyectos.model.Proyecto;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;
import com.aninfo.proyectos.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RestController
public class EmpleadoController {

    @Autowired
    private final EmpleadoService empleadoService = new EmpleadoService();

    /*
     * GET REQUESTS /////////////////////////////////////////////////////////////////////////////////
     * */

    @RequestMapping(method= RequestMethod.GET, value="/empleados")
    public JSONArray getAllEmpleados() throws ParseException {
        return empleadoService.getAllEmpleados();
    }

    @RequestMapping(method=RequestMethod.GET, value="/empleados/{id}")
    public JSONObject getEmpleado(@PathVariable("id") long id) throws ParseException {
        return empleadoService.getEmpleado(id);
    }

    @RequestMapping(method=RequestMethod.GET, value="/empleados/{id}/proyectos")
    public ArrayList<Proyecto> getProyectosFromEmpleado(@PathVariable("id") long id){
        return empleadoService.getProyectosFromEmpleado(id);
    }

    @RequestMapping(method=RequestMethod.GET, value="/empleados/{id}/tareas")
    public ArrayList<Tarea> getTareasFromEmpleado(@PathVariable("id") long legajo){
        return empleadoService.getTareasFromEmpleado(legajo);
    }
}

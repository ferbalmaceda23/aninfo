package com.aninfo.proyectos.controller;

import java.util.ArrayList;
import com.aninfo.proyectos.model.Tarea;
import com.aninfo.proyectos.model.Proyecto;
import org.springframework.web.bind.annotation.*;
import com.aninfo.proyectos.service.ProyectoService;
import com.aninfo.proyectos.exception.NoExisteTareaException;
import org.springframework.beans.factory.annotation.Autowired;
import com.aninfo.proyectos.exception.NoExisteProyectoException;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RestController
public class ProyectoController {

    @Autowired
    private final ProyectoService proyectoService = new ProyectoService();

    /*
    * GET REQUESTS /////////////////////////////////////////////////////////////////////////////////
    * */

    @RequestMapping(method= RequestMethod.GET, value="/proyectos")
    public ArrayList<Proyecto> getAllProyectos(){
        return proyectoService.getAllProyectos();
    }

    @RequestMapping(method=RequestMethod.GET, value="/proyectos/{id}")
    public Proyecto getProyecto(@PathVariable("id") int id) throws NoExisteProyectoException {
        return proyectoService.getProyecto(id);
    }

    @RequestMapping(method=RequestMethod.GET, value="/proyectos/{id1}/tareas/{id2}")
    public Tarea getTareaFromProyecto(@PathVariable("id1") int id_proyecto, @PathVariable("id2")int id_tarea) throws NoExisteProyectoException {
        return proyectoService.getTareaFromProyecto(id_proyecto, id_tarea);
    }

    @RequestMapping(method=RequestMethod.GET, value="/proyectos/{id}/tareas")
    public ArrayList<Tarea> getAllTareasFromProyecto(@PathVariable("id") int id) throws NoExisteProyectoException {
        return proyectoService.getAllTareasFromProyecto(id);
    }

    @RequestMapping(method=RequestMethod.GET, value="/proyectos/{id1}/tareas/{id2}/empleados/{id3}/exist")
    public boolean empleadoEstaEnTarea(@PathVariable("id1")int id_proyecto, @PathVariable("id2")int id_tarea, @PathVariable("id3")long legajo){
        return proyectoService.empleadoEstaEnTarea(id_proyecto, id_tarea, legajo);
    }

    /*
    * POST REQUESTS /////////////////////////////////////////////////////////////////////////////////
    * */

    @RequestMapping(method=RequestMethod.POST, value="/proyectos")
    public void addProyecto(@RequestBody Proyecto proyecto){ proyectoService.addProyecto(proyecto); }

    @RequestMapping(method=RequestMethod.POST, value="/proyectos/{id}/tareas")
    public void addTareaToProyecto(@PathVariable("id") int id, @RequestBody Tarea tarea) throws NoExisteProyectoException {
        proyectoService.addTareaToProyecto(id, tarea);
    }

    /*
    * DELETE REQUESTS /////////////////////////////////////////////////////////////////////////////////
    * */

    @RequestMapping(method=RequestMethod.DELETE, value="/proyectos/{id}")
    public void deleteProyecto(@PathVariable("id") int id){
        proyectoService.deleteProyecto(id);
    }

    @RequestMapping(method=RequestMethod.DELETE, value="/proyectos/{id1}/tareas/{id2}")
    public void deleteTareaFromProyecto(@PathVariable("id1") int id_proyecto, @PathVariable("id2") int id_tarea) throws NoExisteProyectoException {
        proyectoService.deleteTareaFromProyecto(id_proyecto, id_tarea);
    }

    @RequestMapping(method=RequestMethod.DELETE, value="/proyectos/{id1}/tareas/{id2}/empleados/{id3}")
    public void deleteEmpleadoFromTareaOfProyecto(@PathVariable("id1")int id_proyecto, @PathVariable("id2")int id_tarea, @PathVariable("id3")long legajo) throws NoExisteTareaException {
        proyectoService.deleteEmpleadoFromTareaOfProyecto(id_proyecto, id_tarea, legajo);
    }

    @RequestMapping(method=RequestMethod.DELETE, value="/proyectos")
    public void deleteAllProyectos(){
        proyectoService.deleteAllProyectos();
    }

    /*
    * PUT REQUESTS /////////////////////////////////////////////////////////////////////////////////
    * */

    @RequestMapping(method=RequestMethod.PUT, value="/proyectos/{id}")
    public void updateProyecto(@PathVariable("id")int id, @RequestBody Proyecto proyecto) throws NoExisteProyectoException {
        proyectoService.updateProyecto(id, proyecto);
    }

    @RequestMapping(method=RequestMethod.PUT, value="/proyectos/{id1}/tareas/{id2}")
    public void updateTareaFromProyecto(@PathVariable("id1") int id_proyecto, @PathVariable("id2") int id_tarea, @RequestBody Tarea tarea) throws NoExisteProyectoException {
        proyectoService.updateTareaFromProyecto(id_proyecto, id_tarea, tarea);
    }

    @RequestMapping(method=RequestMethod.PUT, value="/proyectos/{id1}/empleados/{id2}")
    public void setLiderOfProyecto(@PathVariable("id1")int id_proyecto, @PathVariable("id2") long legajo){
        proyectoService.setLiderOfProyecto(id_proyecto, legajo);
    }

    @RequestMapping(method=RequestMethod.PUT, value="/proyectos/{id1}/tareas/{id2}/empleados/{id3}")
    public void addEmpleadoToTareaFromProyecto(@PathVariable("id1")int id_proyecto, @PathVariable("id2") int id_tarea, @PathVariable("id3") long legajo) throws NoExisteTareaException {
        proyectoService.addEmpleadoToTareaFromProyecto(id_proyecto, id_tarea, legajo);
    }

    @RequestMapping(method=RequestMethod.PUT, value="/proyectos/{id1}/tareas/{id2}/tickets/{id3}")
    public void setTicketToTareaFromProyecto(@PathVariable("id1")int id_proyecto, @PathVariable("id2") int id_tarea, @PathVariable("id3") int id_ticket){
        proyectoService.setTicketToTareaFromProyecto(id_proyecto, id_tarea, id_ticket);
    }
}
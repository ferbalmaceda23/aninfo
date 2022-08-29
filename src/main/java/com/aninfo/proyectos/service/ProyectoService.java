package com.aninfo.proyectos.service;

import java.util.ArrayList;
import com.aninfo.proyectos.model.Tarea;
import com.aninfo.proyectos.model.Proyecto;
import org.springframework.stereotype.Service;
import com.aninfo.proyectos.repository.ProyectoRepository;
import com.aninfo.proyectos.exception.NoExisteTareaException;
import org.springframework.beans.factory.annotation.Autowired;
import com.aninfo.proyectos.exception.NoExisteProyectoException;

@Service
public class ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private final TareaService tareaService = new TareaService();

    public void addProyecto(Proyecto proyecto) {
        if (!proyectoRepository.findById(proyecto.getId()).isPresent()){
            if (proyecto.getTareas().size() > 0){
                for (Tarea tarea : proyecto.getTareas()){
                    tarea.setIdProyecto(proyecto.getId());
                    tareaService.addTarea(tarea);
                }
            }
            proyectoRepository.save(proyecto);
        }
    }

    public Proyecto getProyecto(int id) throws NoExisteProyectoException {
        if (proyectoRepository.findById(id).isPresent()){
            return proyectoRepository.findById(id).get();
        }
        throw new NoExisteProyectoException();
    }

    public ArrayList<Proyecto> getAllProyectos() {
        return (ArrayList<Proyecto>) proyectoRepository.findAll();
    }

    public void updateProyecto(int id, Proyecto proyecto) throws NoExisteProyectoException {
        if (proyectoRepository.findById(id).isPresent()){
            Proyecto p = proyectoRepository.getReferenceById(id);
            p.setNombre(proyecto.getNombre());
            p.setEstado(proyecto.getEstado());
            p.setDescripcion(proyecto.getDescripcion());
            p.setFechaInicio(proyecto.getFechaInicio());
            p.setFechaFin(proyecto.getFechaFin());
            p.setLegajoLider(proyecto.getLegajoLider());
            proyectoRepository.save(p);
        }
    }

    public void deleteProyecto(int id){
        proyectoRepository.deleteById(id);
    }

    public void addTareaToProyecto(int id, Tarea tarea) throws NoExisteProyectoException {
        if (!proyectoRepository.findById(id).isPresent()){
            throw new NoExisteProyectoException();
        }
        Proyecto proyecto = proyectoRepository.findById(id).get();
        proyecto.addTarea(tarea);
        tarea.setIdProyecto(id);
        tareaService.addTarea(tarea);
        proyectoRepository.save(proyecto);
    }

    public void deleteTareaFromProyecto(int id_proyecto, int id_tarea) throws NoExisteProyectoException {
        if (!proyectoRepository.findById(id_proyecto).isPresent()){
            throw new NoExisteProyectoException();
        }
        Proyecto proyecto = proyectoRepository.findById(id_proyecto).get();
        proyecto.deleteTarea(id_tarea);
        tareaService.deleteTarea(id_tarea);
        proyectoRepository.save(proyecto);
    }

    public void updateTareaFromProyecto(int id_proyecto, int id_tarea, Tarea tarea) throws NoExisteProyectoException {
        if (!proyectoRepository.findById(id_proyecto).isPresent()){
            throw new NoExisteProyectoException();
        }
        Proyecto proyecto = proyectoRepository.findById(id_proyecto).get();
        proyecto.updateTarea(id_tarea, tarea);
        tareaService.updateTarea(id_tarea, tarea);
        proyectoRepository.save(proyecto);
    }

    public ArrayList<Tarea> getAllTareasFromProyecto(int id) throws NoExisteProyectoException {
        if (!proyectoRepository.findById(id).isPresent()){
            throw new NoExisteProyectoException();
        }
        Proyecto proyecto = proyectoRepository.findById(id).get();
        return proyecto.getTareas();
    }

    public void deleteAllProyectos() {
        proyectoRepository.deleteAll();
    }

    public void setLiderOfProyecto(int id_proyecto, long legajo) {
        if (proyectoRepository.findById(id_proyecto).isPresent()){
            Proyecto proyecto = proyectoRepository.findById(id_proyecto).get();
            proyecto.setLegajoLider(legajo);
            proyectoRepository.save(proyecto);
        }
    }

    public boolean empleadoEstaEnTarea(int id_proyecto, int id_tarea, long legajo) {
        if (!proyectoRepository.findById(id_proyecto).isPresent()){
            return false;
        }
        Proyecto proyecto = proyectoRepository.findById(id_proyecto).get();
        ArrayList<Tarea> tareas = proyecto.getTareas();
        if (!tareaEnProyecto(tareas, id_tarea)) {
            return false;
        }
        return empleadoEnProyecto(tareas, legajo);
    }

    private boolean tareaEnProyecto(ArrayList<Tarea> tareas, int id){
        boolean estaEnProyecto = false;
        for (Tarea tarea : tareas){
            if (tarea.getId() == id){
                estaEnProyecto = true;
                break;
            }
        }
        return estaEnProyecto;
    }

    private boolean empleadoEnProyecto(ArrayList<Tarea> tareas, long legajo){
        boolean estaEnProyecto = false;
        for (Tarea tarea : tareas){
            if (tarea.getEmpleados().contains(legajo)){
                estaEnProyecto = true;
                break;
            }
        }
        return estaEnProyecto;
    }

    public void addEmpleadoToTareaFromProyecto(int id_proyecto, int id_tarea, long legajo) throws NoExisteTareaException {
        if (proyectoRepository.findById(id_proyecto).isPresent()){
            Proyecto proyecto = proyectoRepository.getReferenceById(id_proyecto);
            Tarea tarea = proyecto.getTarea(id_tarea);
            tarea.addEmpleado(legajo);
            tareaService.addEmpleadoToTarea(id_tarea, legajo);
            proyectoRepository.save(proyecto);
        }
    }

    public void deleteEmpleadoFromTareaOfProyecto(int id_proyecto, int id_tarea, long legajo) throws NoExisteTareaException {
        if (proyectoRepository.findById(id_proyecto).isPresent()){
            Proyecto p = proyectoRepository.getReferenceById(id_proyecto);
            Tarea tarea = p.getTarea(id_tarea);
            tarea.deleteEmpleado(legajo);
            tareaService.deleteEmpleadoFromTarea(id_tarea, legajo);
            proyectoRepository.save(p);
        }
    }

    public void setTicketToTareaFromProyecto(int id_proyecto, int id_tarea, int id_ticket) {
        if (proyectoRepository.findById(id_proyecto).isPresent()){
            Proyecto p = proyectoRepository.getReferenceById(id_proyecto);
            Tarea tarea = p.getTarea(id_tarea);
            tarea.setIdTicket(id_ticket);
            tareaService.setTicketOfTarea(id_tarea, id_ticket);
            proyectoRepository.save(p);
        }
    }

    public Tarea getTareaFromProyecto(int id_proyecto, int id_tarea) throws NoExisteProyectoException {
        if (!proyectoRepository.findById(id_proyecto).isPresent()){
            throw  new NoExisteProyectoException();
        }
        Proyecto p = proyectoRepository.getReferenceById(id_proyecto);
        return p.getTarea(id_tarea);
    }
}
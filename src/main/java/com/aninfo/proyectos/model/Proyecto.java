package com.aninfo.proyectos.model;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String nombre;
    private String estado;
    private String fechaInicio;
    private String fechaFin;
    private String descripcion;
    private long legajoLider;

    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.ALL})
    private final List<Tarea> tareas = new ArrayList<>();

    public Proyecto(){
    }

    public Proyecto(int id, String nombre, String estado, String fechaInicio, String fechaFin, String descripcion, long legajoLider){
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descripcion = descripcion;
        this.legajoLider = legajoLider;
    }

    public int getId() {
        return this.id;
    }

    public String getNombre(){
        return this.nombre;
    }

    public String getEstado(){
        return this.estado;
    }

    public String getFechaInicio() { return this.fechaInicio; }

    public String getFechaFin() { return this.fechaFin; }

    public void setId(int id){
        this.id = id;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setEstado(String estado){
        this.estado = estado;
    }

    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }

    public void setFechaFin(String fechaFin) { this.fechaFin = fechaFin; }

    public void addTarea(Tarea tarea){
        tareas.add(tarea);
    }

    public void updateTarea(int id_tarea, Tarea tarea){
        Tarea t = findTarea(id_tarea);
        t.setNombre(tarea.getNombre());
        t.setEstado(tarea.getEstado());
        t.setDescripcion(tarea.getDescripcion());
        t.setIdTicket(tarea.getIdTicket());
        t.setFechaCreacion(tarea.getFechaCreacion());
    }

    public Tarea getTarea(int id_tarea){
        return findTarea(id_tarea);
    }

    public void deleteTarea(int id){
        tareas.removeIf(tarea -> tarea.getId() == id);
    }

    public ArrayList<Tarea> getTareas(){
        return new ArrayList<>(tareas);
    }

    public void setLegajoLider(long legajo){
        this.legajoLider = legajo;
    }

    public long getLegajoLider(){
        return this.legajoLider;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public String getDescripcion(){
        return this.descripcion;
    }

    private Tarea findTarea(int id){
        Tarea tarea = null;
        for (Tarea t : this.tareas){
            if (t.getId() == id){
                tarea = t;
                break;
            }
        }
        return tarea;
    }
}
package com.aninfo.proyectos.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.CascadeType;
import java.util.List;
import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nombre;
    private String descripcion;
    private String estado;
    private String fechaCreacion;

    private int idTicket;
    private int idProyecto;

    @ElementCollection()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value={CascadeType.ALL})
    @JoinColumn(columnDefinition = "tareas")
    private final List<Long> empleados = new ArrayList<>();

    public Tarea(){

    }

    public Tarea(int id, String nombre, String descripcion, String estado, String fechaCreacion, int idProyecto, int idTicket) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.idTicket = idTicket;
        this.idProyecto = idProyecto;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id) { this.id = id; }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public String getDescripcion(){
        return this.descripcion;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return this.nombre;
    }

    public void setEstado(String estado) { this.estado = estado; }

    public String getEstado() { return this.estado; }

    public void setIdProyecto(int id){
        this.idProyecto = id;
    }

    public int getIdProyecto(){
        return this.idProyecto;
    }

    public void setIdTicket(int idTicket){
        this.idTicket = idTicket;
    }

    public int getIdTicket() {
        return this.idTicket;
    }

    public void addEmpleado(long legajo){
        empleados.add(legajo);
    }

    public void deleteEmpleado(long legajo){
        empleados.removeIf(legajo_empleado -> legajo_empleado == legajo);
    }

    public ArrayList<Long> getEmpleados(){
        return new ArrayList<>(empleados);
    }

    public void setFechaCreacion(String fechaCreacion){
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaCreacion(){
        return this.fechaCreacion;
    }
}


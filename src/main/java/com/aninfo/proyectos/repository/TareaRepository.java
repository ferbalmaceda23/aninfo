package com.aninfo.proyectos.repository;

import com.aninfo.proyectos.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaRepository extends JpaRepository<Tarea, Integer> {

}
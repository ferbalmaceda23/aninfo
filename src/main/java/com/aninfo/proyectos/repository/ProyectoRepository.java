package com.aninfo.proyectos.repository;

import com.aninfo.proyectos.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {
}
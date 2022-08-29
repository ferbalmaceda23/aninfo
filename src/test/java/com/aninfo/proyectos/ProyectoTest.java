package com.aninfo.proyectos;

import java.util.*;

import org.junit.Assert;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.Given;
import org.springframework.http.*;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import com.aninfo.proyectos.model.Proyecto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.spring.CucumberContextConfiguration;
import com.aninfo.proyectos.repository.ProyectoRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

@CucumberContextConfiguration
@SpringBootTest
@Transactional
public class ProyectoTest {

    private final TestRestTemplate testRestTemplate = new TestRestTemplate();
    private final Proyecto proyectoEsperado = new Proyecto();
    private ArrayList <Proyecto> proyectosEsperados = new ArrayList<Proyecto>();
    private Proyecto proyectoDb = new Proyecto();
    private ResponseEntity<Proyecto> latestResponse;
    private ResponseEntity<Proyecto[]> latestResponseArray;

    private ObjectMapper objectMapper;

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Before
    public void setup() {
        proyectoEsperado.setNombre("Primer proyecto");
        proyectoEsperado.setEstado("En proceso");
        proyectoEsperado.setFechaInicio("01/05/2021");
        proyectoEsperado.setFechaFin("25/7/2022");
        proyectoEsperado.setDescripcion("descripcion del proyecto");

        proyectoDb.setNombre("Segundo proyecto");
        proyectoDb.setEstado("En proceso");
        proyectoDb.setFechaInicio("01/07/2021");
        proyectoDb.setFechaFin("01/7/2022");
        proyectoDb.setDescripcion("descripcion del proyecto 2");
    }

    @Given("^soy un empleado")
    public void givenSoyUnEmpleado(){
    }

    @When("^el empleado agrega un proyecto")
    public void whenElEmpleadoAgregaUnProyecto(){
        latestResponse = testRestTemplate.postForEntity("https://moduloproyectos.herokuapp.com/proyectos", proyectoEsperado, Proyecto.class);
    }

    @And("^el empleado recibe un status code de (\\d+)")
    public void andElEmpleadoRecibeUnStatusCodeDe(int statusCode) {

        HttpStatus currentStatusCode = null;
        if (latestResponse != null) {
            currentStatusCode = latestResponse.getStatusCode();
        } else {
            currentStatusCode = latestResponseArray.getStatusCode();
        }
        Assertions.assertEquals(statusCode, currentStatusCode.value());
    }

    @Then("^el proyecto se agrega")
    public void thenElProyectoSeAgrega() {
        Proyecto proyectoActual = proyectoRepository.getReferenceById(proyectoEsperado.getId());
        Assertions.assertEquals(proyectoEsperado.getId(), proyectoActual.getId());
    }

    @Given("^hay proyectos cargados")
    public void givenHayProyectosCargados() {
        proyectoRepository.save(proyectoEsperado);
        proyectoRepository.save(proyectoDb);
    }

    @When("^el empleado pide todos los proyectos")
    public void whenElEmpleadoPideTodosLosProyectos() {
        latestResponseArray = testRestTemplate.getForEntity("https://moduloproyectos.herokuapp.com/proyectos", Proyecto[].class);
        Proyecto[] ps = latestResponseArray.getBody();
        Collections.addAll(proyectosEsperados, ps);
    }

    @Then("^se devuelven todos los proyectos")
    public void thenSeDevuelvenTodosLosProyectos() {
        ArrayList<Proyecto> proyectosActuales = (ArrayList<Proyecto>) proyectoRepository.findAll();
        Assertions.assertTrue(assertArray(proyectosEsperados, proyectosActuales));
    }

    @When("^el empleado pide un proyecto")
    public void whenElEmpleadoPideUnProyecto() {
        int idProyecto = proyectoEsperado.getId();
        latestResponse = testRestTemplate.getForEntity("https://moduloproyectos.herokuapp.com/proyectos/{id}", Proyecto.class, idProyecto);
    }

    @Then("^se devuelve el proyecto pedido")
    public void thenSeDevuelveElProyectoPedido() {
        Proyecto proyectoActual = proyectoRepository.getReferenceById(proyectoEsperado.getId());
        Assertions.assertEquals(latestResponse.getBody().getId(), proyectoActual.getId());
    }

    @When("^el empleado borra un proyecto sin tareas")
    public void whenElEmpleadoBorraUnProyectoSinTareas() {
        int idProyecto = proyectoDb.getId();
        latestResponse  = testRestTemplate.exchange(
                "https://moduloproyectos.herokuapp.com/proyectos/{id}",
                HttpMethod.DELETE,
                new HttpEntity<Proyecto>(new HttpHeaders()),
                Proyecto.class,
                idProyecto
        );
    }

    @Then("^el proyecto se borra")
    public void thenElProyectoSeBorra() {
        Assert.assertFalse((proyectoRepository.findById(proyectoDb.getId()).isPresent()));
    }

    @When("^el empleado borra un proyecto que no existe")
    public void whenElEmpleadoBorraUnProyectoQueNoExiste() {
        proyectosEsperados = (ArrayList<Proyecto>) proyectoRepository.findAll();
        latestResponse  = testRestTemplate.exchange(
                "https://moduloproyectos.herokuapp.com/proyectos/{id}",
                HttpMethod.DELETE,
                new HttpEntity<Proyecto>(new HttpHeaders()),
                Proyecto.class,
                -10
        );
    }

    @Then("^no se borra ningun proyecto")
    public void thenNoSeBorraNingunProyecto() {
        ArrayList<Proyecto> proyectosActuales = (ArrayList<Proyecto>) proyectoRepository.findAll();
        Assertions.assertTrue(assertArray(proyectosEsperados, proyectosActuales));
    }



    @When("^el empleado borra todos los proyectos")
    public void whenElEmpleadoBorraTodosLosProyectos() {
        latestResponseArray  = testRestTemplate.exchange(
                "https://moduloproyectos.herokuapp.com/proyectos",
                HttpMethod.DELETE,
                new HttpEntity<Proyecto>(new HttpHeaders()),
                Proyecto[].class
        );
    }

    @Then("^se borran todos los proyectos")
    public void thenSeBorranTodosLosProyectos() {
        Assert.assertFalse((proyectoRepository.findById(proyectoEsperado.getId()).isPresent()));
        Assert.assertFalse((proyectoRepository.findById(proyectoDb.getId()).isPresent()));
    }

    private boolean assertArray(ArrayList<Proyecto> p1, ArrayList<Proyecto> p2){
        boolean iguales = true;
        for (Proyecto proyecto : p1) {
            if (!elementoEnArray(p2, proyecto)) {
                iguales = false;
                break;
            }
        }
        return iguales;
    }

    private boolean elementoEnArray(ArrayList<Proyecto> p, Proyecto proyecto){
        for (Proyecto proy : p){
            if (proy.getId() == proyecto.getId()){
                return true;
            }
        }
        return false;
    }
}
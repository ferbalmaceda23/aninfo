Feature: Request de proyectos
  Scenario: Empleado hace POST a /proyectos
    Given soy un empleado
    When el empleado agrega un proyecto
    Then el proyecto se agrega
    And el empleado recibe un status code de 200

  Scenario: Empleado hace GET a /proyectos
    Given hay proyectos cargados
    When el empleado pide todos los proyectos
    Then se devuelven todos los proyectos
    And el empleado recibe un status code de 200

  Scenario: Empleado hace GET a /proyectos/{id}
    Given hay proyectos cargados
    When el empleado pide un proyecto
    Then se devuelve el proyecto pedido
    And el empleado recibe un status code de 200

  Scenario: Empleado hace DELETE a /proyectos/{id} sin tareas
    Given hay proyectos cargados
    When el empleado borra un proyecto sin tareas
    Then el proyecto se borra
    And el empleado recibe un status code de 200

  Scenario: Empleado hace DELETE a /proyectos/{id} a un proyecto que no existe
    Given hay proyectos cargados
    When el empleado borra un proyecto que no existe
    Then no se borra ningun proyecto

  Scenario: Empleado hace DELETE a /proyectos
    Given hay proyectos cargados
    When el empleado borra todos los proyectos
    Then se borran todos los proyectos
    And el empleado recibe un status code de 200

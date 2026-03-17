# Caso 1 - Demoblaze
# Feature: Registro con datos random

Feature: Registro de usuario en Demoblaze con datos aleatorios

  Background:
    Given el usuario esta en la pagina de Demoblaze

  @registro @smoke
  Scenario: Registro exitoso con datos random
    When el usuario hace click en Sign up
    And ingresa datos de registro aleatorios
    Then el registro debe ser exitoso

  @registro @regression
  Scenario: Registro y login con el mismo usuario random
    When el usuario hace click en Sign up
    And ingresa datos de registro aleatorios
    Then el registro debe ser exitoso
    When el usuario hace click en Login
    And hace login con el usuario recien registrado
    Then debe ver el mensaje de bienvenida

  @registro @negative
  Scenario: Registro con usuario ya existente
    When el usuario hace click en Sign up
    And ingresa el usuario existente "testuser_envioclick" con su contrasena
    Then debe aparecer mensaje de usuario ya existente

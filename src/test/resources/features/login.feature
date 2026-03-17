# Caso 1 - Demoblaze
# Feature: Login

Feature: Login en Demoblaze

  Background:
    Given el usuario esta en la pagina de Demoblaze

  @login @smoke
  Scenario: Login exitoso con credenciales validas
    When el usuario hace click en Login
    And ingresa el usuario valido y contrasena valida
    Then debe ver el mensaje de bienvenida

  @login @negative
  Scenario: Login fallido con credenciales invalidas
    When el usuario hace click en Login
    And ingresa el usuario invalido y contrasena invalida
    Then no debe estar logueado

  @login @smoke
  Scenario: Logout exitoso
    When el usuario hace click en Login
    And ingresa el usuario valido y contrasena valida
    Then debe ver el mensaje de bienvenida
    And el usuario hace logout
    Then el boton de Login debe ser visible

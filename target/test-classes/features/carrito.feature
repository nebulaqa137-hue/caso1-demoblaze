# Caso 1 - Demoblaze
# Feature: Carrito y Compra

Feature: Carrito y Compra en Demoblaze

  Background:
    Given el usuario esta en la pagina de Demoblaze
    And el usuario esta logueado

  @carrito @smoke
  Scenario: Agregar producto al carrito
    When el usuario selecciona el primer producto
    And agrega el producto al carrito
    And navega al carrito
    Then el carrito debe tener al menos 1 producto

  @carrito @regression
  Scenario: Validar producto especifico en carrito
    When el usuario selecciona el producto "Samsung galaxy s6"
    And agrega el producto al carrito
    And navega al carrito
    Then el producto "Samsung galaxy s6" debe estar en el carrito

  @carrito @regression
  Scenario: Eliminar producto del carrito
    When el usuario selecciona el primer producto
    And agrega el producto al carrito
    And navega al carrito
    And elimina todos los productos del carrito
    Then el carrito debe estar vacio

  @compra @e2e
  Scenario: Completar compra end to end
    When el usuario selecciona el primer producto
    And agrega el producto al carrito
    And navega al carrito
    And completa la orden de compra
    Then debe ver la confirmacion de compra

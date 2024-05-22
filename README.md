# Desarrollo de aplicaciones - Grupo C

## Integrantes:
- Juan Manuel Sanchez Diaz
- Diego Ivan Kippes

## [Sonarcloud](https://sonarcloud.io/summary/new_code?id=dkippes_desa-unq-grupo-c)

---
## TODO

- [ ] mejorar visualización de errores
- [ ] Corregir issues sonacloud

---
## Entrega Nro 1

| Entregable | Estado |
|------------|--------|
| Creación de repositorios GitHub | 🟢     |
| Configuración en Travis/GitHubActions | 🟢     |
| Build corriendo y SUCCESS | 🟢     |
| SonarCloud (Registrar el proyecto Backend) | 🟢     |
| Deploy automático utilizando HEROKU o similar | 🟡?    |
| TAG en GitHub y Confeccionar Release Notes | 🟢     |
| Clean Code según la materia (todo en Inglés) | 🟢     |
| Configuración de Swagger en el back-API (v3) | 🟢     |
| Implementar modelo completo | 🟢     |
| Testing automático unitario según las pautas | 🟢     |
| Proveer servicio de registración de usuario (punto 1) | 🟢     |

---
## Entrega Nro 2

| Entregable | Estado |
|------------|--------|
| Estado de build en "Verde" | 🟢     |
| Utilizar HSQLDB para persistir datos (opcion H2) | 🟢     |
| Crear datos de prueba cuando levanta la aplicación | 🟢     |
| Documentation de Endpoints (APIs) con Swagger (v3) | 🟢     |
| TAG en GitHub y Confeccionar Release Notes de entrega 2 | 🟢     |
| Listar cotizacion de criptoactivos | 🟢 |
| Permitir que un usuario exprese su intención de compra/venta | 🟢     |
| Construir un listado donde se muestran las intenciones activas de compra/venta |🟢     |
| Procesar la transacción informada por un usuario | 🟢     |
| Informar al usuario el volumen operado de cripto activos entre dos fechas | 🟢     |
| Testing integral de 2 controllers (end-to-end) | 🟢     |

---
## Entrega Nro 3

| Entregable                                                                                                                                                              | Estado |
|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----|
| Crear un test de arquitectura - J                                                                                                                                       | 🔴 |
| Auditoria de Web-Services. Loguear <timestamp,user,operación/metodo, parámetros, tiempoDeEjecicion> de los servicios publicados con Spring utilizando Log4j/logback - D | 🔴 |
| TAG en GitHub y Confeccionar Release Notes de entrega 3 - J                                                                                                             | 🔴 |
| Segurizar el acceso a la API (JWT) - J                                                                                                                                  | 🔴 |
| Mostrar las cotizaciones de las últimas 24hs para un cripto activo dado - D                                                                                             | 🔴 |
| Listado de cotizaciones (alta performance - implementar cache) - D                                                                                                      | 🔴 |
---
## Correr docker

1. Generar jar => ./gradlew build -x test
2. docker-compose build
3. docker-compose up

## Swagger
* Debe estar corriendo la aplicacion en el puerto 8080
[Documentacion](http://localhost:8080/swagger-ui/index.html)

### H2

1. Correrlo desde http://localhost:8080/h2-console
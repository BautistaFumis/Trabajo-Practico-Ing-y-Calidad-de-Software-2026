# Registro de Cambios (Changelog) - WMS & TMS System

Todas las modificaciones notables de este proyecto serán documentadas en este archivo.

## [1.1.0] - Nueva funcionalidad
### Agregado
- Seguimiento de envíos por identificador en el módulo TMS (`GET /api/tms/envios/{idEnvio}`), con respuesta 404 para envíos inexistentes.
- Envío de ejemplo `ENV-1003` en estado `PENDIENTE_DESPACHO`.
- Pruebas automáticas del controlador de transporte.

### Cambiado
- El controlador TMS pasa a llamarse `ControladorTransporte` y expone el estado general en `GET /api/tms/envios`.

## [1.0.1] - Hotfix
### Corregido
- Se sincronizó la versión informada por la configuración local con la versión productiva.
- Se agregó una prueba automática para evitar inconsistencias entre archivos de configuración.

## [1.0.0] - Release Inicial
### Agregado
- Módulo WMS (Warehouse Management System) con endpoints de stock e inventarios.
- Módulo TMS (Transportation Management System) con endpoints de rutas y despachos.
- Configuración de Integración Continua (CI) con GitHub Actions (`ci.yml`).
- Asignación de responsables de código (`CODEOWNERS`).
- Ignorado de archivos locales y temporales (`.gitignore`).

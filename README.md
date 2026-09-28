# Sistema Logístico WMS-TMS

Repositorio del Trabajo Práctico Integrador **"Sistemas de Gestión de la Configuración"** de la materia
Ingeniería y Calidad de Software 2026 - UTN FRSF.

El software versionado es una API REST en **Java 17 + Spring Boot 3** que modela dos módulos de un
sistema logístico para e-Commerce:

- **WMS (Warehouse Management System):** gestión de depósitos y stock.
- **TMS (Transportation Management System):** gestión de transporte y seguimiento de envíos.

Sobre este software se aplicó **Gitflow** como flujo de ramificación, con integración continua en
GitHub Actions y revisión de cambios mediante Pull Requests.

---

## Equipo

| Integrante (GitHub) | Rol en el repositorio |
|---|---|
| [@BautistaFumis](https://github.com/BautistaFumis) | Code owner |
| [@bautimoreno10](https://github.com/bautimoreno10) | Code owner |
| [@gcinquini](https://github.com/gcinquini) | Code owner |
| [@CristianLocatelli02](https://github.com/CristianLocatelli02) | Code owner |

---

## Tecnologías

- Java 17 (compatible con JDK 17 o superior)
- Spring Boot 3.2.3 (`spring-boot-starter-web`)
- Maven 3.9+
- JUnit 5 + MockMvc para pruebas
- GitHub Actions para CI

---

## Cómo compilar, probar y ejecutar

Requisitos: JDK 17+ y Maven 3.9+ en el `PATH` (con `JAVA_HOME` configurado).

```bash
# Compilar y ejecutar todas las pruebas
mvn clean test

# Levantar la aplicación (puerto 8080)
mvn spring-boot:run

# Generar el ejecutable
mvn clean package
java -jar target/wmstms-<version>.jar
```

---

## Endpoints disponibles

| Módulo | Método | Ruta | Descripción |
|---|---|---|---|
| WMS | GET | `/api/wms/stock` | Estado general del depósito principal |
| TMS | GET | `/api/tms/envios` | Estado general del transporte (rutas activas, entregas pendientes) |
| TMS | GET | `/api/tms/envios/{idEnvio}` | Seguimiento de un envío. Devuelve `404` si no existe |

Envíos de ejemplo: `ENV-1001` (EN_TRANSITO), `ENV-1002` (ENTREGADO), `ENV-1003` (PENDIENTE_DESPACHO).

```bash
curl http://localhost:8080/api/tms/envios/ENV-1001
```

---

## Estructura del proyecto

```
.github/
  CODEOWNERS                  -> responsables de revisión
  pull_request_template.md    -> plantilla que se completa en cada PR
  workflows/ci.yml            -> pipeline de integración continua
src/main/java/com/logistics/wmstms/
  WmsTmsApplication.java      -> punto de entrada Spring Boot
  wms/                        -> módulo de depósitos
  tms/                        -> módulo de transporte y seguimiento de envíos
src/main/resources/application.properties
src/test/java/...             -> pruebas automáticas
config.properties             -> configuración de la aplicación (nombre y versión)
CHANGELOG.md                  -> historial de versiones
```

### Configuración

- `config.properties` y `application.properties` informan la versión de la aplicación (`app.version`).
  La prueba `VersionConsistencyTests` verifica que ambas coincidan (se agregó en el hotfix 1.0.1).
- Las configuraciones **locales** de cada desarrollador (`application-local.properties`, archivos `*.env`,
  carpetas del IDE, `target/`) están excluidas en `.gitignore` y **no deben versionarse**.

---

## Gestión de la configuración

### Estrategia de ramas (Gitflow)

| Rama | Propósito | Nace de | Se integra en |
|---|---|---|---|
| `main` | Código en producción. Cada versión queda marcada con un tag | - | - |
| `develop` | Integración de funcionalidades para la próxima versión | `main` | - |
| `feature/*` | Una funcionalidad nueva | `develop` | `develop` |
| `release/vX.Y.Z` | Preparación de una versión (número de versión, CHANGELOG, ajustes finales) | `develop` | `main` y `develop` |
| `hotfix/X.Y.Z` | Corrección urgente de un error en producción | `main` | `main` y `develop` |

Los merges hacia `main` y `develop` se hacen con `--no-ff` para que quede registrado en el historial
qué rama se integró y cuándo.

### Versionado

Se usa versionado semántico (`MAYOR.MENOR.PARCHE`). Cada pase a producción genera un tag anotado en `main`
y una entrada en el [CHANGELOG](CHANGELOG.md).

| Tag | Contenido |
|---|---|
| `v1.0.0` | Release inicial: módulos WMS y TMS |
| `v1.0.1` | Hotfix: versión de configuración desincronizada |
| `v1.1.0` | Seguimiento de envíos por identificador |
| `v1.2.0` | Documentación del repositorio y plantilla de Pull Request |

### Convención de commits

Mensajes en español con el formato `tipo(ámbito): descripción`, por ejemplo
`feat(tms): agrega seguimiento de envios por identificador`.
Tipos usados: `feat`, `fix`, `docs`, `chore`, `revert`, `merge`.

### Integración continua

`.github/workflows/ci.yml` compila y ejecuta las pruebas con Maven y Java 17 en cada Pull Request y en
cada push a `main` o `develop`. Un PR con el CI en rojo no debe mergearse.

### Revisión de código

`.github/CODEOWNERS` asigna a los integrantes del equipo como revisores automáticos de todo el repositorio,
de los workflows y de los módulos WMS y TMS.

---

## Resolución del inciso 7 (Gitflow aplicado)

- **7a - Modificación y push:** se subió el software base WMS/TMS.
- **7b - No subir configuraciones locales:** se configuró `.gitignore` para excluir artefactos de compilación
  (`target/`, `*.class`, `*.jar`), carpetas de IDE (`.idea/`, `.vscode/`), logs y configuraciones locales
  (`application-local.properties`, `*.env`). Así se evitan conflictos entre desarrolladores, se protegen datos
  sensibles y el CI construye siempre desde código fuente limpio.
- **7c / 7d - Release 1:** se creó `release/v1.0.0` y se preparó la versión (número de versión y CHANGELOG).
- **7e - Release 1 a producción:** siguiendo Gitflow, la rama `release` se integra en `main`, se crea el tag
  (`v1.0.0`) y se integra de vuelta en `develop` para que los ajustes de la release no se pierdan. En la
  v1.0.0 la integración quedó lineal (fast-forward); desde la v1.1.0 se aplicó `merge --no-ff`.
- **7f - Error en producción:** se creó `hotfix/1.0.1` **desde `main`** (no desde `develop`, que puede tener
  funcionalidades sin terminar). Se corrigió la versión informada por `config.properties` y se agregó una
  prueba automática para que el error no se repita.
- **7g - Hotfix a producción:** `hotfix/1.0.1` se integró en `main` con el tag `v1.0.1` y también en `develop`,
  para que la corrección esté en las próximas versiones.
- **7h - Nueva funcionalidad:** se creó `feature/seguimiento-envios` desde `develop`.
- **7i - Modificación A, modificación B y volver a A:** se publicaron A (`bed2043`, envío `ENV-1003`) y B
  (`578b241`, endpoint de estado). B se deshizo con `git revert` (`18a97e7`) y no con `git reset` + `push --force`:
  como B ya estaba publicada, `revert` crea un commit nuevo que invierte los cambios sin reescribir el historial
  compartido, y deja trazabilidad de que el cambio se deshizo.
- **7j - Nueva funcionalidad a producción:** siguiendo Gitflow, `feature` → `develop` → `release/v1.1.0`
  (número de versión y CHANGELOG, pruebas en verde) → `main` con tag `v1.1.0` → de vuelta a `develop`.
  El PR #2 se abrió por error contra `main`. En lugar de reescribir el historial de producción, se corrigió
  integrando la feature en `develop` y formalizando el pase a producción con `release/v1.1.0` y su tag.

---

## 8a. ¿Qué documentamos en este README y por qué?

El README es lo primero que ve cualquier persona que entra al repositorio y se versiona junto con el código,
así que siempre describe el estado real del software de cada versión. Por eso documentamos:

1. **Qué es el sistema y para qué sirve:** da contexto a quien llega sin conocer el proyecto.
2. **Tecnologías y requisitos:** qué hay que tener instalado para trabajar.
3. **Cómo compilar, probar y ejecutar:** permite reproducir el build de forma idéntica en cualquier máquina,
   que es uno de los objetivos de la gestión de la configuración.
4. **Endpoints y ejemplos de uso:** cómo consumir la funcionalidad sin leer el código.
5. **Estructura y configuración:** dónde está cada cosa y qué archivos no deben subirse.
6. **Estrategia de ramas, versionado y convención de commits:** son las reglas de trabajo del equipo. Si no
   están escritas, cada integrante trabaja distinto y el historial pierde trazabilidad.
7. **CI y revisión de código:** qué controles de calidad debe pasar un cambio antes de integrarse.
8. **Equipo:** a quién consultar.

El detalle de cada versión va en el `CHANGELOG.md` y el detalle de cada cambio en los commits y los PRs.
El README describe el proyecto y cómo se trabaja en él.

---

## 8b. Cambios de personas externas al equipo: ¿qué datos pedimos en un Pull Request?

Cuando alguien externo propone un cambio, el revisor no conoce su contexto ni su motivación. Para evaluarlo
sin idas y vueltas le pedimos que complete:

| Dato solicitado | Por qué lo pedimos |
|---|---|
| **Descripción del cambio** | Qué se modificó, explicado en lenguaje claro |
| **Motivación / issue relacionado** | Por qué hace falta. Vincula el cambio con un requerimiento o un error reportado (trazabilidad) |
| **Tipo de cambio** (nueva funcionalidad, corrección, refactor, documentación, configuración) | Define cómo impacta en el número de versión y a qué rama debe apuntar el PR |
| **Rama destino** | Según Gitflow, las features van a `develop` y solo las releases y hotfixes van a `main` |
| **Cómo se probó** (pasos, casos, datos de prueba) | Permite al revisor reproducir y validar el comportamiento |
| **Pruebas automáticas agregadas o modificadas** | Asegura que el cambio no se rompa en el futuro |
| **Impacto y riesgos** (endpoints, configuración, compatibilidad) | Anticipa efectos en producción y en otros módulos |
| **Evidencia** (salida de la API, capturas, logs) | Muestra el resultado sin tener que ejecutar el código |
| **Checklist** (CI en verde, CHANGELOG, documentación, sin configuraciones locales) | Verifica que se cumplan las reglas del repositorio antes de pedir revisión |

### ¿Qué nos ofrece GitHub para esto?

- **Plantilla de Pull Request (`.github/pull_request_template.md`):** GitHub precarga ese texto en la
  descripción de cada PR nuevo, así el autor no puede olvidarse de ningún dato. **Está agregada en este
  repositorio.**
- **Plantillas de issues (`.github/ISSUE_TEMPLATE/`):** estandarizan cómo se reportan errores y se piden
  funcionalidades, y los PR pueden vincularse al issue (`Closes #N`) para cerrarlo automáticamente al mergear.
- **CODEOWNERS:** asigna revisores automáticamente según los archivos que toca el PR.
- **Revisiones de código:** comentarios por línea, *suggested changes* que el autor aplica con un clic y
  estados *Approve* / *Request changes*.
- **Checks de estado (GitHub Actions):** el resultado del CI aparece en el PR, así se ve si el cambio compila
  y pasa las pruebas antes de revisarlo.
- **Reglas de protección de ramas:** permiten exigir en `main` y `develop` que el PR tenga aprobaciones, que
  el CI esté en verde y que no haya push directo.
- **`CONTRIBUTING.md`:** GitHub lo muestra a quien abre un PR o un issue, con las reglas para contribuir.
- **Labels, milestones y Draft PRs:** clasifican el cambio, lo asocian a una versión y permiten pedir feedback
  temprano sin que el PR esté listo para mergear.

Con la plantilla, CODEOWNERS y el CI, un PR externo llega con la información necesaria, se asigna a los
revisores correctos y ya trae la validación automática.

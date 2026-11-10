# Backend de demostración — Seguridad con Spring Security y JWT

**Actividad:** Desarrollo Backend II (PBY2202), Semana 3, Clínica PETSALUD.  
**Tipo de entrega:** código base proporcionado y documentación de análisis técnico. **No representa un sistema clínico terminado.**

## Alcance y estado real
Este repositorio conserva el backend original de productos/usuarios con una **única adaptación para publicación**: se retiraron credenciales embebidas de `application.properties` y `DataInitializer.java`, reemplazándolas por variables de entorno. No se implementaron roles nuevos, controles adicionales ni pruebas de seguridad nuevas. Véase [`docs/05-cambios-de-publicacion.md`](docs/05-cambios-de-publicacion.md).

Se identificó Spring Security con sesión `STATELESS`, filtro Bearer JWT, hash BCrypt, repositorios Spring Data JPA y registro básico de solicitudes. **No existe RBAC efectivo con perfiles CLIENTE, EMPLEADO y GERENTE**: los usuarios reciben `ROLE_ADMIN` y los endpoints requieren, en general, sólo autenticación. La consola H2 está expuesta en la configuración de desarrollo. El tratamiento de CSRF debe evaluarse según cómo se almacene/transporte el JWT.

## Organización
- `src/main/java/...`: código fuente y configuración de seguridad.
- [`docs/01-matriz-evidencias.md`](docs/01-matriz-evidencias.md): relación entre rúbrica, clases y conclusiones.
- [`docs/02-analisis-amenazas.md`](docs/02-analisis-amenazas.md): amenazas y alcance real de mitigaciones.
- [`docs/03-configuracion-y-flujo.md`](docs/03-configuracion-y-flujo.md): secuencia de autenticación y configuración.
- [`docs/04-plan-pruebas-no-ejecutadas.md`](docs/04-plan-pruebas-no-ejecutadas.md): casos de prueba propuestos, **sin resultados inventados**.
- [`docs/05-cambios-de-publicacion.md`](docs/05-cambios-de-publicacion.md): cambios mínimos respecto del ZIP original.
- [`docs/06-guia-github.md`](docs/06-guia-github.md): publicación y enlace para la entrega.

## Configuración local (desarrollo)
**Requisitos:** Java y Maven compatibles con `pom.xml` (Spring Boot `4.0.5`, propiedad `java.version=20`). La compilación no ha sido verificada en esta entrega; el ZIP original carece de `.mvn/wrapper` por lo que para ejecutar use una instalación de Maven. Verificar la disponibilidad de versiones y compatibilidad en el equipo.

Defina las variables de entorno `JWT_SECRET` (secreto aleatorio robusto, mínimo 32 bytes) y `ADMIN_SEED_PASSWORD` (contraseña de prueba no reutilizada). El archivo `.env.example` es **sólo una plantilla**; Spring Boot no carga automáticamente `.env`. No publicar un `.env` con valores reales.

Ejemplo orientativo para Linux/macOS:
```bash
export JWT_SECRET="$(openssl rand -base64 48)"
export ADMIN_SEED_PASSWORD="$(openssl rand -base64 18)"
mvn spring-boot:run
```

En PowerShell, definir `$env:JWT_SECRET` y `$env:ADMIN_SEED_PASSWORD` antes de ejecutar `mvn spring-boot:run`. El usuario de demostración se denomina `admin`, con contraseña definida por variable de entorno. **No exponga la aplicación a Internet**: H2 y otras configuraciones son de desarrollo.

## Referencias para el informe
Explicar sólo lo observado en código, sin afirmar pruebas de integración o ataques simulados ejecutados. El informe académico se entrega por separado en el formato de Duoc UC. El enlace de GitHub debe apuntar al repositorio creado por la persona que entrega.

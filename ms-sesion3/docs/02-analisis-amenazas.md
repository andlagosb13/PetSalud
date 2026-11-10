# Análisis de amenazas — PETSALUD

El caso de estudio describe gestión de citas, expedientes, inventario y clientes; sin embargo el código real incluye únicamente recursos de **productos** y **usuarios**. El análisis de impacto clínico es contextual/hipotético, no evidencia de módulos implementados.

| Amenaza | Impacto potencial | Control observado | Brecha / precisión |
|---|---|---|---|
| Acceso anónimo a recursos protegidos | Lectura, cambios o eliminación de registros | `anyRequest().authenticated()` y filtro JWT | No establece permisos específicos por rol ni propiedad del registro |
| Escalada horizontal/vertical de privilegios | Acceso a otros usuarios o funciones administrativas | Autoridad `ROLE_ADMIN` fija | No existe RBAC real; `/api/usuarios/{id}` no comprueba titularidad en controlador |
| Robo o falsificación de JWT | Suplantación de usuario | Firma HS256, fecha de expiración, validación | No se observan revocación, rotación, audience/issuer ni mecanismos de logout; antes el secreto estaba embebido |
| Inyección SQL | Acceso o alteración no autorizada de BD | Repositorios Spring Data JPA en operaciones observadas | No prueba inmunidad general; requiere pruebas y revisión de consultas futuras |
| XSS | Ejecución de scripts en consumidores web | APIs REST y uso de DTO para ciertas respuestas | No se ha verificado sanitización de salida en clientes, CSP ni pruebas XSS |
| CSRF | Peticiones no deseadas realizadas desde navegador autenticado | CSRF deshabilitado | Puede ser razonable en API Bearer sin credenciales enviadas automáticamente; no es protección demostrada ante clientes basados en cookies |
| Fuerza bruta / abuso | Acceso indebido, degradación | `SuspiciousActivityService` registra solicitudes y umbrales | Log de advertencias no equivale a rate limiting o bloqueo efectivo; no se identificó llamada a `recordFailedLogin` desde `AuthController` |
| Exposición de entorno de desarrollo | Manipulación de BD y exposición de configuración | H2 y Actuator en propiedades | Consola H2 permitida, frame options desactivadas; restringir/deshabilitar en producción |
| Filtración de contraseñas | Compromiso de cuentas | BCrypt en `UsuarioService` y `DataInitializer` | No implica política de contraseñas, MFA ni gestión completa de secretos |

## Estrategia
Spring Security es adecuado para centralizar autenticación y autorización en Java/Spring Boot; JWT firmado permite autenticar solicitudes sin sesión HTTP en servidor. `UserDetailsService` integra la identidad persistida con Spring Security, BCrypt resiste mejor ataques offline de contraseñas que un hash rápido, y los logs ayudan al diagnóstico. Son mecanismos complementarios, no reemplazan TLS, RBAC efectivo, endurecimiento de H2 o pruebas automatizadas.

## Alternativas discutidas en la actividad
- En memoria: útil para pruebas aisladas; no corresponde a un directorio de usuarios persistente.
- JDBC / BD: permite persistencia de usuarios; el proyecto usa Spring Data JPA sobre H2 con `UserDetailsService`, no la implementación `JdbcUserDetailsManager`.
- LDAP/LDAPS: útil para directorios organizacionales; **no implementado**.
- IDaaS: identidad externalizada mediante proveedor; **no implementada**.
- JWT: **implementado** para autenticación HTTP stateless; los permisos siguen limitados.

# Matriz de trazabilidad a la pauta de evaluación

**Método:** inspección estática de archivos del ZIP entregado. **No se ejecutó el proyecto ni una batería de pruebas funcionales.**

| Criterio y puntos | Evidencia verificable | Estado / limitación |
|---|---|---|
| 1. Amenazas (10) | `docs/02-analisis-amenazas.md`, `ConfigSpringSecurity.java`, `ProductoController.java`, `UsuarioController.java` | Análisis contextual, no pentest |
| 2. Framework y autorización (20) | `pom.xml`, `ConfigSpringSecurity.java`, `CustomUserDetailsService.java` | Spring Security y JWT presentes; RBAC por tres perfiles ausente |
| 3. JWT (15) | `JwtUtil.java`, `JwtAuthenticationFilter.java`, `AuthController.java`, `JwtProperties.java` | Generación, firma HS256, expiración y validación en fuente; ejecución no comprobada |
| 4. Roles (15) | `CustomUserDetails.java`, `ConfigSpringSecurity.java` | `ROLE_ADMIN` fijo para todos; no `@PreAuthorize`, ni diferenciación cliente/empleado/gerente |
| 5. Pruebas XSS/CSRF/SQLi (15) | `src/test/.../Semana5ApplicationTests.java`, `docs/04-plan-pruebas-no-ejecutadas.md` | Sólo prueba `contextLoads`; se documentan escenarios, no resultados |
| 6. GitHub (10) | Estructura del repositorio, README, `docs/06-guia-github.md` | Publicación requiere acción del estudiante y URL real; no se ha subido automáticamente |
| 7. Informe (15) | Documentos técnicos en `docs/` | Material de respaldo para redactar formato institucional, no reemplaza informe |

## Ubicaciones destacadas

- `src/main/java/cl/duoc/semana5/security/config/ConfigSpringSecurity.java`: `.csrf(...disable())`, `.sessionCreationPolicy(STATELESS)`, `permitAll()` a GET `/api/productos`, `/h2-console/**` y `/api/auth/**`, `anyRequest().authenticated()`.
- `src/main/java/cl/duoc/semana5/security/util/JwtUtil.java`: claims `sub`, `iat`, `exp`, firma `HS256`, verificación de token.
- `src/main/java/cl/duoc/semana5/security/filter/JwtAuthenticationFilter.java`: lectura de `Authorization: Bearer`, carga de usuario, contexto de seguridad.
- `src/main/java/cl/duoc/semana5/security/model/CustomUserDetails.java`: autoridad fija `ROLE_ADMIN`.
- `src/main/java/cl/duoc/semana5/security/monitor/SuspiciousActivityService.java`: registros y umbrales, **no bloqueo** de solicitudes por tasa o login.
- `src/main/java/cl/duoc/semana5/service/UsuarioService.java`: uso de `PasswordEncoder` en alta y actualización.
- `src/main/java/cl/duoc/semana5/config/DataInitializer.java`: cuenta de ejemplo; contraseña ahora proviene de variable de entorno.

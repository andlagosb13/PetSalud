# Plan de verificación de seguridad — pruebas NO ejecutadas

**Estado de evidencia:** ninguna de las pruebas HTTP siguientes fue ejecutada ni se presentan sus resultados como observados. En `src/test/java/.../Semana5ApplicationTests.java` sólo se declara `contextLoads()`.

| ID | Escenario de prueba propuesto | Resultado a verificar | Evidencia disponible |
|---|---|---|---|
| T01 | GET `/api/productos` sin token | Respuesta permitida | Regla estática `permitAll()` |
| T02 | GET `/api/usuarios` sin token | Rechazo por autenticación (código exacto pendiente) | Regla `authenticated()` |
| T03 | POST `/api/auth/login` con credenciales de prueba configuradas | Token JWT firmado | Lógica en `AuthController` y `JwtUtil` |
| T04 | Acceso protegido con token expirado/alterado | Rechazo, registro de intento JWT inválido | `JwtAuthenticationFilter`; sin prueba ejecutada |
| T05 | Acceso a usuarios con token de cliente y token de gerente | Diferencia de autorización por rol | **No puede acreditarse:** ambos perfiles no existen |
| T06 | Entrada SQLi en identificadores/campos | Ausencia de ejecución SQL manipulada y error controlado | Uso de JPA; prueba pendiente |
| T07 | Payload XSS en campos y consumidores | Escape/sanitización en cliente y ausencia de ejecución | No hay frontend ni prueba XSS |
| T08 | Solicitud desde navegador ajeno con credenciales automáticas | Resistencia a CSRF según almacenamiento real del token | CSRF deshabilitado; modelo de cliente no acreditado |
| T09 | Intentos fallidos repetidos de login | Generación de advertencia / posible bloqueo | Monitor tiene método, pero no se ve integrado al login |
| T10 | GET `/h2-console/` en entorno accesible | Confirmar exposición y restringir fuera de desarrollo | Permitido por código; requiere comprobación runtime |

## Procedimiento para documentar resultados futuros
Registrar para cada caso: configuración del entorno, fecha, comando o cliente HTTP, request sin exponer credenciales, respuesta (código y cuerpo depurado), logs pertinentes y conclusión. Nunca publicar tokens válidos, JWT secrets, contraseñas ni información personal. Adjuntar capturas sólo después de ejecutar las pruebas.

**Conclusión para el informe:** se realizó un análisis de configuración y se propone un plan de pruebas; no debe declararse una protección exhaustivamente comprobada frente a XSS, CSRF y SQL Injection.

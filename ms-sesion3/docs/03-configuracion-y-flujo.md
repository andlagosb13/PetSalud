# Guía técnica de configuración y flujo de autenticación

## 1. Dependencias y configuración
En `pom.xml`: `spring-boot-starter-security`, `spring-boot-starter-web`, `spring-boot-starter-data-jpa`, `jjwt-api/impl/jackson` versión `0.11.5`, H2 y validación. `application.properties` configura H2 en memoria, JWT (`jwt.secret`, `jwt.expiration`) y endpoints Actuator. Para GitHub, el secreto JWT y contraseña inicial se parametrizaron mediante variables de entorno; ver cambios de publicación.

## 2. Identificación del usuario
`CustomUserDetailsService.loadUserByUsername()` consulta `UsuarioRepo.findByUsername()` y adapta `Usuario` a `CustomUserDetails`. `ConfigSpringSecurity.authenticationProvider()` conecta un `DaoAuthenticationProvider` con BCrypt (`BCryptPasswordEncoder`). La creación/actualización de usuarios codifica contraseñas mediante `UsuarioService`.

## 3. Inicio de sesión
`POST /api/auth/login` recibe `username` y `password`, usa `AuthenticationManager.authenticate()` y genera un token mediante `JwtUtil.generateToken()`. El token contiene `subject`, fecha de emisión, expiración y firma HMAC HS256. El valor de expiración configurado es `3600000` milisegundos (1 hora).

## 4. Solicitud autenticada
Un cliente envía `Authorization: Bearer <TOKEN>`. `JwtAuthenticationFilter` lee el encabezado, extrae el usuario, consulta `UserDetailsService`, valida el JWT y establece `SecurityContextHolder` si corresponde. La política `SessionCreationPolicy.STATELESS` evita utilizar la sesión HTTP como mecanismo principal de autenticación.

## 5. Reglas reales de autorización
- `GET /api/productos`: público por regla exacta de método y ruta.
- `/api/auth/**`: público (login).
- `/h2-console/**`: público en la configuración de desarrollo.
- Demás rutas: requieren autenticación, **no rol específico**.

`CustomUserDetails.getAuthorities()` devuelve `ROLE_ADMIN` para cualquier usuario. No están implementados los roles `CLIENTE`, `EMPLEADO`, `GERENTE`; tampoco se observaron reglas con `@PreAuthorize`. El código no justifica afirmar que existe segregación de permisos.

## 6. Monitorización
`SuspiciousActivityService` registra solicitudes por IP, JWT inválidos y ofrece métodos para advertir de intentos de login fallidos y operaciones. Umbrales observables: 5 fallos de login o 200 solicitudes en 15 minutos, **para generar advertencias**; no significa que la solicitud sea bloqueada ni que todos los métodos de monitorización estén integrados.

## 7. Limitaciones operativas
Sólo se realizó inspección estática. No hay evidencia reproducible de pruebas HTTP satisfactorias, arranque de aplicación o compatibilidad de entorno. La consola H2 y el flag de frame options no deben mantenerse públicos en producción. El proyecto es un backend de demostración, no incluye módulos clínicos PETSALUD.

# Cambios mínimos aplicados para preparar el repositorio público

Se mantiene la lógica de autenticación y autorización del ZIP original. **Sólo para evitar publicar valores de credenciales directamente en GitHub** se efectuaron estos ajustes:

1. `src/main/resources/application.properties`: `jwt.secret` se reemplazó por `${JWT_SECRET}`; se eliminaron propiedades de ejemplo `spring.security.user.name/password` y se añadió `app.seed.admin-password=${ADMIN_SEED_PASSWORD}`.
2. `src/main/java/cl/duoc/semana5/config/DataInitializer.java`: la contraseña inicial antes literal ahora se obtiene mediante `@Value("${app.seed.admin-password}")`. Sigue creando un usuario `admin` para demostración si no existe.
3. `.gitignore`: se agregó exclusión de `.env`, variantes, logs y `.DS_Store`.
4. Se agregó `.env.example` con **marcadores ficticios**, README y carpeta `docs/`.
5. Se retiraron metadatos `.DS_Store` y scripts auxiliares de migración de IDE, no necesarios para el proyecto.

**Consecuencia:** sin definir `JWT_SECRET` y `ADMIN_SEED_PASSWORD`, el proyecto no está listo para iniciar; se requiere configuración local. No se afirma que estos cambios solucionen otras vulnerabilidades. Si la clave JWT original se utilizó en un ambiente real, debe considerarse expuesta y rotarse.

**No se hicieron:** refactorización de controladores, perfiles nuevos, endurecimiento de H2, cambios de CSRF, pruebas de seguridad o publicación en GitHub.

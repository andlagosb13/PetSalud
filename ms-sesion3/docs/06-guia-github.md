# Subida del código a GitHub

1. Crear un repositorio nuevo en GitHub, preferentemente **privado** hasta revisar posibles datos sensibles y finalizar la entrega. No inicializarlo con README si se utilizará Git local.
2. Descomprimir `PETSALUD_GitHub_documentado.zip` y abrir terminal **dentro de `ms-sesion3/`**, donde se encuentra `pom.xml`.
3. Ejecutar (reemplazar URL por la verdadera):

```bash
git init
git add .
git commit -m "Documentar seguridad backend semana 3"
git branch -M main
git remote add origin https://github.com/TU_USUARIO/TU_REPOSITORIO.git
git push -u origin main
```

4. Revisar en la web que existan `pom.xml`, `src/`, `README.md` y `docs/`, **sin** `.env` ni credenciales. Para compartir con el docente, configurar visibilidad/permisos según instrucciones del curso.
5. Copiar URL real del repositorio y pegarla en la sección de entrega de Duoc UC.

Si se prefiere la interfaz web de GitHub, crear el repositorio y usar «Add file / Upload files» para cargar el **contenido de la carpeta `ms-sesion3`**, no sólo el ZIP (un ZIP único no permite revisar el árbol de código desde GitHub).

**Nota:** la documentación de pruebas declara expresamente que no se han ejecutado. El vínculo se debe completar después de publicar; no se inventa URL.

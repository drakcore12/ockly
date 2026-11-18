# Manual del sistema Ockly hexagonal

## Requisitos

Se verificó con JDK 17.0.21, Maven 3.10.0, Spring Boot 3.5.16, Visual Studio Code y MariaDB 10.11.16 de XAMPP. Se necesita un navegador e internet la primera vez. No iniciar el Tomcat de XAMPP: Spring Boot trae su servidor.

## Instalación y base de datos

1. Instalar JDK 17 y Maven. Configurar JAVA_HOME y agregar sus carpetas bin al Path. En otra terminal, comprobar java -version y mvn -version.

2. Instalar XAMPP en C:\xampp e iniciar MySQL. Iniciar Apache si se va a usar phpMyAdmin. Descomprimir Ockly_Hexagonal_Fuente.zip y abrir la carpeta que contiene pom.xml.

3. La base debe existir antes de iniciar Ockly. En http://localhost/phpmyadmin/, abrir SQL y ejecutar todo database/schema.sql. Crea ockly_db, categorias, productos y su clave foránea. Si ya existen las tablas de Ockly, se reutilizan; no se necesita borrarlas ni migrar sus datos.

4. Revisar src/main/resources/application.properties: servidor localhost, puerto 3306, base ockly_db, usuario root y contraseña vacía en la instalación local. Si la cuenta tiene clave, definir $env:DB_PASSWORD="su_clave" en PowerShell antes de ejecutar. DB_USERNAME y DB_URL permiten cambiar usuario o conexión.

5. En la carpeta con pom.xml, ejecutar mvn spring-boot:run. Esperar Started OcklyApplication y abrir http://localhost:8080/. Mantener la terminal abierta; para detener, usar Ctrl+C.

6. Ejecutar mvn clean test para las pruebas automáticas. Usan H2 temporal y no limpian MariaDB. La aplicación normal usa la base de XAMPP.

## Rutas

Todas parten de `http://localhost:8080`; sustituir `{id}` por un identificador existente.

| Función | Método y ruta |
| --- | --- |
| Inicio | GET / |
| Listar categorías | GET /categorias |
| Crear categoría | GET /categorias/nuevo |
| Detalle y edición de categoría | GET /categorias/{id} y /categorias/{id}/editar |
| Guardar categoría | POST /categorias/guardar |
| Eliminar categoría | POST /categorias/{id}/eliminar |
| Listar productos | GET /productos |
| Crear producto | GET /productos/nuevo |
| Detalle y edición de producto | GET /productos/{id} y /productos/{id}/editar |
| Guardar producto | POST /productos/guardar |
| Eliminar producto | POST /productos/{id}/eliminar |

## Uso

En Categorías, elegir Nueva categoría, escribir el nombre y guardar. En Productos, elegir Nuevo producto y completar nombre, precio, existencias y categoría. En ambos listados, Consultar abre el detalle; Editar carga el formulario y guarda los cambios; Eliminar borra el registro. Una categoría con productos no se puede borrar: primero hay que quitar sus productos. El detalle de la categoría muestra sus artículos asociados.

## Problemas comunes

Si mvn no aparece, revisar el Path y abrir otra terminal. Si falla la conexión, comprobar MySQL, puerto y credenciales. Si falta una tabla, ejecutar schema.sql. Si 8080 está ocupado, cerrar la otra instancia. Los registros inexistentes devuelven 404 y las categorías inválidas, 400.

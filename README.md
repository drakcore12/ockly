# Ockly

Sistema administrativo para una papelería, migrado de MVC a arquitectura hexagonal con Java 17 y Spring Boot.

**Integrantes:** Sebastián Pineda, Melissa Betancur Osorio y Miguel Ángel Noreña Cano.  
Institución Universitaria Pascual Bravo, Arquitectura de Software, 2026.

## Informe final

- [Informe final en PDF](entrega/Informe_Final_Ockly.pdf).
- [Documento editable en Google Docs](https://docs.google.com/document/d/1BO3xjO9o3EaBIa-C6huaP5zL5_Z4CUJ2HHjbeZ-e5Lw/edit).

El informe reúne el documento técnico, el diagrama, la comparación entre arquitecturas, las siete reflexiones, las conclusiones, el manual y la evidencia del funcionamiento.

## Código y ejecución

El código completo está en este repositorio. Para descargarlo, usar **Code → Download ZIP**.

1. Instalar JDK 17 y Maven.
2. Iniciar MySQL de XAMPP. Iniciar Apache si se va a usar phpMyAdmin.
3. Ejecutar `database/schema.sql` antes del primer arranque. Se utiliza `ockly_db` en el puerto `3306`.
4. Revisar `src/main/resources/application.properties`. La configuración local usa `root` y contraseña vacía; se puede definir `DB_PASSWORD` si la cuenta tiene clave.
5. En la carpeta con `pom.xml`, ejecutar `mvn spring-boot:run` y abrir `http://localhost:8080/`.

Pruebas automáticas: `mvn clean test`. Las pruebas usan H2 temporal; la aplicación normal usa MySQL/MariaDB.

Las instrucciones completas, las rutas y el uso de categorías y productos están en el informe final.

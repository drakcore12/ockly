# Ockly con arquitectura hexagonal

Sistema administrativo para una papelería. Actividad final de Arquitectura de Software, Institución Universitaria Pascual Bravo, 2026.

**Integrantes:** Sebastián Pineda, Melissa Betancur Osorio y Miguel Ángel Noreña Cano.

La aplicación parte del mismo Ockly MVC. Conserva los CRUD de categorías y productos, su relación, las pantallas Thymeleaf y la base `ockly_db`. El núcleo ahora usa Java y puertos propios; Spring MVC y JPA quedan en adaptadores.

## Entrega final

- [Informe y manual en PDF](entrega/hexagonal/Informe_Ockly_Hexagonal.pdf).
- [Informe editable en Word](entrega/hexagonal/Informe_Ockly_Hexagonal.docx).
- [Código fuente en ZIP](entrega/hexagonal/Ockly_Hexagonal_Fuente.zip).
- [Evidencias y resultados](entrega/hexagonal/evidencias/).
- [Entrega completa en ZIP](entrega/hexagonal/Ockly_Hexagonal_Entrega.zip).

## Ejecutar

1. Instalar JDK 17 y Maven; comprobar `java -version` y `mvn -version`.
2. Iniciar MySQL de XAMPP. Apache permite usar `http://localhost/phpmyadmin/`; no iniciar Tomcat desde XAMPP.
3. Ejecutar todo [database/schema.sql](database/schema.sql) antes del primer arranque. Reutiliza las tablas existentes y no borra sus datos.
4. Revisar [application.properties](src/main/resources/application.properties): localhost, puerto 3306, base ockly_db, root y contraseña vacía por defecto. Usar DB_PASSWORD si la cuenta tiene clave; DB_USERNAME y DB_URL para otra conexión.
5. Abrir una terminal en la carpeta que contiene pom.xml y ejecutar `mvn spring-boot:run`.
6. Abrir `http://localhost:8080/`. Categorías: `/categorias`. Productos: `/productos`.

Para detener: Ctrl+C. Si 8080 está ocupado, cerrar la otra instancia o definir `$env:PORT="8081"` en PowerShell y abrir ese puerto.

## Arquitectura y manual

- [Comparación, estructura, diagrama y siete reflexiones](docs/migracion-hexagonal.md).
- [Manual de instalación, conexión, rutas y uso](docs/manual-sistema.md).
- [Diagrama de dependencias](docs/arquitectura-hexagonal.png).

Los controladores solo reciben puertos de entrada. Los casos de uso reciben puertos de salida y modelos del dominio. Los adaptadores JPA implementan esos contratos y convierten entidades mediante un mapper. CatalogoConfiguration conecta los componentes y aplica las transacciones sin importar Spring en domain ni application.

## Pruebas

Ejecutar `mvn clean test`. H2 se usa solo en las pruebas automáticas; la ejecución normal usa MySQL/MariaDB.

Verificación local del 18 de noviembre de 2026:

- 24 pruebas automáticas aprobadas: 17 del negocio sin Spring ni base, 5 de integración y 2 de dependencias de arquitectura.
- 21 comprobaciones de CRUD, rutas y validaciones en MariaDB, más la comprobación del reinicio.
- Datos anteriores conservados y registros temporales de prueba eliminados.

Los registros completos y capturas nuevas están en la carpeta de evidencias. La configuración de GitHub Actions ejecuta las pruebas automáticamente.

## Versión anterior

La versión MVC se conserva en [codex/ockly-mvc](https://github.com/drakcore12/ockly/tree/codex/ockly-mvc) y su entrega está en los archivos históricos de `entrega/`. La entrega actual es `entrega/hexagonal/`.

## Alcance

Catálogo de productos y categorías. No incluye ventas, pagos ni cuentas de usuarios. La migración conserva ese alcance y el esquema de base de datos.

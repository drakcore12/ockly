package co.edu.pascualbravo.ockly;

import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.assertj.core.api.Assertions.assertThat;

class ArquitecturaHexagonalTest {
    private final Path clases=Path.of("target/classes/co/edu/pascualbravo/ockly");

    @Test void nucleoNoDependeDeFrameworksNiInfraestructura() throws Exception {
        for (String paquete : new String[]{"domain","application"}) {
            try (var archivos=Files.walk(clases.resolve(paquete))) {
                for (Path archivo : archivos.filter(p -> p.toString().endsWith(".class")).toList()) {
                    String bytecode=new String(Files.readAllBytes(archivo),StandardCharsets.ISO_8859_1);
                    assertThat(bytecode).as(archivo.toString()).doesNotContain(
                        "org/springframework", "jakarta/", "co/edu/pascualbravo/ockly/infrastructure");
                }
            }
        }
    }
    @Test void webNoAccedeAJpaNiALosAdaptadoresDeSalida() throws Exception {
        try (var archivos=Files.walk(clases.resolve("infrastructure/adapter/in/web"))) {
            for (Path archivo : archivos.filter(p -> p.toString().endsWith(".class")).toList()) {
                String bytecode=new String(Files.readAllBytes(archivo),StandardCharsets.ISO_8859_1);
                assertThat(bytecode).as(archivo.toString()).doesNotContain("jakarta/persistence",
                    "org/springframework/data", "co/edu/pascualbravo/ockly/infrastructure/adapter/out",
                    "co/edu/pascualbravo/ockly/application/service");
            }
        }
    }
}

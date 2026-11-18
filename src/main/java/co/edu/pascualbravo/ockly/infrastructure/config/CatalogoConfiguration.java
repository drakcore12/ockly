package co.edu.pascualbravo.ockly.infrastructure.config;

import co.edu.pascualbravo.ockly.application.service.GestionarCategoriasService;
import co.edu.pascualbravo.ockly.application.service.GestionarProductosService;
import co.edu.pascualbravo.ockly.domain.port.in.*;
import co.edu.pascualbravo.ockly.domain.port.out.*;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import java.util.Properties;

@Configuration
public class CatalogoConfiguration {
    @Bean
    public GestionarCategoriasUseCase categorias(CategoriaRepositoryPort categorias,
            ProductoRepositoryPort productos, PlatformTransactionManager tx) {
        return transaccional(new GestionarCategoriasService(categorias, productos), GestionarCategoriasUseCase.class, tx);
    }
    @Bean
    public GestionarProductosUseCase productos(ProductoRepositoryPort productos,
            CategoriaRepositoryPort categorias, PlatformTransactionManager tx) {
        return transaccional(new GestionarProductosService(productos, categorias), GestionarProductosUseCase.class, tx);
    }
    // La transacción envuelve cada caso de uso completo sin introducir Spring en el núcleo.
    private <T> T transaccional(T servicio, Class<T> puerto, PlatformTransactionManager tx) {
        Properties reglas = new Properties();
        reglas.setProperty("guardar", "PROPAGATION_REQUIRED");
        reglas.setProperty("eliminar", "PROPAGATION_REQUIRED");
        reglas.setProperty("*", "PROPAGATION_REQUIRED,readOnly");
        TransactionInterceptor interceptor = new TransactionInterceptor();
        interceptor.setTransactionManager(tx);
        interceptor.setTransactionAttributes(reglas);
        ProxyFactory factory = new ProxyFactory(servicio);
        factory.setInterfaces(puerto);
        factory.addAdvice(interceptor);
        return puerto.cast(factory.getProxy());
    }
}

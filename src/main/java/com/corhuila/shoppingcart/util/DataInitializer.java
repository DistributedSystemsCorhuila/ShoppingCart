package com.corhuila.shoppingcart.util;

import com.corhuila.shoppingcart.entities.CustomerEntity;
import com.corhuila.shoppingcart.entities.ProductEntity;
import com.corhuila.shoppingcart.repository.ICustomerRepository;
import com.corhuila.shoppingcart.repository.IProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Inicializador de datos de prueba.
 * Se ejecuta automáticamente al iniciar la aplicación solo si la BD está vacía.
 */
@Component
@AllArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final ICustomerRepository customerRepository;
    private final IProductRepository productRepository;

    @Override
    public void run(String... args) {
        seedCustomers();
        seedProducts();
    }

    // ── Clientes ──────────────────────────────────────────────────────────

    private void seedCustomers() {
        List<CustomerEntity> customers = List.of(
                CustomerEntity.builder()
                        .name("Juan Carlos Tamayo")
                        .email("juan.tamayo@corhuila.edu.co")
                        .phone("3101234567")
                        .address("Calle 10 # 5-20, Neiva, Huila")
                        .build(),
                CustomerEntity.builder()
                        .name("María Fernanda López")
                        .email("maria.lopez@gmail.com")
                        .phone("3209876543")
                        .address("Carrera 8 # 12-45, Neiva, Huila")
                        .build(),
                CustomerEntity.builder()
                        .name("Carlos Andrés Pérez")
                        .email("carlos.perez@hotmail.com")
                        .phone("3154567890")
                        .address("Avenida 15 # 3-10, Pitalito, Huila")
                        .build(),
                CustomerEntity.builder()
                        .name("Luisa Valentina García")
                        .email("luisa.garcia@gmail.com")
                        .phone("3187654321")
                        .address("Calle 5 # 8-30, Garzón, Huila")
                        .build(),
                CustomerEntity.builder()
                        .name("Andrés Felipe Martínez")
                        .email("andres.martinez@yahoo.com")
                        .phone("3012345678")
                        .address("Transversal 12 # 6-15, La Plata, Huila")
                        .build(),
                CustomerEntity.builder()
                        .name("Sofía Isabel Ramírez")
                        .email("sofia.ramirez@outlook.com")
                        .phone("3058765432")
                        .address("Diagonal 9 # 4-22, Campoalegre, Huila")
                        .build(),
                CustomerEntity.builder()
                        .name("Diego Alejandro Torres")
                        .email("diego.torres@gmail.com")
                        .phone("3113456789")
                        .address("Calle 18 # 7-50, Neiva, Huila")
                        .build(),
                CustomerEntity.builder()
                        .name("Valentina Ospina")
                        .email("valentina.ospina@corhuila.edu.co")
                        .phone("3226543210")
                        .address("Carrera 5 # 9-40, Neiva, Huila")
                        .build()
        );

        // Inserta solo los que NO existen por email (evita duplicados)
        List<CustomerEntity> nuevos = customers.stream()
                .filter(c -> !customerRepository.existsByEmail(c.getEmail()))
                .toList();

        if (nuevos.isEmpty()) {
            log.info("✅ Todos los clientes ya existen en la BD.");
            return;
        }

        customerRepository.saveAll(nuevos);
        log.info("✅ {} cliente(s) nuevo(s) insertados exitosamente.", nuevos.size());
    }

    // ── Productos ─────────────────────────────────────────────────────────

    private void seedProducts() {
        if (productRepository.count() > 0) {
            log.info("✅ Productos ya existen en la BD, se omite la carga inicial.");
            return;
        }

        List<ProductEntity> products = List.of(
                ProductEntity.builder()
                        .name("Laptop HP Pavilion 15")
                        .description("Laptop 15.6\", Intel Core i5, 16GB RAM, 512GB SSD")
                        .price(new BigDecimal("2500000.00"))
                        .stock(10)
                        .active(true)
                        .build(),
                ProductEntity.builder()
                        .name("Mouse Inalámbrico Logitech")
                        .description("Mouse ergonómico inalámbrico, 1600 DPI, batería larga duración")
                        .price(new BigDecimal("85000.00"))
                        .stock(50)
                        .active(true)
                        .build(),
                ProductEntity.builder()
                        .name("Teclado Mecánico Redragon")
                        .description("Teclado mecánico RGB, switches Blue, USB")
                        .price(new BigDecimal("150000.00"))
                        .stock(30)
                        .active(true)
                        .build(),
                ProductEntity.builder()
                        .name("Monitor Samsung 24\"")
                        .description("Monitor Full HD 1080p, 75Hz, panel IPS, HDMI/VGA")
                        .price(new BigDecimal("680000.00"))
                        .stock(15)
                        .active(true)
                        .build(),
                ProductEntity.builder()
                        .name("Auriculares Sony WH-1000XM4")
                        .description("Auriculares Bluetooth con cancelación de ruido activa")
                        .price(new BigDecimal("1200000.00"))
                        .stock(20)
                        .active(true)
                        .build(),
                ProductEntity.builder()
                        .name("Disco Duro Externo Seagate 1TB")
                        .description("HDD externo portátil 1TB, USB 3.0")
                        .price(new BigDecimal("220000.00"))
                        .stock(40)
                        .active(true)
                        .build(),
                ProductEntity.builder()
                        .name("Cámara Web Logitech C920")
                        .description("Webcam HD 1080p, micrófono estéreo integrado, USB")
                        .price(new BigDecimal("350000.00"))
                        .stock(25)
                        .active(true)
                        .build(),
                ProductEntity.builder()
                        .name("Silla Ergonómica de Oficina")
                        .description("Silla con soporte lumbar, apoyabrazos ajustables, ruedas")
                        .price(new BigDecimal("450000.00"))
                        .stock(8)
                        .active(true)
                        .build()
        );

        productRepository.saveAll(products);
        log.info("✅ {} productos de prueba creados exitosamente.", products.size());
    }
}

# 🛒 Shopping Cart API

API REST para gestión de carrito de compras desarrollada con **Spring Boot 3.5**, **PostgreSQL** y documentada con **Swagger UI**.

> **Proyecto:** Carrito de Compras  
> **Autor:** Juan Carlos Tamayo Andrade  
> **Institución:** Universidad CorHuila  
> **Materia:** Sistemas Distribuidos

---

## 🧰 Tecnologías utilizadas

| Tecnología | Versión |
|---|---|
| Java | 17 |
| Spring Boot | 3.5.13 |
| Spring Data JPA | 3.5.x |
| PostgreSQL | 17.x |
| Lombok | 1.18.x |
| SpringDoc OpenAPI (Swagger) | 2.8.0 |
| Maven | 3.x |

---

## 📁 Estructura del proyecto

```
com.corhuila.shoppingcart
│
├── constant/
│   ├── AppConstants.java            ← URLs base, mensajes de éxito y error
│   ├── SwaggerConfig.java           ← Configuración OpenAPI / Swagger UI
│   └── GlobalExceptionHandler.java  ← Manejo global de excepciones
│
├── controllers/
│   ├── ProductController.java       ← CRUD de productos
│   ├── CustomerController.java      ← CRUD de clientes
│   ├── CartController.java          ← Gestión del carrito
│   └── OrderController.java         ← Checkout y órdenes
│
├── dto/
│   ├── ResponseGenerico.java        ← Respuesta estandarizada <T>
│   ├── ProductDto.java
│   ├── CustomerDto.java
│   ├── CartDto.java
│   ├── CartItemDto.java
│   ├── OrderDto.java
│   └── OrderItemDto.java
│
├── entities/
│   ├── ProductEntity.java
│   ├── CustomerEntity.java
│   ├── CartEntity.java
│   ├── CartItemEntity.java
│   ├── OrderEntity.java
│   └── OrderItemEntity.java
│
├── Enum/
│   ├── CartStatus.java              ← ACTIVE | CHECKED_OUT | ABANDONED
│   └── OrderStatus.java             ← PENDING | CONFIRMED | SHIPPED | DELIVERED | CANCELLED
│
├── repository/
│   ├── IProductRepository.java
│   ├── ICustomerRepository.java
│   ├── ICartRepository.java
│   └── IOrderRepository.java
│
├── services/
│   ├── IProductService.java
│   ├── ICustomerService.java
│   ├── ICartService.java
│   ├── IOrderService.java
│   └── impl/
│       ├── ProductServiceImpl.java
│       ├── CustomerServiceImpl.java
│       ├── CartServiceImpl.java
│       └── OrderServiceImpl.java
│
└── util/
    └── PriceUtil.java               ← Lógica de descuentos automáticos
```

---

## ⚙️ Configuración

### 1. Requisitos previos

- Java 17+
- Maven 3.8+
- PostgreSQL 14+

### 2. Crear la base de datos

```sql
CREATE DATABASE shopping_cart_db;
```

### 3. Configurar `application.properties`

```properties
# Datasource
spring.datasource.url=jdbc:postgresql://localhost:5432/shopping_cart_db
spring.datasource.username=postgres
spring.datasource.password=postgres

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Swagger
springdoc.swagger-ui.path=/swagger-ui/index.html
springdoc.api-docs.path=/api-docs
```

### 4. Ejecutar la aplicación

```bash
./mvnw spring-boot:run
```

O desde IntelliJ IDEA: clic en ▶️ sobre `ShoppingCartApplication.java`

---

## 📖 Documentación Swagger UI

Una vez iniciada la aplicación accede a:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🌐 Endpoints disponibles

### 📦 Productos — `/api/v1/products`

| Método | Endpoint | Descripción |
|---|---|---|
| `POST` | `/api/v1/products` | Crear producto |
| `GET` | `/api/v1/products` | Listar todos los productos |
| `GET` | `/api/v1/products/active` | Listar productos activos |
| `GET` | `/api/v1/products/{id}` | Obtener producto por ID |
| `PUT` | `/api/v1/products/{id}` | Actualizar producto |
| `DELETE` | `/api/v1/products/{id}` | Eliminar producto (baja lógica) |

### 👤 Clientes — `/api/v1/customers`

| Método | Endpoint | Descripción |
|---|---|---|
| `POST` | `/api/v1/customers` | Registrar cliente |
| `GET` | `/api/v1/customers` | Listar todos los clientes |
| `GET` | `/api/v1/customers/{id}` | Obtener cliente por ID |
| `PUT` | `/api/v1/customers/{id}` | Actualizar cliente |
| `DELETE` | `/api/v1/customers/{id}` | Eliminar cliente |

### 🛒 Carrito — `/api/v1/carts`

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/api/v1/carts/customer/{customerId}` | Obtener o crear carrito activo |
| `GET` | `/api/v1/carts/{cartId}` | Obtener carrito por ID |
| `POST` | `/api/v1/carts/{cartId}/items` | Agregar producto al carrito |
| `PUT` | `/api/v1/carts/{cartId}/items/{itemId}?quantity=N` | Actualizar cantidad de ítem |
| `DELETE` | `/api/v1/carts/{cartId}/items/{itemId}` | Eliminar ítem del carrito |
| `DELETE` | `/api/v1/carts/{cartId}/items` | Vaciar carrito completo |

### 📋 Órdenes — `/api/v1/orders`

| Método | Endpoint | Descripción |
|---|---|---|
| `POST` | `/api/v1/orders/checkout/{cartId}` | Realizar checkout |
| `GET` | `/api/v1/orders` | Listar todas las órdenes |
| `GET` | `/api/v1/orders/{id}` | Obtener orden por ID |
| `GET` | `/api/v1/orders/customer/{customerId}` | Órdenes por cliente |
| `PATCH` | `/api/v1/orders/{id}/status?status=CONFIRMED` | Actualizar estado de orden |

---

## 💰 Descuentos automáticos (Patrón Strategy)

Al hacer checkout, se aplica descuento según el total del carrito:

| Total del carrito | Descuento |
|---|---|
| < $100.000 | Sin descuento |
| $100.000 – $299.999 | 10% |
| ≥ $300.000 | 15% |

---

## 🧩 Patrones de diseño aplicados

| Patrón | Implementación |
|---|---|
| **Repository** | Interfaces `I*Repository` con Spring Data JPA |
| **Strategy** | `PriceUtil` aplica distintos algoritmos de descuento |
| **Builder** | Todas las entidades y DTOs usan `@Builder` de Lombok |
| **DTO** | Objetos de transferencia que separan la capa de presentación de las entidades |

---

## 📐 Principios SOLID aplicados

| Principio | Aplicación |
|---|---|
| **S** — Single Responsibility | Cada clase tiene una única responsabilidad |
| **O** — Open/Closed | Nuevas estrategias de descuento sin modificar código existente |
| **L** — Liskov Substitution | Los `*ServiceImpl` son substituibles por sus interfaces |
| **I** — Interface Segregation | Interfaces separadas: `IProductService`, `ICartService`, etc. |
| **D** — Dependency Inversion | Los controllers dependen de interfaces, no de implementaciones |

---

## 📬 Ejemplo de uso rápido

### 1. Crear un producto
```json
POST /api/v1/products
{
  "name": "Laptop HP",
  "description": "Laptop 15 pulgadas, 16GB RAM",
  "price": 2500000.00,
  "stock": 10
}
```

### 2. Registrar un cliente
```json
POST /api/v1/customers
{
  "name": "Juan Pérez",
  "email": "juan@email.com",
  "phone": "3101234567",
  "address": "Calle 10 # 5-20, Neiva"
}
```

### 3. Obtener/crear carrito
```
GET /api/v1/carts/customer/1
```

### 4. Agregar producto al carrito
```json
POST /api/v1/carts/1/items
{
  "productId": 1,
  "quantity": 2
}
```

### 5. Hacer checkout
```
POST /api/v1/orders/checkout/1
```

---

## 📄 Licencia

 © 2026 CorHuila

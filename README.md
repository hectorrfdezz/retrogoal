# RetroGoal – Tienda web de camisetas de fútbol

RetroGoal es una aplicación web de comercio electrónico desarrollada como proyecto final
del ciclo formativo de Desarrollo de Aplicaciones Web. La tienda está especializada
en la venta de camisetas de fútbol retro y actuales. El proyecto utiliza **Spring Boot**
como base del back‑end, **Thymeleaf** como motor de plantillas para el front‑end y
**Spring Security** para gestionar la autenticación y autorización de usuarios.

## Características implementadas

La aplicación incluye las funcionalidades esenciales descritas en el pliego de requisitos:

| Área                        | Funcionalidad                                                                                       |
|----------------------------|------------------------------------------------------------------------------------------------------|
| Registro y autenticación   | Registro de nuevos usuarios con validación, inicio de sesión, cierre de sesión.                   |
| Roles y protección de rutas | Roles `USER` y `ADMIN` con protección de rutas mediante Spring Security.                           |
| Catálogo                   | Listado de productos con filtros por nombre, equipo, era, talla y precio máximo.                  |
| Buscador                   | Búsqueda parcial por nombre (ignora mayúsculas/minúsculas).                                        |
| Página de producto         | Ficha de producto con imagen, descripción, precio, talla y sugerencias de productos relacionados. |
| Carrito                    | Carrito en sesión: añadir productos, modificar cantidades y eliminar artículos.                   |
| Checkout                   | Flujo de compra con dirección de envío; al confirmar se genera un pedido y se limpia el carrito.  |
| Gestión de pedidos         | Los usuarios pueden revisar el resumen al finalizar la compra; los administradores pueden cambiar el estado de los pedidos. |
| CRUD de productos          | Panel de administración con altas, bajas y modificaciones de productos.                           |
| Gestión de pedidos (admin) | Listado de pedidos con posibilidad de modificar su estado (PENDING, PAID, SHIPPED, COMPLETED, CANCELLED). |
| PWA básico                 | No implementado por completo; la arquitectura está preparada para añadir manifest y service worker. |
| Recomendaciones simples    | Relación many‑to‑many entre productos para mostrar sugerencias básicas en la página de producto.   |
| Seguridad                  | Contraseñas almacenadas con BCrypt; rutas protegidas; uso de roles.                                |
| Diseño responsive          | Plantillas con Bootstrap que se adaptan a distintos tamaños de pantalla.                          |
| Base de datos              | Configuración por defecto con H2 en memoria; se puede cambiar a MySQL editando `application.properties`. |

## Requisitos previos

Para compilar y ejecutar el proyecto es necesario disponer de **Java 17** y **Maven**. Si se desea usar una base de datos
MySQL en lugar de H2, hay que proporcionar los datos de conexión en `src/main/resources/application.properties`.

## Compilación y ejecución

1. Clona o descarga este repositorio.
2. Sitúate en la carpeta `retrogoal` y ejecuta:

```bash
mvn spring-boot:run
```

3. Accede a la aplicación en `http://localhost:8080`.

### Acceso de ejemplo

El inicializador de datos crea un usuario administrador con estos datos:

- **Correo:** `admin@retrogoal.com`
- **Contraseña:** `admin123`

Utiliza estas credenciales para acceder al panel de administración (`/admin`).

## Estructura del proyecto

```
retrogoal/
├── pom.xml                    # Dependencias Maven y configuración de Spring Boot
├── README.md                 # Este archivo
├── src/main/java
│   └── com/retrogoal/retrogoal
│       ├── RetrogoalApplication.java         # Clase principal
│       ├── config
│       │   ├── DataInitializer.java         # Carga datos de prueba (roles, admin, productos)
│       │   └── SecurityConfig.java          # Configuración de Spring Security
│       ├── controller                       # Controladores MVC
│       │   ├── AdminController.java
│       │   ├── AuthController.java
│       │   ├── CartController.java
│       │   ├── CheckoutController.java
│       │   ├── HomeController.java
│       │   └── ProductController.java
│       ├── dto
│       │   └── UserRegistrationDto.java     # DTO para registro de usuarios
│       ├── model                            # Entidades JPA
│       │   ├── Order.java
│       │   ├── OrderItem.java
│       │   ├── OrderStatus.java
│       │   ├── Product.java
│       │   ├── Role.java
│       │   └── User.java
│       ├── repository                       # Repositorios Spring Data
│       │   ├── OrderItemRepository.java
│       │   ├── OrderRepository.java
│       │   ├── ProductRepository.java
│       │   ├── RoleRepository.java
│       │   └── UserRepository.java
│       ├── service                          # Interfaces y servicios
│       │   ├── CartService.java
│       │   ├── OrderService.java
│       │   ├── ProductService.java
│       │   ├── UserService.java
│       │   └── impl
│       │       ├── OrderServiceImpl.java
│       │       ├── ProductServiceImpl.java
│       │       ├── UserDetailsServiceImpl.java
│       │       └── UserServiceImpl.java
│       └── config                           # Beans de configuración
│           └── DataInitializer.java
└── src/main/resources
    ├── application.properties              # Configuración de bases de datos y otros parámetros
    ├── templates                           # Plantillas Thymeleaf
    │   ├── admin
    │   │   ├── dashboard.html
    │   │   ├── orderDetail.html
    │   │   └── productForm.html
    │   ├── cart.html
    │   ├── catalog.html
    │   ├── checkout.html
    │   ├── index.html
    │   ├── login.html
    │   ├── orderConfirmation.html
    │   ├── product.html
    │   └── register.html
    └── static                              # Archivos estáticos (no utilizados explícitamente)

```

## Personalización y ampliación

- **Pasarela de pagos:** la integración con Stripe está preparada a nivel de dependencias. Para habilitarla
  se deben obtener las claves públicas y privadas en la consola de Stripe e indicarlas como variables de
  entorno (`STRIPE_SECRET_KEY` y `STRIPE_PUBLISHABLE_KEY`). Después, sustituye la lógica de pago
  ficticia en `CheckoutController` por la creación de un `PaymentIntent` y el uso de Stripe Checkout o
  Stripe Elements.
- **Base de datos:** para usar MySQL en lugar de H2, actualiza las propiedades `spring.datasource.*` en
  `application.properties` y añade tus credenciales. Ajusta también `spring.jpa.hibernate.ddl-auto` a
  `update` o `validate` según tus necesidades.
- **PWA:** puedes convertir la aplicación en una PWA añadiendo un `manifest.json` y un `service-worker.js` en
  la carpeta `static`. Luego enlaza el manifiesto en las plantillas.
- **Recomendaciones avanzadas:** actualmente se muestran recomendaciones básicas basadas en relaciones
  manuales. Puedes implementar algoritmos de filtrado colaborativo utilizando el historial de compras.
- **Cupones y fidelización:** la arquitectura permite añadir nuevas entidades para gestionar cupones,
  descuentos y sistemas de puntos.

## Cumplimiento legal

Se han incorporado las medidas básicas de seguridad (cifrado de contraseñas, rutas protegidas) y el proyecto
está preparado para incluir textos legales obligatorios (política de cookies, privacidad) mediante
plantillas o componentes adicionales. Recuerda adaptar dichos textos al RGPD y a la LOPDGDD.

# RetroGoal – Tienda web de camisetas de fútbol

RetroGoal es una aplicación web de comercio electrónico desarrollada como proyecto final del ciclo de **Desarrollo de Aplicaciones Web**.

La aplicación está orientada a la venta de camisetas de fútbol retro y actuales. Permite consultar un catálogo de productos, registrarse, iniciar sesión, añadir camisetas al carrito, completar un checkout, realizar un pago de prueba con Stripe, consultar una ubicación mediante Google Maps y gestionar productos desde un panel de administración.

El proyecto está desarrollado con **Java 17**, **Spring Boot**, **Thymeleaf**, **Spring Security**, **JPA/Hibernate**, **Maven**, **HTML5**, **CSS3**, **Bootstrap** y **JavaScript**.

---

## 1. Descripción general

La idea de RetroGoal surge al analizar tiendas pequeñas y vendedores de camisetas deportivas que gestionan sus ventas mediante redes sociales, mensajes directos o catálogos poco estructurados.

Con esta aplicación se busca ofrecer una alternativa más ordenada y profesional, donde el cliente pueda consultar productos, añadirlos al carrito y completar una compra de forma sencilla.

El proyecto funciona como un MVP de tienda online y reúne varias partes trabajadas durante el ciclo: backend, frontend, base de datos, seguridad, control de versiones, pruebas, integración con servicios externos y documentación técnica.

---

## 2. Funcionalidades principales

| Área                     | Funcionalidad                                                          |
| ------------------------ | ---------------------------------------------------------------------- |
| Registro y autenticación | Registro de usuarios, inicio de sesión y cierre de sesión              |
| Roles                    | Roles de usuario normal y administrador                                |
| Catálogo                 | Listado de camisetas con información de producto                       |
| Ficha de producto        | Página individual con imagen, descripción, precio y datos del producto |
| Carrito                  | Añadir productos, modificar cantidades y eliminar artículos            |
| Checkout                 | Confirmación del pedido antes de continuar al pago                     |
| Stripe Checkout          | Redirección a Stripe en modo prueba                                    |
| Google Maps              | Página con ubicación de referencia de la tienda                        |
| Internacionalización     | Soporte para español, inglés y francés                                 |
| Administración           | Panel para crear, editar y eliminar productos                          |
| Pedidos                  | Gestión básica de pedidos y estados                                    |
| Seguridad                | Contraseñas cifradas con BCrypt y rutas protegidas                     |
| Errores                  | Página de error personalizada                                          |
| Responsive               | Diseño adaptable mediante Bootstrap                                    |
| Python                   | Script auxiliar para analizar el valor estimado del inventario         |
| GitHub Actions           | Workflow básico de integración continua con Maven                      |

---

## 3. Tecnologías utilizadas

### Backend

* Java 17
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* Maven
* H2 para desarrollo
* Preparado para migración futura a MySQL o PostgreSQL

### Frontend

* HTML5
* CSS3
* Bootstrap
* Thymeleaf
* JavaScript

### Servicios externos

* Stripe Checkout
* Google Maps API
* GitHub
* GitHub Actions

### Herramientas auxiliares

* Visual Studio Code / IntelliJ IDEA
* Git
* Maven
* Python
* Matplotlib

---

## 4. Estructura del proyecto

```text
retrogoal/
├── .github/
│   └── workflows/
│       └── maven.yml
├── data/
│   └── productos_retrogoal.csv
├── docs/
│   └── img/
│       ├── grafica_valor_inventario_por_equipo.jpg
│       └── grafica_valor_inventario_por_equipo.png
├── scripts/
│   └── analisis_productos.py
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/retrogoal/retrogoal/
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       ├── exception/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       └── service/
│   │   └── resources/
│   │       ├── static/
│   │       ├── templates/
│   │       ├── messages.properties
│   │       ├── messages_en.properties
│   │       ├── messages_fr.properties
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

### Explicación de carpetas

| Carpeta / archivo               | Función                                         |
| ------------------------------- | ----------------------------------------------- |
| `.github/workflows/`            | Contiene el workflow de GitHub Actions          |
| `data/`                         | Datos auxiliares usados por el script de Python |
| `docs/img/`                     | Imágenes y gráficas usadas como evidencias      |
| `scripts/`                      | Scripts auxiliares del proyecto                 |
| `src/main/java/`                | Código principal Java                           |
| `src/main/resources/templates/` | Plantillas Thymeleaf                            |
| `src/main/resources/static/`    | Archivos CSS, JS e imágenes                     |
| `src/test/`                     | Pruebas del proyecto                            |
| `pom.xml`                       | Dependencias y configuración de Maven           |
| `README.md`                     | Guía principal del repositorio                  |

---

## 5. Arquitectura

RetroGoal sigue una arquitectura basada en el patrón **MVC**.

| Capa        | Función                                                                   |
| ----------- | ------------------------------------------------------------------------- |
| Modelo      | Entidades JPA que representan usuarios, productos, pedidos y roles        |
| Vista       | Plantillas Thymeleaf que generan las páginas HTML                         |
| Controlador | Clases que reciben las peticiones HTTP y devuelven vistas o redirecciones |
| Servicio    | Lógica de negocio de la aplicación                                        |
| Repositorio | Acceso a datos mediante Spring Data JPA                                   |

Esta separación ayuda a mantener el código más ordenado y facilita futuras ampliaciones.

---

## 6. Paquetes principales

| Paquete      | Función                                                            |
| ------------ | ------------------------------------------------------------------ |
| `config`     | Configuración de seguridad, internacionalización y datos iniciales |
| `controller` | Controladores web                                                  |
| `dto`        | Objetos de transferencia de datos                                  |
| `exception`  | Gestión de errores                                                 |
| `model`      | Entidades de base de datos                                         |
| `repository` | Repositorios de acceso a datos                                     |
| `service`    | Lógica de negocio                                                  |

---

## 7. Modelo de datos

Las entidades principales del proyecto son:

| Entidad       | Descripción                             |
| ------------- | --------------------------------------- |
| `User`        | Usuario registrado en la aplicación     |
| `Role`        | Rol asignado a un usuario               |
| `Product`     | Camiseta o producto del catálogo        |
| `Order`       | Pedido realizado por un usuario         |
| `OrderItem`   | Línea de pedido con producto y cantidad |
| `OrderStatus` | Estado del pedido                       |

Relaciones principales:

* Un usuario puede tener uno o varios roles.
* Un usuario puede realizar varios pedidos.
* Un pedido pertenece a un usuario.
* Un pedido contiene una o varias líneas de pedido.
* Cada línea de pedido referencia un producto.
* Un producto puede aparecer en varias líneas de pedido.
* Un pedido tiene un estado asociado.

---

## 8. Endpoints principales

| Método | Ruta                          | Controlador                               | Acceso              | Función                              |
| ------ | ----------------------------- | ----------------------------------------- | ------------------- | ------------------------------------ |
| GET    | `/`                           | HomeController                            | Público             | Página de inicio                     |
| GET    | `/catalog`                    | ProductController                         | Público             | Catálogo de productos                |
| GET    | `/product/{id}`               | ProductController                         | Público             | Detalle de producto                  |
| GET    | `/login`                      | AuthController                            | Público             | Formulario de inicio de sesión       |
| POST   | `/login`                      | Spring Security                           | Público             | Procesa el inicio de sesión          |
| GET    | `/register`                   | AuthController                            | Público             | Formulario de registro               |
| POST   | `/register`                   | AuthController                            | Público             | Registra un nuevo usuario            |
| POST   | `/logout`                     | Spring Security                           | Usuario autenticado | Cierra la sesión                     |
| GET    | `/cart`                       | CartController                            | Usuario autenticado | Muestra el carrito                   |
| POST   | `/cart/add/{id}`              | CartController                            | Usuario autenticado | Añade un producto al carrito         |
| POST   | `/cart/update`                | CartController                            | Usuario autenticado | Modifica cantidades                  |
| POST   | `/cart/remove/{id}`           | CartController                            | Usuario autenticado | Elimina un producto                  |
| GET    | `/checkout`                   | CheckoutController                        | Usuario autenticado | Muestra el checkout                  |
| POST   | `/checkout`                   | CheckoutController                        | Usuario autenticado | Procesa el checkout                  |
| POST   | `/create-checkout-session`    | PaymentController / StripeCheckoutService | Usuario autenticado | Crea una sesión de Stripe            |
| GET    | `/payment/success`            | PaymentController                         | Usuario autenticado | Pago completado                      |
| GET    | `/payment/cancel`             | PaymentController                         | Usuario autenticado | Pago cancelado                       |
| GET    | `/map`                        | MapController                             | Público             | Muestra la ubicación con Google Maps |
| GET    | `/admin`                      | AdminController                           | Administrador       | Panel de administración              |
| GET    | `/admin/products`             | AdminController                           | Administrador       | Lista productos                      |
| GET    | `/admin/products/new`         | AdminController                           | Administrador       | Formulario de nuevo producto         |
| POST   | `/admin/products/save`        | AdminController                           | Administrador       | Guarda producto nuevo o editado      |
| GET    | `/admin/products/edit/{id}`   | AdminController                           | Administrador       | Formulario de edición                |
| POST   | `/admin/products/delete/{id}` | AdminController                           | Administrador       | Elimina producto                     |
| GET    | `/error`                      | GlobalExceptionHandler                    | Público             | Página de error personalizada        |

---

## 9. Seguridad

La seguridad se gestiona con **Spring Security**.

Medidas aplicadas:

* Registro e inicio de sesión.
* Contraseñas cifradas mediante BCrypt.
* Gestión de roles.
* Protección de rutas administrativas.
* Control de acceso a funcionalidades según usuario.
* Variables de entorno para claves sensibles.
* Página de error personalizada.

Roles principales:

| Rol     | Permisos                                                 |
| ------- | -------------------------------------------------------- |
| `USER`  | Consultar catálogo, usar carrito y realizar checkout     |
| `ADMIN` | Acceder al panel de administración y gestionar productos |

---

## 10. Internacionalización

La aplicación incluye soporte para tres idiomas:

* Español
* Inglés
* Francés

Los textos se gestionan mediante los siguientes archivos:

```text
messages.properties
messages_en.properties
messages_fr.properties
```

El idioma se puede cambiar mediante el parámetro `lang`.

Ejemplos:

```text
http://localhost:8080/?lang=es
http://localhost:8080/?lang=en
http://localhost:8080/?lang=fr
```

---

## 11. Stripe Checkout

La aplicación utiliza **Stripe Checkout** en modo prueba.

Flujo básico:

1. El usuario añade productos al carrito.
2. Accede al checkout.
3. Confirma la compra.
4. El backend crea una sesión de Stripe Checkout.
5. Stripe devuelve una URL de pago.
6. El usuario es redirigido a Stripe.
7. Si el pago se completa, vuelve a la página de éxito.
8. Si cancela, vuelve a la página de cancelación.

La aplicación no almacena datos bancarios. El pago se gestiona desde Stripe.

Variables necesarias:

```text
STRIPE_SECRET_KEY=sk_test_...
STRIPE_PUBLISHABLE_KEY=pk_test_...
APP_BASE_URL=http://localhost:8080
```

---

## 12. Google Maps

La página `/map` integra Google Maps JavaScript API para mostrar una ubicación ficticia de demostración de la tienda.

Variables relacionadas:

```text
GOOGLE_MAPS_API_KEY=tu_clave_de_google_maps
STORE_LOCATION_NAME=RetroGoal Sevilla
STORE_LOCATION_ADDRESS=Calle Sierpes 1, 41004 Sevilla, España
STORE_LOCATION_LATITUDE=37.3891
STORE_LOCATION_LONGITUDE=-5.9845
```

Si no se configura la clave de Google Maps, la página puede cargar un aviso en lugar del mapa.

---

## 13. Variables de entorno

Para ejecutar todas las funcionalidades externas, se recomienda configurar estas variables:

```text
STRIPE_SECRET_KEY=sk_test_...
STRIPE_PUBLISHABLE_KEY=pk_test_...
GOOGLE_MAPS_API_KEY=AIza...
APP_BASE_URL=http://localhost:8080
```

Ejemplo en PowerShell:

```powershell
$env:STRIPE_SECRET_KEY="sk_test_..."
$env:STRIPE_PUBLISHABLE_KEY="pk_test_..."
$env:GOOGLE_MAPS_API_KEY="AIza..."
$env:APP_BASE_URL="http://localhost:8080"
```

---

## 14. Requisitos previos

Para ejecutar el proyecto es necesario tener instalado:

* Java 17
* Maven
* Git
* Navegador web actualizado
* Python 3, solo para ejecutar el análisis auxiliar

Comprobar Java:

```bash
java -version
```

Comprobar Maven:

```bash
mvn -version
```

Comprobar Python:

```bash
python --version
```

---

## 15. Ejecución local

Clonar el repositorio:

```bash
git clone https://github.com/hectorrfdezz/retrogoal.git
cd retrogoal
```

Ejecutar la aplicación:

```bash
mvn spring-boot:run
```

Abrir en el navegador:

```text
http://localhost:8080
```

---

## 16. Usuario administrador de prueba

El inicializador de datos crea un usuario administrador para pruebas locales:

```text
Correo: admin@retrogoal.com
Contraseña: admin123
```

Este usuario permite acceder al panel de administración durante el desarrollo.

---

## 17. Pruebas

El proyecto incluye pruebas básicas relacionadas con la lógica principal de la aplicación.

Ejecutar tests:

```bash
mvn test
```

Compilar y construir el proyecto:

```bash
mvn clean package
```

---

## 18. GitHub Actions

El proyecto incluye un workflow básico de integración continua en:

```text
.github/workflows/maven.yml
```

Este workflow se ejecuta al realizar `push` o `pull request` sobre las ramas configuradas.

Su función es:

* Descargar el repositorio.
* Configurar Java 17.
* Usar Maven.
* Compilar el proyecto.
* Ejecutar pruebas.
* Comprobar que el proyecto puede construirse correctamente.

Esto ayuda a detectar fallos antes de integrar cambios en las ramas principales.

---

## 19. Análisis auxiliar con Python

El proyecto incluye un script auxiliar de Python para analizar datos del catálogo.

Ruta del script:

```text
scripts/analisis_productos.py
```

Archivo de datos:

```text
data/productos_retrogoal.csv
```

Gráfica generada:

```text
docs/img/grafica_valor_inventario_por_equipo.jpg
```

El script calcula el valor estimado del inventario por equipo usando la fórmula:

```text
valor estimado = precio × stock
```

Después agrupa los resultados por equipo y genera una gráfica de barras.

Ejecutar el análisis:

```bash
python scripts/analisis_productos.py
```

Si falta la librería `matplotlib`, se puede instalar con:

```bash
python -m pip install matplotlib
```

Este análisis sirve como apoyo para la administración del catálogo, ya que permite ver qué equipos concentran mayor valor dentro del inventario.

---

## 20. Diseño visual

La aplicación utiliza una estética oscura y deportiva.

Colores principales usados en el CSS:

| Elemento                | Código                |
| ----------------------- | --------------------- |
| Fondo general           | `#0b0d17`             |
| Texto principal         | `#f8f9fa`             |
| Navbar y footer         | `#000000`             |
| Tarjetas de producto    | `#1e1f29`             |
| Hover de enlaces        | `#00c6ff`             |
| Botón principal         | `#17a2b8`             |
| Hover botón principal   | `#138496`             |
| Fondo sección principal | `#050505` y `#1d1d1d` |
| Fondo sección mapa      | `#101522` y `#1e293b` |

El diseño se apoya en Bootstrap para facilitar la adaptación a escritorio, tablet y móvil.

---

## 21. Accesibilidad y SEO

Medidas aplicadas:

* Uso de HTML semántico.
* Títulos descriptivos.
* Metadescripciones.
* Idioma dinámico.
* Formularios con etiquetas.
* Botones visibles.
* Contraste entre fondo oscuro y texto claro.
* Página de error personalizada.
* Diseño responsive.
* Navegación consistente.

---

## 22. Incidencias resueltas

Durante el desarrollo se resolvieron varios problemas:

| Incidencia                                        | Solución                                                    |
| ------------------------------------------------- | ----------------------------------------------------------- |
| Error en carrito por lógica compleja en Thymeleaf | Se movió el cálculo al backend                              |
| Página Whitelabel Error Page                      | Se añadió una vista `error.html` y un manejador global      |
| Traducciones incompletas                          | Se centralizaron textos en archivos `messages`              |
| Configuración inicial de Google Maps              | Se configuró el uso de API Key mediante variable de entorno |
| Pantalla de pago antigua                          | Se sustituyó por Stripe Checkout                            |
| Claves sensibles                                  | Se pasaron a variables de entorno                           |

---

## 23. Estado actual

Funcionalidades implementadas:

* Catálogo de productos.
* Ficha de producto.
* Registro e inicio de sesión.
* Gestión de roles.
* Carrito de compra.
* Checkout.
* Stripe Checkout en modo prueba.
* Google Maps API.
* Internacionalización.
* Panel de administración.
* Página de error personalizada.
* Pruebas básicas.
* GitHub Actions.
* Script auxiliar de análisis con Python.

---

## 24. Mejoras futuras

Posibles ampliaciones:

* Despliegue real en producción.
* Configuración de dominio y HTTPS.
* Migración definitiva a MySQL o PostgreSQL.
* Docker y Docker Compose.
* Webhooks avanzados de Stripe.
* Facturación.
* Gestión logística.
* Cupones.
* Valoraciones de productos.
* Panel interno de analítica.
* Integración del análisis Python dentro del panel de administración.
* Sistema de recomendaciones más avanzado.
* Mejoras adicionales de accesibilidad.

---

## 25. Comandos útiles

Ejecutar aplicación:

```bash
mvn spring-boot:run
```

Ejecutar pruebas:

```bash
mvn test
```

Construir proyecto:

```bash
mvn clean package
```

Ejecutar análisis Python:

```bash
python scripts/analisis_productos.py
```

Consultar estado de Git:

```bash
git status
```

Añadir cambios:

```bash
git add -A
```

Crear commit:

```bash
git commit -m "Mensaje del cambio"
```

Subir cambios:

```bash
git push
```

---

## 26. Autor

Proyecto desarrollado por **Héctor Ramos Fernández** para el módulo de Proyecto Intermodular de Desarrollo de Aplicaciones Web.

Curso: **2º DAW**
Proyecto: **RetroGoal**

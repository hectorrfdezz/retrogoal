# RetroGoal – Tienda web de camisetas de fútbol


---

RetroGoal es una aplicación web de comercio electrónico desarrollada como proyecto final del ciclo de **Desarrollo de Aplicaciones Web**.

La tienda está especializada en camisetas de fútbol retro y actuales. Permite consultar productos, registrarse, iniciar sesión, añadir artículos al carrito, completar un checkout, realizar pagos de prueba con Stripe y gestionar productos desde un panel de administración.

El proyecto está desarrollado principalmente con **Java 17**, **Spring Boot**, **Thymeleaf**, **Spring Security**, **JPA/Hibernate**, **Maven**, **HTML5**, **CSS3**, **Bootstrap** y **JavaScript**.

---

## Funcionalidades principales

| Área                     | Funcionalidad                                           |
| ------------------------ | ------------------------------------------------------- |
| Registro y autenticación | Registro de usuarios, login y cierre de sesión          |
| Roles                    | Usuarios normales y administradores                     |
| Catálogo                 | Listado de camisetas con información de producto        |
| Ficha de producto        | Vista individual de cada camiseta                       |
| Carrito                  | Añadir, modificar cantidades y eliminar productos       |
| Checkout                 | Confirmación de compra antes del pago                   |
| Stripe Checkout          | Pago en modo prueba mediante redirección segura         |
| Google Maps              | Ubicación de referencia de la tienda                    |
| Internacionalización     | Soporte para español, inglés y francés                  |
| Administración           | CRUD de productos y gestión básica                      |
| Seguridad                | BCrypt, rutas protegidas y control por roles            |
| Responsive               | Diseño adaptable mediante Bootstrap                     |
| Errores                  | Página de error personalizada                           |
| Python                   | Analítica conectada a MySQL con métricas y gráficas     |
| GitHub Actions           | Integración continua básica con Maven                   |

---

## Requisitos previos

Para ejecutar el proyecto se necesita:

* Java 17
* Maven
* Git
* Navegador web actualizado
* Python 3, solo si se quiere ejecutar la analítica conectada a MySQL

Comprobar versiones:

```bash
java -version
mvn -version
python --version
```

---

## Ejecución local

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

## Usuario administrador de prueba

El inicializador de datos crea un usuario administrador para poder acceder al panel de administración en local:

```text
Correo: admin@retrogoal.com
Contraseña: admin123
```

Panel de administración:

```text
http://localhost:8080/admin
```

---

## Estructura del proyecto

```text
retrogoal/
├── .github/
│   └── workflows/
│       └── maven.yml
├── data/
│   ├── productos_retrogoal.csv
│   └── metricas_retrogoal_bd.csv        # Se genera al ejecutar el script Python
├── docs/
│   └── img/
│       ├── grafica_resumen_bd_retrogoal.png       # Se genera con Python
│       ├── grafica_camisetas_vendidas_por_dia.png # Se genera con Python
│       ├── grafica_pagos_registrados_por_dia.png  # Se genera con Python
│       └── grafica_top_camisetas_vendidas.png     # Se genera con Python
├── scripts/
│   ├── analisis_retrogoal_bd.py
│   └── requirements.txt
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

| Carpeta / archivo               | Uso                                  |
| ------------------------------- | ------------------------------------ |
| `.github/workflows/`            | Workflow de GitHub Actions           |
| `data/`                         | CSV de productos y CSV de métricas generado por Python |
| `docs/img/`                     | Gráficas generadas por el script Python y otras imágenes |
| `scripts/`                      | Scripts auxiliares en Python         |
| `src/main/java/`                | Código Java de la aplicación         |
| `src/main/resources/templates/` | Vistas Thymeleaf                     |
| `src/main/resources/static/`    | CSS, JS e imágenes estáticas         |
| `src/test/`                     | Pruebas del proyecto                 |
| `pom.xml`                       | Dependencias y configuración Maven   |

---

## Configuración de variables de entorno

Para utilizar Stripe y Google Maps se pueden configurar estas variables:

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

## Stripe Checkout

La aplicación utiliza Stripe Checkout en modo prueba. El usuario confirma el pedido desde el checkout y es redirigido a Stripe para simular el pago.

La aplicación no almacena datos bancarios, ya que el proceso de pago se gestiona desde Stripe.

---

## Google Maps

La página `/map` integra Google Maps JavaScript API para mostrar una ubicación ficticia de referencia de la tienda.

Si no se configura la variable `GOOGLE_MAPS_API_KEY`, la página puede mostrar un aviso en lugar del mapa.

---

## Internacionalización

La aplicación incluye soporte para:

* Español
* Inglés
* Francés

Los textos se gestionan mediante los archivos:

```text
messages.properties
messages_en.properties
messages_fr.properties
```

---

## Pruebas

Ejecutar las pruebas:

```bash
mvn test
```

Construir el proyecto:

```bash
mvn clean package
```

---

## GitHub Actions

El proyecto incluye un workflow básico de integración continua en:

```text
.github/workflows/maven.yml
```

Este workflow compila el proyecto y ejecuta las pruebas mediante Maven cuando se realizan cambios en el repositorio.

---

## Analítica con Python conectada a MySQL

El proyecto incluye un script de Python conectado directamente a la base de datos MySQL de RetroGoal. El archivo anterior basado en CSV fue sustituido por:

```text
scripts/analisis_retrogoal_bd.py
```

Este script lee los datos reales de las mismas tablas que usa la aplicación Spring Boot:

| Tabla | Qué analiza |
| --- | --- |
| `users` | Total de usuarios registrados |
| `products` | Productos del catálogo, stock y valor estimado del inventario |
| `orders` | Pedidos creados, pagos registrados e ingresos confirmados |
| `order_items` | Cantidad de camisetas vendidas y top de camisetas vendidas |

### Cómo funciona la gráfica de Python

La gráfica no está conectada al frontend ni se actualiza sola. Funciona así:

1. La aplicación Spring Boot guarda usuarios, productos, pedidos y líneas de pedido en MySQL.
2. El script `scripts/analisis_retrogoal_bd.py` se conecta a esa base de datos.
3. Ejecuta consultas SQL sobre `users`, `products`, `orders` y `order_items`.
4. Con los resultados genera un CSV de métricas y varias gráficas en `docs/img/`.
5. Para actualizar los datos hay que volver a ejecutar manualmente el script.

Por tanto, si se registra un nuevo usuario, se vende una camiseta o se confirma un pago, la gráfica se actualiza cuando se ejecuta:

```bash
python scripts/analisis_retrogoal_bd.py
```

### Qué campo usa como eje X

Para las gráficas por fecha se usa:

```text
orders.order_date
```

Ejemplos:

| Gráfica | Eje X | Eje Y |
| --- | --- | --- |
| Camisetas vendidas por día | `DATE(orders.order_date)` | `SUM(order_items.quantity)` |
| Pagos registrados por día | `DATE(orders.order_date)` | `COUNT(orders.id)` con estado pagado |
| Top camisetas vendidas | Nombre del producto desde `products.name` | `SUM(order_items.quantity)` |
| Resumen general | Métricas generales | Cantidades totales |

### Cómo interpreta los pagos

RetroGoal no tiene una tabla independiente llamada `payments`. El pago queda representado mediante el estado del pedido en la tabla `orders`.

El script considera como pagos registrados los pedidos con estos estados:

```text
PAID, SHIPPED, COMPLETED
```

La razón es que un pedido `PAID` ya fue pagado, y si posteriormente pasa a `SHIPPED` o `COMPLETED`, sigue siendo un pedido pagado.

### Archivos que genera

Al ejecutar el script se generan o actualizan estos archivos:

```text
data/metricas_retrogoal_bd.csv
docs/img/grafica_resumen_bd_retrogoal.png
docs/img/grafica_resumen_bd_retrogoal.jpg
docs/img/grafica_camisetas_vendidas_por_dia.png
docs/img/grafica_camisetas_vendidas_por_dia.jpg
docs/img/grafica_pagos_registrados_por_dia.png
docs/img/grafica_pagos_registrados_por_dia.jpg
docs/img/grafica_top_camisetas_vendidas.png
docs/img/grafica_top_camisetas_vendidas.jpg
```

### Instalación de dependencias Python

Desde la raíz del proyecto:

```bash
python -m pip install -r scripts/requirements.txt
```

También se pueden instalar manualmente:

```bash
python -m pip install matplotlib pymysql
```

### Ejecución

Primero debe estar funcionando MySQL con la base de datos `retrogoal`. Después:

```bash
python scripts/analisis_retrogoal_bd.py
```

El script reutiliza la configuración de `src/main/resources/application.properties`:

```text
spring.datasource.url
spring.datasource.username
spring.datasource.password
```

También permite sobrescribir la conexión con variables de entorno:

```text
RETROGOAL_DB_HOST
RETROGOAL_DB_PORT
RETROGOAL_DB_NAME
RETROGOAL_DB_USER
RETROGOAL_DB_PASSWORD
```

---

## PALETA DE COLORES DEL PROYECTO

Esta es la paleta principal propia de **RetroGoal**, extraída directamente del código del proyecto. Los colores aparecen principalmente en:

```text
src/main/resources/static/css/style.css
```

También aparece un color repetido en el footer:

```text
src/main/resources/templates/fragments/footer.html
```

El proyecto no tiene una paleta centralizada con variables CSS tipo `:root`; los colores están escritos directamente en el CSS y, en algún caso, en estilos del HTML.

### Paleta principal propia

| Uso | Color | HEX exacto |
| --- | --- | --- |
| Fondo general de la web | Azul/negro muy oscuro | `#0b0d17` |
| Navbar y footer | Negro puro | `#000000` |
| Texto principal claro | Blanco grisáceo Bootstrap | `#f8f9fa` |
| Blanco puro en botones | Blanco | `#ffffff` / `#fff` |
| Hover enlaces navbar/footer | Azul cian brillante | `#00c6ff` |
| Botón principal personalizado | Turquesa Bootstrap info oscuro | `#17a2b8` |
| Hover botón principal | Turquesa más oscuro | `#138496` |
| Fondo de cards/productos | Gris azulado oscuro | `#1e1f29` |
| Hero inicio, inicio gradiente | Negro casi puro | `#050505` |
| Hero inicio, final gradiente | Gris muy oscuro | `#1d1d1d` |
| Hero mapa, inicio gradiente | Azul noche | `#101522` |
| Hero mapa, final gradiente | Azul grisáceo oscuro | `#1e293b` |

### Transparencias usadas

| Uso | Valor exacto |
| --- | --- |
| Sombra cards al pasar el ratón | `rgba(0, 0, 0, 0.4)` |
| Borde superior del footer | `rgba(255,255,255,0.08)` |
| Sombra tarjetas/mapa | `rgba(0, 0, 0, 0.35)` |

Además de estos colores propios, el proyecto utiliza colores procedentes de Bootstrap mediante clases como `btn-primary`, `btn-success`, `btn-danger`, `btn-secondary`, `bg-dark`, `text-muted`, `alert-success` o `alert-danger`. Esos colores vienen de Bootstrap 5.3.3.

---

## Autor

Proyecto desarrollado por **Héctor Ramos Fernández** para el Proyecto Intermodular de Desarrollo de Aplicaciones Web.

Curso: **2º DAW**
Proyecto: **RetroGoal**

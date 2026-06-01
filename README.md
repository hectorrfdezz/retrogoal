# RetroGoal – Tienda web de camisetas de fútbol

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
| Python                   | Análisis auxiliar del inventario mediante CSV y gráfica |
| GitHub Actions           | Integración continua básica con Maven                   |

---

## Requisitos previos

Para ejecutar el proyecto se necesita:

* Java 17
* Maven
* Git
* Navegador web actualizado
* Python 3, solo si se quiere ejecutar el análisis auxiliar

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
│   └── productos_retrogoal.csv
├── docs/
│   └── img/
│       └── grafica_valor_inventario_por_equipo.jpg
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

| Carpeta / archivo               | Uso                                  |
| ------------------------------- | ------------------------------------ |
| `.github/workflows/`            | Workflow de GitHub Actions           |
| `data/`                         | CSV usado para el análisis auxiliar  |
| `docs/img/`                     | Gráficas e imágenes de documentación |
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

## Análisis auxiliar con Python

El proyecto incluye un pequeño script de Python para analizar datos del catálogo.

Archivos relacionados:

```text
scripts/analisis_productos.py
data/productos_retrogoal.csv
docs/img/grafica_valor_inventario_por_equipo.jpg
```

El script calcula el valor estimado del inventario por equipo usando el precio y el stock de cada producto.

Ejecutar el análisis:

```bash
python scripts/analisis_productos.py
```

Si falta `matplotlib`:

```bash
python -m pip install matplotlib
```

---

## Mejoras futuras

Algunas mejoras planteadas para futuras versiones son:

* Despliegue real en producción.
* Dominio y HTTPS.
* Migración definitiva a MySQL o PostgreSQL.
* Docker y Docker Compose.
* Webhooks avanzados de Stripe.
* Panel interno de analítica.
* Integración del análisis Python dentro del panel de administración.
* Sistema de valoraciones de productos.
* Mejoras adicionales de accesibilidad.

---

## Autor

Proyecto desarrollado por **Héctor Ramos Fernández** para el Proyecto Intermodular de Desarrollo de Aplicaciones Web.

Curso: **2º DAW**
Proyecto: **RetroGoal**

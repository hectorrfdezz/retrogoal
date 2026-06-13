from pathlib import Path
from urllib.parse import urlparse
import os
import re

import matplotlib

matplotlib.use("Agg")
import matplotlib.pyplot as plt
import pymysql


ROOT_DIR = Path(__file__).resolve().parents[1]
APP_PROPERTIES = ROOT_DIR / "src" / "main" / "resources" / "application.properties"
IMG_DIR = ROOT_DIR / "docs" / "img"

OUTPUT_PNG = IMG_DIR / "grafica_resumen_bd_retrogoal.png"
OUTPUT_JPG = IMG_DIR / "grafica_resumen_bd_retrogoal.jpg"


def leer_application_properties():
    """Lee la configuración de la base de datos desde application.properties."""
    propiedades = {}

    if not APP_PROPERTIES.exists():
        return propiedades

    for linea in APP_PROPERTIES.read_text(encoding="utf-8").splitlines():
        linea = linea.strip()

        if not linea or linea.startswith("#") or "=" not in linea:
            continue

        clave, valor = linea.split("=", 1)
        propiedades[clave.strip()] = valor.strip()

    return propiedades


def resolver_placeholder(valor):
    """
    Resuelve valores tipo ${MYSQL_USER:root}.
    Si existe la variable de entorno, la usa.
    Si no existe, usa el valor por defecto.
    """
    patron = re.fullmatch(r"\$\{([^:}]+):?([^}]*)\}", valor)

    if not patron:
        return valor

    variable_entorno = patron.group(1)
    valor_defecto = patron.group(2)

    return os.getenv(variable_entorno, valor_defecto)


def obtener_configuracion_bd():
    """
    Obtiene los datos de conexión a MySQL desde application.properties.
    Si no encuentra algo, usa valores típicos de desarrollo local.
    """
    propiedades = leer_application_properties()

    host = "localhost"
    puerto = 3306
    nombre_bd = "retrogoal"
    usuario = "root"
    password = ""

    url_bd = resolver_placeholder(propiedades.get("spring.datasource.url", ""))
    usuario = resolver_placeholder(propiedades.get("spring.datasource.username", usuario))
    password = resolver_placeholder(propiedades.get("spring.datasource.password", password))

    if url_bd.startswith("jdbc:"):
        url_bd = url_bd.replace("jdbc:", "", 1)

    if url_bd:
        url_parseada = urlparse(url_bd)

        if url_parseada.hostname:
            host = url_parseada.hostname

        if url_parseada.port:
            puerto = url_parseada.port

        if url_parseada.path and url_parseada.path != "/":
            nombre_bd = url_parseada.path.replace("/", "")

    return host, puerto, nombre_bd, usuario, password


def conectar_bd():
    """Abre la conexión con la base de datos MySQL de RetroGoal."""
    host, puerto, nombre_bd, usuario, password = obtener_configuracion_bd()

    conexion = pymysql.connect(
        host=host,
        port=puerto,
        user=usuario,
        password=password,
        database=nombre_bd,
        charset="utf8mb4",
        cursorclass=pymysql.cursors.DictCursor,
    )

    return conexion


def obtener_resumen_bd(conexion):
    """
    Consulta datos básicos de la base de datos.

    Esta gráfica resume el estado general del proyecto:
    - usuarios registrados;
    - productos del catálogo;
    - unidades totales en stock;
    - pedidos registrados.
    """
    consultas = {
        "Usuarios registrados": "SELECT COUNT(*) AS valor FROM users",
        "Productos en catálogo": "SELECT COUNT(*) AS valor FROM products",
        "Unidades en stock": "SELECT COALESCE(SUM(stock), 0) AS valor FROM products",
        "Pedidos registrados": "SELECT COUNT(*) AS valor FROM orders",
    }

    resumen = {}

    with conexion.cursor() as cursor:
        for nombre_metrica, sql in consultas.items():
            cursor.execute(sql)
            fila = cursor.fetchone()
            resumen[nombre_metrica] = float(fila["valor"] or 0)

    return resumen


def crear_grafica_resumen(resumen):
    """Genera una única gráfica de barras con el resumen general de la base de datos."""
    IMG_DIR.mkdir(parents=True, exist_ok=True)

    etiquetas = list(resumen.keys())
    valores = list(resumen.values())

    fig, ax = plt.subplots(figsize=(10, 6))

    ax.bar(etiquetas, valores)
    ax.set_title("Resumen general de la base de datos RetroGoal")
    ax.set_xlabel("Métrica")
    ax.set_ylabel("Cantidad")
    ax.tick_params(axis="x", rotation=20)

    for posicion, valor in enumerate(valores):
        ax.text(
            posicion,
            valor,
            f"{valor:.0f}",
            ha="center",
            va="bottom"
        )

    fig.tight_layout()
    fig.savefig(OUTPUT_PNG, dpi=140, bbox_inches="tight")
    fig.savefig(OUTPUT_JPG, dpi=140, bbox_inches="tight")
    plt.close(fig)


def main():
    print("Generando gráfica resumen de la base de datos RetroGoal...")

    try:
        conexion = conectar_bd()
    except Exception as error:
        print("No se pudo conectar con MySQL.")
        print("Comprueba que MySQL está iniciado y que existe la base de datos retrogoal.")
        print(f"Detalle técnico: {error}")
        return

    try:
        resumen = obtener_resumen_bd(conexion)
        crear_grafica_resumen(resumen)

        print("Gráfica generada correctamente:")
        print(f"- {OUTPUT_PNG}")
        print(f"- {OUTPUT_JPG}")

        print()
        print("Datos usados en la gráfica:")
        for metrica, valor in resumen.items():
            print(f"- {metrica}: {valor:.0f}")

    finally:
        conexion.close()


if __name__ == "__main__":
    main()
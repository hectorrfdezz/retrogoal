from pathlib import Path
import csv
from collections import defaultdict

import matplotlib
matplotlib.use("Agg")  # Evita problemas de renderizado en algunos entornos

import matplotlib.pyplot as plt


ROOT_DIR = Path(__file__).resolve().parents[1]
DATA_DIR = ROOT_DIR / "data"
IMG_DIR = ROOT_DIR / "docs" / "img"

CSV_PATH = DATA_DIR / "productos_retrogoal.csv"
OUTPUT_PNG = IMG_DIR / "grafica_valor_inventario_por_equipo.png"
OUTPUT_JPG = IMG_DIR / "grafica_valor_inventario_por_equipo.jpg"


PRODUCTOS_EJEMPLO = [
    {"name": "Camiseta Real Madrid 2014", "team": "Real Madrid", "season": "2013/14", "price": "49.99", "stock": "8"},
    {"name": "Camiseta Barcelona 2011", "team": "FC Barcelona", "season": "2010/11", "price": "54.99", "stock": "6"},
    {"name": "Camiseta Milan 2007", "team": "AC Milan", "season": "2006/07", "price": "59.99", "stock": "5"},
    {"name": "Camiseta Manchester United 2008", "team": "Manchester United", "season": "2007/08", "price": "57.99", "stock": "7"},
    {"name": "Camiseta Arsenal 2004", "team": "Arsenal", "season": "2003/04", "price": "52.99", "stock": "4"},
    {"name": "Camiseta Juventus 1998", "team": "Juventus", "season": "1997/98", "price": "44.99", "stock": "9"},
    {"name": "Camiseta Real Madrid 2002", "team": "Real Madrid", "season": "2001/02", "price": "64.99", "stock": "3"},
    {"name": "Camiseta FC Barcelona 2009", "team": "FC Barcelona", "season": "2008/09", "price": "61.99", "stock": "5"},
    {"name": "Camiseta Inter 2010", "team": "Inter de Milán", "season": "2009/10", "price": "58.99", "stock": "4"},
    {"name": "Camiseta Liverpool 2005", "team": "Liverpool", "season": "2004/05", "price": "55.99", "stock": "6"},
]


def preparar_carpetas():
    DATA_DIR.mkdir(parents=True, exist_ok=True)
    IMG_DIR.mkdir(parents=True, exist_ok=True)


def crear_csv_si_no_existe():
    if CSV_PATH.exists():
        return

    with CSV_PATH.open("w", newline="", encoding="utf-8") as file:
        campos = ["name", "team", "season", "price", "stock"]
        writer = csv.DictWriter(file, fieldnames=campos)
        writer.writeheader()
        writer.writerows(PRODUCTOS_EJEMPLO)


def leer_productos():
    productos = []

    with CSV_PATH.open("r", encoding="utf-8") as file:
        reader = csv.DictReader(file)

        for row in reader:
            try:
                productos.append({
                    "name": row["name"],
                    "team": row["team"],
                    "season": row["season"],
                    "price": float(row["price"]),
                    "stock": int(row["stock"]),
                })
            except (ValueError, KeyError):
                print(f"Fila ignorada por datos incorrectos: {row}")

    return productos


def generar_grafica_valor_inventario(productos):
    valor_por_equipo = defaultdict(float)

    for producto in productos:
        valor_inventario = producto["price"] * producto["stock"]
        valor_por_equipo[producto["team"]] += valor_inventario

    equipos = list(valor_por_equipo.keys())
    valores = list(valor_por_equipo.values())

    fig, ax = plt.subplots(figsize=(11, 6), facecolor="white")
    ax.set_facecolor("white")

    ax.bar(equipos, valores)

    ax.set_title("Valor estimado del inventario por equipo", color="black")
    ax.set_xlabel("Equipo", color="black")
    ax.set_ylabel("Valor estimado del stock (€)", color="black")

    ax.tick_params(axis="x", rotation=35, colors="black")
    ax.tick_params(axis="y", colors="black")

    for spine in ax.spines.values():
        spine.set_color("black")

    plt.tight_layout()

    fig.savefig(
        OUTPUT_PNG,
        format="png",
        dpi=120,
        bbox_inches="tight",
        facecolor="white"
    )

    fig.savefig(
        OUTPUT_JPG,
        format="jpg",
        dpi=120,
        bbox_inches="tight",
        facecolor="white"
    )

    plt.close(fig)


def mostrar_resumen(productos):
    total_productos = len(productos)
    total_unidades = sum(producto["stock"] for producto in productos)
    valor_total = sum(producto["price"] * producto["stock"] for producto in productos)

    print("Análisis auxiliar de RetroGoal")
    print("--------------------------------")
    print(f"Productos analizados: {total_productos}")
    print(f"Unidades totales en stock: {total_unidades}")
    print(f"Valor estimado del inventario: {valor_total:.2f} €")
    print(f"Gráfica PNG generada en: {OUTPUT_PNG}")
    print(f"Gráfica JPG generada en: {OUTPUT_JPG}")


def main():
    preparar_carpetas()
    crear_csv_si_no_existe()

    productos = leer_productos()

    if not productos:
        print("No se encontraron productos para analizar.")
        return

    generar_grafica_valor_inventario(productos)
    mostrar_resumen(productos)


if __name__ == "__main__":
    main()
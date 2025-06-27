#Automatización Mercado Libre con Java y Selenium

Este proyecto automatiza la búsqueda de productos en [MercadoLibre México](https://www.mercadolibre.com.mx), aplicando filtros y ordenamiento de resultados, 
y guardando los datos en un archivo Excel con capturas de pantalla.

---

## 🚀 ¿Qué hace este proyecto?

1. Ingresa a MercadoLibre y selecciona país (México).
2. Busca un producto (ej. "Playstation 5").
3. Cierra el banner de cookies si aparece.
4. Aplica filtros: "Nuevo" y "Local".(Se aplica filtro Local ya que no se mostros el filtro "Ubicación")
5. Ordena los resultados por "Mayor precio".
6. Extrae los primeros 5 productos (nombre y precio).
7. Guarda la información en un archivo ".xlsx".
8. Captura pantallas del flujo.
-------------------------------------------------------------------------------
## Tecnologías utilizadas

- Java 17  
- Selenium WebDriver  
-  TestNG  
- Apache POI (para Excel)  
- Maven  
- ChromeDriver  

----------------------------------------------------------------------------------

## Configuración antes de ejecutar
- Crear carpeta llamada "resources" dentro de la raíz del proyecto
- Dentro de la carpeta "resources" crea dos subcarpetas llamadas "screenshots" y  "resultados"

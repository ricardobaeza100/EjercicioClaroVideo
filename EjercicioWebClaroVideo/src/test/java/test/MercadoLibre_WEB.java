package test;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

public class MercadoLibre_WEB {

    WebDriver driver;
    WebDriverWait wait;
    // Configuración del navegador
    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(priority = 1)//Abre la pagina principal
    public void mercadoLibre() throws IOException, InterruptedException {
        driver.get("https://www.mercadolibre.com/");
        tomarCaptura("01_home");
        Thread.sleep(1000);


        WebElement buscaPaisMx =
                driver.findElement(By.id("MX"));//Selecciona el país
        buscaPaisMx.click();

       //busca el producto "PlaySation 5"
        WebElement buscarPlay =
                driver.findElement(By.id("cb1-edit"));
        buscarPlay.sendKeys("Playsation 5");
        buscarPlay.submit();

        tomarCaptura("02_busqueda");


        //Hay un elemento Cookies que no permite realizar el filtro, Cierra el banner que aparece
        try {
            WebDriverWait waitCookies = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement cerrarCookies = waitCookies.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("button.cookie-consent-banner-opt-out__action")));
            cerrarCookies.click();
            System.out.println("Banner de cookies cerrado");
        } catch (Exception e) {
            System.out.println("No apareció el banner de cookies");
        }



       //Aplica el filtro "nuevo"
        WebElement filtroNuevo = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//aside//span[text()='Nuevo']")));
        filtroNuevo.click();


        tomarCaptura("03_filtro_nuevo");

        Thread.sleep(3000);

        //Aplica el filtro "Local"( se aplica este filtro ya que "ubicación" no se me mostro
        WebElement filtroLocal = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//aside//span[text()='Local']")));
        filtroLocal.click();


        tomarCaptura("04_filtro_local");
        Thread.sleep(4000);

        // Hacer Clic en el botón para abrir el menu
        WebElement ordenaMenu =
                wait.until(ExpectedConditions.elementToBeClickable(
                        By.cssSelector("button.andes-dropdown__trigger")));
        ordenaMenu.click();

       //Espera que se muestra la opción y filtra por "Mayor precio"
        WebElement mayorPrecio =
                wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(text(),'Mayor precio')]")));
        mayorPrecio.click();

        Thread.sleep(3000);
        tomarCaptura("05_ordenado_precio");


        // Extrae la lista de los productos mostrados
        List<WebElement> productos = driver.findElements(By.cssSelector("li.ui-search-layout__item"));
        Workbook workbook = new XSSFWorkbook();//Crea el excel donde se guardaran los resulados
        Sheet sheet = workbook.createSheet("Productos");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Nombre");
        header.createCell(1).setCellValue("Precio");

        //Muestra en consola los primero 5 productos mostrados
        System.out.println("\n Primeros 5 productos ordenados por mayor precio:");
        for (int i = 0; i < Math.min(5, productos.size()); i++) {
            WebElement prod = productos.get(i);
            //Extrae el nombre del producto
            String nombre = prod.findElement(By.cssSelector("h3.poly-component__title-wrapper")).getText();
            //Extrae el precio del producto
            String precio = prod.findElement(By.cssSelector(".andes-money-amount__fraction")).getText();
            //Muestra resultado en consola
            System.out.println((i + 1) + ". " + nombre + " - $" + precio);

            //Guarda el Excel
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(nombre);
            row.createCell(1).setCellValue(precio);
        }
        //Guarda el Excel en la carpeta
        try (FileOutputStream fileOut = new FileOutputStream("resources/resultados/resultados.xlsx")) {
            workbook.write(fileOut);
        }
        workbook.close();
    }
    // Funcion para tomar las cpaturas
    public void tomarCaptura(String nombre) throws IOException {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Files.copy(src.toPath(), Paths.get("resources/screenshots/" + nombre + ".png"));
    }


    //Cierra el navegador
    @AfterClass
    public void CerraNavegador() {
        if (driver != null) {
            driver.quit();
        }
    }
}
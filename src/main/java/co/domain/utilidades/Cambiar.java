package co.domain.utilidades;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import java.util.Set;

public class Cambiar extends PageObject {

    public static void ventana(String window, WebDriver driver) {
        String ventanaPrincipal = driver.getWindowHandle();
        switch (window) {
            case "Siguiente":
                Set<String> ventanas = driver.getWindowHandles();
                for (String ventana : ventanas) {
                    if (!ventana.equals(ventanaPrincipal)) {
                        driver.switchTo().window(ventana);
                        Dimension dimension = new Dimension(1366,1200);
                        driver.manage().window().setSize(dimension);
                    }
                }
                break;
            case "Principal":
                driver.switchTo().window(ventanaPrincipal);
                driver.manage().window().maximize();
                break;
            default:
                break;
        }
    }

    public static void framePrincipal(WebDriver driver) {
        driver.switchTo().parentFrame();
    }

    public static void frameSecundario(By frameId, WebDriver driver) {
        driver.switchTo().frame(driver.findElement(frameId));
    }

}

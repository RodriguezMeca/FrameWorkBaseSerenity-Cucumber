package co.domain.constantes;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class PaginaLogueo extends PageObject {
    public static final String URL_GOOGLE = "https://www.google.com/";
    public static final Target TXT_BUSQUEDA = Target.the("Caja de texto para buscar")
            .located(By.xpath("//textarea[@id='APjFqb']"));
    public static final Target BTN_BUTTON = Target.the("Caja de texto para buscar")
            .located(By.xpath("//div[@class='lJ9FBc']//input[@name='btnK']"));

}

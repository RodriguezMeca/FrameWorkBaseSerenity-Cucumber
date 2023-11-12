package co.domain.constantes;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class PaginaBusqueda extends PageObject {

    public static final Target MSN_RESULT = Target.the("Busqueda Colombia")
            .located(By.xpath("//span[@role='heading']"));
}

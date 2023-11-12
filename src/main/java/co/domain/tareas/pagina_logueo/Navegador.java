package co.domain.tareas.pagina_logueo;

import co.domain.constantes.PaginaLogueo;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;
import net.thucydides.core.annotations.Step;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class Navegador implements Task {
    public static Performable inicializado() {
        return instrumented(Navegador.class);
    }

    @Override
    @Step("{0} Abre el navegador")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Open.url(PaginaLogueo.URL_GOOGLE));
    }
}

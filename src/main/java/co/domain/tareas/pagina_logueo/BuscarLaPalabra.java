package co.domain.tareas.pagina_logueo;

import co.domain.interacciones.Click;
import co.domain.interacciones.Escribir;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.annotations.Step;

import static co.domain.constantes.PaginaLogueo.*;

public class BuscarLaPalabra implements Task {
    private final String texto;

    public BuscarLaPalabra(String texto) {
        this.texto = texto;
    }

    public static Performable texto(String texto) {
        return Instrumented.instanceOf(BuscarLaPalabra.class).withProperties(texto);
    }
    @Override
    @Step("{0} Ingresa datos")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Escribir.textoYObjeto(texto,TXT_BUSQUEDA));
        actor.attemptsTo(Click.objeto(BTN_BUTTON));
    }
}

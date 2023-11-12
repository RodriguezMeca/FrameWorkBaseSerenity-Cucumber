package co.domain.interacciones;

import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

public class Validar implements Question<Boolean> {

    private final String value;
    private final Integer option;
    private final Target elemento;

    public Validar(String value, Target elemento, Integer option) {
        this.value = value;
        this.elemento = elemento;
        this.option = option;
    }

    public static Validar visible(String value, Target elemento) {
        return Instrumented.instanceOf(Validar.class).withProperties(value,elemento,1);
    }

    public static Validar texto(String value, Target elemento) {
        return Instrumented.instanceOf(Validar.class).withProperties(value,elemento,2);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        if (option == 1) {
            return elemento.resolveFor(actor).isVisible();
        } else {
            return value.contains(elemento.resolveFor(actor).getText());
        }
    }
}

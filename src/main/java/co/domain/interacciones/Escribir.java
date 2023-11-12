package co.domain.interacciones;

import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;


public class Escribir implements Interaction {

    private final String text;
    private final Target element;

    public Escribir(String text, Target element) {
        this.text = text;
        this.element = element;
    }

    public static Performable textoYObjeto(String text, Target element) {
        return Instrumented.instanceOf(Escribir.class).withProperties(text,element);
    }


    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(element, WebElementStateMatchers.isVisible())
                        .forNoMoreThan(15).seconds(),
                Enter.theValue(text).into(element)
        );
    }

}

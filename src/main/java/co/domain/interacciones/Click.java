package co.domain.interacciones;

import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;

public class Click implements Interaction {

    private final Target element;

    public Click(Target element) {
        this.element = element;
    }

    public static Performable objeto(Target element) {
        return Instrumented.instanceOf(Click.class).withProperties(element);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(element, WebElementStateMatchers.isVisible())
                        .forNoMoreThan(15).seconds(),
                net.serenitybdd.screenplay.actions.Click.on(element)
        );
    }

}

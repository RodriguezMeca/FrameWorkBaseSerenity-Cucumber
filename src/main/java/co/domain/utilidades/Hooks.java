package co.domain.utilidades;

import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.thucydides.core.annotations.Managed;
import org.openqa.selenium.WebDriver;

import static net.serenitybdd.screenplay.actors.OnStage.setTheStage;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;

public class Hooks {
    static Actor actor;

    @Managed
    static WebDriver driver;

    private Hooks() {
    }

    public static Actor actor(String user) {
        setTheStage(new OnlineCast());
        return theActorCalled(user).can(BrowseTheWeb.with(getDriver()));
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static void showReport(String title, String message) {
        Serenity.recordReportData().withTitle(title).andContents(message);
    }

}

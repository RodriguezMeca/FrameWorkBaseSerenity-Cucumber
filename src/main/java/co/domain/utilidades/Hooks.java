package co.domain.utilidades;

import io.cucumber.java.AfterStep;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.thucydides.core.annotations.Managed;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


public class Hooks {

    private static Scenario scenario;
    static Actor actor;

    @Managed
    static WebDriver driver;

    private Hooks() {
    }

    public static Actor actorInit(String user) {
        actor = Actor.named(user);
        actor.can(BrowseTheWeb.with(driver));
        return actor;
    }
    @BeforeStep
    public void beforeScenario(Scenario scenario) {
        setScenario(scenario);
    }

    @AfterStep
    public static void takeScreenShot(Scenario scenario) {
        if (scenario.isFailed()) {
            scenario.log("El escenario fallo, refiérase a la imagen");
            final byte [] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot,"image/png","image");
        }
    }

    public static void logValueToReport(String message, int valueToLog) {
        Scenario scenario = getScenario();
        scenario.log(message + valueToLog);
    }


    public static Scenario getScenario() {
        return scenario;
    }

    public static void setScenario(Scenario currentScenario) {
        scenario = currentScenario;
    }

}

package co.domain.utilidades;

import org.junit.jupiter.api.*;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.thucydides.core.annotations.Managed;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {

    private static final Logger LOGGER = LoggerFactory.getLogger(Hooks.class);
    static Actor actor;

    @Managed
    static WebDriver driver;

    private Hooks() {
    }

    @BeforeEach
    public void testInit() {
        LOGGER.info("BEFORE EACH TEST!!!");
    }

    public static Actor actorInit(String user) {
        actor = Actor.named(user);
        actor.can(BrowseTheWeb.with(driver));
        return actor;
    }

    @AfterEach
    public void closeTest() {
        LOGGER.info("AFTER EACH TEST!!!");
    }

}

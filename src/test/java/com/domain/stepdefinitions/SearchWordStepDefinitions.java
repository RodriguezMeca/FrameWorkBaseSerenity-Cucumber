package com.domain.stepdefinitions;

import co.domain.interacciones.Validar;
import co.domain.tareas.pagina_logueo.BuscarLaPalabra;
import co.domain.tareas.pagina_logueo.Navegador;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.thucydides.core.annotations.Managed;
import org.openqa.selenium.WebDriver;

import static co.domain.constantes.PaginaBusqueda.MSN_RESULT;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.setTheStage;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;

public class SearchWordStepDefinitions {

    private static final String analista = "analista";

    @Managed
    WebDriver driver;

    @Before
    public void configuracionInicial(){
        setTheStage(new OnlineCast());
        theActorCalled(analista).can(BrowseTheWeb.with(driver));
    }

    @Given("^enter to the website Google$")
    public void navigateToGoogle() {
        theActorCalled(analista).has(Navegador.inicializado());
    }

    @When("^write to word Colombia into search$")
    public void writeWord() {
        theActorCalled(analista).attemptsTo(BuscarLaPalabra.texto("Colombia"));
    }

    @Then("^validated to results$")
    public void validate() {
        theActorCalled(analista).should(seeThat(Validar.visible("si",MSN_RESULT)));
    }

}

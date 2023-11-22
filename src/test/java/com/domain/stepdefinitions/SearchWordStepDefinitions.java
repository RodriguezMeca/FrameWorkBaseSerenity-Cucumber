package com.domain.stepdefinitions;

import co.domain.interacciones.Validar;
import co.domain.tareas.pagina_logueo.BuscarLaPalabra;
import co.domain.tareas.pagina_logueo.Navegador;
import co.domain.utilidades.Hooks;
import io.cucumber.java.en.*;

import static co.domain.constantes.PaginaBusqueda.MSN_RESULT;
import static co.domain.utilidades.Hooks.actor;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;


public class SearchWordStepDefinitions {


    @Given("^enter to the website Google$")
    public void navigateToGoogle() {
        actor("Cliente").has(Navegador.inicializado());
    }

    @When("^write to word Ecuador into search$")
    public void writeWord() {
        actor("Cliente").attemptsTo(BuscarLaPalabra.texto("Ecuador"));
    }


    @Then("^validated to results$")
    public void validate() {
        actor("Cliente").should(seeThat(Validar.visible("si",MSN_RESULT)));
        actor("Cliente").should(seeThat(Validar.texto("Ecuador",MSN_RESULT)));
        Hooks.showReport("Result",MSN_RESULT.resolveFor(actor("Cliente")).getText());
    }

}

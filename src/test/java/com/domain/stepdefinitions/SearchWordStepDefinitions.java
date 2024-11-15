package com.domain.stepdefinitions;

import co.domain.interacciones.Validar;
import co.domain.tareas.pagina_logueo.BuscarLaPalabra;
import co.domain.tareas.pagina_logueo.Navegador;
import io.cucumber.java.en.*;
import net.serenitybdd.screenplay.Actor;

import static co.domain.constantes.PaginaBusqueda.MSN_RESULT;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

public class SearchWordStepDefinitions {


    @Given("{actor} enter to the website Google")
    public void navigateToGoogle(Actor actor) {
        actor.has(Navegador.inicializado());
    }

    @When("{actor} write to word Ecuador into search")
    public void writeWord(Actor actor) {
        actor.attemptsTo(BuscarLaPalabra.texto("Ecuador"));
    }

    @Then("{actor} validated to results")
    public void validate(Actor actor) {
        actor.should(seeThat(Validar.texto("Ecuador",MSN_RESULT)));

    }

}

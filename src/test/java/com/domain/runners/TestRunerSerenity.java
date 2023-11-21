package com.domain.runners;

import co.domain.interacciones.Validar;
import co.domain.tareas.pagina_logueo.BuscarLaPalabra;
import co.domain.tareas.pagina_logueo.Navegador;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static co.domain.constantes.PaginaBusqueda.MSN_RESULT;
import static co.domain.utilidades.Hooks.actorInit;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

@ExtendWith(SerenityJUnit5Extension.class)
class TestRunerSerenity {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestRunerSerenity.class);

    @Test
    @DisplayName("Search word in Google")
    void testSearch() {
        actorInit("Cliente").has(Navegador.inicializado());
        actorInit("Cliente").attemptsTo(BuscarLaPalabra.texto("Colombia"));
        actorInit("Cliente").should(seeThat(Validar.visible("si",MSN_RESULT)));
        actorInit("Cliente").should(seeThat(Validar.texto("Colombia",MSN_RESULT)));
    }

}

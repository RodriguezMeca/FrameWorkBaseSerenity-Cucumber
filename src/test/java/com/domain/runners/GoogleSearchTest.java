package com.domain.runners;

import co.domain.interacciones.Validar;
import co.domain.tareas.pagina_logueo.BuscarLaPalabra;
import co.domain.tareas.pagina_logueo.Navegador;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.annotations.CastMember;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static co.domain.constantes.PaginaBusqueda.MSN_RESULT;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

@ExtendWith(SerenityJUnit5Extension.class)
class GoogleSearchTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleSearchTest.class);
    @CastMember(name = "Cliente")
    Actor cliente;

    @BeforeEach
    public void openDriver() {
        LOGGER.info("BEFORE EACH TEST!!!");
    }

    @Test
    @DisplayName("Search word in Google")
    void testSearch() {
        cliente.has(Navegador.inicializado());
        cliente.attemptsTo(BuscarLaPalabra.texto("Colombia"));
        cliente.should(seeThat(Validar.visible("si",MSN_RESULT)));
        cliente.should(seeThat(Validar.texto("Colombia",MSN_RESULT)));
    }

    @AfterEach
    public void cerrarDriver() {
        LOGGER.info("AFTER EACH TEST!!!");
    }

}

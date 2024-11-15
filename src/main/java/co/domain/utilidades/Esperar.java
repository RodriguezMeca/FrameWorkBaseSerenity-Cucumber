package co.domain.utilidades;

import net.serenitybdd.core.pages.PageObject;

public class Esperar extends PageObject {

	public static void unMomento(int tiempoEspera) {
		int tiempoEsperaSegundos = 1000*tiempoEspera;
		new net.serenitybdd.model.time.InternalSystemClock().pauseFor(tiempoEsperaSegundos);
	}

}
package co.domain.utilidades;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.time.InternalSystemClock;

public class Esperar extends PageObject {

	public static void unMomento(int tiempoEspera) {
		int tiempoEsperaSegundos = 1000*tiempoEspera;
		new InternalSystemClock().pauseFor(tiempoEsperaSegundos);
	}

}
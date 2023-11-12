package co.domain.utilidades;

import net.serenitybdd.core.pages.PageObject;

import java.text.Normalizer;

public class Normalizar extends PageObject {

    public static String textoNormalizado(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }

}

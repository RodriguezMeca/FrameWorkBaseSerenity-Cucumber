# Comenzando con Serenity y Cucumber

Serenity BDD es una biblioteca que facilita la escritura de pruebas automatizadas de aceptación de alta calidad, con potentes funciones de informes y documentación viva. Ofrece un fuerte soporte tanto para pruebas web con Selenium como para pruebas de API con RestAssured.

Serenity fomenta un buen diseño de automatización de pruebas y admite varios patrones de diseño, incluyendo los clásicos objetos de página (Page Objects), el enfoque más reciente de Objetos de Página Ligeros/Clases de Acción, y el patrón más sofisticado y flexible de Screenplay.

La versión más reciente de Serenity es compatible con Cucumber 6.x.

## Proyecto de inicio
El mejor punto de partida para usar Serenity y Cucumber es clonar o descargar el proyecto de inicio en GitHub (https://github.com/RodriguezMeca/FrameWorkBaseSerenity-Cucumber). Este proyecto ofrece una configuración básica junto con pruebas de muestra y clases de soporte. Utiliza un enfoque más clásico, con clases de acción y objetos de página ligeros implementando el patrón Screenplay.

### Estructura del directorio del proyecto
El proyecto tiene scripts de construcción Gradle, y sigue la estructura de directorios estándar utilizada en la mayoría de los proyectos de Serenity:
```Gherkin
src
+ main
  + co
    + domain
      + constantes
      |    PaginaBusqueda.java
      |    PaginaLogueo.java
      + interacciones
      |    Click.java
      |    Escribir.java
      |    Validar.java
      + tareas
          + paginalogueo
      |       BuscarPalabra.java
      |       Navegador.java
      + utilidades
      |    Cambiar.java
      |    Esperar.java
      |    Normalizar.java
+ test
    + java
      + co
        + domain
          + runners
          |   TestRunner.java
          + stepDefinitions
          |   ParameterDefinitions.java
          |   SearchWordStepDefinitions.java
    + resources
        + features
        |   SearchGoogle.feature
        - logback-test.xml
        - serenity.conf
```
Serenity 2.2.13 introdujo la integración con WebDriverManager para descargar los binarios de WebDriver.

## El escenario de muestra
Ambas versiones del proyecto de ejemplo utilizan el mismo escenario de muestra de Cucumber. En este escenario, Sergey (a quien le gusta buscar cosas) realiza una búsqueda en internet:

```Gherkin
@SearchGoogle
Feature: Search into Google

  Scenario: Search into Google to Word Ecuador
    Given Omar enter to the website Google
    When he write to word Ecuador into search
    Then he validated to results
```

### Implementación de Screenplay
El código de muestra en la rama principal utiliza el patrón Screenplay, que describe las pruebas en términos de actores y las tareas que realizan. Las tareas se representan como objetos realizados por un actor, en lugar de métodos. Esto las hace más flexibles y componibles, aunque un poco más verbosas. Aquí hay un ejemplo:

```Java
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
```
Ahora bien, para instanciar de manera centralizada y que no se genere duplicidad en código, se utiliza una clase de configuracion inicial (`ParameterDefinitions`), de donde se extrae el `Actor` y el `driver` a utilizar.
```Java
public class ParameterDefinitions {

    @ParameterType(".*")
    public Actor actor(String actorName) {
        return OnStage.theActorCalled(actorName);
    }

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }
}
```

Las clases de Screenplay enfatizan componentes reutilizables y un estilo declarativo muy legible, mientras que los Objetos de Página Ligeros y las Clases de Acción optan por un estilo más imperativo.

La clase `Navegador` es responsable de abrir la página principal de Google:

```Java
public class Navegador implements Task {
    public static Performable inicializado() {
        return instrumented(Navegador.class);
    }

    @Override
    @Step("{0} Abre el navegador")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Open.url(PaginaLogueo.URL_GOOGLE));
    }
}
```
La clase `BuscarLaPalabra` realiza la búsqueda:

```Java
public class BuscarLaPalabra implements Task {
    private final String texto;

    public BuscarLaPalabra(String texto) {
        this.texto = texto;
    }

    public static Performable texto(String texto) {
        return Instrumented.instanceOf(BuscarLaPalabra.class).withProperties(texto);
    }
    @Override
    @Step("{0} Ingresa datos")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Escribir.textoYObjeto(texto,TXT_BUSQUEDA));
        actor.attemptsTo(Click.objeto(BTN_BUTTON));
    }
}
```

Hacemos una pausa acá, para decir que las clase Click y Escribir, lo que hacen es utilizar las propias librerías de Serenity, pero implementando esperas explícitas:
#### Clase Click
```Java
public class Click implements Interaction {

    private final Target element;

    public Click(Target element) {
        this.element = element;
    }

    public static Performable objeto(Target element) {
        return Instrumented.instanceOf(Click.class).withProperties(element);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(element, WebElementStateMatchers.isVisible())
                        .forNoMoreThan(15).seconds(),
                net.serenitybdd.screenplay.actions.Click.on(element)
        );
    }
}
```
#### Clase Escribir
```Java
public class Escribir implements Interaction {

    private final String text;
    private final Target element;

    public Escribir(String text, Target element) {
        this.text = text;
        this.element = element;
    }

    public static Performable textoYObjeto(String text, Target element) {
        return Instrumented.instanceOf(Escribir.class).withProperties(text,element);
    }


    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(element, WebElementStateMatchers.isVisible())
                        .forNoMoreThan(15).seconds(),
                Enter.theValue(text).into(element)
        );
    }
}
```
En Screenplay, los localizadores se mantienen en objetos ligeros de página o componente, como este:

```Java
public class PaginaBusqueda extends PageObject {
    public static final Target MSN_RESULT = Target.the("search Ecuador")
            .located(By.xpath("//div[@role='heading'][normalize-space()='Ecuador']"));
}
```
```Java
public class PaginaLogueo extends PageObject {
    public static final String URL_GOOGLE = "https://www.google.com/";
    public static final Target TXT_BUSQUEDA = Target.the("Caja de texto para buscar")
            .located(By.xpath("//textarea[@id='APjFqb']"));
    public static final Target BTN_BUTTON = Target.the("Caja de texto para buscar")
            .located(By.xpath("//div[@class='lJ9FBc']//input[@name='btnK']"));
}
```
El DSL de Screenplay es rico y flexible, ideal para equipos que trabajan en grandes proyectos de automatización de pruebas con muchos miembros y que se sientan cómodos con Java y patrones de diseño.

## Ejecutando las pruebas
Para ejecutar el proyecto de muestra, puedes correr la clase `TestRunner` o ejecutar `gradle test` desde la línea de comandos.

Por defecto, las pruebas se ejecutarán en Chrome. Puedes ejecutarlas en Firefox sobrescribiendo la propiedad driver, por ejemplo:

```Bash
$ gradle clean test -Pdriver=firefox
```

## Generando los informes
Los informes de Serenity se generan al final de todas las pruebas mediante el serenity-gradle-plugin. Los resultados se registrarán en el directorio target/site/serenity.

## Configuración simplificada de WebDriver y otros extras de Serenity
Los proyectos de ejemplo utilizan algunas características de Serenity que facilitan la configuración de las pruebas. En particular, Serenity usa el archivo serenity.conf en el directorio src/test/resources para configurar las opciones de ejecución de pruebas.

### Configuración de WebDriver
La configuración de WebDriver se gestiona completamente desde este archivo, como se ilustra a continuación:

```Java
headless.mode = false

webdriver {
    driver = chrome
    capabilities {
        browserName = "chrome"
        acceptInsecureCerts = true
        "goog:chromeOptions" {
            args = ["start-maximized", "test-type", "no-sandbox", "lang=es", "disable-popup-blocking", "disable-download-notification",
                    "ignore-certificate-errors", "allow-running-insecure-content", "disable-translate", "always-authorize-plugins",
                    "disable-extensions", "remote-allow-origins=*", "incognito", "disable-infobars", "disable-gpu", "disable-default-apps"]
        }
    }
}
```
Serenity utiliza WebDriverManager para descargar automáticamente los binarios de WebDriver antes de que se ejecuten las pruebas.

### Configuraciones específicas de entornos
También podemos configurar propiedades y opciones específicas para cada entorno, lo que permite ejecutar pruebas en diferentes ambientes. Aquí configuramos tres entornos, dev, staging y prod, con diferentes URLs de inicio para cada uno:

```Java
environments {
    default {
        webdriver.base.url = "https://duckduckgo.com"
    }
    dev {
        webdriver.base.url = "https://duckduckgo.com/dev"
    }
    staging {
        webdriver.base.url = "https://duckduckgo.com/staging"
    }
    prod {
        webdriver.base.url = "https://duckduckgo.com/prod"
    }
}
```
Se utiliza la propiedad del sistema environment para determinar en qué entorno ejecutar las pruebas. Por ejemplo, para ejecutar las pruebas en el entorno de staging, se podría usar:

```Bash
$ mvn clean verify -Denvironment=staging
```
Consulta [**este articulo**](https://johnfergusonsmart.com/environment-specific-configuration-in-serenity-bdd/) para obtener más detalles sobre esta característica.

## ¿Quieres aprender más?
Para obtener más información sobre Serenity BDD, puedes leer el Libro de Serenity BDD, la fuente oficial de documentación en línea de Serenity. Otras fuentes incluyen:
* **[Aprende Serenity BDD en linea](https://expansion.serenity-dojo.com/)** con cursos de la Biblioteca de Entrenamiento de Serenity Dojo.
* **[Serenity BDD en videos cortos](https://www.youtube.com/channel/UCav6-dPEUiLbnu-rgpy7_bw/featured)** - consejos y trucos sobre Serenity BDD.
* Para publicaciones regulares sobre las mejores prácticas de automatización de pruebas ágiles, únete a los grupos de **[Agile Test Automation Secrets](https://www.linkedin.com/groups/8961597/)** en [LinkedIn](https://www.linkedin.com/groups/8961597/) y [Facebook](https://www.facebook.com/groups/agiletestautomation/).
* [**Blog de Serenity BDD**](https://johnfergusonsmart.com/category/serenity-bdd/) - artículos regulares sobre Serenity BDD.
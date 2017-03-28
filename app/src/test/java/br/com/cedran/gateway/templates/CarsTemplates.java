package br.com.cedran.gateway.templates;

import br.com.cedran.domains.Car;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class CarsTemplates implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(Car.class).addTemplate("valid Volkswagen car", new Rule() {
            {
                add("brand", "Volkswagen");
                add("model", "Jetta");
                add("color", "red");
                add("year", 2017);
            }
        }).addTemplate("valid Fiat car", new Rule() {
            {
                add("brand", "Fiat");
                add("model", "Bravo");
                add("color", "black");
                add("year", 2017);
            }
        });
    }
}

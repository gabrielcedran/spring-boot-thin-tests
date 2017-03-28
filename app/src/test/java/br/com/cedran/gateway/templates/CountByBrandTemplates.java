package br.com.cedran.gateway.templates;

import br.com.cedran.domains.CountByBrand;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class CountByBrandTemplates implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(CountByBrand.class).addTemplate("count by brand Volkswagen", new Rule() {
            {
                add("brand", "Volkswagen");
                add("count", 2);
            }
        }).addTemplate("count by brand Fiat", new Rule() {
            {
                add("brand", "Fiat");
                add("count", 1);
            }
        });
    }
}

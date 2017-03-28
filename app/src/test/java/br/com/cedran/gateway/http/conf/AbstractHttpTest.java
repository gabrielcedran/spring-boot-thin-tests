package br.com.cedran.gateway.http.conf;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import br.com.cedran.gateway.http.CustomExceptionHandler;

@RunWith(SpringRunner.class)
@WebAppConfiguration
public abstract class AbstractHttpTest {

    protected MockMvc loadController(final Object controller) {
        return standaloneSetup(controller).setControllerAdvice(new CustomExceptionHandler()).build();
    }
}

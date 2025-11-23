package learn.acceptance.template;

import learn.commerce.CommerceApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest(classes = {
        CommerceApplication.class,
        BaseAcceptanceTemplate.class
})
@AutoConfigureMockMvc
@ComponentScan(basePackages = "learn.acceptance")
public class BaseAcceptanceTemplate {
}

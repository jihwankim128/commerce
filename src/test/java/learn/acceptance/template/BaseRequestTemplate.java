package learn.acceptance.template;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

public class BaseRequestTemplate {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected <T> T extractBody(MvcResult result, Class<T> clazz) throws Exception {
        String responseJson = result.getResponse().getContentAsString();
        Object bodyObject = JsonPath.read(responseJson, "$.body");
        String bodyJson = objectMapper.writeValueAsString(bodyObject);
        return objectMapper.readValue(bodyJson, clazz);
    }
}

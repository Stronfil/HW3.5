package com.komissarov.springstore;

import com.komissarov.springstore.entity.Product;
import io.florianlopes.spring.test.web.servlet.request.MockMvcRequestBuilderUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application.properties")
class SpringStoreApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void tryToStart() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.core.StringContains.containsString("Products page")));
    }

    @Test
    void tryAddToCart() throws Exception {
        MvcResult result = mockMvc.perform(formLogin("http://127.0.0.1:8189/login")
                .user("user")
                .password("100")).andReturn();
        MockHttpSession session = (MockHttpSession) result.getRequest().getSession(false);

        mockMvc.perform(get("http://127.0.0.1:8189/viewProducts").session(session));

        Product product = new Product();
        product.setId(1);
        product.setTitle("product 1");
        product.setCost(1.0d);

//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(product);

//        mockMvc.perform(post("http://127.0.0.1:8189/addToCart")
//                .with(csrf())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(json)
//                .session(session));
        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/addToCart", product)
                .with(csrf())
                .session(session));

        mockMvc.perform(get("/cart").session(session))
//                .andDo(print())
                .andExpect(content().string(org.hamcrest.core.StringContains.containsString("product 1")));
    }
}
package com.icommerce.nab.product.service.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.icommerce.nab.dto.builder.RestfulResponseBuilder;
import com.icommerce.nab.dto.dto.ProductDTO;
import com.icommerce.nab.dto.restful.ICommerceResponse;
import com.icommerce.nab.product.service.MockDataUtils;
import com.icommerce.nab.product.service.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static com.icommerce.nab.common.constant.ICommerceConstants.TRANSACTION_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private Gson gson;

    @Before
    public void init() {
        gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setDateFormat("yyyy-MM-dd")
                .create();
    }

    @Test
    public void shouldDeliverOrdersSuccessfully() throws Exception {
        List<ProductDTO> productDTOS = MockDataUtils.prepareProductDTOs(1);
        ICommerceResponse response = RestfulResponseBuilder.buildSuccessResponse(UUID.randomUUID().toString(), productDTOS);
        String textResponse = gson.toJson(response, ICommerceResponse.class);

        Mockito.when(productService.savingProducts(Mockito.anyString(), Mockito.anyList())).thenReturn(response);

        this.mockMvc.perform(
                post("/savingProducts")
                        .header(TRANSACTION_ID, UUID.randomUUID().toString())
                        .header(HttpHeaders.AUTHORIZATION, "Basic Y2h1b25nOjEyMw==")
                        .content(gson.toJson(productDTOS))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(textResponse));

        Mockito.verify(productService, Mockito.times(1)).savingProducts(Mockito.anyString(), Mockito.anyList());
    }
}
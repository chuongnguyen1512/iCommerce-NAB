package com.icommerce.nab.deliver.service.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.icommerce.nab.deliver.service.MockDataUtils;
import com.icommerce.nab.deliver.service.service.DeliverService;
import com.icommerce.nab.dto.builder.RestfulRequestBuilder;
import com.icommerce.nab.dto.builder.RestfulResponseBuilder;
import com.icommerce.nab.dto.dto.OrderDTO;
import com.icommerce.nab.dto.restful.ICommerceRequest;
import com.icommerce.nab.dto.restful.ICommerceResponse;
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
public class DeliverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeliverService deliverService;

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
        List<OrderDTO> orderDTO = MockDataUtils.prepareOrderDTOs(1);
        ICommerceRequest request = RestfulRequestBuilder.buildICommerceRequest(UUID.randomUUID().toString(), orderDTO);

        ICommerceResponse response = RestfulResponseBuilder.buildSuccessResponse(UUID.randomUUID().toString(), orderDTO);
        String textResponse = gson.toJson(response, ICommerceResponse.class);

        Mockito.when(deliverService.deliverOrders(Mockito.anyString(), Mockito.anyList())).thenReturn(response);

        this.mockMvc.perform(
                post("/deliverOrders")
                        .header(TRANSACTION_ID, UUID.randomUUID().toString())
                        .header(HttpHeaders.AUTHORIZATION, "Basic Y2h1b25nOjEyMw==")
                        .content(gson.toJson(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(textResponse));

        Mockito.verify(deliverService, Mockito.times(1)).deliverOrders(Mockito.anyString(), Mockito.anyList());
    }
}
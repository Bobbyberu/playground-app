package com.playground.controllers;

import com.playground.model.entity.Sport;
import com.playground.service.SportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static com.playground.controllers.ControllersUnitTestUtils.buildSport;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(value = SportController.class, secure = false)
public class SportControllerTest {

    private static final Sport SPORT = buildSport();

    private final static String CONTENT = "{\"name\":\"Test\",\"symbol\":\"test\"}";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SportService sportService;

    @Test
    public void getSports_ExpectOk() throws Exception {
        when(sportService.getSports()).thenReturn(Arrays.asList(SPORT));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/sports")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

        String expected = "[{\"id\":1,\"name\":\"sport\",\"symbol\":\"\"}]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getSport_ExistingId_ExpectOk() throws Exception {
        when(sportService.getSport(anyInt())).thenReturn(SPORT);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/sports/0").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

        String expected = "{\"id\":1,\"name\":\"sport\",\"symbol\":\"\"}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getSport_NonExistingId_ExpectNoFound() throws Exception {
        when(sportService.getSport(anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/sports/2").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(result.getResolvedException().getMessage());

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("Sport with id 2 not found", result.getResolvedException().getMessage());
    }

    @Test
    public void createSport_ExpectCreated() throws Exception {
        when(sportService.createSport(any(Sport.class))).thenReturn(SPORT);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/sports")
                .accept(MediaType.APPLICATION_JSON)
                .content(CONTENT)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        String expected = "{\"id\":1,\"name\":\"sport\",\"symbol\":\"\"}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void updateSport_ExistingId_ExpectOk() throws Exception {
        Sport newSport = buildSport();
        when(newSport.getSymbol()).thenReturn("\uD83D\uDC4C");

        when(sportService.getSport(anyInt())).thenReturn(SPORT);
        when(sportService.updateSport(anyInt(), any(Sport.class))).thenReturn(newSport);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/sports/0")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(CONTENT);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        String expected = "{\"id\":1,\"name\":\"sport\",\"symbol\":\"\\uD83D\\uDC4C\"}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void updateSport_NonExistingId_ExpectNotFound() throws Exception {
        when(sportService.getSport(anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/sports/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(CONTENT);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("Sport with id 2 not found", result.getResolvedException().getMessage());
    }

    @Test
    public void deleteSport_ExpectingId_ExpectNoContent() throws Exception {
        when(sportService.getSport(anyInt())).thenReturn(SPORT);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/sports/0")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }

    @Test
    public void deleteSport_NonExpectingId_ExpectNotFound() throws Exception {
        when(sportService.getSport(anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/sports/2")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertEquals("Sport with id 2 not found", result.getResolvedException().getMessage());
    }
}

package com.playground.controllers;

import com.playground.model.entity.Sport;
import com.playground.service.SportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(value = SportController.class, secure = false)
public class SportControllerTest {

    @Autowired
    private SportController sportController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SportService sportService;

    private Sport mockSport = new Sport("Badminton", "\uD83C\uDFF8");

    @Test
    public void testGetSportsExpectOk() throws Exception {
        Mockito.when(sportService.getSports()).thenReturn(Arrays.asList(mockSport));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/sports")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse().getContentAsString());

        String expected = "[{\"id\":0,\"name\":\"Badminton\", \"symbol\":\"\uD83C\uDFF8\"}]";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testGetSportWithExistingIdExpectOk() throws Exception {
        Mockito.when(sportService.getSport(Mockito.anyInt())).thenReturn(mockSport);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/sports/0").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse().getContentAsString());

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

        String expected = "{\"id\":0,\"name\":\"Badminton\", \"symbol\":\"\uD83C\uDFF8\"}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testGetSportWithNonExistingIdExpectNoFound() throws Exception {
        int id = 2;

        Mockito.when(sportService.getSport(Mockito.anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/sports/" + id).accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(result.getResolvedException().getMessage());

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("Sport with id "+ id +" not found", result.getResolvedException().getMessage());
    }

    @Test
    public void testCreateSportExpectCreated() throws Exception {
        Sport mockSport = new Sport("Sport","Symbol");

        Mockito.when(sportService.createSport(Mockito.any(Sport.class))).thenReturn(mockSport);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/sports")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Test\",\"symbol\":\"test\"}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        String expected = "{\"id\":0,\"name\":\"Sport\", \"symbol\":\"Symbol\"}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testUpdateSportWithExistingIdExpectOk() throws Exception {
        Sport newMockSport = new Sport(mockSport.getName(), "Change");
        newMockSport.setId(mockSport.getId());

        Mockito.when(sportService.getSport(Mockito.anyInt())).thenReturn(mockSport);
        Mockito.when(sportService.updateSport(Mockito.anyInt(), Mockito.any(Sport.class))).thenReturn(newMockSport);

        String content = "{\"name\":\"Badminton\",\"symbol\":\"Change\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/sports/" + mockSport.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content)
                ;

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        String expected = "{\"id\":0,\"name\":\"Badminton\", \"symbol\":\"Change\"}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testUpdateSportWithNonExistingIdExpectNotFound() throws Exception {
        int id = 2;

        Mockito.when(sportService.getSport(Mockito.anyInt())).thenReturn(null);

        String content = "{\"name\":\"Badminton\",\"symbol\":\"Change\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/sports/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content)
                ;

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(result.getResolvedException().getMessage());

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("Sport with id "+ id +" not found", result.getResolvedException().getMessage());
    }

    @Test
    public void testDeleteSportWithExpectingIdExpectNoContent() throws Exception {
        Mockito.when(sportService.getSport(Mockito.anyInt())).thenReturn(mockSport);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/sports/0")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }

    @Test
    public void testDeleteSportWithNonExpectingIdExpectNotFound() throws Exception {
        int id = 2;

        Mockito.when(sportService.getSport(Mockito.anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/sports/" + id)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResolvedException().getMessage());

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertEquals("Sport with id "+ id +" not found", result.getResolvedException().getMessage());
    }
}

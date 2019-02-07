package com.playground.controllers;

import com.playground.model.entity.ReportPlayground;
import com.playground.model.entity.Playground;
import com.playground.service.ReportPlaygroundService;
import com.playground.service.PlaygroundService;
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
@WebMvcTest(value = ReportPlaygroundController.class, secure = false)
public class ReportPlaygroundControllerTest {

    @Autowired
    private ReportPlaygroundController reportPlaygroundController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportPlaygroundService reportPlaygroundService;

    @MockBean
    private PlaygroundService playgroundService;

    private Playground mockPlayground = new Playground();

    private ReportPlayground mockReportPlayground = new ReportPlayground(null, mockPlayground, "description");

    @Test
    public void testGetReportPlaygroundsExpectOk() throws Exception {
        Mockito.when(reportPlaygroundService.getReportPlaygrounds()).thenReturn(Arrays.asList(mockReportPlayground));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/reportPlaygrounds")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse().getContentAsString());

        String expected = "[{\"id\":0,\"author\":null,\"playground\":{\"id\":0,\"name\":null,\"covered\":false,\"latitude\":0.0,\"longitude\":0.0,\"surface\":null,\"description\":null,\"averageMark\":0.0,\"imageName\":null,\"players\":null,\"sports\":null,\"city\":null,\"address\":null,\"private\":false},\"description\":\"description\"}]";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testGetReportPlaygroundWithExistingPlaygroundAndExistingIdExpectOk() throws Exception {
        Mockito.when(playgroundService.getPlayground(Mockito.anyInt())).thenReturn(mockPlayground);
        Mockito.when(reportPlaygroundService.getReportPlayground(Mockito.anyInt())).thenReturn(mockReportPlayground);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playgrounds/0/reportPlaygrounds/0").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse().getContentAsString());

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

        String expected = "{\"id\":0,\"author\":null,\"playground\":{\"id\":0,\"name\":null,\"covered\":false,\"latitude\":0.0,\"longitude\":0.0,\"surface\":null,\"description\":null,\"averageMark\":0.0,\"imageName\":null,\"players\":null,\"sports\":null,\"city\":null,\"address\":null,\"private\":false},\"description\":\"description\"}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testGetReportPlaygroundWithExistingPlaygroundAndNonExistingIdExpectNoFound() throws Exception {
        int id = 2;

        Mockito.when(playgroundService.getPlayground(Mockito.anyInt())).thenReturn(mockPlayground);
        Mockito.when(reportPlaygroundService.getReportPlayground(Mockito.anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playgrounds/0/reportPlaygrounds/" + id).accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(result.getResolvedException().getMessage());

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("ReportPlayground with id "+ id +" not found for this playground", result.getResolvedException().getMessage());
    }

    @Test
    public void testGetReportPlaygroundWithNonExistingPlaygroundExpectNoFound() throws Exception {
        int id = 2;

        Mockito.when(playgroundService.getPlayground(Mockito.anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playgrounds/" + id + "/reportPlaygrounds/0").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(result.getResolvedException().getMessage());

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("Playground with id "+ id +" not found", result.getResolvedException().getMessage());
    }

    @Test
    public void testCreateReportPlaygroundExpectCreated() throws Exception {
        ReportPlayground mockReportPlayground = new ReportPlayground(null, mockPlayground, "description");

        Mockito.when(playgroundService.getPlayground(Mockito.anyInt())).thenReturn(mockPlayground);
        Mockito.when(reportPlaygroundService.createReportPlayground(Mockito.any(Playground.class), Mockito.any(ReportPlayground.class))).thenReturn(mockReportPlayground);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/playgrounds/0/reportPlaygrounds")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"description\":\"description\"}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        String expected = "{\"id\":0,\"author\":null,\"playground\":{\"id\":0,\"name\":null,\"covered\":false,\"latitude\":0.0,\"longitude\":0.0,\"surface\":null,\"description\":null,\"averageMark\":0.0,\"imageName\":null,\"players\":null,\"sports\":null,\"city\":null,\"address\":null,\"private\":false},\"description\":\"description\"}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }


    @Test
    public void testDeleteReportPlaygroundWithExistingPlaygroundAndExistingIdExpectNoContent() throws Exception {
        Mockito.when(playgroundService.getPlayground(Mockito.anyInt())).thenReturn(mockPlayground);
        Mockito.when(reportPlaygroundService.getReportPlayground(Mockito.anyInt())).thenReturn(mockReportPlayground);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/playgrounds/0/reportPlaygrounds/0")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }

    @Test
    public void testDeleteReportPlaygroundWithNonExistingPlaygroundExpectNotFound() throws Exception {
        int id = 2;

        Mockito.when(playgroundService.getPlayground(Mockito.anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/playgrounds/" + id + "/reportPlaygrounds/0")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResolvedException().getMessage());

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertEquals("Playground with id "+ id +" not found", result.getResolvedException().getMessage());
    }

    @Test
    public void testDeleteReportPlaygroundWithExistingPlaygroundAndNonExistingIdExpectNotFound() throws Exception {
        int id = 2;

        Mockito.when(playgroundService.getPlayground(Mockito.anyInt())).thenReturn(mockPlayground);
        Mockito.when(reportPlaygroundService.getReportPlayground(Mockito.anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/playgrounds/0/reportPlaygrounds/" + id)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResolvedException().getMessage());

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertEquals("ReportPlayground with id "+ id +" not found for this playground", result.getResolvedException().getMessage());
    }
}

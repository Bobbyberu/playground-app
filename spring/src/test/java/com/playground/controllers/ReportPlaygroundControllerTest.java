package com.playground.controllers;

import com.playground.model.entity.Playground;
import com.playground.model.entity.ReportPlayground;
import com.playground.model.entity.User;
import com.playground.service.PlaygroundService;
import com.playground.service.ReportPlaygroundService;
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

import static com.playground.controllers.ControllersUnitTestUtils.buildPlayground;
import static com.playground.controllers.ControllersUnitTestUtils.buildReportPlayground;
import static com.playground.controllers.ControllersUnitTestUtils.buildUser;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ReportPlaygroundController.class, secure = false)
public class ReportPlaygroundControllerTest {

    private final static Playground PLAYGROUND = buildPlayground();
    private final static User AUTHOR = buildUser();
    private final static ReportPlayground REPORT_PLAYGROUND = buildReportPlayground(PLAYGROUND, AUTHOR);

    private final static String CONTENT = "{\"description\":\"description\"}";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportPlaygroundService reportPlaygroundService;

    @MockBean
    private PlaygroundService playgroundService;

    @Test
    public void getReportPlaygrounds_ExpectOk() throws Exception {
        when(reportPlaygroundService.getReportPlaygrounds()).thenReturn(Arrays.asList(REPORT_PLAYGROUND));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/reportPlaygrounds")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

        String expected = "[{\"id\":0,\"author\":{\"id\":1,\"username\":\"user\",\"enabled\":false,\"archived\":false,\"banned\":false},\"playground\":{\"id\":1,\"name\":\"name\",\"latitude\":0.0,\"longitude\":0.0},\"description\":\"description\"}]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getReportPlayground_ExistingId_ExpectOk() throws Exception {
        when(reportPlaygroundService.getReportPlayground(anyInt())).thenReturn(REPORT_PLAYGROUND);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/reportPlaygrounds/0")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

        String expected = "{\"id\":0,\"author\":{\"id\":1,\"username\":\"user\",\"enabled\":false,\"archived\":false,\"banned\":false},\"playground\":{\"id\":1,\"name\":\"name\",\"latitude\":0.0,\"longitude\":0.0},\"description\":\"description\"}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getReportPlayground_ExistingPlaygroundAndNonExistingId_ExpectNoFound() throws Exception {
        when(reportPlaygroundService.getReportPlayground(anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/reportPlaygrounds/2")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("ReportPlayground with id 2 was not found", result.getResolvedException().getMessage());
    }

    @Test
    public void createReportPlayground_ExpectCreated() throws Exception {
        when(playgroundService.getPlayground(anyInt())).thenReturn(PLAYGROUND);
        when(reportPlaygroundService.createReportPlayground(
                any(Playground.class), any(ReportPlayground.class))).thenReturn(REPORT_PLAYGROUND);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/playgrounds/0/reportPlaygrounds")
                .accept(MediaType.APPLICATION_JSON)
                .content(CONTENT)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        String expected = "{\"id\":0,\"author\":{\"id\":1,\"username\":\"user\",\"enabled\":false,\"archived\":false,\"banned\":false},\"playground\":{\"id\":1,\"name\":\"name\",\"latitude\":0.0,\"longitude\":0.0},\"description\":\"description\"}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void deleteReportPlayground_ExistingPlaygroundAndExistingId_ExpectNoContent() throws Exception {
        when(reportPlaygroundService.getReportPlayground(anyInt())).thenReturn(REPORT_PLAYGROUND);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/reportPlaygrounds/0")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }

    @Test
    public void deleteReportPlayground_NonExistingId_ExpectNotFound() throws Exception {
        when(playgroundService.getPlayground(anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/reportPlaygrounds/2")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertEquals("ReportPlayground with id 2 was not found", result.getResolvedException().getMessage());
    }
}

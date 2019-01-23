package com.playground.controllers;

import com.playground.model.Playground;
import com.playground.service.PlaygroundService;
import com.playground.service.SportService;
import com.playground.service.UserService;
import com.playground.storage.StorageService;
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
@WebMvcTest(value = PlaygroundController.class, secure = false)
public class PlaygroundControllerTest {

    @Autowired
    private PlaygroundController playgroundController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlaygroundService playgroundService;

    @MockBean
    private StorageService storageService;

    @MockBean
    private UserService userService;

    @MockBean
    private SportService sportService;

    private Playground mockPlayground = new Playground("name", false, false, 0, 0, "surface", "description", "city", "address");

    @Test
    public void testGetPlaygroundsExpectOk() throws Exception {
        Mockito.when(playgroundService.getPlaygrounds()).thenReturn(Arrays.asList(mockPlayground));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/playgrounds")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

        String expected = "[{\"id\":0,\"name\":\"name\",\"covered\":false,\"latitude\":0.0,\"longitude\":0.0,\"surface\":\"surface\",\"description\":\"description\",\"averageMark\":0.0,\"imageName\":null,\"players\":null,\"sports\":null,\"city\":\"city\",\"address\":\"address\",\"private\":false}]";

        JSONAssert.assertEquals(expected, response.getContentAsString(), false);
    }

    @Test
    public void testGetPlaygroundWithExistingIdExpectOk() throws Exception {
        Mockito.when(playgroundService.getPlayground(Mockito.anyInt())).thenReturn(mockPlayground);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playgrounds/0").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        String expected = "{\"id\":0,\"name\":\"name\",\"covered\":false,\"latitude\":0.0,\"longitude\":0.0,\"surface\":\"surface\",\"description\":\"description\",\"averageMark\":0.0,\"imageName\":null,\"players\":null,\"sports\":null,\"city\":\"city\",\"address\":\"address\",\"private\":false}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testGetPlaygroundWithNonExistingIdExpectNoFound() throws Exception {
        int id = 2;

        Mockito.when(playgroundService.getPlayground(Mockito.anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playgrounds/" + id).accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(result.getResolvedException().getMessage());

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("Playground with id "+ id +" not found", result.getResolvedException().getMessage());
    }

    @Test
    public void testCreatePlaygroundExpectCreated() throws Exception {
        Playground mockPlayground = new Playground("name", false, false, 0, 0, "surface", "description", "city", "address");

        Mockito.when(playgroundService.createPlayground(Mockito.any(Playground.class))).thenReturn(mockPlayground);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/playgrounds")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Test\",\"isPrivate\":false}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        String expected = "{\"id\":0,\"name\":\"name\",\"covered\":false,\"latitude\":0.0,\"longitude\":0.0,\"surface\":\"surface\",\"description\":\"description\",\"averageMark\":0.0,\"imageName\":null,\"players\":null,\"sports\":null,\"city\":\"city\",\"address\":\"address\",\"private\":false}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testUpdatePlaygroundWithExistingIdExpectOk() throws Exception {
        Playground newMockPlayground = new Playground("name", false, false, 0, 0, "surface", "description", "city", "address");
        newMockPlayground.setId(mockPlayground.getId());

        Mockito.when(playgroundService.getPlayground(Mockito.anyInt())).thenReturn(mockPlayground);
        Mockito.when(playgroundService.updatePlayground(Mockito.anyInt(), Mockito.any(Playground.class))).thenReturn(newMockPlayground);

        String content = "{\"name\":\"Playground\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/playgrounds/" + mockPlayground.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content)
                ;

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        String expected = "{\"id\":0,\"name\":\"name\",\"covered\":false,\"latitude\":0.0,\"longitude\":0.0,\"surface\":\"surface\",\"description\":\"description\",\"averageMark\":0.0,\"imageName\":null,\"players\":null,\"sports\":null,\"city\":\"city\",\"address\":\"address\",\"private\":false}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testUpdatePlaygroundWithNonExistingIdExpectNotFound() throws Exception {
        int id = 2;

        Mockito.when(playgroundService.getPlayground(Mockito.anyInt())).thenReturn(null);

        String content = "{\"name\":\"Badminton\",\"symbol\":\"Change\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/playgrounds/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content)
                ;

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(result.getResolvedException().getMessage());

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("Playground with id "+ id +" not found", result.getResolvedException().getMessage());
    }

    @Test
    public void testDeletePlaygroundWithExpectingIdExpectNoContent() throws Exception {
        Mockito.when(playgroundService.getPlayground(Mockito.anyInt())).thenReturn(mockPlayground);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/playgrounds/0")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }

    @Test
    public void testDeletePlaygroundWithNonExpectingIdExpectNotFound() throws Exception {
        int id = 2;

        Mockito.when(playgroundService.getPlayground(Mockito.anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/playgrounds/" + id)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResolvedException().getMessage());

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertEquals("Playground with id "+ id +" not found", result.getResolvedException().getMessage());
    }

    @Test
    public void testSearchWithKeywordExpectOk() throws Exception {
        Mockito.when(playgroundService.searchPlaygroundByKeyword(Mockito.anyString())).thenReturn(Arrays.asList(mockPlayground));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/playgrounds/search/keyword")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

        String expected = "[{\"id\":0,\"name\":\"name\",\"covered\":false,\"latitude\":0.0,\"longitude\":0.0,\"surface\":\"surface\",\"description\":\"description\",\"averageMark\":0.0,\"imageName\":null,\"players\":null,\"sports\":null,\"city\":\"city\",\"address\":\"address\",\"private\":false}]";

        JSONAssert.assertEquals(expected, response.getContentAsString(), false);
    }
}

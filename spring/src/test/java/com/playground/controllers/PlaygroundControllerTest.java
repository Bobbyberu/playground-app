package com.playground.controllers;

import com.playground.model.Playground;
import com.playground.model.Sport;
import com.playground.model.User;
import com.playground.service.*;
import com.playground.storage.StorageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
import java.util.HashSet;
import java.util.Set;

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

    @MockBean
    private CommentService commentService;

    @MockBean
    private ReportPlaygroundService reportPlaygroundService;

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

    @Test
    public void testAddPlayerToPlaygroundWithExistingPlaygroundAndUserAndSportExpectOk() throws Exception {
        User mockUser = new User("player","player");
        Sport mockSport = new Sport("Basketball", "🏀");

        Set<Sport> sports = new HashSet<>();
        sports.add(mockSport);

        mockPlayground.setSports(sports);

        String content = "{\"name\":\"playground\"}";

        Mockito.when(playgroundService.getPlayground(Mockito.anyInt())).thenReturn(mockPlayground);
        Mockito.when(userService.getUser(Mockito.anyInt())).thenReturn(mockUser);
        Mockito.when(sportService.getSport(Mockito.anyInt())).thenReturn(mockSport);

        Set<User> users = new HashSet<>();
        users.add(mockUser);

        mockPlayground.setPlayers(users);

        Mockito.when(playgroundService.addPlayerToPlayground(Mockito.any(Playground.class), Mockito.any(User.class), Mockito.any(Sport.class))).thenReturn(mockPlayground);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/playgrounds/0/player/0/sport/0/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        String expected = "{\"id\":0,\"name\":\"name\",\"covered\":false,\"latitude\":0.0,\"longitude\":0.0,\"surface\":\"surface\",\"description\":\"description\",\"averageMark\":0.0,\"imageName\":null,\"players\":[{\"id\":0,\"username\":\"player\",\"mail\":null,\"birthDate\":null,\"avatarName\":null,\"friends\":null,\"favouriteSports\":null,\"city\":null,\"role\":null,\"enabled\":false,\"archived\":false,\"banned\":false,\"playing\":null,\"credentialsNonExpired\":true,\"accountNonExpired\":true,\"accountNonLocked\":true}],\"sports\":[{\"id\":0,\"name\":\"Basketball\",\"symbol\":\"\\uD83C\\uDFC0\"}],\"city\":\"city\",\"address\":\"address\",\"private\":false}";

        JSONAssert.assertEquals(expected, response.getContentAsString(), false);
    }

    @Test
    public void testAddPlayerToPlaygroundWithExistingPlaygroundAndUserAndSportExpectForbidden() throws Exception {
        User mockUser = new User("player","player");
        Sport mockSport = new Sport("Basketball", "🏀");

        Set<Sport> sports = new HashSet<>();
        mockPlayground.setSports(sports);

        String content = "{\"name\":\"playground\"}";

        Mockito.when(playgroundService.getPlayground(Mockito.anyInt())).thenReturn(mockPlayground);
        Mockito.when(userService.getUser(Mockito.anyInt())).thenReturn(mockUser);
        Mockito.when(sportService.getSport(Mockito.anyInt())).thenReturn(mockSport);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/playgrounds/0/player/0/sport/0/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }

    @Test
    public void testAddPlayerToPlaygroundWithNonExistingPlaygroundExpectNotFound() throws Exception {
        int id = 2;
        String content = "{\"name\":\"playground\"}";

        Mockito.when(playgroundService.getPlayground(Mockito.anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/playgrounds/" + id + "/player/0/sport/0/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(result.getResolvedException().getMessage());

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("Playground with id "+ id +" not found", result.getResolvedException().getMessage());
    }

    @Test
    public void testAddPlayerToPlaygroundWithNonExistingUserExpectNotFound() throws Exception {
        int id = 2;
        String content = "{\"name\":\"playground\"}";

        Mockito.when(playgroundService.getPlayground(Mockito.anyInt())).thenReturn(mockPlayground);
        Mockito.when(userService.getUser(Mockito.anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/playgrounds/0/player/" + id + "/sport/0/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(result.getResolvedException().getMessage());

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("User with id "+ id +" not found", result.getResolvedException().getMessage());
    }

    @Test
    public void testAddPlayerToPlaygroundWithNonExistingSportExpectNotFound() throws Exception {
        int id = 2;
        User mockUser = new User("player","player");
        String content = "{\"name\":\"playground\"}";

        Mockito.when(playgroundService.getPlayground(Mockito.anyInt())).thenReturn(mockPlayground);
        Mockito.when(userService.getUser(Mockito.anyInt())).thenReturn(mockUser);
        Mockito.when(sportService.getSport(Mockito.anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/playgrounds/0/player/0/sport/" + id + "/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(result.getResolvedException().getMessage());

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("Sport with id "+ id +" not found", result.getResolvedException().getMessage());
    }

    @Test
    public void testRemovePlayerToPlaygroundWithExistingPlaygroundAndUserExpectOk() throws Exception {
        User mockUser = new User("player","player");

        Set<Sport> sports = new HashSet<>();
        mockPlayground.setSports(sports);

        String content = "{\"name\":\"playground\"}";

        Mockito.when(playgroundService.getPlayground(Mockito.anyInt())).thenReturn(mockPlayground);
        Mockito.when(userService.getUser(Mockito.anyInt())).thenReturn(mockUser);

        Set<User> users = new HashSet<>();
        mockPlayground.setPlayers(users);

        Mockito.when(playgroundService.removePlayerFromPlayground(Mockito.any(Playground.class), Mockito.any(User.class))).thenReturn(mockPlayground);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/playgrounds/0/player/0/remove")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        String expected = "{\"id\":0,\"name\":\"name\",\"covered\":false,\"latitude\":0.0,\"longitude\":0.0,\"surface\":\"surface\",\"description\":\"description\",\"averageMark\":0.0,\"imageName\":null,\"players\":[],\"sports\":[],\"city\":\"city\",\"address\":\"address\",\"private\":false}";

        JSONAssert.assertEquals(expected, response.getContentAsString(), false);
    }

    @Test
    public void testRemovePlayerToPlaygroundWithNonExistingPlaygroundExpectNotFound() throws Exception {
        int id = 2;
        String content = "{\"name\":\"playground\"}";

        Mockito.when(playgroundService.getPlayground(Mockito.anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/playgrounds/" + id + "/player/0/remove")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(result.getResolvedException().getMessage());

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("Playground with id "+ id +" not found", result.getResolvedException().getMessage());
    }

    @Test
    public void testRemovePlayerToPlaygroundWithNonExistingUserExpectNotFound() throws Exception {
        int id = 2;
        String content = "{\"name\":\"playground\"}";

        Mockito.when(playgroundService.getPlayground(Mockito.anyInt())).thenReturn(mockPlayground);
        Mockito.when(userService.getUser(Mockito.anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/playgrounds/0/player/" + id + "/remove")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(result.getResolvedException().getMessage());

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("User with id "+ id +" not found", result.getResolvedException().getMessage());
    }
}

package com.playground.controllers;

import com.playground.model.entity.Playground;
import com.playground.model.entity.Schedule;
import com.playground.model.entity.Sport;
import com.playground.model.entity.User;
import com.playground.model.wrapper.ScheduleWrapper;
import com.playground.service.CommentService;
import com.playground.service.PlaygroundService;
import com.playground.service.ReportPlaygroundService;
import com.playground.service.ScheduleService;
import com.playground.service.SportService;
import com.playground.service.UserService;
import com.playground.storage.StorageService;
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
import java.util.HashSet;
import java.util.Set;

import static com.playground.controllers.ControllersUnitTestUtils.buildPlayground;
import static com.playground.controllers.ControllersUnitTestUtils.buildSchedules;
import static com.playground.controllers.ControllersUnitTestUtils.buildSport;
import static com.playground.controllers.ControllersUnitTestUtils.buildUser;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PlaygroundController.class, secure = false)
public class PlaygroundControllerTest {

    private final static String CONTENT = "{\"playground\":{\"name\":\"name\"}}";

    private final static String CONTENT_WITH_SCHEDULES = "{\"playground\":{\"name\":\"name\"}, \"schedules\":[{\"day\": 0}]}";

    private final static Playground PLAYGROUND = buildPlayground();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @MockBean
    private PlaygroundService playgroundService;

    @MockBean
    private ReportPlaygroundService reportPlaygroundService;

    @MockBean
    private ScheduleService scheduleService;

    @MockBean
    private SportService sportService;

    @MockBean
    private StorageService storageService;

    @MockBean
    private UserService userService;

    @Test
    public void getPlaygrounds_ExpectOK() throws Exception {
        when(playgroundService.getPlaygrounds()).thenReturn(Arrays.asList(PLAYGROUND));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/playgrounds")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        String expected = "[{\"id\":1,\"latitude\":0.0,\"longitude\":0.0}]";

        JSONAssert.assertEquals(expected, response.getContentAsString(), false);
    }

    @Test
    public void getPlayground_ExistingId_ExpectOK() throws Exception {
        when(playgroundService.getPlayground(anyInt())).thenReturn(PLAYGROUND);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playgrounds/1").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        String expected = "{\"id\":1,\"name\":\"name\",\"isPrivate\":false,\"covered\":false,\"latitude\":0.0,\"longitude\":0.0,\"surface\":\"surface\",\"description\":\"description\",\"averageMark\":0.0,\"sports\":[],\"city\":\"Villié-Morgon\",\"address\":\"address\",\"schedules\":{\"playgroundId\":1,\"days\":[]}}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getPlayground_NonExistingId_ExpectNotFound() throws Exception {
        when(playgroundService.getPlayground(anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playgrounds/2").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("Playground with id 2 not found", result.getResolvedException().getMessage());
    }

    @Test
    public void createPlayground_ExpectCreated() throws Exception {
        when(playgroundService.createPlayground(any(Playground.class))).thenReturn(PLAYGROUND);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/playgrounds")
                .accept(MediaType.APPLICATION_JSON)
                .content(CONTENT)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        String expected = "{\"id\":1,\"name\":\"name\",\"isPrivate\":false,\"covered\":false,\"latitude\":0.0,\"longitude\":0.0,\"surface\":\"surface\",\"description\":\"description\",\"averageMark\":0.0,\"sports\":[],\"city\":\"Villié-Morgon\",\"address\":\"address\",\"schedules\":{\"playgroundId\":1,\"days\":[]}}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void createPlayground_Schedule_ExpectCreated() throws Exception {
        Set<Schedule> schedules = buildSchedules();

        Playground playgroundWithSchedules = buildPlayground();
        when(playgroundWithSchedules.getSchedules()).thenReturn(schedules);

        when(playgroundService.createPlayground(any(Playground.class))).thenReturn(playgroundWithSchedules);
        when(playgroundService.updatePlayground(anyInt(), any(Playground.class))).thenReturn(playgroundWithSchedules);
        when(scheduleService.isTimeInvalid(any(ScheduleWrapper.class))).thenReturn(false);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/playgrounds")
                .accept(MediaType.APPLICATION_JSON)
                .content(CONTENT_WITH_SCHEDULES)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        String expected = "{\"id\":1,\"name\":\"name\",\"isPrivate\":false,\"covered\":false,\"latitude\":0.0,\"longitude\":0.0,\"surface\":\"surface\",\"description\":\"description\",\"averageMark\":0.0,\"sports\":[],\"city\":\"Villié-Morgon\",\"address\":\"address\",\"schedules\":{\"playgroundId\":1,\"days\":[{\"day\":\"MONDAY\",\"opening\":\"12:00\",\"closure\":\"00:00\"}]}}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void createPlayground_InvalidSchedule_ExpectBadRequest() throws Exception {
        Set<Schedule> schedules = buildSchedules();

        Playground playgroundWithSchedules = buildPlayground();
        when(playgroundWithSchedules.getSchedules()).thenReturn(schedules);

        when(playgroundService.createPlayground(any(Playground.class))).thenReturn(playgroundWithSchedules);
        when(playgroundService.updatePlayground(anyInt(), any(Playground.class))).thenReturn(playgroundWithSchedules);
        when(scheduleService.isTimeInvalid(any(ScheduleWrapper.class))).thenReturn(true);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/playgrounds")
                .accept(MediaType.APPLICATION_JSON)
                .content(CONTENT_WITH_SCHEDULES)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void updatePlayground_ExistingIdExpectOK() throws Exception {
        Playground newPlayground = buildPlayground();
        when(newPlayground.getCity()).thenReturn("New York");

        when(playgroundService.getPlayground(anyInt())).thenReturn(PLAYGROUND);
        when(playgroundService.updatePlayground(anyInt(), any(Playground.class))).thenReturn(newPlayground);

        String content = "{\"name\":\"Playground\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/playgrounds/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        String expected = "{\"id\":1,\"name\":\"name\",\"isPrivate\":false,\"covered\":false,\"latitude\":0.0,\"longitude\":0.0,\"surface\":\"surface\",\"description\":\"description\",\"averageMark\":0.0,\"sports\":[],\"city\":\"New York\",\"address\":\"address\",\"schedules\":{\"playgroundId\":1,\"days\":[]}}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void updatePlayground_NonExistingId_ExpectNotFound() throws Exception {
        when(playgroundService.getPlayground(anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/playgrounds/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(CONTENT);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("Playground with id 2 not found", result.getResolvedException().getMessage());
    }

    @Test
    public void deletePlayground_ExistingId_ExpectNoContent() throws Exception {
        when(playgroundService.getPlayground(anyInt())).thenReturn(PLAYGROUND);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/playgrounds/0")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }

    @Test
    public void deletePlayground_NonExistingId_ExpectNotFound() throws Exception {
        when(playgroundService.getPlayground(anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/playgrounds/2")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertEquals("Playground with id 2 not found", result.getResolvedException().getMessage());
    }

    @Test
    public void searchWithKeyword_ExpectOk() throws Exception {
        when(playgroundService.searchPlaygroundByKeyword(anyString())).thenReturn(Arrays.asList(PLAYGROUND));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/playgrounds/search/keyword")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        String expected = "[{\"id\":1,\"name\":\"name\",\"isPrivate\":false,\"covered\":false,\"latitude\":0.0,\"longitude\":0.0,\"surface\":\"surface\",\"description\":\"description\",\"averageMark\":0.0,\"sports\":[],\"city\":\"Villié-Morgon\",\"address\":\"address\",\"schedules\":{\"playgroundId\":1,\"days\":[]}}]";

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        JSONAssert.assertEquals(expected, response.getContentAsString(), false);
    }

    @Test
    public void addPlayerToPlayground_ExistingPlaygroundAndUserAndSport_ExpectOK() throws Exception {
        Playground playground = buildPlayground();

        Set<Sport> sports = new HashSet<>();
        Sport sport = buildSport();
        sports.add(sport);

        Set<User> users = new HashSet<>();
        User user = buildUser();
        users.add(user);

        when(playground.getSports()).thenReturn(sports);
        when(playground.getPlayers()).thenReturn(users);
        when(playgroundService.getPlayground(anyInt())).thenReturn(playground);
        when(userService.getUser(anyInt())).thenReturn(user);
        when(sportService.getSport(anyInt())).thenReturn(sport);

        playground.setPlayers(users);

        when(playgroundService.addPlayerToPlayground(any(Playground.class), any(User.class), any(Sport.class))).thenReturn(playground);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/playgrounds/0/player/0/sport/0/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(CONTENT)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        String expected = "{\"id\":1,\"name\":\"name\",\"isPrivate\":false,\"covered\":false,\"latitude\":0.0,\"longitude\":0.0,\"surface\":\"surface\",\"description\":\"description\",\"averageMark\":0.0,\"players\":[{\"id\":1,\"username\":\"user\",\"enabled\":false,\"archived\":false,\"banned\":false}],\"sports\":[{\"id\":1,\"name\":\"sport\",\"symbol\":\"\"}],\"city\":\"Villié-Morgon\",\"address\":\"address\",\"schedules\":{\"playgroundId\":1,\"days\":[]}}";

        JSONAssert.assertEquals(expected, response.getContentAsString(), false);
    }

    @Test
    public void addPlayerToPlayground_ExistingPlaygroundAndUserAndSportNotInPlayground_ExpectForbidden() throws Exception {
        Playground playground = buildPlayground();

        Set<User> users = new HashSet<>();
        User user = buildUser();
        users.add(user);

        when(playgroundService.getPlayground(anyInt())).thenReturn(playground);
        when(userService.getUser(anyInt())).thenReturn(user);
        when(sportService.getSport(anyInt())).thenReturn(mock(Sport.class));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/playgrounds/0/player/0/sport/0/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(CONTENT)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }

    @Test
    public void addPlayer_NonExistingPlayground_ExpectNotFound() throws Exception {
        when(playgroundService.getPlayground(anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/playgrounds/2/player/0/sport/0/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(CONTENT)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("Playground with id 2 not found", result.getResolvedException().getMessage());
    }

    @Test
    public void addPlayerToPlayground_NonExistingPlayer_ExpectNotFound() throws Exception {
        when(playgroundService.getPlayground(anyInt())).thenReturn(PLAYGROUND);
        when(userService.getUser(anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/playgrounds/0/player/2/sport/0/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(CONTENT)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("User with id 2 not found", result.getResolvedException().getMessage());
    }

    @Test
    public void addPlayerToPlayground_NonExistingSport_ExpectNotFound() throws Exception {
        User user = buildUser();

        when(playgroundService.getPlayground(anyInt())).thenReturn(PLAYGROUND);
        when(userService.getUser(anyInt())).thenReturn(user);
        when(sportService.getSport(anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/playgrounds/0/player/0/sport/2/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(CONTENT)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("Sport with id 2 not found", result.getResolvedException().getMessage());
    }

    @Test
    public void removePlayerFromPlayground_ExistingPlaygroundAndUser_ExpectOK() throws Exception {
        Playground playground = buildPlayground();

        Set<User> users = new HashSet<>();
        User user = buildUser();
        users.add(user);

        Set<Sport> sports = new HashSet<>();
        Sport sport = buildSport();
        sports.add(sport);

        when(playgroundService.getPlayground(anyInt())).thenReturn(playground);
        when(userService.getUser(anyInt())).thenReturn(user);
        when(playground.getPlayers()).thenReturn(users);
        when(playground.getSports()).thenReturn(sports);

        when(playgroundService.removePlayerFromPlayground(any(Playground.class), any(User.class))).thenReturn(playground);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/playgrounds/0/player/0/remove")
                .contentType(MediaType.APPLICATION_JSON)
                .content(CONTENT)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        String expected = "{\"id\":1,\"name\":\"name\",\"isPrivate\":false,\"covered\":false,\"latitude\":0.0,\"longitude\":0.0,\"surface\":\"surface\",\"description\":\"description\",\"averageMark\":0.0,\"players\":[{\"id\":1,\"username\":\"user\",\"enabled\":false,\"archived\":false,\"banned\":false}],\"sports\":[{\"id\":1,\"name\":\"sport\",\"symbol\":\"\"}],\"city\":\"Villié-Morgon\",\"address\":\"address\",\"schedules\":{\"playgroundId\":1,\"days\":[]}}";

        JSONAssert.assertEquals(expected, response.getContentAsString(), false);
    }

    @Test
    public void removePlayerFromPlayground_NonExistingPlayground_ExpectNotFound() throws Exception {
        when(playgroundService.getPlayground(anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/playgrounds/2/player/0/remove")
                .contentType(MediaType.APPLICATION_JSON)
                .content(CONTENT)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("Playground with id 2 not found", result.getResolvedException().getMessage());
    }

    @Test
    public void removePlayerFromPlayground_NonExistingUser_ExpectNotFound() throws Exception {
        when(playgroundService.getPlayground(anyInt())).thenReturn(PLAYGROUND);
        when(userService.getUser(anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/playgrounds/0/player/2/remove")
                .contentType(MediaType.APPLICATION_JSON)
                .content(CONTENT)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("User with id 2 not found", result.getResolvedException().getMessage());
    }
}

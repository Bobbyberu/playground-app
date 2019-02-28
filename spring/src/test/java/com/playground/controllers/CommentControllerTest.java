package com.playground.controllers;

import com.playground.model.entity.Comment;
import com.playground.model.entity.Playground;
import com.playground.service.CommentService;
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

import static com.playground.controllers.ControllersUnitTestUtils.buildComment;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CommentController.class, secure = false)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @MockBean
    private PlaygroundService playgroundService;

    private Comment comment = buildComment();

    @Test
    public void testGetComments() throws Exception {
        when(commentService.getComments()).thenReturn(Arrays.asList(comment));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/comments")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "[{\"id\":1,\"playgroundId\":1,\"author\":{\"id\":1,\"username\":\"user\",\"enabled\":false,\"archived\":false,\"banned\":false},\"mark\":5.0}]";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testGetCommentsByPlaygroundWithExistingPlayground() throws Exception {
        Playground playground = comment.getPlayground();

        when(playgroundService.getPlayground(anyInt())).thenReturn(playground);
        when(commentService.getCommentsByPlayground(any(Playground.class))).thenReturn(Arrays.asList(comment));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/playgrounds/1/comments")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "[{\"id\":1,\"playgroundId\":1,\"author\":{\"id\":1,\"username\":\"user\",\"enabled\":false,\"archived\":false,\"banned\":false},\"mark\":5.0}]";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testGetCommentsByPlaygroundWithNonExistingPlayground() throws Exception {
        when(playgroundService.getPlayground(Mockito.anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playgrounds/2/comments").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("Playground with id 2 not found", result.getResolvedException().getMessage());
    }

    @Test
    public void testGetCommentWithExistingPlaygroundAndExistingId() throws Exception {
        when(commentService.getComment(1)).thenReturn(comment);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/comments/1").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

        String expected = "{\"id\":1,\"playgroundId\":1,\"author\":{\"id\":1,\"username\":\"user\",\"enabled\":false,\"archived\":false,\"banned\":false},\"mark\":5.0}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testGetCommentWithExistingPlaygroundAndNonExistingId() throws Exception {
        when(playgroundService.getPlayground(Mockito.anyInt())).thenReturn(mock(Playground.class));
        when(commentService.getComment(Mockito.anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/comments/2").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse dto = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), dto.getStatus());
        assertEquals("Comment with id 2 not found for this playground", result.getResolvedException().getMessage());
    }

    @Test
    public void testCreateComment() throws Exception {
        when(playgroundService.getPlayground(Mockito.anyInt())).thenReturn(mock(Playground.class));
        when(commentService.createComment(Mockito.any(Playground.class), Mockito.any(Comment.class))).thenReturn(comment);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/playgrounds/0/comments")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"comment\":\"comment\",\"mark\":1.0}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse dto = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), dto.getStatus());

        String expected = "{\"id\":1,\"playgroundId\":1,\"author\":{\"id\":1,\"username\":\"user\",\"enabled\":false,\"archived\":false,\"banned\":false},\"mark\":5.0}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }


    @Test
    public void testDeleteCommentWithExistingId() throws Exception {
        when(commentService.getComment(Mockito.anyInt())).thenReturn(mock(Comment.class));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/comments/0")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }

    @Test
    public void testDeleteCommentWithExistingPlaygroundAndNonExistingId() throws Exception {
        when(commentService.getComment(Mockito.anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/comments/2")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResolvedException().getMessage());

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertEquals("Comment with id 2 not found for this playground", result.getResolvedException().getMessage());
    }
}

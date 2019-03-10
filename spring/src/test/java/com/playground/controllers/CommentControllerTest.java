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

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CommentController.class, secure = false)
public class CommentControllerTest {

    @Autowired
    private CommentController commentController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @MockBean
    private PlaygroundService playgroundService;

    private Playground mockPlayground = new Playground();

    private Comment mockComment = new Comment(mockPlayground, null, "comment", 5.0);

    @Test
    public void testGetCommentsExpectOk() throws Exception {
        Mockito.when(commentService.getComments()).thenReturn(Arrays.asList(mockComment));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/comments")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse().getContentAsString());

        String expected = "[{\"id\":0,\"playground\":{\"id\":0,\"name\":null,\"covered\":false,\"latitude\":0.0,\"longitude\":0.0,\"surface\":null,\"description\":null,\"averageMark\":0.0,\"imageName\":null,\"players\":null,\"sports\":null,\"city\":null,\"address\":null,\"private\":false},\"author\":null,\"archived\":false,\"comment\":\"comment\",\"mark\":5.0}]";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testGetCommentsByPlaygroundWithExistingPlaygroundExpectOk() throws Exception {
        Mockito.when(playgroundService.getPlayground(Mockito.anyInt())).thenReturn(mockPlayground);
        Mockito.when(commentService.getCommentsByPlayground(Mockito.any(Playground.class))).thenReturn(Arrays.asList(mockComment));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/playgrounds/0/comments")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse().getContentAsString());

        String expected = "[{\"id\":0,\"playground\":{\"id\":0,\"name\":null,\"covered\":false,\"latitude\":0.0,\"longitude\":0.0,\"surface\":null,\"description\":null,\"averageMark\":0.0,\"imageName\":null,\"players\":null,\"sports\":null,\"city\":null,\"address\":null,\"private\":false},\"author\":null,\"archived\":false,\"comment\":\"comment\",\"mark\":5.0}]";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testGetCommentsByPlaygroundWithNonExistingPlaygroundExpectNotFound() throws Exception {
        int id = 2;

        Mockito.when(playgroundService.getPlayground(Mockito.anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playgrounds/" + id + "/comments").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(result.getResolvedException().getMessage());

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("Playground with id "+ id +" not found", result.getResolvedException().getMessage());
    }

    @Test
    public void testGetCommentWithExistingPlaygroundAndExistingIdExpectOk() throws Exception {
        Mockito.when(playgroundService.getPlayground(Mockito.anyInt())).thenReturn(mockPlayground);
        Mockito.when(commentService.getComment(Mockito.anyInt())).thenReturn(mockComment);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playgrounds/0/comments/0").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse().getContentAsString());

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

        String expected = "{\"id\":0,\"playground\":{\"id\":0,\"name\":null,\"covered\":false,\"latitude\":0.0,\"longitude\":0.0,\"surface\":null,\"description\":null,\"averageMark\":0.0,\"imageName\":null,\"players\":null,\"sports\":null,\"city\":null,\"address\":null,\"private\":false},\"author\":null,\"archived\":false,\"comment\":\"comment\",\"mark\":5.0}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testGetCommentWithExistingPlaygroundAndNonExistingIdExpectNoFound() throws Exception {
        int id = 2;

        Mockito.when(playgroundService.getPlayground(Mockito.anyInt())).thenReturn(mockPlayground);
        Mockito.when(commentService.getComment(Mockito.anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playgrounds/0/comments/" + id).accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse dto = result.getResponse();

        System.out.println(result.getResolvedException().getMessage());

        assertEquals(HttpStatus.NOT_FOUND.value(), dto.getStatus());
        assertEquals("Comment with id "+ id +" not found for this playground", result.getResolvedException().getMessage());
    }

    @Test
    public void testGetCommentWithNonExistingPlaygroundExpectNoFound() throws Exception {
        int id = 2;

        Mockito.when(playgroundService.getPlayground(Mockito.anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playgrounds/" + id + "/comments/0").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(result.getResolvedException().getMessage());

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("Playground with id "+ id +" not found", result.getResolvedException().getMessage());
    }

    @Test
    public void testCreateCommentExpectCreated() throws Exception {
        Comment mockComment = new Comment(mockPlayground, null, "comment", 1.0);

        Mockito.when(playgroundService.getPlayground(Mockito.anyInt())).thenReturn(mockPlayground);
        Mockito.when(commentService.createComment(Mockito.any(Playground.class), Mockito.any(Comment.class))).thenReturn(mockComment);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/playgrounds/0/comments")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"comment\":\"comment\",\"mark\":1.0}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse dto = result.getResponse();

        System.out.println(dto.getContentAsString());

        assertEquals(HttpStatus.CREATED.value(), dto.getStatus());

        String expected = "{\"id\":0,\"playground\":{\"id\":0,\"name\":null,\"covered\":false,\"latitude\":0.0,\"longitude\":0.0,\"surface\":null,\"description\":null,\"averageMark\":0.0,\"imageName\":null,\"players\":null,\"sports\":null,\"city\":null,\"address\":null,\"private\":false},\"author\":null,\"archived\":false,\"comment\":\"comment\",\"mark\":1.0}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }


    @Test
    public void testDeleteCommentWithExistingIdExpectNoContent() throws Exception {
        Mockito.when(commentService.getComment(Mockito.anyInt())).thenReturn(mockComment);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/comments/0")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }

    @Test
    public void testDeleteCommentWithExistingPlaygroundAndNonExistingIdExpectNotFound() throws Exception {
        int id = 2;

        Mockito.when(commentService.getComment(Mockito.anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/comments/" + id)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResolvedException().getMessage());

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertEquals("Comment with id "+ id +" not found for this playground", result.getResolvedException().getMessage());
    }
}

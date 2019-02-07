package com.playground.controllers;

import com.playground.model.entity.ReportComment;
import com.playground.model.entity.Comment;
import com.playground.service.ReportCommentService;
import com.playground.service.CommentService;
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
@WebMvcTest(value = ReportCommentController.class, secure = false)
public class ReportCommentControllerTest {

    @Autowired
    private ReportCommentController reportCommentController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportCommentService reportCommentService;

    @MockBean
    private CommentService commentService;

    private Comment mockComment = new Comment();

    private ReportComment mockReportComment = new ReportComment(null, mockComment, "description");

    @Test
    public void testGetReportCommentsExpectOk() throws Exception {
        Mockito.when(reportCommentService.getReportComments()).thenReturn(Arrays.asList(mockReportComment));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/reportComments")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse().getContentAsString());

        String expected = "[{\"id\":0,\"comment\":{\"id\":0,\"playground\":null,\"author\":null,\"archived\":false,\"comment\":null,\"mark\":0.0},\"author\":null,\"description\":\"description\"}]";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testGetReportCommentWithExistingCommentAndExistingIdExpectOk() throws Exception {
        Mockito.when(commentService.getComment(Mockito.anyInt())).thenReturn(mockComment);
        Mockito.when(reportCommentService.getReportComment(Mockito.anyInt())).thenReturn(mockReportComment);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/reportComments/0").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse().getContentAsString());

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

        String expected = "{\"id\":0,\"comment\":{\"id\":0,\"playground\":null,\"author\":null,\"archived\":false,\"comment\":null,\"mark\":0.0},\"author\":null,\"description\":\"description\"}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testGetReportCommentWithExistingCommentAndNonExistingIdExpectNoFound() throws Exception {
        int id = 2;

        Mockito.when(commentService.getComment(Mockito.anyInt())).thenReturn(mockComment);
        Mockito.when(reportCommentService.getReportComment(Mockito.anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/reportComments/" + id).accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(result.getResolvedException().getMessage());

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("ReportComment with id "+ id +" not found for this comment", result.getResolvedException().getMessage());
    }

    @Test
    public void testGetReportCommentWithNonExistingCommentExpectNoFound() throws Exception {
        int id = 2;

        Mockito.when(commentService.getComment(Mockito.anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/comments/" + id + "/reportComments/0").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(result.getResolvedException().getMessage());

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("Comment with id "+ id +" not found", result.getResolvedException().getMessage());
    }

    @Test
    public void testCreateReportCommentExpectCreated() throws Exception {
        ReportComment mockReportComment = new ReportComment(null, mockComment, "description");

        Mockito.when(commentService.getComment(Mockito.anyInt())).thenReturn(mockComment);
        Mockito.when(reportCommentService.createReportComment(Mockito.any(Comment.class), Mockito.any(ReportComment.class))).thenReturn(mockReportComment);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/comments/0/reportComments")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"description\":\"description\"}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        String expected = "{\"id\":0,\"comment\":{\"id\":0,\"playground\":null,\"author\":null,\"archived\":false,\"comment\":null,\"mark\":0.0},\"author\":null,\"description\":\"description\"}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }


    @Test
    public void testDeleteReportCommentWithExistingCommentAndExistingIdExpectNoContent() throws Exception {
        Mockito.when(commentService.getComment(Mockito.anyInt())).thenReturn(mockComment);
        Mockito.when(reportCommentService.getReportComment(Mockito.anyInt())).thenReturn(mockReportComment);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/reportComments/0")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }

    @Test
    public void testDeleteReportCommentWithNonExistingCommentExpectNotFound() throws Exception {
        int id = 2;

        Mockito.when(commentService.getComment(Mockito.anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/comments/" + id + "/reportComments/0")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResolvedException().getMessage());

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertEquals("Comment with id "+ id +" not found", result.getResolvedException().getMessage());
    }

    @Test
    public void testDeleteReportCommentWithExistingCommentAndNonExistingIdExpectNotFound() throws Exception {
        int id = 2;

        Mockito.when(commentService.getComment(Mockito.anyInt())).thenReturn(mockComment);
        Mockito.when(reportCommentService.getReportComment(Mockito.anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/reportComments/" + id)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResolvedException().getMessage());

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertEquals("ReportComment with id "+ id +" not found for this comment", result.getResolvedException().getMessage());
    }
}

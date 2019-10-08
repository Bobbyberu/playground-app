package com.playground.controllers;

import com.playground.model.entity.Comment;
import com.playground.model.entity.ReportComment;
import com.playground.model.entity.User;
import com.playground.service.CommentService;
import com.playground.service.ReportCommentService;
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

import static com.playground.controllers.ControllersUnitTestUtils.buildComment;
import static com.playground.controllers.ControllersUnitTestUtils.buildReportComment;
import static com.playground.controllers.ControllersUnitTestUtils.buildUser;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ReportCommentController.class, secure = false)
public class ReportCommentControllerTest {

    private final static String CONTENT = "{\"description\":\"description\"}";

    private final static Comment COMMENT = buildComment();
    private final static User AUTHOR = buildUser();
    private final static ReportComment REPORT_COMMENT = buildReportComment(COMMENT, AUTHOR);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportCommentService reportCommentService;

    @MockBean
    private CommentService commentService;

    @Test
    public void getReportComments_ExpectOK() throws Exception {
        when(reportCommentService.getReportComments()).thenReturn(Arrays.asList(REPORT_COMMENT));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/reportComments")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

        String expected = "[{\"id\":0,\"comment\":{\"id\":1,\"playgroundId\":1,\"author\":{\"id\":1,\"username\":\"user\",\"enabled\":false,\"archived\":false,\"banned\":false},\"mark\":0.0},\"author\":{\"id\":1,\"username\":\"user\",\"enabled\":false,\"archived\":false,\"banned\":false}}]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getReportComment_ExistingCommentAndExistingId_ExpectOK() throws Exception {
        when(commentService.getComment(anyInt())).thenReturn(COMMENT);
        when(reportCommentService.getReportComment(anyInt())).thenReturn(REPORT_COMMENT);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/reportComments/0")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

        String expected = "{\"id\":0,\"comment\":{\"id\":1,\"playgroundId\":1,\"author\":{\"id\":1,\"username\":\"user\",\"enabled\":false,\"archived\":false,\"banned\":false},\"mark\":0.0},\"author\":{\"id\":1,\"username\":\"user\",\"enabled\":false,\"archived\":false,\"banned\":false}}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getReportComment_ExistingCommentAndNonExistingId_ExpectNotFound() throws Exception {
        when(commentService.getComment(anyInt())).thenReturn(COMMENT);
        when(reportCommentService.getReportComment(anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/reportComments/2")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("ReportComment with id 2 not found for this comment", result.getResolvedException().getMessage());
    }

    @Test
    public void createReportComment_ExpectCreated() throws Exception {
        when(commentService.getComment(anyInt())).thenReturn(COMMENT);
        when(reportCommentService.createReportComment(any(Comment.class), any(ReportComment.class))).thenReturn(REPORT_COMMENT);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/comments/0/reportComments")
                .accept(MediaType.APPLICATION_JSON)
                .content(CONTENT)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        String expected = "{\"id\":0,\"comment\":{\"id\":1,\"playgroundId\":1,\"author\":{\"id\":1,\"username\":\"user\",\"enabled\":false,\"archived\":false,\"banned\":false},\"mark\":0.0},\"author\":{\"id\":1,\"username\":\"user\",\"enabled\":false,\"archived\":false,\"banned\":false}}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }


    @Test
    public void deleteReportComment_ExistingCommentAndExistingId_ExpectNoContent() throws Exception {
        when(commentService.getComment(anyInt())).thenReturn(COMMENT);
        when(reportCommentService.getReportComment(anyInt())).thenReturn(REPORT_COMMENT);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/reportComments/0")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }

    @Test
    public void deleteReportComment_NonExistingComment_ExpectNotFound() throws Exception {
        when(commentService.getComment(anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/reportComments/2")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertEquals("ReportComment with id 2 not found for this comment", result.getResolvedException().getMessage());
    }
}

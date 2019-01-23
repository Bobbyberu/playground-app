package com.playground.controllers;

import com.playground.model.Role;
import com.playground.service.RoleService;
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
@WebMvcTest(value = RoleController.class, secure = false)
public class RoleControllerTest {

    @Autowired
    private RoleController roleController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    private Role mockRole = new Role("ROLE_TEST");

    @Test
    public void testGetRolesExpectOk() throws Exception {
        Mockito.when(roleService.getRoles()).thenReturn(Arrays.asList(mockRole));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/roles")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse().getContentAsString());

        String expected = "[{\"id\":0,\"name\":\"ROLE_TEST\",\"authority\":\"ROLE_TEST\"}]";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testGetRoleWithExistingIdExpectOk() throws Exception {
        Mockito.when(roleService.getRole(Mockito.anyInt())).thenReturn(mockRole);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/roles/0").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse().getContentAsString());

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

        String expected = "{\"id\":0,\"name\":\"ROLE_TEST\", \"authority\":\"ROLE_TEST\"}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testGetRoleWithNonExistingIdExpectNoFound() throws Exception {
        int id = 2;

        Mockito.when(roleService.getRole(Mockito.anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/roles/" + id).accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(result.getResolvedException().getMessage());

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("Role with id "+ id +" not found", result.getResolvedException().getMessage());
    }

    @Test
    public void testCreateRoleExpectCreated() throws Exception {
        Role mockRole = new Role("Role");

        Mockito.when(roleService.createRole(Mockito.any(Role.class))).thenReturn(mockRole);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/roles")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Role\"}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        String expected = "{\"id\":0,\"name\":\"Role\", \"authority\":\"Role\"}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testUpdateRoleWithExistingIdExpectOk() throws Exception {
        Role newMockRole = new Role("ROLE_CHANGE");
        newMockRole.setId(mockRole.getId());

        Mockito.when(roleService.getRole(Mockito.anyInt())).thenReturn(mockRole);
        Mockito.when(roleService.updateRole(Mockito.anyInt(), Mockito.any(Role.class))).thenReturn(newMockRole);

        String content = "{\"name\":\"ROLE_CHANGE\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/roles/" + mockRole.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content)
                ;

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        String expected = "{\"id\":0,\"name\":\"ROLE_CHANGE\", \"authority\":\"ROLE_CHANGE\"}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testUpdateRoleWithNonExistingIdExpectNotFound() throws Exception {
        int id = 2;

        Mockito.when(roleService.getRole(Mockito.anyInt())).thenReturn(null);

        String content = "{\"name\":\"ROLE\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/roles/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content)
                ;

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(result.getResolvedException().getMessage());

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("Role with id "+ id +" not found", result.getResolvedException().getMessage());
    }

    @Test
    public void testDeleteRoleWithExpectingIdExpectNoContent() throws Exception {
        Mockito.when(roleService.getRole(Mockito.anyInt())).thenReturn(mockRole);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/roles/0")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }

    @Test
    public void testDeleteRoleWithNonExpectingIdExpectNotFound() throws Exception {
        int id = 2;

        Mockito.when(roleService.getRole(Mockito.anyInt())).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/roles/" + id)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResolvedException().getMessage());

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertEquals("Role with id "+ id +" not found", result.getResolvedException().getMessage());
    }
}

package com.modulenotfound.motivation.rest;

import com.modulenotfound.motivation.domain.Kid;
import com.modulenotfound.motivation.service.KidService;
import com.modulenotfound.motivation.service.UnknownKidIdException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(KidController.class)
public class KidControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KidService kidService;

    @Test
    public void getAllKids() throws Exception {
        when(this.kidService.getAllKids()).thenReturn(Arrays.asList(
                Kid.builder().id(1).name("kid1").point(10).build(),
                Kid.builder().id(2).name("kid2").point(20).build(),
                Kid.builder().id(3).name("kid3").point(30).build()
        ));

        this.mockMvc.perform(get("/api/kids").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..name", containsInAnyOrder("kid1", "kid2", "kid3")));
    }

    @Test
    public void addKid() throws Exception {
        when(kidService.addKid(any())).thenReturn(Kid.builder().id(1).name("kid1").point(10).build());

        String jsonFromFile = new String(Files.readAllBytes((new File(getClass().getResource("/kid.json").getPath())).toPath()));
        this.mockMvc.perform(post("/api/kids")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFromFile))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("kid1")))
                .andExpect(jsonPath("$.point", is(10)));
    }

    @Test
    public void getKid() throws Exception {
        when(kidService.getKid(1L)).thenReturn(Kid.builder().id(1).name("kid1").point(10).build());

        this.mockMvc.perform(get("/api/kid/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("kid1")))
                .andExpect(jsonPath("$.point", is(10)));
    }

    @Test
    public void getKid_idNotExist() throws Exception {
        when(kidService.getKid(1L)).thenThrow(new UnknownKidIdException("id not exist"));

        this.mockMvc.perform(get("/api/kid/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(100)));
    }

    @Test
    public void deleteKid_idNotExist() throws Exception {
        doThrow(new UnknownKidIdException("id not exist")).when(kidService).removeKid(1L);

        this.mockMvc.perform(delete("/api/kid/1"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
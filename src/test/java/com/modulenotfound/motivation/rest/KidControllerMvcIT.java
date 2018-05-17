package com.modulenotfound.motivation.rest;

import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.nio.file.Files;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class KidControllerMvcIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void addKid() throws Exception {
        String jsonFromFile = new String(Files.readAllBytes((new File(getClass().getResource("/kid.json").getPath())).toPath()));
        this.mockMvc.perform(post("/api/kids")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFromFile))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", not(0)))
                .andExpect(jsonPath("$.name", is("kid1")))
                .andExpect(jsonPath("$.point", is(10)));
    }

    @Test
    public void getAllKids() throws Exception {
        String jsonFromFile = new String(Files.readAllBytes((new File(getClass().getResource("/kid.json").getPath())).toPath()));
        this.mockMvc.perform(post("/api/kids")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFromFile));

        this.mockMvc.perform(get("/api/kids"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("kid1")));
    }

    @Test
    public void getKid() throws Exception {
        String jsonFromFile = new String(Files.readAllBytes((new File(getClass().getResource("/kid.json").getPath())).toPath()));
        MvcResult mvcResult = this.mockMvc.perform(post("/api/kids")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFromFile))
                .andReturn();

        int id = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.id");

        this.mockMvc.perform(get("/api/kid/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("kid1")))
                .andExpect(jsonPath("$.point", is(10)))
                .andExpect(jsonPath("$.id", is(id)));
    }

    @Test
    public void getKid_idNotExist() throws Exception {
        this.mockMvc.perform(get("/api/kid/2345"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(100)));
    }

    @Test
    public void deleteKid() throws Exception {
        String jsonFromFile = new String(Files.readAllBytes((new File(getClass().getResource("/kid.json").getPath())).toPath()));
        MvcResult mvcResult = this.mockMvc.perform(post("/api/kids")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFromFile))
                .andReturn();
        int id = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.id");

        this.mockMvc.perform(delete("/api/kid/" + id))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteKid_idNotExist() throws Exception {
        this.mockMvc.perform(delete("/api/kid/2345"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(100)));
    }
}
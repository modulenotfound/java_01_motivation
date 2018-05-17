package com.modulenotfound.motivation.domain;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class KidJsonTest {

    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testSerialize() throws Exception {
        List<Kid> kids = Arrays.asList(
                Kid.builder().id(1).name("kid1").point(10).build(),
                Kid.builder().id(2).name("kid2").point(20).build(),
                Kid.builder().id(3).name("kid3").point(30).build()
        );

        String jsonFromJava = this.objectMapper.writeValueAsString(kids);

        String jsonFromFile = new String(Files.readAllBytes((new File(getClass().getResource("/kids.json").getPath())).toPath()));

        JSONAssert.assertEquals(jsonFromFile, jsonFromJava, true);
    }

    @Test
    public void testDeserialize() throws Exception {
        Kid[] kids = objectMapper.readValue(new File(getClass().getResource("/kids.json").getPath()), Kid[].class);
        assertThat(Arrays.stream(kids).map(kid -> kid.getName()).collect(Collectors.toSet())).containsExactly("kid1", "kid2", "kid3");
    }
}
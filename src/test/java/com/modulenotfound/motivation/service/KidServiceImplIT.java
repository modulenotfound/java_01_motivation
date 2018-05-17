package com.modulenotfound.motivation.service;

import com.modulenotfound.motivation.domain.Kid;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class KidServiceImplIT {

    @Autowired
    private KidService kidService;

    @Test
    public void getAllKids() {
        this.kidService.addKid(Kid.builder()
                .name("kid1")
                .point(10)
                .build());
        this.kidService.addKid(Kid.builder()
                .name("kid2")
                .point(20)
                .build());
        this.kidService.addKid(Kid.builder()
                .name("kid3")
                .point(30)
                .build());

        List<Kid> allKids = this.kidService.getAllKids();
        assertThat(allKids.stream().map(kid -> kid.getName()).collect(Collectors.toSet()))
                .containsExactly("kid1", "kid2", "kid3");
    }

    @Test
    public void addAndGetKid() throws Exception {
        Kid someKid = this.kidService.addKid(Kid.builder()
                .name("someKid")
                .point(10)
                .build());

        Kid kid = this.kidService.getKid(someKid.getId());
        assertThat(kid.getName()).isEqualTo("someKid");
    }

    @Test
    public void removeKid() throws Exception {
        Kid someKid = this.kidService.addKid(Kid.builder()
                .name("someKid")
                .point(10)
                .build());

        this.kidService.removeKid(someKid.getId());
    }

    @Test(expected = UnknownKidIdException.class)
    public void removeKid_IdNotExist() throws Exception {
        this.kidService.removeKid(1234L);
    }
}
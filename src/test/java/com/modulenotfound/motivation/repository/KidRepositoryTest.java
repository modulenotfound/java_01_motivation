package com.modulenotfound.motivation.repository;

import com.modulenotfound.motivation.domain.Kid;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class KidRepositoryTest {

    @Autowired
    private KidRepository repository;

    @Test
    public void saveAndFindKidById() {
        Kid kid = Kid.builder()
                .name("kid")
                .point(10)
                .build();
        Kid savedKid = this.repository.save(kid);

        Optional<Kid> foundKid = this.repository.findById(savedKid.getId());
        assertThat(foundKid.get().getName()).isEqualTo("kid");
        assertThat(foundKid.get().getPoint()).isEqualTo(10);
    }

    @Test
    public void saveAndFindAllKids() {
        List<Kid> kids = Arrays.asList(
                Kid.builder().name("kid1").point(10).build(),
                Kid.builder().name("kid2").point(20).build(),
                Kid.builder().name("kid3").point(30).build()
        );
        this.repository.saveAll(kids);

        Iterable<Kid> allKids = this.repository.findAll();
        assertThat(Lists.newArrayList(allKids).size()).isEqualTo(3);
        assertThat(Lists.newArrayList(allKids).stream().map(kid -> kid.getName()).toArray()).contains("kid1", "kid2", "kid3");
    }

    @Test
    public void findKidsByPointGreaterThan() {
        List<Kid> kids = Arrays.asList(
                Kid.builder().name("kid1").point(10).build(),
                Kid.builder().name("kid2").point(20).build(),
                Kid.builder().name("kid3").point(30).build()
        );
        this.repository.saveAll(kids);

        List<Kid> kidsFound = this.repository.findKidsByPointGreaterThan(15);
        assertThat(kidsFound.size()).isEqualTo(2);
        assertThat(kidsFound.stream().map(kid -> kid.getName()).toArray()).contains("kid2", "kid3");
    }

    @Test
    public void findKidsByPointGreaterThan_emptyList() {
        List<Kid> kids = Arrays.asList(
                Kid.builder().name("kid1").point(10).build(),
                Kid.builder().name("kid2").point(20).build(),
                Kid.builder().name("kid3").point(30).build()
        );
        this.repository.saveAll(kids);

        List<Kid> kidsByPointGreaterThan = this.repository.findKidsByPointGreaterThan(40);
        assertThat(kidsByPointGreaterThan).isEmpty();
    }

    @Test
    public void findKidsByPointGreaterThan_withQuery() {
        List<Kid> kids = Arrays.asList(
                Kid.builder().name("kid1").point(10).build(),
                Kid.builder().name("kid2").point(20).build(),
                Kid.builder().name("kid3").point(30).build()
        );
        this.repository.saveAll(kids);

        List<Kid> kidsFound = this.repository.findKidsByPointGreaterThan_withQuery(15);
        assertThat(kidsFound.size()).isEqualTo(2);
        assertThat(kidsFound.stream().map(kid -> kid.getName()).toArray()).contains("kid2", "kid3");
    }
}
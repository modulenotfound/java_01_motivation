package com.modulenotfound.motivation.service;

import com.modulenotfound.motivation.domain.Kid;
import com.modulenotfound.motivation.repository.KidRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class KidServiceImplTest {

    @Mock
    private KidRepository kidRepository;

    @InjectMocks
    private KidServiceImpl kidService;

    @Test
    public void getAllKids_shouldCallRepo() {
        this.kidService.getAllKids();
        verify(this.kidRepository).findAll();
    }

    @Test
    public void getAllKids() {
        when(this.kidRepository.findAll()).thenReturn(Arrays.asList(
                Kid.builder().name("kid1").build(),
                Kid.builder().name("kid2").build()
        ));

        List<Kid> allKids = this.kidService.getAllKids();

        assertThat(allKids.size()).isEqualTo(2);
    }
}
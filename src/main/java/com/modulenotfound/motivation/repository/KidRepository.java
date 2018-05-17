package com.modulenotfound.motivation.repository;

import com.modulenotfound.motivation.domain.Kid;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface KidRepository extends CrudRepository<Kid, Long> {
    List<Kid> findKidsByPointGreaterThan(int point);

    @Query("SELECT kid FROM Kid kid WHERE kid.point > ?1")
    List<Kid> findKidsByPointGreaterThan_withQuery(int point);
}

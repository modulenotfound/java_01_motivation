package com.modulenotfound.motivation.service;

import com.modulenotfound.motivation.domain.Kid;
import com.modulenotfound.motivation.repository.KidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KidServiceImpl implements KidService {

    @Autowired
    private KidRepository kidRepository;

    @Override
    public List<Kid> getAllKids() {
        List<Kid> kids = new ArrayList<>();
        kidRepository.findAll().forEach(kids::add);
        return kids;
    }

    @Override
    public Kid getKid(long id) throws UnknownKidIdException {
        Optional<Kid> optionalKid = kidRepository.findById(id);
        if (optionalKid.isPresent()) {
            return optionalKid.get();
        } else {
            throw new UnknownKidIdException("Kid id not exist!");
        }
    }

    @Override
    public Kid addKid(Kid newKid) {
        return kidRepository.save(newKid);
    }

    @Override
    public void removeKid(long id) throws UnknownKidIdException {
        try {
            kidRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new UnknownKidIdException("Kid id not exist!");
        }
    }
}

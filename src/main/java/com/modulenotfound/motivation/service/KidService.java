package com.modulenotfound.motivation.service;

import com.modulenotfound.motivation.domain.Kid;

import java.util.List;

public interface KidService {
    List<Kid> getAllKids();
    Kid getKid(long id) throws UnknownKidIdException;
    Kid addKid(Kid newKid);
    void removeKid(long id) throws UnknownKidIdException;
}

package com.modulenotfound.motivation.rest;

import com.modulenotfound.motivation.domain.Kid;
import com.modulenotfound.motivation.service.UnknownKidIdException;
import com.modulenotfound.motivation.service.KidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class KidController {

    @Autowired
    private KidService kidService;

    @GetMapping(path = "/api/kids")
    public List<Kid> getAllKids() {
        return kidService.getAllKids();
    }

    @GetMapping(path = "/api/kid/{id}")
    public Kid getKid(@PathVariable("id") long id) throws UnknownKidIdException {
        return kidService.getKid(id);
    }

    @PostMapping(path = "/api/kids", consumes = "application/json")
    public Kid addKid(@RequestBody Kid newKid) {
        return kidService.addKid(newKid);
    }

    @DeleteMapping("/api/kid/{id}")
    public void removeKid(@PathVariable("id") long id) throws UnknownKidIdException {
        kidService.removeKid(id);
    }

    @ExceptionHandler(UnknownKidIdException.class)
    protected ResponseEntity handleKidIdUnknownException(UnknownKidIdException ex) {
        return ResponseEntity.badRequest().body(new RestError(RestError.UNKNOWN_KID_ID, ex.getMessage()));
    }
}




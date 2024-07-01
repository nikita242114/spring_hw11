package ru.gb.controller;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import ru.gb.model.Issue;
import ru.gb.aspects.Timer;
import ru.gb.model.Reader;
import ru.gb.service.ReaderService;

import java.util.List;

@RestController
@RequestMapping("/readers")

public class ReaderController {
    private final ReaderService readerService;

    @Autowired
    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }
    // получить читателя по id
    @GetMapping("/{id}")
    public ResponseEntity<Reader> getReaderById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(readerService.getReaderById(id));
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //получить список всех читателей
    @Timer
    @GetMapping
    public ResponseEntity<List<Reader>> getAllReaders() {
        return ResponseEntity.ok(readerService.getAllReaders()); }


    // создание читателя
    @PostMapping
    public ResponseEntity<Reader> addReader(@RequestBody Reader reader){
        return new ResponseEntity<>(readerService.addReader(reader),HttpStatus.CREATED);
    }

    //обновление читателей
    @PutMapping("/{id}")
    public ResponseEntity<Reader> updateReaders(@PathVariable Long id, @RequestBody Reader reader){
        return ResponseEntity.ok(readerService.updateReaders(id, reader));
    }

    // удаление читателя
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReader(@PathVariable Long id){
        readerService.deleteReader(id);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }
    //получить список запросов по id читателя
//    @GetMapping("/{id}/issue")
//    public List<Issue> getAllIssueByReaderId(@PathVariable Long id){
//        return readerService.getAllIssueByReaderId(id);
//    }
}
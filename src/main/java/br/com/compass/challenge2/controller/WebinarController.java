package br.com.compass.challenge2.controller;

import br.com.compass.challenge2.entity.Webinar;
import br.com.compass.challenge2.records.WebinarRecord;
import br.com.compass.challenge2.service.WebinarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/webinars")
public class WebinarController {

    private WebinarService webinarService;

    @Autowired
    public WebinarController(WebinarService webinarService) {
        this.webinarService = webinarService;
    }

    @GetMapping
    public ResponseEntity<List<Webinar>> findAllGroups() {

        List<Webinar> webinars = webinarService.findAll();
        return new ResponseEntity<>(webinars, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Webinar> findWebinarById(@PathVariable Long id) {

        Webinar webinar = webinarService.findById(id);
        return new ResponseEntity<>(webinar, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Webinar> createWebinar(@Valid @RequestBody WebinarRecord webinarRecord) {

        Webinar newWebinar = new Webinar(0L, webinarRecord.name(), webinarRecord.datetime());
        webinarService.save(newWebinar);
        return new ResponseEntity<>(newWebinar, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Webinar> updateWebinar(@PathVariable Long id,
                                             @Valid @RequestBody WebinarRecord webinarRecord) {

        Webinar webinar = new Webinar(id, webinarRecord.name(), webinarRecord.datetime());
        Webinar updatedWebinar = webinarService.update(webinar);

        return new ResponseEntity<>(updatedWebinar, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Webinar> deleteWebinarById(@PathVariable Long id) {
        Webinar webinar = webinarService.findById(id);

        Webinar deletedWebinar = webinarService.deleteById(id);
        return new ResponseEntity<>(deletedWebinar, HttpStatus.OK);
    }

}

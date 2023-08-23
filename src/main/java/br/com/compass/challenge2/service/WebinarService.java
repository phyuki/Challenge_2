package br.com.compass.challenge2.service;

import br.com.compass.challenge2.entity.Organizer;
import br.com.compass.challenge2.entity.Student;
import br.com.compass.challenge2.entity.Webinar;
import br.com.compass.challenge2.repository.WebinarRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class WebinarService implements CrudService<Webinar>{

    private final WebinarRepository webinarRepository;

    @Autowired
    public WebinarService(WebinarRepository webinarRepository) {
        this.webinarRepository = webinarRepository;
    }

    @Override
    public Webinar save(Webinar webinar) {
        return webinarRepository.save(webinar);
    }

    @Override
    public List<Webinar> findAll() {
        return webinarRepository.findAll();
    }

    @Override
    public Webinar findById(Long id) {
        try {
            return webinarRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Repository does not exist with id: " + id);
        }
    }

    @Override
    public Webinar update(Webinar webinar) {
        if (webinarRepository.existsById(webinar.getId())) {
            return webinarRepository.save(webinar);
        } else {
            throw new EntityNotFoundException("Webinar with id " + webinar.getId() + " not found.");
        }
    }

    @Override
    public Webinar deleteById(Long id) {
        Webinar webinar;
        try {
            webinar = webinarRepository.findById(id).get();
            webinarRepository.deleteById(id);
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Webinar does not exist with id: " + id);
        }
        return webinar;
    }
}

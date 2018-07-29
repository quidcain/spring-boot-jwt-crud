package org.quidcain.jad.backend.controller;

import org.quidcain.jad.backend.model.Sample;
import org.quidcain.jad.backend.repository.SampleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.quidcain.jad.backend.constants.ApiConstants.*;
import static org.quidcain.jad.backend.constants.ApiConstants.MESSAGE_PAYLOAD_PROPERTY;

@RestController
@RequestMapping("/samples")
public class SampleController {
    private SampleRepository sampleRepository;

    public SampleController(SampleRepository sampleRepository) {
        this.sampleRepository = sampleRepository;
    }

    @GetMapping
    public Map<String, Object> findAll(@PageableDefault(size = 2) Pageable pageable) {
        Map<String, Object> model = new HashMap<>();
        Page<Sample> page = sampleRepository.findAll(pageable);
        model.put(CURRENT_PAGE_PAYLOAD_PROPERTY, pageable.getPageNumber());
        model.put(TOTAL_PAGES_PAYLOAD_PROPERTY, page.getTotalPages());
        model.put(CONTENT_PAYLOAD_PROPERTY, page.getContent());
        return model;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Void> create(@RequestBody Sample sample) {
        sampleRepository.save(sample);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Sample> byId = sampleRepository.findById(id);
        if (byId.isPresent()) {
            return new ResponseEntity<>(byId, HttpStatus.OK);
        } else {
            return generateNotFoundResponse(id);
        }
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Sample sample) {
        sample.setId(id);
        sampleRepository.save(sample);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Sample> byId = sampleRepository.findById(id);
        if (byId.isPresent()) {
            sampleRepository.delete(byId.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return generateNotFoundResponse(id);
        }
    }

    protected ResponseEntity<?> generateNotFoundResponse(Long id) {
        HashMap<String, Object> payload = new HashMap<>();
        HttpStatus status = HttpStatus.NOT_FOUND;
        payload.put(STATUS_PAYLOAD_PROPERTY, status);
        payload.put(MESSAGE_PAYLOAD_PROPERTY, String.format("Sample with id %d is not found", id));
        return ResponseEntity.status(status).body(payload);
    }

}

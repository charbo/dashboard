package org.dashboard.back.rest;

import org.dashboard.back.model.Source;
import org.dashboard.back.repository.SourceRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SourceApi {

    private SourceRepository sourceRepository;

    public SourceApi(SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
    }

    @RequestMapping(value = "/source",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    public List<Source> getAvailableSources() {
        return sourceRepository.findAll();
    }


    @RequestMapping(value = "/source",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    public Source createSource(@RequestBody Source source) {
        return sourceRepository.save(source);
    }

}

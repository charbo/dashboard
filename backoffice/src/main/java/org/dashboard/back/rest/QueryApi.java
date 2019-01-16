package org.dashboard.back.rest;

import org.dashboard.back.dto.QueryDTO;
import org.dashboard.back.model.Query;
import org.dashboard.back.model.Source;
import org.dashboard.back.repository.SourceRepository;
import org.dashboard.back.services.QueryService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QueryApi {

    private QueryService queryService;

    public QueryApi(QueryService queryService) {
        this.queryService = queryService;
    }


    //TODO add a filter
    @RequestMapping(value = "/query",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    public List<QueryDTO> getAvailableQueries() {
        return queryService.getQueries();
    }


    @RequestMapping(value = "/query",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    public void createSource(@RequestBody QueryDTO query) {
        this.queryService.saveQuery(query);
    }

}

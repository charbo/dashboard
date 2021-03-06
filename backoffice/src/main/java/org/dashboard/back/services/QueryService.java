package org.dashboard.back.services;

import org.dashboard.back.dto.ParameterDTO;
import org.dashboard.back.dto.QueryDTO;
import org.dashboard.back.model.Parameter;
import org.dashboard.back.model.Query;
import org.dashboard.back.model.Source;
import org.dashboard.back.repository.QueryRepository;
import org.dashboard.back.repository.SourceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//TODO manage exceptions
@Service
public class QueryService {
    private QueryRepository queryRepository;
    private SourceRepository sourceRepository;

    public QueryService(QueryRepository queryRepository, SourceRepository sourceRepository) {
        this.queryRepository = queryRepository;
        this.sourceRepository = sourceRepository;
    }

    public void saveQuery(QueryDTO queryDTO) {
        this.queryRepository.save(toQuery(queryDTO));
    }

    private Query toQuery(QueryDTO queryDTO) {
        Source source = this.sourceRepository.findByName(queryDTO.getSource());

        Query query = new Query();
        query.setName(queryDTO.getName());
        query.setSql(queryDTO.getSql());
        query.setSourceId(source.getId());
        query.setMultiSeries(queryDTO.isMulti());

        for (ParameterDTO param : queryDTO.getParameters()) {
            Parameter parameter = new Parameter();
            parameter.setName(param.getKey());
            parameter.setDefaultValue(param.getValue());
            query.addParameter(parameter);
        }

        return query;
    }

    public List<QueryDTO> getQueries() {
        return this.queryRepository.findAll().stream().map(q -> toQueryDTO(q)).collect(Collectors.toList());
    }

    private QueryDTO toQueryDTO(Query query) {
        QueryDTO dto = new QueryDTO();
        dto.setName(query.getName());
        dto.setSql(query.getSql());
        dto.setSource(this.sourceRepository.findOne(query.getSourceId()).getName());
        dto.setMulti(query.isMultiSeries());

        for (Parameter param : query.getParameters()) {
            ParameterDTO parameter = new ParameterDTO();
            parameter.setKey(param.getName());
            parameter.setValue(param.getDefaultValue());
            dto.addParameter(parameter);
        }

        return dto;
    }
}

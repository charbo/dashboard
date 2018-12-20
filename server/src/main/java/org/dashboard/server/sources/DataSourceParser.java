package org.dashboard.server.sources;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dashboard.server.datas.JdbcTemplateFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DataSourceParser {

    public static final Map<String, JdbcTemplate> parseJSon(InputStream file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Source> dataSources = mapper.readValue(file,new TypeReference<List<Source>>(){});
        return dataSources.stream().collect(Collectors.toMap(Source::getName, JdbcTemplateFactory::createJdbcTemplate));
    }
}

package org.dashboard.server.sources;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class QueryParser {

    public static final Map<String, Query> parseJSon(InputStream file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Query> queries = mapper.readValue(file,new TypeReference<List<Query>>(){});
        return queries.stream().collect(Collectors.toMap(Query::getName, Function.identity()));
    }
}
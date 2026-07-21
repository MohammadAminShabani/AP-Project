package divar.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import divar.dto.response.CityResponse;
import divar.network.ApiClient;
import divar.network.ApiException;

import java.io.IOException;
import java.util.List;

public class CityService {

    private static final ObjectMapper mapper =
            new ObjectMapper();

    public List<CityResponse> getAll()
            throws IOException, InterruptedException, ApiException {

        String json =
                ApiClient.get("/cities");

        return mapper.readValue(
                json,
                new TypeReference<List<CityResponse>>() {}
        );
    }

}
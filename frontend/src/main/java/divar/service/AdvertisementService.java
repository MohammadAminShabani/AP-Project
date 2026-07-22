package divar.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import divar.dto.request.CreateAdvertisementRequest;
import divar.dto.request.UpdateAdvertisementRequest;
import divar.dto.response.AdvertisementResponse;
import divar.network.ApiClient;
import divar.network.ApiException;

import java.io.IOException;
import java.util.List;

public class AdvertisementService {

    private static final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

    public List<AdvertisementResponse> getAll()
            throws IOException, InterruptedException, ApiException {

        String json = ApiClient.get("/ads");

        return mapper.readValue(
                json,
                new TypeReference<List<AdvertisementResponse>>() {
                }
        );
    }

    public AdvertisementResponse getById(Long id)
            throws IOException, InterruptedException, ApiException {

        String json = ApiClient.get("/ads/" + id);

        return mapper.readValue(json, AdvertisementResponse.class);
    }

    public AdvertisementResponse create(CreateAdvertisementRequest request)
            throws IOException, InterruptedException, ApiException {

        String json = mapper.writeValueAsString(request);

        System.out.println("REQUEST JSON:");
        System.out.println(json);


        String response = ApiClient.post("/ads", json);


        return mapper.readValue(response, AdvertisementResponse.class);
    }


    public AdvertisementResponse update(Long id,
                                        UpdateAdvertisementRequest request)
            throws IOException, InterruptedException, ApiException {

        String json = mapper.writeValueAsString(request);

        String response = ApiClient.put("/ads/" + id, json);

        return mapper.readValue(response, AdvertisementResponse.class);
    }


    public void delete(Long id)
            throws IOException, InterruptedException, ApiException {

        ApiClient.delete("/ads/" + id);
    }

    public AdvertisementResponse markAsSold(Long id)
            throws IOException, InterruptedException, ApiException {

        String response = ApiClient.put("/ads/" + id + "/sold", "");

        return mapper.readValue(response, AdvertisementResponse.class);
    }


    public AdvertisementResponse uploadImage(
            Long advertisementId,
            java.io.File image
    )
            throws IOException,
            InterruptedException,
            ApiException {

        String response =
                ApiClient.uploadFile(
                        "/ads/" +
                                advertisementId +
                                "/images",
                        image
                );

        return mapper.readValue(
                response,
                AdvertisementResponse.class
        );
    }

}
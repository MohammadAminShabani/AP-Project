package divar.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import divar.dto.response.AdvertisementResponse;
import divar.network.ApiClient;
import divar.network.ApiException;
import divar.session.SessionManager;
import divar.util.Constants;

import java.io.IOException;
import java.util.List;

public class FavoriteService {

    private static final ObjectMapper mapper = new ObjectMapper();

    public void add(Long advertisementId)
            throws IOException, InterruptedException, ApiException {

        Long userId = SessionManager.getUserId();

        ApiClient.post(
                Constants.FAVORITE_API
                        + "?userId=" + userId
                        + "&advertisementId=" + advertisementId,
                ""
        );
    }

    public void remove(Long advertisementId)
            throws IOException, InterruptedException, ApiException {

        Long userId = SessionManager.getUserId();

        ApiClient.delete(
                Constants.FAVORITE_API
                        + "?userId=" + userId
                        + "&advertisementId=" + advertisementId
        );
    }

    public List<AdvertisementResponse> getFavorites()
            throws IOException, InterruptedException, ApiException {

        Long userId = SessionManager.getUserId();

        String response =
                ApiClient.get(
                        Constants.FAVORITE_API + "/" + userId
                );

        return mapper.readValue(
                response,
                new TypeReference<List<AdvertisementResponse>>() {});
    }

}
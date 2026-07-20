package divar.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import divar.dto.response.AdvertisementResponse;
import divar.dto.response.UserResponse;
import divar.network.ApiClient;
import divar.network.ApiException;

import java.io.IOException;
import java.util.List;

public class AdminService {

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final String ADMIN_ENDPOINT = "/admin";

    /* ================= Users ================= */

    public List<UserResponse> getAllUsers()
            throws IOException, InterruptedException, ApiException {

        String json = ApiClient.get(
                ADMIN_ENDPOINT + "/users"
        );

        return mapper.readValue(
                json,
                new TypeReference<List<UserResponse>>() {}
        );
    }

    public void blockUser(Long userId)
            throws IOException, InterruptedException, ApiException {

        ApiClient.put(
                ADMIN_ENDPOINT + "/users/" + userId + "/block",
                ""
        );
    }

    public void unblockUser(Long userId)
            throws IOException, InterruptedException, ApiException {

        ApiClient.put(
                ADMIN_ENDPOINT + "/users/" + userId + "/unblock",
                ""
        );
    }

    /* ================= Advertisements ================= */

    public List<AdvertisementResponse> getAllAdvertisements()
            throws IOException, InterruptedException, ApiException {

        String json = ApiClient.get(
                ADMIN_ENDPOINT + "/advertisements"
        );

        return mapper.readValue(
                json,
                new TypeReference<List<AdvertisementResponse>>() {}
        );
    }

    public void approveAdvertisement(Long advertisementId)
            throws IOException, InterruptedException, ApiException {

        ApiClient.put(
                ADMIN_ENDPOINT + "/advertisements/"
                        + advertisementId + "/approve",
                ""
        );
    }

    public void rejectAdvertisement(Long advertisementId)
            throws IOException, InterruptedException, ApiException {

        ApiClient.put(
                ADMIN_ENDPOINT + "/advertisements/"
                        + advertisementId + "/reject",
                ""
        );
    }

    public void deleteAdvertisement(Long advertisementId)
            throws IOException, InterruptedException, ApiException {

        ApiClient.delete(
                ADMIN_ENDPOINT + "/advertisements/"
                        + advertisementId
        );
    }

}
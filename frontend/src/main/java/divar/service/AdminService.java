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

    private static final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

    public List<UserResponse> getAllUsers()
            throws IOException, InterruptedException, ApiException {

        String json = ApiClient.get("/admin/users");

        return mapper.readValue(
                json,
                new TypeReference<List<UserResponse>>() {
                }
        );
    }

    public List<AdvertisementResponse> getAllAdvertisements()
            throws IOException, InterruptedException, ApiException {

        String json = ApiClient.get("/admin/advertisements");

        return mapper.readValue(
                json,
                new TypeReference<List<AdvertisementResponse>>() {
                }
        );
    }

    public void approveAdvertisement(Long id)
            throws IOException, InterruptedException, ApiException {

        ApiClient.put("/admin/advertisements/" + id + "/approve", "");
    }

    public void rejectAdvertisement(Long id)
            throws IOException, InterruptedException, ApiException {

        ApiClient.put("/admin/advertisements/" + id + "/reject", "");
    }

    public void deleteAdvertisement(Long id)
            throws IOException, InterruptedException, ApiException {

        ApiClient.delete("/admin/advertisements/" + id);
    }

    public void blockUser(Long id)
            throws IOException, InterruptedException, ApiException {

        ApiClient.put("/admin/users/" + id + "/block", "");
    }

    public void unblockUser(Long id)
            throws IOException, InterruptedException, ApiException {

        ApiClient.put("/admin/users/" + id + "/unblock", "");
    }
}

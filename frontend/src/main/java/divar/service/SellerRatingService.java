package divar.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import divar.dto.request.RateSellerRequest;
import divar.dto.response.SellerRatingAverageResponse;
import divar.dto.response.SellerRatingResponse;
import divar.network.ApiClient;
import divar.network.ApiException;
import divar.session.SessionManager;
import divar.util.Constants;

import java.io.IOException;
import java.util.List;

public class SellerRatingService {

    private static final ObjectMapper mapper = new ObjectMapper();

    public SellerRatingResponse rateSeller(Long advertisementId,
                                           RateSellerRequest request)
            throws IOException, InterruptedException, ApiException {

        Long buyerId = SessionManager.getUserId();

        String json = mapper.writeValueAsString(request);

        String response = ApiClient.post(
                Constants.RATING_API
                        + "?buyerId=" + buyerId
                        + "&advertisementId=" + advertisementId,
                json);

        return mapper.readValue(response, SellerRatingResponse.class);
    }

    public List<SellerRatingResponse> getRatings(Long sellerId)
            throws IOException, InterruptedException, ApiException {

        String response =
                ApiClient.get(Constants.RATING_API + "/" + sellerId);

        return mapper.readValue(
                response,
                new TypeReference<List<SellerRatingResponse>>() {
                });
    }

    public SellerRatingAverageResponse getAverage(Long sellerId)
            throws IOException, InterruptedException, ApiException {

        String response =
                ApiClient.get(
                        Constants.RATING_API
                                + "/"
                                + sellerId
                                + "/average");

        return mapper.readValue(
                response,
                SellerRatingAverageResponse.class);
    }

    public void deleteRating(Long advertisementId)
            throws IOException, InterruptedException, ApiException {

        Long buyerId = SessionManager.getUserId();

        ApiClient.delete(
                Constants.RATING_API
                        + "?buyerId=" + buyerId
                        + "&advertisementId=" + advertisementId);
    }

}
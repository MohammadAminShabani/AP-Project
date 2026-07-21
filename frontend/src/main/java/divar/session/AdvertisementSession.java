package divar.session;

import divar.dto.response.AdvertisementResponse;

public class AdvertisementSession {

    private static AdvertisementResponse advertisement;
    public static final String ADVERTISEMENT = "Advertisement.fxml";

    private AdvertisementSession() {
    }

    public static void setAdvertisement(AdvertisementResponse ad) {
        advertisement = ad;
    }

    public static AdvertisementResponse getAdvertisement() {
        return advertisement;
    }

    public static void clear() {
        advertisement = null;
    }
}
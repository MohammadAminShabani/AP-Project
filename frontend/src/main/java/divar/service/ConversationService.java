package divar.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import divar.dto.response.ConversationResponse;
import divar.network.ApiClient;
import divar.network.ApiException;
import divar.util.Constants;

import java.io.IOException;
import java.util.List;

public class ConversationService {

    private static final ObjectMapper mapper = new ObjectMapper();

    public List<ConversationResponse> getMyConversations()
            throws IOException, InterruptedException, ApiException {

        String response = ApiClient.get(
                Constants.CONVERSATION_API
        );

        return mapper.readValue(
                response,
                new TypeReference<List<ConversationResponse>>() {}
        );
    }

}
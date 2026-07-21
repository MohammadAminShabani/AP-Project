package divar.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import divar.dto.request.CreateMessageRequest;
import divar.dto.response.MessageResponse;
import divar.network.ApiClient;
import divar.network.ApiException;
import divar.util.Constants;

import java.io.IOException;
import java.util.List;

public class MessageService {

    private static final ObjectMapper mapper =
            new ObjectMapper().findAndRegisterModules();

    public List<MessageResponse> getMessages(Long conversationId)
            throws IOException, InterruptedException, ApiException {

        String response =
                ApiClient.get(
                        Constants.MESSAGE_API +
                                "/conversation/" +
                                conversationId
                );

        return mapper.readValue(
                response,
                new TypeReference<List<MessageResponse>>() {});
    }

    public MessageResponse send(CreateMessageRequest request)
            throws IOException, InterruptedException, ApiException {

        String json =
                mapper.writeValueAsString(request);

        String response =
                ApiClient.post(
                        Constants.MESSAGE_API,
                        json
                );

        return mapper.readValue(
                response,
                MessageResponse.class
        );
    }

    public void delete(Long id)
            throws IOException, InterruptedException, ApiException {

        ApiClient.delete(
                Constants.MESSAGE_API + "/" + id
        );

    }

}
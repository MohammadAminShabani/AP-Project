package divar.session;

import divar.dto.response.ConversationResponse;

public final class ConversationSession {

    private static ConversationResponse conversation;

    private ConversationSession() {
    }

    public static void setConversation(ConversationResponse conversationResponse) {
        conversation = conversationResponse;
    }

    public static ConversationResponse getConversation() {
        return conversation;
    }

    public static Long getConversationId() {
        return conversation == null ? null : conversation.getId();
    }

    public static void clear() {
        conversation = null;
    }

    public static boolean hasConversation() {
        return conversation != null;
    }
}
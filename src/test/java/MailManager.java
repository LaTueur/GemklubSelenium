import com.mailslurp.apis.*;
import com.mailslurp.clients.*;
import com.mailslurp.models.*;

import java.util.*;

class MailManager {
    private ApiClient client;
    private InboxDto inbox;
    private WaitForControllerApi waitApi;
    private Email email;

    public MailManager(String apiKey) throws ApiException {
        client = Configuration.getDefaultApiClient();
        client.setApiKey(apiKey);

        InboxControllerApi inboxApi = new InboxControllerApi(client);
        inbox = inboxApi.createInboxWithDefaults();

        waitApi = new WaitForControllerApi(client);
    }

    public MailManager(String apiKey, String inboxId) throws ApiException {
        client = Configuration.getDefaultApiClient();
        client.setApiKey(apiKey);

        InboxControllerApi inboxApi = new InboxControllerApi(client);
        UUID inboxUid = UUID.fromString(inboxId);
        inbox = inboxApi.getInbox(inboxUid);

        waitApi = new WaitForControllerApi(client);
    }

    public String getEmailAddress() {
        return inbox.getEmailAddress();
    }

    public void waitForLatestEmail() throws ApiException {
        email = waitApi.waitForLatestEmail(inbox.getId(), 3000L, true, null, null, null, null);
    }

    public String getLatestEmailSubject() {
        return email.getSubject();
    }

    public String getLatestEmailBody() {
        return email.getBody();
    }
}

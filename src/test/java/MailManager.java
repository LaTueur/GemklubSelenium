import com.mailslurp.apis.*;
import com.mailslurp.clients.*;
import com.mailslurp.models.*;

class MailManager {
    private ApiClient client;
    private InboxDto inbox;
    private WaitForControllerApi waitApi;

    public MailManager(String apiKey) throws ApiException {
        client = Configuration.getDefaultApiClient();
        client.setApiKey(apiKey);

        InboxControllerApi inboxApi = new InboxControllerApi(client);
        inbox = inboxApi.createInbox(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);

        waitApi = new WaitForControllerApi(client);
    }

    public String getEmailAddress() {
        return inbox.getEmailAddress();
    }

    public String lastestEmailSubject() throws ApiException {
        Email email = waitApi.waitForLatestEmail(inbox.getId(), 3000L, true, null, null, null, null);
        return email.getSubject();
    }
}

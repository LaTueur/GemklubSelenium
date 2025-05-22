import com.mailslurp.apis.*;
import com.mailslurp.clients.*;
import com.mailslurp.models.*;

class MailManager {
    private ApiClient client;
    private Inbox inbox;
    private WaitForControllerApi waitApi;

    public MailManager(String apiKey) {
        client = Configuration.getDefaultApiClient();
        client.setApiKey(apiKey);

        InboxControllerApi inboxApi = new InboxControllerApi(client);
        inbox = inboxApi.createInbox(null, null, null, null, null, null, null, null, null);

        waitApi = new WaitForControllerApi(client);
    }

    public String getEmailAddress() {
        return inbox.getEmailAddress();
    }

    public String lastestEmailSubject() {
        Email email = waitApi.waitForLatestEmail(inbox.getId(), 30000L, true);
        return email.getSubject();
    }
}

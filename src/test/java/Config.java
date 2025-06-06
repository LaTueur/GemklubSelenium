class Config {
    private String email;
    private String password;
    private String apiKey;
    private String inboxId;

    public Config() {
        email = getEnvOrThrow("GEMKLUB_EMAIL");
        password = getEnvOrThrow("GEMKLUB_PASSWORD");
        apiKey = getEnvOrThrow("MAILSLURP_API_KEY");
        inboxId = getEnvOrThrow("MAILSLURP_INBOX_ID");
    }

    private String getEnvOrThrow(String key) {
        String value = System.getenv(key);
        if (value == null || value.isEmpty()) {
            throw new IllegalStateException("Missing required environment variable: " + key);
        }
        return value;
    }

    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getApiKey() { return apiKey; }
    public String getInboxId() { return inboxId; }
}
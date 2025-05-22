class RandomGenerator {
    private static String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private Random random = new Random();

    public RandomGenerator() {}

    public String randomEmail() {
        return randomSequence(6) + "@gmail.com";
    }

    public String randomPassword() {
        return randomSequence(10);
    }

    private String randomSequence(int n) {
        String seq = "";
        for(int i = 0; i < n; i++){
            seq = seq + alphabet.charAt(random.nextInt(alphabet.length()));
        }
        return seq;
    }
}
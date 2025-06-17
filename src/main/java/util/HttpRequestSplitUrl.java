package util;

public class HttpRequestSplitUrl {
    public String extractUrl(String line) {
        String[] tokens = line.split(" ");
        if (tokens[0].equals("GET") && tokens[1].equals("/index.html")) {
            return tokens[1];
        }
        return "";
    }
}

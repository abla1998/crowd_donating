package org.mql.crowddonating.utilities;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.server.UID;
import java.text.Normalizer;
import java.util.Locale;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Pattern;

public class Utility {
    
    /**
     * [^\\w-] only latin characters
     * [^\pL\s-] all UTF-8 characters
     */
    private static final Pattern NONLATIN = Pattern.compile("[^\\pL\\s-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/";


    public static String toSlug(String input) {
        String nonwhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nonwhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

    public static String cleanupSpaces(String input) {
        return input.trim().replaceAll(" +", " ");
    }

    public static String upload(String path, MultipartFile file) {
        String name = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path fileNameAndPath = Paths.get(uploadDir + path, name);
        try {
            Files.write(fileNameAndPath, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return name;
    }

    public static String getBaseURL(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);
        if ((serverPort != 80) && (serverPort != 443)) {
            url.append(":").append(serverPort);
        }
        url.append(contextPath);
        if (url.toString().endsWith("/")) {
            url.append("/");
        }
        return url.toString();
    }

    public static String getJsonFromUrl(String link) {
        String json = null;
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");
            connection.connect();
            InputStream inStream = connection.getInputStream();
            json = Utility.streamToString(inStream); // input stream to string
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return json;
    }

    public static String streamToString(InputStream inputStream) {
        return new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
    }

    /**
     * @param value the double to round
     * @param places number of floating numbers
     * @return double
     */
    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();
        BigDecimal bigDecimal = new BigDecimal(Double.toString(value));
        bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_EVEN);
        return bigDecimal.doubleValue();
    }
}

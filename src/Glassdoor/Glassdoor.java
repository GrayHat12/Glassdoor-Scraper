package Glassdoor;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Glassdoor {

    private String companyName;
    private String glassdoorUrl = "";
    private String rating = "";
    
    final private String GOOGLE_SEARCH_URL = "https://www.google.com/search?q=";
    final private String GLASSDOOR_URL_REGEX = "https:\\/\\/www\\.glassdoor\\.co\\.in\\/reviews\\/[^\\.]+\\.htm";

    public Glassdoor(String companyName) {
        this.companyName = companyName.toLowerCase();
    }

    private String searchGoogle() throws IOException {
        String searchTerm = this.companyName + "+site%3Aglassdoor.co.in".trim().replaceAll(" ", "");
        Document googleSearch = Jsoup.connect(GOOGLE_SEARCH_URL+searchTerm).get();
        String pageSource = googleSearch.outerHtml();
        Pattern pattern = Pattern.compile(GLASSDOOR_URL_REGEX, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(pageSource);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return null;
    }

    private String getRating(String glassdoorUrl) throws IOException {
        Document glassdoor = Jsoup.connect(glassdoorUrl).get();
        Element ratingElement = glassdoor.selectFirst("#EmpStats .v2__EIReviewsRatingsStylesV2__ratingNum");
        if (ratingElement == null) return null;
        String ratString = ratingElement.text();
        return ratString;
    }

    public void scrape() throws Exception {
        String glassdoorUrl = this.searchGoogle();
        this.glassdoorUrl = glassdoorUrl;
        if(glassdoorUrl == null) {
            throw new Exception("Failed to get glassdoor url");
        };
        String rating = getRating(glassdoorUrl);
        this.rating = rating;
        if (rating == null) {
            throw new Exception("Failed to get rating");
        }
        if (rating.length() == 0) {
            throw new Exception("Failed to get rating");
        }
    }

    public String getGlassDoorURL() {
        return glassdoorUrl;
    }
    
    public String getRating() {
        return rating;
    }

}

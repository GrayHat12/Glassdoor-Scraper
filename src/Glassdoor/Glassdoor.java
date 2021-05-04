package Glassdoor;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Glassdoor {
    
    private GlassdoorData glassdoorData;

    final private String GOOGLE_SEARCH_URL = "https://www.google.com/search?q=";
    final private String GLASSDOOR_URL_REGEX = "https:\\/\\/www\\.glassdoor\\.co\\.in\\/reviews\\/[^\\.]+\\.htm";

    public Glassdoor(String companyName) {
        glassdoorData = new GlassdoorData(companyName.toLowerCase());
    }

    private String searchGoogle(String companyName) throws IOException {
        String searchTerm = companyName + "+site%3Aglassdoor.co.in".trim().replaceAll(" ", "");
        Document googleSearch = Jsoup.connect(GOOGLE_SEARCH_URL+searchTerm).get();
        String pageSource = googleSearch.outerHtml();
        Pattern pattern = Pattern.compile(GLASSDOOR_URL_REGEX, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(pageSource);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return null;
    }

    private String getRating(Document glassdoorpage) throws IOException {
        Element ratingElement = glassdoorpage.selectFirst("#EmpStats .v2__EIReviewsRatingsStylesV2__ratingNum");
        if (ratingElement == null) return null;
        String ratString = ratingElement.text();
        return ratString;
    }

    private Document getGlassDoorPage(String url) throws IOException {
        Document glassdoorpage = Jsoup.connect(url).get();
        return glassdoorpage;
    }

    private String getApproveOfCEO(Document document) {
        Element element = document.selectFirst("#EmpStats_Approve");
        if (element== null) return null;
        return element.attr("data-percentage");
    }

    private List<String> getCons(Document document) {
        List<String> cons = new LinkedList<>();
        Element consElement = document.selectFirst(".cons");
        if (consElement== null) return cons;
        Elements listItems = consElement.select("li");
        if (listItems== null) return cons;
        for(Element element: listItems) {
            Element span = element.selectFirst("span");
            if (span== null) continue;
            cons.add(span.text());
        }
        return cons;
    }

    private List<String> getPros(Document document) {
        List<String> pros = new LinkedList<>();
        Element consElement = document.selectFirst(".pros");
        if (consElement== null) return pros;
        Elements listItems = consElement.select("li");
        if (listItems == null) return pros;
        for(Element element: listItems) {
            Element span = element.selectFirst("span");
            if (span == null) continue;
            pros.add(span.text());
        }
        return pros;
    }

    private String getLastUpdated(Document document) {
        String text = document.outerHtml();
        Pattern pattern = Pattern.compile("Updated\\s\\d[^<]+", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return null;
    }

    private String getRecommendToFriend(Document document) {
        Element element = document.selectFirst("#EmpStats_Recommend");
        if (element == null) return null;
        return element.attr("data-percentage");
    }

    private String getTotalBenefits(Document document) {
        Element element = document.selectFirst(".benefits span");
        if (element == null) return null;
        return element.text().trim();
    }

    private String getTotalInterviews(Document document) {
        Element element = document.selectFirst(".interviews span");
        if (element == null) return null;
        return element.text().trim();
    }

    private String getTotalPhotos(Document document) {
        Element element = document.selectFirst(".photos span");
        if (element == null) return null;
        return element.text().trim();
    }

    private String getTotalSalaries(Document document) {
        Element element = document.selectFirst(".salaries span");
        if (element == null) return null;
        return element.text().trim();
    }
    
    private String getTotalJobs(Document document) {
        Element element = document.selectFirst(".jobs span");
        if (element == null) return null;
        return element.text().trim();
    }
    
    private String getTotalReviews(Document document) {
        Element element = document.selectFirst(".reviews span");
        if (element == null) return null;
        return element.text().trim();
    }

    public void scrape() throws Exception {
        String glassdoorUrl = this.searchGoogle(glassdoorData.companyName);
        glassdoorData.url = glassdoorUrl;
        if(glassdoorUrl == null) {
            throw new Exception("Failed to get glassdoor url");
        };
        Document glassdoorpage = getGlassDoorPage(glassdoorUrl);
        glassdoorData.rating = getRating(glassdoorpage);
        glassdoorData.approveOfCEO = getApproveOfCEO(glassdoorpage);
        glassdoorData.cons = getCons(glassdoorpage);
        glassdoorData.pros = getPros(glassdoorpage);
        glassdoorData.lastUpdated = getLastUpdated(glassdoorpage);
        glassdoorData.recommendedToFriend = getRecommendToFriend(glassdoorpage);
        glassdoorData.totalBenefits = getTotalBenefits(glassdoorpage);
        glassdoorData.totalInterviews = getTotalInterviews(glassdoorpage);
        glassdoorData.totalPhotos = getTotalPhotos(glassdoorpage);
        glassdoorData.totalJobs = getTotalJobs(glassdoorpage);
        glassdoorData.totalReviews = getTotalReviews(glassdoorpage);
        glassdoorData.totalSalaries = getTotalSalaries(glassdoorpage);
    }

    public GlassdoorData getData() {
        return glassdoorData;
    }
}
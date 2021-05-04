package Glassdoor;

import java.util.List;

public class GlassdoorData {
    public String totalReviews = null;
    public String totalJobs = null;
    public String totalSalaries = null;
    public String totalInterviews = null;
    public String totalBenefits = null;
    public String totalPhotos = null;
    public String lastUpdated = null;
    public String rating = null;
    public String recommendedToFriend = null;
    public String approveOfCEO = null;
    public List<String> pros = null;
    public List<String> cons = null;
    public String url = null;
    public String companyName;
    public GlassdoorData(String companyName) {
        this.companyName= companyName;
    }
    @Override
    public String toString() {
        String result = "{\n";
        result += "\t\"totalReviews\" : " + "\"" + totalReviews + "\",\n";
        result += "\t\"totalJobs\" : " + "\"" + totalJobs + "\",\n";
        result += "\t\"totalSalaries\" : " + "\"" + totalSalaries + "\",\n";
        result += "\t\"totalInterviews\" : " + "\"" + totalInterviews + "\",\n";
        result += "\t\"totalBenefits\" : " + "\"" + totalBenefits + "\",\n";
        result += "\t\"totalPhotos\" : " + "\"" + totalPhotos + "\",\n";
        result += "\t\"lastUpdated\" : " + "\"" + lastUpdated + "\",\n";
        result += "\t\"rating\" : " + "\"" + rating + "\",\n";
        result += "\t\"recommendedToFriend\" : " + "\"" + recommendedToFriend + "\",\n";
        result += "\t\"approveOfCEO\" : " + "\"" + approveOfCEO + "\",\n";
        result += "\t\"url\" : " + "\"" + url + "\",\n";
        result += "\t\"companyName\" : " + "\"" + companyName + "\",\n";
        result += "\t\"pros\" : [\n";
        for(String pro : pros) {
            result += "\t\t\""+pro+"\",\n";
        }
        result+="\t],\n";
        result += "\t\"cons\" : [\n";
        for(String con : cons) {
            result += "\t\t\""+con+"\",\n";
        }
        result+="\t]\n";
        result += "}";
        return result;
    }
}

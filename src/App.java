import Glassdoor.Glassdoor;

public class App {
    public static void main(String[] args) throws Exception {
        String company = "Edyst";
        Glassdoor glassdoor = new Glassdoor(company);
        try {
            glassdoor.scrape();
            String glassdoorUrl = glassdoor.getGlassDoorURL();
            String rating = glassdoor.getRating();
            System.out.println("Rating "+rating);
            System.out.println("URL "+glassdoorUrl);
        }catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
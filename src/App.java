import Glassdoor.Glassdoor;
import Glassdoor.GlassdoorData;

public class App {
    public static void main(String[] args) throws Exception {
        String company = "Edyst";
        Glassdoor glassdoor = new Glassdoor(company);
        try {
            glassdoor.scrape();
            GlassdoorData data = glassdoor.getData();
            System.out.println(data.toString());
            //System.out.println(data.toString());
        }catch(Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }
}
package lurajcevi_zadaca_2;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lurajcevi_zadaca_2.model.Season;
import lurajcevi_zadaca_2.model.SportsClub;


/**
 *
 * @author luka
 */
public class Lurajcevi_zadaca_2 {

    private static String sportsClubRegex = "^([1-9]{1}[0-9]{0,3})([a-zA-Z]{1,20})\\s*$";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Pattern p = Pattern.compile(sportsClubRegex);
        Matcher m;
        
        if (args.length < 4 ) {
            System.out.println("You must provide exactly four (4) arguments.");
            return;
        }

        try {
            FileInputStream fstream = new FileInputStream(args[0]);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            Season season = new Season(Integer.parseInt(args[1]),
                                       Integer.parseInt(args[2]),
                                       Integer.parseInt(args[3]));
            while ((strLine = br.readLine()) != null) {
                m = p.matcher(strLine);
                if (m.matches()) {
                    new SportsClub(Integer.parseInt(m.group(1)),
                                                    m.group(2), 
                                                    season);
                }
                else {
                    throw new Exception("File corrupted.");
                }
            }
            season.start();
            in.close();
        } catch (Exception e) {
            System.out.println("Error occured. Don't know why.");
        }
    }
}

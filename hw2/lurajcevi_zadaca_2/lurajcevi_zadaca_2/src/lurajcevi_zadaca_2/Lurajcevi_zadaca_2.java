package lurajcevi_zadaca_2;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lurajcevi_zadaca_2.model.Season;
import lurajcevi_zadaca_2.model.SportsClub;
import lurajcevi_zadaca_2.model.Table;


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
                // TODO unique club names
                // Jedan klub može imati samo jedan zapis! 
                //System.out.println(strLine);
                m = p.matcher(strLine);
                //System.out.println(m.matches());
                if (m.matches()) {
                    new SportsClub(Integer.parseInt(m.group(1)),
                                                   m.group(2), 
                                                   season);
                    //clubs.add(sp);
                }
                else {
                    throw new Exception("File corrupted.");
                }
            }
            // save clubs to initial table
            //initialTable = new Table(clubs);
            // start the season with initial table
            // TODO check if numbers (args 1-3)
            season.start();
            in.close();
        } catch (Exception e) {
            //TODO print normal error message
            e.printStackTrace();
        }
    }
}

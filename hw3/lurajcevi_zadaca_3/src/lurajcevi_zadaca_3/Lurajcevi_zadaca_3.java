package lurajcevi_zadaca_3;

import lurajcevi_zadaca_3.mvc.UrlContentController;
import lurajcevi_zadaca_3.mvc.UrlContentModel;
import lurajcevi_zadaca_3.mvc.UrlContentView;



/**
 *
 * @author luka
 */
public class Lurajcevi_zadaca_3 {
    
    /**
     * Entry point for the application, handles command line arguments
     * @param args 
     */
    public static void main(String[] args) {
        try {
        if (args.length != 2) {
            System.out.println("2 arguments must be given.");
        } else {
            UrlContentModel model = new UrlContentModel(args[0], Integer.parseInt(args[1]));
            UrlContentView view = new UrlContentView();
            UrlContentController controller = new UrlContentController(model, 
                                                                       view, 
                                                                       Integer.parseInt(args[1]));
        }
        } catch (Exception e) {
            System.out.println("Wrong input. First argument is URL, and the "
                             + "second one is a number.");
        }
    }
    
}

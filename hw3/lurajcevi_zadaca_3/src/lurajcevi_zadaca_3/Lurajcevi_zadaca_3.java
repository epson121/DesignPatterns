package lurajcevi_zadaca_3;

import lurajcevi_zadaca_3.mvc.UrlContentController;
import lurajcevi_zadaca_3.mvc.UrlContentModel;
import lurajcevi_zadaca_3.mvc.UrlContentView;



/**
 *
 * @author luka
 */
public class Lurajcevi_zadaca_3 {
    
    public static void main(String[] args) {
        // TODO value check (if string, and if int)
        System.out.println(args.length);
        if (args.length != 2) {
            System.out.println("2 arguments must be given.");
        } else {
            UrlContentModel model = new UrlContentModel(args[0], Integer.parseInt(args[1]));
            UrlContentView view = new UrlContentView();
            UrlContentController controller = new UrlContentController(model, 
                                                                       view, 
                                                                       Integer.parseInt(args[1]));
            //controller
        }
    }
    
}

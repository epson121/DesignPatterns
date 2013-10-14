/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lurajcevi_zadaca_1;

import lurajcevi_zadaca_1.abstractFactory.FileSystemFactory;

/**
 *
 * @author luka
 */
public class Lurajcevi_zadaca_1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
        if (args.length == 0){
            System.out.println("Wrong argument.");
            return;
        }
        */
        FileSystemFactory factory = 
                FileSystemFactory.getFactory(System.getenv("DS_TIP"));

        FileSystem fs = factory.getFileSystem("/home/luka/documents/faks/test/");        
        
        //fs.listDirectory();
    }

}

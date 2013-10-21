/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lurajcevi_zadaca_1;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lurajcevi_zadaca_1.abstractFactory.FileSystemFactory;

/**
 *
 * @author luka
 */
public class Lurajcevi_zadaca_1 {
    
    private static Command getM(String input, Pattern[] patterns) {
        Matcher m;
        System.out.println("INPUT: " + input);
        for (int i = 0; i < patterns.length; i++) {
            m = patterns[i].matcher(input);
            if (m.matches()) {
                Command c = new Command(i, m);
                return c;
            }
        }
        return null;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String listTreeRegex = "^ls -R *$";
        String listFromRegex = "^ls  -asd*$";
        String listItemRegex = "^ls -id ([0-9]+) *$";
        String listParentsRegex = "^ls -p ([0-9]+) *$";
        String addFileRegex = "^touch -id ([0-9]+) ([a-zA-Z0-9_]+) *$";
        String removeFileRegex = "^rm -id ([0-9]+) *$";
        String moveFileRegex = "^mv --file ([0-9]+) --folder ([0-9]+) ([a-zA-Z0-9_\\.]+) *$";
        String copyFileRegex = "^cp --file ([0-9]+) --folder ([0-9]+) ([a-zA-Z0-9_\\.]+) *$";
        /*String[] regexes = {listTreeRegex, listFromRegex, listItemRegex, listParentsRegex,
            addFileRegex, removeFileRegex, moveFileRegex};*/
        if (args.length == 0) {
            System.out.println("Wrong argument.");
            return;
        }
        
        Pattern[] patterns = {Pattern.compile(listTreeRegex),
                              Pattern.compile(listFromRegex),
                              Pattern.compile(listItemRegex),
                              Pattern.compile(listParentsRegex),
                              Pattern.compile(addFileRegex),
                              Pattern.compile(removeFileRegex),
                              Pattern.compile(moveFileRegex),
                              Pattern.compile(copyFileRegex)
                             };


        FileSystemFactory factory =
                FileSystemFactory.getFactory(System.getenv("DS_TIP"));

        FileSystem fs = factory.getFileSystem(args[0]);
        fs.listFolder(0);
        String input = "";
        Scanner sc = new Scanner(System.in);

        while (!input.equals("q")) {
            input = sc.nextLine();
            Command command = Lurajcevi_zadaca_1.getM(input, patterns);
            int id, folderId;
            String name;
            try{
            System.out.println("ID: " + command.getId());
            switch (command.getId()) {
                case 0:
                    fs.listFolder(0);
                    break;
                case 1:
                    System.out.println("List from...");
                    break;
                case 2:
                    id = Integer.parseInt(command.getM().group(1));
                    fs.listFolder(id);
                    break;
                case 3:
                    id = Integer.parseInt(command.getM().group(1));
                    fs.listParents(id);
                    break;
                case 4:
                    id = Integer.parseInt(command.getM().group(1));
                    name = command.getM().group(2);
                    fs.addFile(id, name);
                    break;
                case 5:
                    id = Integer.parseInt(command.getM().group(1));
                    fs.remove(id);
                    break;
                case 6:
                    //file id
                    id = Integer.parseInt(command.getM().group(1));
                    folderId = Integer.parseInt(command.getM().group(2));
                    name = command.getM().group(3);
                    fs.move(id, folderId, name);
                    break;
                case 7:
                    id = Integer.parseInt(command.getM().group(1));
                    folderId = Integer.parseInt(command.getM().group(2));
                    name = command.getM().group(3);
                    fs.copy(id, folderId, name);
                    break;
                default:
                    System.out.println("Wrong command.");
            }
            } catch (Exception e) {
                System.out.println("Wrong command.");
            }
        }
        System.out.println("Exiting.");

        /*fs.listDirectoryTree();
         fs.addFile(8, "tester12.sml");
         fs.listDirectoryTree();
         fs.remove(1);
         fs-.listDirectoryTree();
         fs.listParents(5);
         System.out.println("#############");
         fs.listParents(0);
         System.out.println("#############");
         fs.listParents(8);
         fs.move(3, 0, "lvl1-moved.txt");
         fs.listFolder(0);
         fs.copy(3, 4, "lvl1-moved-copied.txt");
         fs.listFolder(0);
         fs.remove(1);
         fs.listFolder(0);
         */
    }
}

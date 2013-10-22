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
        String moveFolderRegex = "^mv --folder ([0-9]+) --folder ([0-9]+) *$";
        String copyFileRegex = "^cp --file ([0-9]+) --folder ([0-9]+) ([a-zA-Z0-9_\\.]+) *$";
        
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
                              Pattern.compile(moveFolderRegex),
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
            if ("q".equals(input)) {
                break;
            }
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
                    fs.moveFile(id, folderId, name);
                    break;
                case 7:
                    //file id
                    id = Integer.parseInt(command.getM().group(1));
                    folderId = Integer.parseInt(command.getM().group(2));
                    //name = command.getM().group(3);
                    fs.moveFolder(id, folderId);
                    break;
                case 8:
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
                e.printStackTrace();
            }
        }
        System.out.println("Exiting.");
    }
}

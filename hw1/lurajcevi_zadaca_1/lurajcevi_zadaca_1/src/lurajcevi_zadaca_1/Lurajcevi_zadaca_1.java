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
        
        String help = "COMMANDS: \n" +
                      "print tree: ls -R\n" +
                      "print from id: ls -c -id (:id)\n" +
                      "print one item: ls -id (:id)\n" +
                      "print parents: ls -p (:id)\n" + 
                      "add file: touch -id (:folderId) (:name)\n" +
                      "remove file/folder: rm -id (:id)\n" +
                      "move file: mv -f1 (:id) -f2 (:id) (:name)\n" +
                      "move folder: mv --folder (:id) --folder (:id)\n" +
                      "copy file: cp --file (:id) --folder (:id) (:name)\n" +
                      "copy folder: cp --folder (:id) --folder (:id)\n" +
                      "show this help: --help\n";
                
        
        String listTreeRegex = "^ls -R *$";
        String listFromRegex = "^ls -c -id ([0-9]+) *$";
        String listItemRegex = "^ls -id ([0-9]+) *$";
        String listParentsRegex = "^ls -p ([0-9]+) *$";
        String addFileRegex = "^touch -id ([0-9]+) (.*) *$";
        String removeFileRegex = "^rm -id ([0-9]+) *$";
        String moveFileRegex = "^mv -f1 ([0-9]+) -f2 ([0-9]+) (.*) *$";
        String moveFolderRegex = "^mv --folder ([0-9]+) --folder ([0-9]+) *$";
        String copyFileRegex = "^cp --file ([0-9]+) --folder ([0-9]+) (.+) *$";
        String copyFolderRegex = "^cp --folder ([0-9]+) --folder ([0-9]+) *$";
        String helpRegex = "^--help";
        
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
                              Pattern.compile(copyFileRegex),
                              Pattern.compile(copyFolderRegex),
                              Pattern.compile(helpRegex),
                             };


        FileSystemFactory factory =
                FileSystemFactory.getFactory(System.getenv("DS_TIP"));
        
        FileSystem fs = factory.getFileSystem(args[0]);
        fs.listFolder(0);
        String input = "";
        Scanner sc = new Scanner(System.in);

        while (!input.equals("q")) {
            System.out.print(">> ");
            input = sc.nextLine();
            if ("q".equals(input)) {
                break;
            }
            Command command = Lurajcevi_zadaca_1.getM(input, patterns);
            int id, folderId;
            String name;
            try{
            
            switch (command.getId()) {
                case 0:
                    fs.listFolder(0);
                    break;
                case 1:
                   id = Integer.parseInt(command.getM().group(1));
                    fs.listFolder(id);
                    break;
                case 2:
                    id = Integer.parseInt(command.getM().group(1));
                    fs.listItem(id);
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
                    id = Integer.parseInt(command.getM().group(1));
                    folderId = Integer.parseInt(command.getM().group(2));
                    name = command.getM().group(3);
                    fs.moveFile(id, folderId, name);
                    break;
                case 7:
                    id = Integer.parseInt(command.getM().group(1));
                    folderId = Integer.parseInt(command.getM().group(2));
                    fs.moveFile(id, folderId, "");
                    break;
                case 8:
                    id = Integer.parseInt(command.getM().group(1));
                    folderId = Integer.parseInt(command.getM().group(2));
                    name = command.getM().group(3);
                    fs.copy(id, folderId, name);
                    break;
                case 9:
                    id = Integer.parseInt(command.getM().group(1));
                    folderId = Integer.parseInt(command.getM().group(2));
                    fs.copy(id, folderId, "");
                    break;
                case 10:
                    System.out.println(help);
                    break;
                default:
                    System.out.println("Wrong command.");
            }
            } catch (Exception e) {
                System.out.println("Wrong command.");
                //e.printStackTrace();
            }
        }
        System.out.println("Exiting.");
    }
}

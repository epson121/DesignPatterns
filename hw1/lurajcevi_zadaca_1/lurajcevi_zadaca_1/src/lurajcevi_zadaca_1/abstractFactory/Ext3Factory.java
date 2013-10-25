
package lurajcevi_zadaca_1.abstractFactory;

import lurajcevi_zadaca_1.Ext3FileSystem;
import lurajcevi_zadaca_1.FileSystem;

/**
 *
 * @author luka
 */
public class Ext3Factory extends FileSystemFactory {
    
    @Override
    public FileSystem getFileSystem(String path) {
        System.out.println("Ext3");
        return new Ext3FileSystem(path);
    }


}

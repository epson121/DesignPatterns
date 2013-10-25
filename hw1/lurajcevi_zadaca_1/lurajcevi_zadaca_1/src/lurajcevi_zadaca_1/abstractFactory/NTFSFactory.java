
package lurajcevi_zadaca_1.abstractFactory;

import lurajcevi_zadaca_1.FileSystem;
import lurajcevi_zadaca_1.NTFSFileSystem;

/**
 *
 * @author luka
 */
public class NTFSFactory extends FileSystemFactory {
    
    @Override
    public FileSystem getFileSystem(String path) {
        System.out.println("NTFS");
        return new NTFSFileSystem(path);
    }

}

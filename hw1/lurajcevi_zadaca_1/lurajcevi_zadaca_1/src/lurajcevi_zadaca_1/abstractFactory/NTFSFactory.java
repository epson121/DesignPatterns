/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
        // TODO: give path as argument
        // TODO: do stuff on NTFSFileSystem class (load data, parse, sort...)
        return new NTFSFileSystem(path);
    }

}

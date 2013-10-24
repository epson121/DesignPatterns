package lurajcevi_zadaca_1.abstractFactory;

import lurajcevi_zadaca_1.FileSystem;

/**
 *
 * @author luka
 */
public abstract class FileSystemFactory {

    private static volatile FileSystemFactory factory;
    private static volatile FileSystem INSTANCE;

    
    private static volatile FileSystemFactory INSTANCE;
       
    // SINGLETON + ABSTRACT FACTORY
    public static FileSystemFactory getFactory(String env) {
        switch (env) {
            case "0":
                factory = new NTFSFactory();
                break;
            case "1":
                factory = new Ext3Factory();
                break;
            default:
                throw new IllegalArgumentException(
                        "This environment variable is not supported.");
        }
        return factory;
    }

    public FileSystem getFileSystem(String path){
        return null;
    };

    public FileSystem getFS(String path) {
        if (INSTANCE == null) {
            synchronized (FileSystemFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = factory.getFileSystem(path);
                }
            }
        }
        return INSTANCE;
    }
}

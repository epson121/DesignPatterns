
package lurajcevi_zadaca_1.abstractFactory;

import lurajcevi_zadaca_1.FileSystem;

/**
 *
 * @author luka
 */
public abstract class FileSystemFactory {
    
    private static volatile FileSystemFactory INSTANCE;
    
    // SINGLETON + ABSTRACT FACTORY
    public static FileSystemFactory getFactory(String env) {
        if (INSTANCE == null) {
            synchronized(FileSystemFactory.class){
                if (INSTANCE == null) {
                    System.out.println("env: " + env);
                    System.out.println(env.equals("0"));
                    switch (env) {
                        case "0":
                            INSTANCE = new NTFSFactory();
                            break;
                        case "1":
                            break;
                        default:

                            throw new IllegalArgumentException(
                                "This environment variable is not supported.");
                    }
                }
            }
        }
        return INSTANCE;
    }
    
    public abstract FileSystem getFileSystem(String path);
    
    
}

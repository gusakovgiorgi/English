package company.self.development.rememberenglishexample.base;

/**
 * Created by notbl on 11/14/2017.
 */

public class DatabaseManager {
    private static DatabaseManager ourInstance;

    public static DatabaseManager getInstance() {
        if (ourInstance==null){
            ourInstance=new DatabaseManager();
        }
        return ourInstance;
    }

    private DatabaseManager() {
    }
}

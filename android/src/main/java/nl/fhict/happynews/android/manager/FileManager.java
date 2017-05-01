package nl.fhict.happynews.android.manager;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Tobi on 01-May-17.
 */
public class FileManager {

    private Logger logger;
    private static FileManager instance = new FileManager();

    private FileManager() {
        logger = Logger.getLogger(getClass().getName());
    }

    /**
     * Gets an instance of Filemanager.
     * @return An instance.
     */
    public static FileManager getInstance() {
        return instance;
    }

    /**
     * Reads a file and returns it's contents as an object.
     * @param context The context.
     * @param filename The filename.
     * @return The object.
     */
    public Object readFile(Context context, String filename) {
        //TODO: change to SQLite db
        Object result = null;
        try {
            FileInputStream fis = context.openFileInput(filename);
            ObjectInputStream is = new ObjectInputStream(fis);
            result = is.readObject();
            is.close();
            fis.close();
        } catch (Exception e) {
            logger.log(Level.WARNING, "The file could not be opened or found.", e);
        }
        return result;
    }

    /**
     * Writes an object to a file.
     * @param context The context.
     * @param filename The filename.
     * @param object The object to write.
     */
    public void writeFile(Context context, String filename, Object object) {
        try {
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(object);
            os.close();
            fos.close();
        } catch (Exception e) {
            logger.log(
                Level.WARNING,
                "The file could not be opened or found, or the object is not serializable.",
                e); //TODO: probably need cleaner exception handling
        }
    }

    /**
     * Checks if a file exists.
     * @param context The context.
     * @param filename The filename.
     * @return Returns true if the file exists. Otherwise false.
     */
    public boolean fileExists(Context context, String filename) {
        try {
            context.openFileInput(filename);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

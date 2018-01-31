import java.io.*;

public class SerializationProfile implements Serializable {

    public static Profile deserialize(String fileName) throws IOException, ClassNotFoundException {

        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Profile obj = (Profile) ois.readObject();
        ois.close();
        return obj;

    }

    public static void serialize(Profile obj, String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.close();
            Lo.g("Profile saved in " + fileName);
        } catch (IOException e) {
            Lo.g("Error: Profile unsaved");
        }
    }
}
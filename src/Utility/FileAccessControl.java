package Utility;


import org.json.JSONArray;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileAccessControl {

    public StringBuffer readfromFileStringBuffer(String path) {

        try {
            FileReader fileReader = new FileReader(path);
            int i, j = 0;
            StringBuffer readtext = new StringBuffer();
            while ((i = fileReader.read()) != -1) {
                j++;
                readtext.append((char) i);
            }
            fileReader.close();


            return readtext;


        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    public Boolean writetoFile(String path, String text) {

        try {
            File file = new File(path);
            if (!file.exists()) {
                System.out.println(file.getPath());
                new File(file.getParent()).mkdir();
                System.out.println(file.getParent());
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(text);
            fileWriter.flush();
            fileWriter.close();

            return true;


        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return false;
    }

    public Boolean appendtoFile(String path, String text) {

        try {

            File file = new File(path);
            if (!file.exists()) {
                new File(file.getParent()).mkdir();
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(path, true);
            fileWriter.append(text);
            fileWriter.flush();
            fileWriter.close();

            return true;


        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return false;
    }

    public JSONArray readfromFileJsonArray(String path) {

        try {
            FileReader fileReader = new FileReader(path);
            int i, j = 0;
            StringBuffer readtext = new StringBuffer();
            while ((i = fileReader.read()) != -1) {
                j++;
                readtext.append((char) i);
            }
            fileReader.close();

            if (readtext.length() <= 0)
                return null;

            readtext = readtext.insert(0, "[");
            readtext = readtext.replace(readtext.length() - 1, readtext.length(), "]");

            JSONArray jsonArray = new JSONArray(readtext.toString());

            JSONArray outputJsonArray = new JSONArray();

            if (jsonArray.length() > 1) {
                for (int k = jsonArray.length() - 1; k >= 0; k--) {

                    outputJsonArray.put(jsonArray.getJSONObject(k));
                }


                return outputJsonArray;
            }
            return jsonArray;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }


}

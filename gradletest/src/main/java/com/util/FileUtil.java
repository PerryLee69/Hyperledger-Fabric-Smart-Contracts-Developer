package main.java.com.util;

import java.io.*;

public class FileUtil {

    public static void writeFile(String filePath,String fileName, String fileContent) {
        File f = new File(filePath);

        if (!f.exists()) {
            f.mkdirs();
        }

        File myFile = new File(f, fileName);

        FileWriter w = null;
        try {
            w = new FileWriter(myFile);

            w.write(fileContent);
        } catch (IOException e) {
            throw new RuntimeException("Error creating file " + fileName, e);
        } finally {
            if (w != null) {
                try {
                    w.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    /*public static String readFileToString(int mode) {
        InputStreamReader inputStreamReader;
        if (mode == 1) {
            inputStreamReader = new InputStreamReader(FileUtil.class.getResourceAsStream("/META-INF/Shim.js"));
        } else {
            inputStreamReader = new InputStreamReader(FileUtil.class.getResourceAsStream("/META-INF/ContractApi.js"));
        }
        StringBuilder fileContent = new StringBuilder();
        BufferedReader in;
        try {
            in = new BufferedReader(inputStreamReader);
            String thisLine;
            while ((thisLine = in.readLine()) != null) {
                fileContent.append(thisLine);
                fileContent.append("\n");
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent.toString();
    }*/

}

package main.java.com;

import freemarker.template.TemplateException;
import main.java.com.mocker.ChaincodeApi;
import main.java.com.util.TemplateUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    public static void main(String[] args) throws IOException {
        int count = 0;
        File file = new File(System.getProperty("user.dir") + "/src/main/js/lib/SmartContract.js");
        List<ChaincodeApi> chaincodeApis = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String tempString;
            while ((tempString = reader.readLine()) != null) {
                if (tempString.toLowerCase().contains("async")) {
                    count++;
                    String name = tempString.substring(tempString.indexOf("async") + 6, tempString.indexOf('('));
                    System.out.println(name);
                    chaincodeApis.add(new ChaincodeApi(name, "", ""));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(count);
        String contractName = "SmartContract";
        String outputPath = String.format("%s/src/test/js/", System.getProperty("user.dir"));
        String testFileName = String.format("%sTest.js", contractName);
        String smartContractPath = String.format("%s\\src\\main\\js\\lib\\%s.js",
                System.getProperty("user.dir"), contractName);
        File f = new File(smartContractPath);
        if (!f.exists()) {
            /*Messages.showErrorDialog(
                    "No such smart contract found in " + smartContractPath,
                    "Not Found");*/
            System.out.println("No such contract found.");
            return;
        }
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("contractName", contractName);
        dataModel.put("chaincodeApis", chaincodeApis);
        /*try {
            TemplateUtil.writeFile(outputPath,
                    testFileName,
                    "mockstub.ftl",
                    dataModel);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (TemplateException exception) {
            // handle empty configuration item for ftl
            // in case users
            *//*Messages.showErrorDialog(
                    "Please fill blank item(s)!",
                    "Missing Item");*//*
            System.out.println("missing item");
            exception.printStackTrace();
        }*/
        try {
            // call cmd to execute dos command for windows system
            String command = "cmd /c mocha " + outputPath + testFileName;
            // String command = "cmd /c ec hello";
            Process process;
            process = Runtime.getRuntime().exec(command);

            // read results from process
            BufferedReader stdout = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String line;
            while ((line = stdout.readLine()) != null) {
                System.out.println(line);
            }
            stdout.close();
            // read error message from process
            BufferedReader stderr = new BufferedReader(new InputStreamReader(
                    process.getErrorStream()));
            String err;
            while ((err = stderr.readLine()) != null) {
                System.out.println(err);
            }
            stderr.close();

            // handle input and error results in multiple threads
            /*final InputStreamReader errStream = new InputStreamReader(
                    process.getErrorStream());
            final InputStreamReader inputStream = new InputStreamReader(
                    process.getInputStream());

            new Thread() {
                public void run() {
                    BufferedReader stderr = new BufferedReader(errStream);
                    printResult(stderr);
                }
            }.start();
            new Thread() {
                public void run() {
                    BufferedReader stdout = new BufferedReader(inputStream);
                    printResult(stdout);
                }
            }.start();*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

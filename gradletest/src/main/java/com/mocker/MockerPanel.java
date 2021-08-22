package main.java.com.mocker;

import com.intellij.execution.filters.TextConsoleBuilder;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.OnePixelSplitter;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class MockerPanel extends SimpleToolWindowPanel {

    public MockerPanel(ToolWindow toolWindow, Project project) {
        super(false, true);

        /*
         * whole GUI construction:
         * SplitPane:
         *     SimpleToolWindowPanel(mock call):
         *         JPanel(toolbar)
         *         ScrollPane:JTable(contract api table)
         *     JPanel(console)
         */

        // create api list for freemarker
        List<ChaincodeApi> chaincodeApis = new ArrayList<>();

        SimpleToolWindowPanel apiPanel = new SimpleToolWindowPanel(true, true);
        JScrollPane scrollPane = new JBScrollPane();
        apiPanel.add(scrollPane, BorderLayout.CENTER);

        // create table by reading contract file
        JTable table = new JBTable();
        JButton getApisButton = new JButton();
        getApisButton.setText("Get Contract And Apis");
        getApisButton.addActionListener(e -> {
            // TODO: clear current content
            // System.out.println(project.getBasePath());
            /*toolWindow.getContentManager().removeAllContents(true);
            toolWindow.getContentManager().addContent();*/
            Vector content = new Vector();
            Vector<String> title = new Vector<>();
            title.add("name");
            title.add("args");
            DefaultTableModel model = new DefaultTableModel(content, title);
            table.setModel(model);
            table.setDefaultRenderer(Object.class, new MyTableCellRender());
            int count = 0;
            File file = new File(project.getBasePath() + "/src/main/js/lib/SmartContract.js");
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String tempString;
                while ((tempString = reader.readLine()) != null) {
                    if (tempString.toLowerCase().contains("async")) {
                        count++;
                        String name = tempString.substring(tempString.indexOf("async") + 6, tempString.indexOf('('));
                        Vector row = new Vector();
                        row.add(name);
                        content.add(row);
                        model = new DefaultTableModel(content, title) {
                            @Override
                            public boolean isCellEditable(int row,int column){
                                return column != 0;
                            }
                        };
                        table.setModel(model);
                        chaincodeApis.add(new ChaincodeApi(name, "", ""));
                    }
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            System.out.println(count);
        });

        // create toolbar within SimpleToolWindowPanel
        JPanel toolsPanel = new JPanel();
        apiPanel.setToolbar(toolsPanel);
        toolsPanel.add(getApisButton);

        // create console within JPanel
        TextConsoleBuilder consoleBuilder = TextConsoleBuilderFactory.getInstance().createBuilder(project);
        ConsoleView console = consoleBuilder.getConsole();
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(console.getComponent(), BorderLayout.CENTER);

        // mock invoke
        JButton invokeButton = new JButton();
        invokeButton.setText("Invoke Apis");
        invokeButton.addActionListener(e -> {
            String contractName = "SmartContract";
            String outputPath = String.format("%s/src/test/js/", project.getBasePath());
            String testFileName = String.format("%sTest.js", contractName);
            String smartContractPath = String.format("%s\\src\\main\\js\\lib\\%s.js",
                    project.getBasePath(), contractName);
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

            long startTime = System.currentTimeMillis();

            // TODO: args within textfield.getText()
            for (int i = 0;i < table.getRowCount();i++) {
                Object cell = table.getValueAt(i, 1);
                System.out.println(cell);
            }

            // call cmd to execute dos command for windows system
            try {
                /*String os = System.getProperty("os.name");
                if (os.toLowerCase().contains("win")) {
                    command = "cmd /c mocha " + outputPath + testFileName;
                } else {
                    command = "mocha" + outputPath + testFileName;
                }*/
                String command = "cmd /c mocha " + outputPath + testFileName;
                // String command = "cmd /c ec hello";
                Process process;
                process = Runtime.getRuntime().exec(command);

                // read results from process
                BufferedReader stdout = new BufferedReader(new InputStreamReader(
                        process.getInputStream()));
                String line;
                while ((line = stdout.readLine()) != null) {
                    // TODO: console.print("\u221A", ConsoleViewContentType.USER_INPUT);
                    console.print(line, ConsoleViewContentType.SYSTEM_OUTPUT);
                    console.print("\n", ConsoleViewContentType.SYSTEM_OUTPUT);
                }
                stdout.close();
                // read error message from process
                BufferedReader stderr = new BufferedReader(new InputStreamReader(
                        process.getErrorStream()));
                String err;
                while ((err = stderr.readLine()) != null) {
                    // System.out.println(err);
                    console.print(err, ConsoleViewContentType.SYSTEM_OUTPUT);
                    console.print("\n", ConsoleViewContentType.SYSTEM_OUTPUT);
                }
                stderr.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            long endTime = System.currentTimeMillis();
            // System.out.println("请求延迟：" + (endTime - startTime) + "ms");
        });
        toolsPanel.add(invokeButton);

        // add table to scroll pane in SimpleToolWindowPanel
        scrollPane.setViewportView(table);

        // create split pane containing SimpleToolWindowPanel and JPanel
        JBSplitter splitPane = new OnePixelSplitter(false, "test", 0.3f);
        splitPane.setFirstComponent(apiPanel);
        splitPane.setSecondComponent(panel);
        setContent(splitPane);
        // setContent(treePanel);
    }

}

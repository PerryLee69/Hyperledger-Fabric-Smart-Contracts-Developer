package main.java.com.generator;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import freemarker.template.TemplateException;
import main.java.com.util.TemplateUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Generator extends AnAction {

    // change button name
    public Generator() {
        super("Generate Initial Chaincode");
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        // Project project = e.getData(PlatformDataKeys.PROJECT);
        Project project = e.getProject();
        if (project != null) {
            // get chaincode name
            String chaincodeName = Messages.showInputDialog(
                    project,
                    "What is your chaincode name?",
                    "Input Chaincode Name",
                    Messages.getQuestionIcon());
            // in case user closes the dialog without input
            if(chaincodeName == null) {
                chaincodeName = "MySmartContract";
            }

            // get mode
            int mode = Messages.showYesNoDialog(
                    "Generate default mode: fabric contract api(otherwise fabric shim)",
                    "Confirm Develop Mode", Messages.getQuestionIcon());

            // start generating
            String basePath = project.getBasePath();
            String outputPath = String.format("%s/src/main/js/", basePath);
            // TODO: add settings for outputPath
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("chaincodeName", chaincodeName);
            if (mode == 1) {
                // fabric shim
                generate(outputPath, chaincodeName, "shim.ftl", dataModel);
                Messages.showMessageDialog(
                        project,
                        "Generate chaincode " + chaincodeName + " in " + outputPath,
                        "Generate Done",
                        Messages.getInformationIcon());
            } else {
                // fabric contract api
                generate(outputPath, "index", "index.ftl", dataModel);
                String libPath = String.format("%s/lib/", outputPath);
                generate(libPath, chaincodeName, "api.ftl", dataModel);
                Messages.showMessageDialog(
                        project,
                        "Generate chaincode " + chaincodeName + " in " + libPath,
                        "Generate Done",
                        Messages.getInformationIcon());
            }
        } else {
            Messages.showErrorDialog(
                    "Please create a project for your own!",
                    "No Project Existed");
        }

    }

    private void generate(String outputPath, String chaincodeName,
                          String templateName, Map<String, Object> dataModel) {
        try {
            TemplateUtil.writeFile(outputPath,
                    String.format("%s.js", chaincodeName),
                    templateName,
                    dataModel);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (TemplateException exception) {
            // handle empty configuration item for ftl
            // in case users
            Messages.showErrorDialog(
                    "Please fill blank item(s)!",
                    "Missing Item");
            exception.printStackTrace();
        }
    }

}

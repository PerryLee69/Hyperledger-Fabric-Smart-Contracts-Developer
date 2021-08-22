package main.java.com.mocker;

import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MockerToolWindow {

    private JButton hideButton;

    private JPanel myToolWindowContent;

    public MockerToolWindow(ToolWindow toolWindow) {
        init();
        // hideButton.addActionListener(e -> toolWindow.hide(null));
    }

    private void init() {
        /*JLabel datetimeLabel = new JLabel();
        datetimeLabel.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        hideButton = new JButton("取消");
        JTextField textField = new JTextField();*/

        myToolWindowContent = new JPanel();
        /*myToolWindowContent.add(datetimeLabel);
        myToolWindowContent.add(hideButton);
        myToolWindowContent.add(textField);*/
    }

    public JPanel getContent() {
        return myToolWindowContent;
    }
}

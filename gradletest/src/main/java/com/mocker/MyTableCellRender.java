package main.java.com.mocker;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class MyTableCellRender implements TableCellRenderer {


    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // TODO: set tool tips for cell(mouse hover)
        // TODO: set column width
        UpperCaseField editor = new UpperCaseField(10);
        if (value != null)
            editor.setText(value.toString());
        editor.setBackground((row % 2 == 0) ? Color.DARK_GRAY : Color.GRAY);
        return editor;
    }
}

package main.java.com.mocker;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class UpperCaseField extends JTextField {
    public UpperCaseField(int cols) {
        super(cols);
    }

    protected Document createDefaultModel() {
        return new UpperCaseDocument();
    }

    static class UpperCaseDocument extends PlainDocument {

        public void insertString(int offs, String str, AttributeSet a)
                throws BadLocationException {

            if (str == null) {
                return;
            }

            //此处用来控制输入
            super.insertString(offs, str, a);
        }
    }

}

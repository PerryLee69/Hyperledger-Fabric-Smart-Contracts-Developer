package main.java.com.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.util.Map;

public class TemplateUtil {

    public static void writeFile(String filePath, String fileName,
                                 String templateName, Map<String, Object> dataModel) throws TemplateException, IOException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
        // use ClassTemplateLoader to load resources file in jar
        configuration.setClassForTemplateLoading(TemplateUtil.class, "/META-INF/");
        // configuration.setTemplateLoader(new ClassTemplateLoader(TemplateUtil.class, "/"));
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        Template template = configuration.getTemplate(templateName);
        File f = new File(filePath);
        if (!f.exists()) {
            f.mkdirs();
        }
        File outputFile = new File(filePath, fileName);
        try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)))) {
            template.process(dataModel, out);
        }

    }
}

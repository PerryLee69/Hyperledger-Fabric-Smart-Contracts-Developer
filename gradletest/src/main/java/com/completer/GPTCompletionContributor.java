package main.java.com.completer;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import main.java.com.util.CompletionUtil;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;


public class GPTCompletionContributor extends CompletionContributor {

    /*public GPTCompletionContributor() {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(PsiElement.class),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               @NotNull ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {
                        resultSet.addElement(LookupElementBuilder.create("Welcome"));
                    }
                }

        );
    }*/



    @Override
    public void fillCompletionVariants(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {
        String currentText = main.java.com.util.CompletionUtil.getCurrentText(parameters);
        if(StringUtils.isNumeric(currentText)) {
            return;
        }

        String data = "{\"text\":\"" + currentText + "\"}";
        long startTime = System.currentTimeMillis();
        String element = CompletionUtil.doPostOrGet("http://127.0.0.1:8888/plugin", data);
        long endTime = System.currentTimeMillis();
        System.out.println("请求延迟：" + (endTime - startTime) + "ms");
        // element = element.substring(0, element.indexOf('\n') + 1);
        // TODO: exception handling
        // TODO: input context
        // TODO: output length(consider speed and time)
        result.addElement(LookupElementBuilder.create(element));
        /*result.addElement(LookupElementBuilder.create("constructor"));
        result.addElement(LookupElementBuilder.create("contract"));
        result.addElement(LookupElementBuilder.create("contract-api"));
        result.addElement(LookupElementBuilder.create("con.length"));
        result.addElement(LookupElementBuilder.create("con.toString()"));*/

        // PsiElement original = parameters.getOriginalPosition();
        // String prefix = result.getPrefixMatcher().getPrefix();
        /*try {
            // call cmd to execute dos command for windows system
            String command = "cmd /c python D:\\graduation\\crawler\\predict.py " + currentText;
            Process process;
            process = Runtime.getRuntime().exec(command);
            process.waitFor();
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\graduation\\crawler\\results.txt")));
            StringBuilder fileContent = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                fileContent.append(line);
                fileContent.append("\n");
            }
            in.close();
            result.addElement(LookupElementBuilder.create(fileContent));

        } catch (Exception e) {
            e.printStackTrace();
        }*/
        // result.addElement(LookupElementBuilder.create("hello"));
    }
}

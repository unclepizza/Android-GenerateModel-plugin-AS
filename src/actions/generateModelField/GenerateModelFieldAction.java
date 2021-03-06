package actions.generateModelField;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.WindowManager;
import org.apache.http.util.TextUtils;

/**
 * 根据文本在类中生成Java Bean字段
 */
public class GenerateModelFieldAction extends AnAction {
    private AnActionEvent anActionEvent;

    private GenerateModelFieldDialog.OnClickListener mClickListener = new GenerateModelFieldDialog.OnClickListener() {

        @Override
        public void onGenerate(String str, String memberType) {
            generateModel(str, memberType);
        }

        @Override
        public void onCancel() {
            //nothing...
        }
    };

    private void generateModel(String str, String memberType) {
        String result = CodeWriter.getInstance().write(str, anActionEvent, memberType, new ZtCodeGenerator());
        //如果有错误信息，弹出来
        if (!TextUtils.isEmpty(result) && !result.equalsIgnoreCase("success")) {
            Messages.showMessageDialog(result, "Error", Messages.getInformationIcon());
        }
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        this.anActionEvent = e;
        GenerateModelFieldDialog generateDialog = new GenerateModelFieldDialog();
        generateDialog.setOnClickListener(mClickListener);
        generateDialog.setTitle("Generate Field By String");
        //自动调整对话框大小
        generateDialog.pack();
        //设置对话框跟随当前windows窗口
        generateDialog.setLocationRelativeTo(WindowManager.getInstance().getFrame(e.getProject()));
        generateDialog.setVisible(true);
    }
}

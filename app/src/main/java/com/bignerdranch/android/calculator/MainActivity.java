package com.bignerdranch.android.calculator;

// 导入必要的包
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // 声明UI组件
    TextView resultTv, solutionTv;  // 结果显示框和计算式显示框
    // 声明计算器按钮
    MaterialButton buttonC, buttonBracketOpen, buttonBracketClose;  // 清除、左括号、右括号按钮
    MaterialButton buttonDivide, buttonMultiply, buttonPlus, buttonMinus, buttonEquals;  // 运算符按钮
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;  // 数字按钮
    MaterialButton buttonAC, buttonDot;  // 全部清除和小数点按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);  // 启用边缘到边缘显示
        setContentView(R.layout.activity_main);
        
        // 设置系统栏内边距
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 初始化显示框
        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);

        // 为所有按钮分配ID并设置点击监听器
        assignID(buttonC, R.id.button_c);
        assignID(buttonBracketOpen, R.id.button_open_bracket);
        assignID(buttonBracketClose, R.id.button_close_bracket);
        assignID(buttonDivide, R.id.button_divide);
        assignID(buttonMultiply, R.id.button_multiply);
        assignID(buttonPlus, R.id.button_plus);
        assignID(buttonMinus, R.id.button_minus);
        assignID(buttonEquals, R.id.button_equals);
        assignID(button0, R.id.button_0);
        assignID(button1, R.id.button_1);
        assignID(button2, R.id.button_2);
        assignID(button3, R.id.button_3);
        assignID(button4, R.id.button_4);
        assignID(button5, R.id.button_5);
        assignID(button6, R.id.button_6);
        assignID(button7, R.id.button_7);
        assignID(button8, R.id.button_8);
        assignID(button9, R.id.button_9);
        assignID(buttonAC, R.id.button_ac);
        assignID(buttonDot, R.id.button_dot);
    }

    /**
     * 为按钮分配ID并设置点击监听器
     * @param btn 按钮对象
     * @param id 按钮资源ID
     */
    void assignID(MaterialButton btn, int id) {
        MaterialButton button = findViewById(id);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        // 处理全部清除(AC)按钮
        if (buttonText.equals("AC")) {
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }
        
        // 处理等号按钮，计算最终结果
        if (buttonText.equals("=")) {
            String finalResult = getResult(dataToCalculate);
            if (!finalResult.equals("Err")) {
                solutionTv.setText(finalResult);
                resultTv.setText(finalResult);
            }
            return;
        }
        
        // 处理清除(C)按钮，删除最后一个字符
        if (buttonText.equals("C")) {
            if (dataToCalculate.length() > 0) {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
                solutionTv.setText(dataToCalculate);
                if (dataToCalculate.length() > 0) {
                    String finalResult = getResult(dataToCalculate);
                    if (!finalResult.equals("Err")) {
                        resultTv.setText(finalResult);
                    }
                } else {
                    resultTv.setText("0");
                }
            }
            return;
        }

        // 将显示符号转换为实际运算符
        if (buttonText.equals("×")) {
            buttonText = "*";
        } else if (buttonText.equals("÷")) {
            buttonText = "/";
        }

        // 防止输入以运算符开头
        if (dataToCalculate.equals("") && isOperator(buttonText)) {
            return;
        }

        // 防止连续输入运算符
        if (dataToCalculate.length() > 0 && isOperator(buttonText) && 
            isOperator(String.valueOf(dataToCalculate.charAt(dataToCalculate.length() - 1)))) {
            return;
        }

        // 更新计算式并显示实时结果
        dataToCalculate = dataToCalculate + buttonText;
        solutionTv.setText(dataToCalculate);

        String finalResult = getResult(dataToCalculate);
        if (!finalResult.equals("Err")) {
            resultTv.setText(finalResult);
        }
    }

    /**
     * 判断字符串是否为运算符
     * @param str 待判断的字符串
     * @return 是运算符返回true，否则返回false
     */
    private boolean isOperator(String str) {
        return str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/");
    }

    /**
     * 计算表达式结果
     * @param data 要计算的表达式字符串
     * @return 计算结果或错误信息
     */
    String getResult(String data) {
        try {
            // 处理空输入
            if (data.isEmpty()) return "0";
            
            // 移除表达式末尾的运算符
            if (isOperator(String.valueOf(data.charAt(data.length() - 1)))) {
                data = data.substring(0, data.length() - 1);
            }
            
            // 使用Rhino引擎计算表达式
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            
            // 移除不必要的小数点零
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            
            // 处理除以零的情况
            if (finalResult.equals("Infinity") || finalResult.equals("-Infinity")) {
                return "Err";
            }
            
            return finalResult;
        } catch (Exception e) {
            return "Err";
        } finally {
            Context.exit();
        }
    }
}
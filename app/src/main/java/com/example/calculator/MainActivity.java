package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvExp, tvResult;
    MaterialButton btnS, btnD, btnF, btnG, btnH, btnJ, btnK,btnL;
    MaterialButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0;
    MaterialButton btnC, btnAC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.right, systemBars.top, systemBars.bottom);
            return insets;
        });

        tvExp = findViewById(R.id.tvExpression);
        tvResult = findViewById(R.id.tvResult);

        initButton(btn0,R.id.btn0);
        initButton(btn1,R.id.btn1);
        initButton(btn2,R.id.btn2);
        initButton(btn3,R.id.btn3);
        initButton(btn4,R.id.btn4);
        initButton(btn5,R.id.btn5);
        initButton(btn6,R.id.btn6);
        initButton(btn7,R.id.btn7);
        initButton(btn8,R.id.btn8);
        initButton(btn9,R.id.btn9);
        initButton(btnS,R.id.btnS);
        initButton(btnD,R.id.btnD);
        initButton(btnF,R.id.btnF);
        initButton(btnG,R.id.btnG);
        initButton(btnH,R.id.btnH);
        initButton(btnJ,R.id.btnJ);
        initButton(btnK,R.id.btnK);
        initButton(btnL,R.id.btnL);
        initButton(btnC,R.id.btnC);
        initButton(btnAC,R.id.btnAC);


tvExp.setText("11231");

    }
    void initButton( MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this::onClick);

    }

    @Override
    public void onClick(View view) {
        MaterialButton btn = (MaterialButton) view;
        String btnText = btn.getText().toString();
        String data = tvExp.getText().toString();

        if(btnText.equals("AC")) {
            tvExp.setText("  ");
            tvResult.setText("");
            return;
        }

        if(btnText.equals("C")) {
            if(data.length() !=0 && !data.equals("0")) {
                data = data.substring(0, data.length() - 1);
                tvExp.setText(data);
                return;
            }
            else {
                data="0";
                tvExp.setText(data);
            }
        }
        if(btnText.equals("=")) {
            tvExp.setText(tvResult.getText());
            return;
        }
        data+=btnText;
        tvExp.setText(data);

        Log.i("Result", data);
        String finalResult = evaluateExpression(data);
        if(!finalResult.equals("Error"))
            tvResult.setText(finalResult);
        Log.i("Result", finalResult);
    }

    private String evaluateExpression(String expression) {
        // Создаем контекст Rhino
        Context rhino = Context.enter();
        // Устанавливаем версию JavaScript, которую будем использовать (по умолчанию актуальная)
        rhino.setOptimizationLevel(-1);
        // Без оптимизации для мобильных устройств
        try {
            // Создаем скриптовый объект Rhino
            Scriptable scope = rhino.initStandardObjects();
            // Выполняем выражение JavaScript
            String result = rhino.evaluateString(scope, expression, "JavaScript", 1, null).toString();
            // Приводим результат к числу и возвращаем его
            DecimalFormat decimalFormat = new DecimalFormat("#.###");

            return decimalFormat.format(Double.parseDouble(result));
        }
        catch (Exception e) {
            return "Error";
        }

        finally {
            // Выход из контекста Rhino, освобождаем ресурсы
            Context.exit();
            }
    }

}
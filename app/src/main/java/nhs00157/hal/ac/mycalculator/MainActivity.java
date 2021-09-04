package nhs00157.hal.ac.mycalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private TextView DisplayPanel;
    String mount_value = "";
    String operator = "";
    String copy_value = "";
    String left_side = "";
    String right_side = "";
    String result = "";
    float resultInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayPanel = (TextView)findViewById(R.id.DisplayPanel);
    }

    public void handleClickNumber(View props) {
        String button_value = (String)((Button) props).getText();

        if (button_value.equals(".")) {
            if (mount_value.indexOf(".") != -1) {
                return;
            } else if (mount_value.equals("")) {
                mount_value = "0.";
                DisplayPanel.setText("0.");
                return;
            } else {
                mount_value = mount_value + button_value;
                DisplayPanel.setText(mount_value);
                return;
            }
        }

        if (mount_value.equals("")) {
            if (button_value.equals("0")) {
                return;
            }
            mount_value = button_value;
            DisplayPanel.setText(button_value);
            return;
        }

        mount_value = mount_value + button_value;
        DisplayPanel.setText(mount_value);
    }

    public void handleClickFunction(View props) {
        int button_value = props.getId();
        switch (button_value) {
            case R.id.KeyCopy:
                copy_value = mount_value;
                break;
            case R.id.KeyClear:
                operator = "";
                break;
            case R.id.KeyAllClear:
                mount_value = "";
                operator = "";
                copy_value = "";
                left_side = "";
                right_side = "";
                DisplayPanel.setText("0");
                break;
            case R.id.KeyBackSpace:
                if (mount_value.length() == 1) {
                    mount_value = "";
                    DisplayPanel.setText("0");
                    break;
                }
                String new_value = mount_value.substring(0, mount_value.length()-1);
                mount_value = new_value;
                DisplayPanel.setText(new_value);
                break;
        }

    }

    public void handleClickOperator(View props) {
        int button_value = props.getId();
        switch (button_value) {
            case R.id.KeyDivision:
                operator = "/";
                left_side = mount_value;
                mount_value = "";
                break;
            case R.id.KeyMultiple:
                operator = "*";
                left_side = mount_value;
                mount_value = "";
                break;
            case R.id.KeySubtraction:
                operator = "-";
                left_side = mount_value;
                mount_value = "";
                break;
            case R.id.KeyAddition:
                operator = "+";
                left_side = mount_value;
                mount_value = "";
                break;
            case R.id.KeyEq:
                if (left_side.equals("")) {
                    break;
                } else {
                    right_side = mount_value;
//                    int left = Integer.parseInt(left_side);
//                    int right = Integer.parseInt(right_side);

                    float left = Float.parseFloat(left_side);
                    float right = Float.parseFloat(right_side);
                    switch (operator) {
                        case "/":
                            resultInt = left / right;
                            break;
                        case "*":
                            resultInt = left * right;
                            break;
                        case "-":
                            resultInt = left - right;
                            break;
                        case "+":
                            resultInt = left + right;
                            break;
                    }
                    result = new Float(resultInt).toString();
                    DisplayPanel.setText(result);

                    mount_value = "";
                    operator = "";
                    copy_value = "";
                    left_side = "";
                    right_side = "";
                    result = "";
                    break;
                }
        }
    }
}


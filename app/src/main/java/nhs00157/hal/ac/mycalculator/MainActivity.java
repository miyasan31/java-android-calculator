package nhs00157.hal.ac.mycalculator;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView DisplayPanel;
    String mountValue = "";
    String resultValue = "0";
    String copyValue = "";
    int operator = 0;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayPanel = (TextView)findViewById(R.id.DisplayPanel);
    }

    public void showNumber(String mountValue) {
        DecimalFormat form = new DecimalFormat("#,##0");
        String strDecimal = "";
        String strInt = "";
        String fText = "";

        if (mountValue.length() > 0) {
            int decimalPoint = mountValue.indexOf(".");
            if (decimalPoint > -1) {
                strDecimal = mountValue.substring(decimalPoint);
                strInt = mountValue.substring(0, decimalPoint);
            } else {
                strInt = mountValue;
            }

            fText = form.format(Double.parseDouble(strInt)) + strDecimal;
        } else {
            fText = "0";
        }
        DisplayPanel.setText(fText);
    }

    public void onClickNumber(View props) {
        String buttonValue = (String)((Button) props).getText();

        if (buttonValue.equals(".")) {
            if (mountValue.length() == 0) {
                mountValue = "0.";
            } else if (mountValue.indexOf(".") == -1) {
                mountValue = mountValue + ".";
            }
        } else {
            mountValue = mountValue + buttonValue;
        }
        showNumber(mountValue);
    }

    public void onClickFunction(View props) {
        int buttonValue = props.getId();

        switch (buttonValue) {
            case R.id.KeyCopy:
                if (count == 0) {
                    copyValue = resultValue;
                    count = 1;
                    return;
                } else {
                    mountValue = copyValue;
                    count = 0;
                }
                break;
            case R.id.KeyClear:
                mountValue = "";
                break;
            case R.id.KeyAllClear:
                mountValue = "";
                resultValue = "0";
                operator = 0;
                count = 0;
                break;
            case R.id.KeyBackSpace:
                if (mountValue.length() == 0) {
                    return;
                } else {
                    mountValue = mountValue.substring(0, mountValue.length() - 1);
                }
                break;
        }
        showNumber(mountValue);
    }

    public void onClickOperator(View props) {
        int buttonValue = props.getId();

        if (operator != 0 && mountValue.length() > 0) {
            resultValue = doCalc();
            showNumber(resultValue);
        } else if (mountValue.length() > 0) {
            resultValue = mountValue;
        }
        mountValue = "";

        if (buttonValue == R.id.KeyEq) {
            operator = 0;
        } else {
            operator = buttonValue;
        }
    }

    public String doCalc() {
        BigDecimal bd1 = new BigDecimal(resultValue);
        BigDecimal bd2 = new BigDecimal(mountValue);
        BigDecimal result = BigDecimal.ZERO;

        switch (operator) {
            case R.id.KeyAddition:
                result = bd1.add(bd2);
                break;
            case R.id.KeySubtraction:
                result = bd1.subtract(bd2);
                break;
            case R.id.KeyMultiple:
                result = bd1.multiply(bd2);
                break;
            case R.id.KeyDivision:
                if (!bd2.equals(BigDecimal.ZERO)) {
                    result = bd1.divide(bd2, 2, BigDecimal.ROUND_HALF_UP);
                } else {
                    Toast toast = Toast.makeText(this, "0で割れませんよ", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
        }

        if (result.toString().indexOf(".") >= 0) {
            return result.toString().replaceAll("￥￥.0+$|0+$", "");
        } else {
            return result.toString();
        }
    }
}


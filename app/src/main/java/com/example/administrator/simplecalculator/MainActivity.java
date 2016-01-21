package com.example.administrator.simplecalculator;

import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private int [] numIds=new int []{R.id.zero,R.id.one,R.id.two,
                                     R.id.three,R.id.four,R.id.five,
                                     R.id.six,R.id.seven,R.id.eight,
                                     R.id.nine,R.id.dot};//所有数字按钮的id
    private Button[] numBtns=new Button[numIds.length];//数字按钮
    private int [] operationIds=new int[]{R.id.plus,R.id.minus,R.id.mul,
                                          R.id.divide,R.id.mode,R.id.equal,
                                          R.id.clear,R.id.back};
    private Button[] operationBtns=new Button[operationIds.length];
    private String num1String;//第一个操作数
    private String num2String;//第二个操作数
    private String operationString;//运算符
    private String tempString;//保存临时数
    private boolean islastClickNum=false;
    private EditText showInfo;
    private boolean isFirstClickOperation=true;
    private double result=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid);
        NumClickListener numClickListener=new NumClickListener();
        for(int i=0;i<numIds.length;i++){
            numBtns[i]=(Button)findViewById(numIds[i]);
            numBtns[i].setOnClickListener(numClickListener);
        }
        OperationListener operationListener=new OperationListener();
        for (int i=0;i<operationIds.length;i++){
            operationBtns[i]= (Button) findViewById(operationIds[i]);
            operationBtns[i].setOnClickListener(operationListener);
        }
        showInfo= (EditText) findViewById(R.id.showInfo);
    }

    private class NumClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {//数字按钮的单击事件的处理
//            Toast.makeText(MainActivity.this,((Button)v).getText().toString(),
//                    Toast.LENGTH_SHORT).show();
            if(islastClickNum){//如果上一次单击的是数字
                if(v.getId()==R.id.dot){//如果单击的是小数点
                    if(tempString.indexOf(".")>=0){//如果已经包含小数点
                        return;
                    }

                }
                tempString+=((Button)v).getText().toString();//拼接新的数

            }else{
                if(v.getId()==R.id.dot){
                    tempString="0.";

                }else {
                    tempString=((Button)v).getText().toString();
                }

            }
            tempString=clearZero(tempString);
            showInfo.setText(tempString);
            islastClickNum=true;


        }
    }

    public String clearZero(String num){
        while (!num.startsWith("0.")&&num.startsWith("0")&&num.length()>1){
            num=num.substring(1);
        }
        return num;
    }

    private class OperationListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {//操作符的事件处理
            String operation=((Button)v).getText().toString();//获取操作符
            if(v.getId()==R.id.clear){//如果单击的是清空按钮
                clear();

            }
            if(v.getId()==R.id.back){//如果单击的是退格键
                if(tempString.length()>1){
                    tempString=tempString.substring(0,tempString.length()-1);
                }else {
                    tempString="0";
                }
                showInfo.setText(tempString);
            }
            if(islastClickNum){//上一次单击的是否为数字
                if(isFirstClickOperation){//如果是第一次单击运算符，则将这个数fuzi给num1String

                    if(!"=".equals(operation)){
                        num1String=tempString;
                        operationString=operation;
                        isFirstClickOperation=false;
                    }
                }else {
                    num2String=tempString;
                    getResult();
                    if(v.getId()==R.id.equal){
                        showInfo.setText(num1String+operationString+num2String+"="+result);
                    }else {
                        num1String=result+"";
                        tempString=num1String;
                        showInfo.setText(num1String);
                        operationString=operation;
                    }

                }

            }else {//多次单击操作运算符
                operationString=operation;
            }
            islastClickNum=false;
//            Toast.makeText(MainActivity.this,((Button)v).getText().toString(),
//                    Toast.LENGTH_SHORT).show();

        }
    }

    public  void clear(){//清空
        islastClickNum=false;
        isFirstClickOperation=true;
        num1String="";
        num2String="";
        operationString="";
        showInfo.setText("0");
        return;


    }
    public void getResult(){
        double num1=Double.parseDouble(num1String);
        double num2=Double.parseDouble(num2String);//将字符串转换为小数
        if("+".equals(operationString)){
            result=num1+num2;
        }else if ("-".equals(operationString)){
            result=num1-num2;
        }else if ("*".equals(operationString)){
            result=num1*num2;
        }else if ("/".equals(operationString)){
            result=num1/num2;
        }else if ("%".equals(operationString)){
            result=num1%num2;
        }
        showInfo.setText(num1String+operationString+num2String+"="+result);

    }
}

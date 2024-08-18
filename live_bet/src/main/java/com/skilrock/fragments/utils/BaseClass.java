package com.skilrock.fragments.utils;


 public  class BaseClass {
    PrintCall printCall;
    private static BaseClass baseClass=null;

    private BaseClass() {
    }

    public static BaseClass getInstance() {
        if (baseClass ==null){
            baseClass =new BaseClass();
        }
        return baseClass;
    }

    public PrintCall getPrintCall() {
        return printCall;
    }

    public void setPrintCall(PrintCall printCall) {
        this.printCall = printCall;
    }
}

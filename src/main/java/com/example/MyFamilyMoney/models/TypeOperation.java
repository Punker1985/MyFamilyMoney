package com.example.MyFamilyMoney.models;

public enum TypeOperation {
    RECEIPT("поступление"),
    SPENDING("списание");

 private final String displayValue;

 private TypeOperation(String displayValue) {
     this.displayValue = displayValue;
 }
 public String getDisplayValue() {
     return displayValue;
    }
}

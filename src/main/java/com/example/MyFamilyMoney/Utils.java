package com.example.MyFamilyMoney;

import com.example.MyFamilyMoney.models.*;
import com.example.MyFamilyMoney.repo.AccountRepository;
import com.example.MyFamilyMoney.repo.OperationsRepository;
import com.example.MyFamilyMoney.repo.TransfersRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Utils {

    public static Long inputAmount (String amount) {
        amount = amount.replace(',','.');
        if (checkNumbers(amount)) {
            double amountDouble = Double.parseDouble(amount) * 100;
            long amountLong = (long) amountDouble;
            return amountLong;
        }
        Long amountLong = Long.valueOf(0);
        return amountLong;
    }

    public static boolean checkNumbers(String input) {
        for (int ctr = 0; ctr < input.length(); ctr++) {
            if ("1234567890.".contains(Character.valueOf(input.charAt(ctr)).toString())) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }
   }

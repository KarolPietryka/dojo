package org.kp.solid.ocp;

import org.kp.solid.ocp.calculator.impl.CalcUnder;
import org.kp.solid.ocp.calculator.Calculator;
import org.kp.solid.ocp.calculator.impl.CalcAbove;

import java.util.Arrays;
import java.util.List;

public class CalcService {
    final int limit = 50;
    List<Calculator> calculatorList = Arrays.asList(
            new CalcAbove(),
            new CalcUnder());
    void calc(int arg){
        calculatorList.forEach(calculator -> {
            if(calculator.handle(arg, limit)) {calculator.calc(arg);}
        });
    }
}

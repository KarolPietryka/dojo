package org.kp.solid.ocp.calculator.impl;

import org.kp.solid.ocp.calculator.Calculator;

public class CalcAbove  implements Calculator {
    @Override
    public void calc(int arg) {
        System.out.println("more then lim");
    }

    @Override
    public boolean handle(int arg, int lim) {
        return arg > lim;
    }
}

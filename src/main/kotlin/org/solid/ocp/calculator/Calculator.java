package org.solid.ocp.calculator;

public interface Calculator {
    void calc(int arg);
    boolean handle(int arg, int lim);
}

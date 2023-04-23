package org.solid.isp.violation;

import java.math.BigDecimal;
import java.util.Optional;

abstract public class AbstractAccount {
    BigDecimal balance;
    BigDecimal withdraw(BigDecimal toWithdraw){
        return Optional.ofNullable(balance)
                .map(balance -> {
                    if(balance.compareTo(toWithdraw) > 0) { return balance.subtract(toWithdraw);}
                    else {
                        BigDecimal left = new BigDecimal(balance.toString());
                        this.balance = new BigDecimal(0);
                        return left;}
                }).orElse(BigDecimal.ZERO);
    }

}

package org.solid.account.impl;



import org.solid.account.AbstractAccount;
import org.solid.account.WithdrawableAccount;

import java.math.BigDecimal;
import java.util.Optional;

public class WithdrawAccount extends AbstractAccount implements WithdrawableAccount {
    public BigDecimal withdraw(BigDecimal toWithdraw){
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

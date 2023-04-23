package org.solid.account;

import java.math.BigDecimal;

public interface WithdrawableAccount {
    BigDecimal withdraw(BigDecimal toWithdraw);
}

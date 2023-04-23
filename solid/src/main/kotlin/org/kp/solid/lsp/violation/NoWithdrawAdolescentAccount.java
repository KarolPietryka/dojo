package org.kp.solid.lsp.violation;

import java.math.BigDecimal;

/**
 * Violation because class is forced to have implementation
 * Solution:
 * @see org.solid.account
 */
public class NoWithdrawAdolescentAccount {
    BigDecimal withdraw(BigDecimal toWithdraw){
        throw new UnsupportedOperationException();
    }
}

package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.unibo.bank.impl.SimpleBankAccount.MANAGEMENT_FEE;
import static it.unibo.bank.impl.StrictBankAccount.TRANSACTION_FEE;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Assertions;

public class TestStrictBankAccount {

    private final static int INITIAL_AMOUNT = 100;

    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        this.mRossi = new AccountHolder("Mario", "Rossi", 1);
        this.bankAccount = new StrictBankAccount(mRossi, 0.0);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        assertEquals(0.0, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactionsCount());
        assertEquals(mRossi, bankAccount.getAccountHolder());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        this.bankAccount.deposit(this.mRossi.getUserID(), INITIAL_AMOUNT);
        assertEquals(INITIAL_AMOUNT, this.bankAccount.getBalance());
        assertEquals(1, this.bankAccount.getTransactionsCount());
        this.bankAccount.chargeManagementFees(this.mRossi.getUserID());
        assertEquals(INITIAL_AMOUNT - (MANAGEMENT_FEE + TRANSACTION_FEE), this.bankAccount.getBalance());
        assertEquals(0, this.bankAccount.getTransactionsCount());
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        try{
            this.bankAccount.withdraw(this.mRossi.getUserID(), -10);
            Assertions.fail();
        } catch(IllegalArgumentException e) {
            assertEquals("Cannot withdraw a negative amount", e.getMessage());
            assertEquals(0.0, this.bankAccount.getBalance());
            assertEquals(0,this.bankAccount.getTransactionsCount());
        }
        
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        try{
            this.bankAccount.withdraw(this.mRossi.getUserID(), this.bankAccount.getBalance() + 1);
            Assertions.fail();
        } catch(IllegalArgumentException e) {
            assertEquals("Insufficient balance", e.getMessage());
            assertEquals(0.0, this.bankAccount.getBalance());
            assertEquals(0,this.bankAccount.getTransactionsCount());

        }
    }
}

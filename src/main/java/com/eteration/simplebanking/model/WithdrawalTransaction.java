package com.eteration.simplebanking.model;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

// This class is a place holder you can change the complete implementation
@Entity
@DiscriminatorValue("WITHDRAWAL")
public class WithdrawalTransaction  extends Transaction{

    public WithdrawalTransaction(double amount) {
        super(amount);
    }
}



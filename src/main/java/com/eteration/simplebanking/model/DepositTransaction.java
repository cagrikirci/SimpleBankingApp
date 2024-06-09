package com.eteration.simplebanking.model;


// This class is a place holder you can change the complete implementation

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("DEPOSIT")
public class DepositTransaction extends  Transaction  {


    public DepositTransaction(double amount) {
        super(amount);
    }
}

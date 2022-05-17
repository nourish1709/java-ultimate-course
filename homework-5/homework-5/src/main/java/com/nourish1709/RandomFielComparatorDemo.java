package com.nourish1709;

import com.bobocode.data.Accounts;
import com.bobocode.model.Account;

public class RandomFielComparatorDemo {

    public static void main(String[] args) {
        var accounts = Accounts.generateAccountList(10);

        var randomFieldComparator = new RandomFieldComparator<>(Account.class);
        accounts.stream()
                .sorted(randomFieldComparator)
                .forEach(System.out::println);

        System.out.println(randomFieldComparator);
    }
}

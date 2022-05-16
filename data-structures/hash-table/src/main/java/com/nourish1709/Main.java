package com.nourish1709;

import com.bobocode.data.Accounts;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        var accounts = Accounts.generateAccountList(10);
        var emailToBirthdayTable = new HashTable<String, LocalDate>();
        accounts.forEach(a -> emailToBirthdayTable.put(a.getEmail(), a.getBirthday()));
        emailToBirthdayTable.printTable();
    }
}

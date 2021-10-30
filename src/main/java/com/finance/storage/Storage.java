package com.finance.storage;

import com.finance.model.Transaction;

import java.util.HashSet;
import java.util.Set;

public class Storage {
    private final static Set<Transaction> list = new HashSet<>();

    public static Set<Transaction> getList() {
        return list;
    }

    public static void setList(Set<Transaction> list) {
        getList().addAll(list);
    }

//    public static TinkOff getById(int id) throws FileNotFoundException {
//        if (id < list.size()) {
//            return list.stream().filter(e-> e.)
//            return list.get(id);
//        } else {
//            throw new FileNotFoundException();
//        }
//    }

//    public static void setById(TinkOff tinkOff) {
//        getList().add(tinkOff);
//    }
}
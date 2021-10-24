package com.finance.storage;

import com.finance.model.TinkOff;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StorageOff {
    private final static Set<TinkOff> list = new HashSet<>();

    public static Set<TinkOff> getList() {
        return list;
    }

    public static void setList(Set<TinkOff> list) {
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
package com.finance.storage;

import com.finance.model.TinkOff;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class StorageOff {
    private final static ArrayList<TinkOff> list = new ArrayList<>();

    public static ArrayList<TinkOff> getList() {
        return list;
    }

    public static void setList(List<TinkOff> list) {
        getList().addAll(list);
    }

    public static TinkOff getById(int id) throws FileNotFoundException {
        if (list.size() > id) {
            return list.get(id);
        } else {
            throw new FileNotFoundException();
        }
    }

    public static void setById(TinkOff tinkOff) {
        getList().add(tinkOff);
    }
}
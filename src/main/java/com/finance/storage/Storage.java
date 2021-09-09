package com.finance.storage;

import com.finance.model.MainTink;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final static List<MainTink> list = new ArrayList<>();

    public static List<MainTink> getList() {
        return list;
    }

    public static MainTink getById(int id) throws FileNotFoundException {
        if (list.size() > id) {
            return list.get(id);
        } else {
            throw new FileNotFoundException();
        }
    }

    public static void setList(List<MainTink> list) {
        getList().addAll(list);
    }

    public static void setById(MainTink mainTink) {
        getList().add(mainTink);
    }
}

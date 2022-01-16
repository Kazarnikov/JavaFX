package com.finance.convert;

import com.finance.model.Check;
import com.finance.model.Transaction;



import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class JOSNParser {
    public static void convertJSON(Collection<Transaction> collection){
       List<Check> checkList = new ArrayList<>();

        collection.forEach(transaction -> {
            if (!checkList.isEmpty()){
                checkList.forEach(check -> {





                });

            } else {
                checkList.add(new Check(transaction));
            }
        });
        System.err.println(checkList);
    }
}

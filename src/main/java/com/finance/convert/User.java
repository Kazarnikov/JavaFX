package com.finance.convert;

import java.util.List;

public enum User implements Impl{
//    VIRA("Вера", "*4749"),
//    VIRA_TRANSFER(),
//    ALEXANDER("*2815", "*0239"),
//    ALEXANDER_TRANSFER("*5558", "*7920");


    VIRA{
        @Override
        public String getName() {
            return "Вера";
        }

        @Override
        public boolean match(String str) {
            return false;
        }

        @Override
        public void draw() {

        }
    },
    VIRA_TRANSFER{
        @Override
        public String getName() {
            return null;
        }

        @Override
        public boolean match(String str) {
            return false;
        }

        @Override
        public void draw() {

        }
    },
    ALEXANDER{
        @Override
        public String getName() {
            return null;
        }

        @Override
        public boolean match(String str) {
            return false;
        }

        @Override
        public void draw() {

        }
    },
    ALEXANDER_TRANSFER{
        @Override
        public String getName() {
            return null;
        }

        @Override
        public boolean match(String str) {
            return false;
        }

        @Override
        public void draw() {

        }
    },

//    private String name;
//    private List<String> numbers;
//
//    User(String...numbers) {
//        this.numbers = numbers;
//    }
//
//    User(String name, String...numbers) {
//        this.name = name;
//        this.numbers = numbers;
//    }
//
//    public String[] getNumbers() {
//        return numbers;
//    }
//
//    public void setNumbers(String...numbers) {
//        this.numbers = numbers;
//    }
//
//    public void addNumbers(String...numbers) {
//        this.numbers = numbers;
//    }



}
interface Impl {
    String getName();
    boolean match(String str);
    void draw();

}

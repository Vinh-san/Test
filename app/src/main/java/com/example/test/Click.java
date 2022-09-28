package com.example.test;

import java.util.List;

public interface Click {

    void event(List<String> strings, String item, int position);
    void transfer(List<String> strings, String item);
}
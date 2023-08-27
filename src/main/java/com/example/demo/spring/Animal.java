package com.example.demo.spring;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.nio.file.Path;
import java.nio.file.Paths;

@SuperBuilder
@Data
public abstract class Animal {
    private String area;

    public Animal(String area) {
        this.area = area;
    }

    public static void test(double a) {
        System.out.println(a == 1607082786557l);
    }


    public static void main(String[] args) {

        String modelFilePath = "D:\\vMonitor.log";
        Path modelPath = Paths.get(modelFilePath);

        System.out.println(modelPath);
    }
}

package com.example.demo.algorithm;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

//评测题目: 有两个文件，文件A包含10万条物流轨迹的文案，每条轨迹占一行，平均20个字；
//         文件B中10万个敏感词（1-4个字符），每个词一行，需要判断并输出包含敏感词的物流轨迹文案。
public class SensitiveTextDectector {

    /**
     * 打印输出包含敏感字符的轨迹记录
     * @param traceRecordFilePath 假设其为物流轨迹所在文件A的路径，是本地文件，文件字符编码为GBK
     * @param sensitiveCharPath 假设其为敏感字符所在文件B的路径，是本地文件，文件字符编码为UTF-8
     */
    public void printSensitiveTraceRecord(String traceRecordFilePath, String sensitiveCharPath) throws IOException {

        Map sensWordMap = getSensitiveCharPath(sensitiveCharPath);


        try (BufferedReader dataReader = Files.newBufferedReader(
                Paths.get(traceRecordFilePath),  Charset.forName("GBK"))) {
            String line;
            while (true) {
                line = dataReader.readLine();
                if (line == null) {
                    break;
                }

                for (int i = 0; i < line.length(); i++) {
                    if (hasSensitiveWorld(line, i, sensWordMap)) {
                        System.out.println(line);
                    }

                }

            }

        }
    }

    private Boolean hasSensitiveWorld(String line, int startIndex, Map sensWordMap) {
        Map current = sensWordMap;
        for (int i = startIndex; i < line.length(); i++) {
            current = (Map) current.get(line.charAt(i));
            if (current == null) {
                return false;
            }
            if("1".equals(current.get("end"))){
                return true;
            }
        }

        return false;
    }


    @SuppressWarnings({ "unchecked" })
    private Map getSensitiveCharPath(String sensitiveCharPath) throws IOException {
        HashMap sensWordMap = new HashMap(50000);
        try (BufferedReader dataReader = Files.newBufferedReader(Paths.get(sensitiveCharPath))) {
            String line;
            while (true) {
                line = dataReader.readLine();
                if (line == null) {
                    break;
                }

                Map current = sensWordMap;
                Map<String, String> newMap;

                for(char c:  line.toCharArray()){
                    Object wordMap = current.get(c);

                    if(wordMap != null){
                        current = (Map) wordMap;
                    } else{
                        newMap = new HashMap<>();
                        current.put(c, newMap);
                        current = newMap;
                    }
                }

                current.put("end", "1");

            }
        }

        return sensWordMap;
    }

    public static void main(String[] args) throws IOException{
        SensitiveTextDectector sensitiveTextDectector = new SensitiveTextDectector();
        sensitiveTextDectector.printSensitiveTraceRecord(
                "C:\\Users\\张航\\Desktop\\a.txt",
                "C:\\Users\\张航\\Desktop\\b.txt");
    }

}

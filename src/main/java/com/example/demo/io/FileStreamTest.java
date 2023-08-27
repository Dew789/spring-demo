package com.example.demo.io;

import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.nio.*;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.Arrays;

public class FileStreamTest {

    public static void processFile() throws IOException {
        File file = new ClassPathResource("data").getFile();
        try (OutputStream output = new FileOutputStream(file) ) {
            byte data[] = {1, 2, 3, 4, 5};
            output.write(data);
        }

        try (InputStream input = new FileInputStream(file)) {
            int n;
            while ((n = input.read()) != -1) {
                System.out.println(n);
            }
        }
    }

    public static void processFileNIO() throws IOException {
        Path path = new ClassPathResource("data").getFile().toPath();

        try (FileChannel channel = FileChannel.open(path)) {
            int capacity = 10;
            ByteBuffer bf = ByteBuffer.allocate(capacity);
            while (channel.read(bf) != -1) {
                System.out.println("Limit：" + bf.limit() + ",Capacity" + bf.capacity() + " ,Position" + bf.position());
                bf.clear();
                System.out.println(Arrays.toString(bf.array()) );
            }
        }
    }

    public static void main(String[] args) {
        try {
            processFileNIO();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
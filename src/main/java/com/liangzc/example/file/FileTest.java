package com.liangzc.example.file;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileTest {


    public static void main(String[] args) throws IOException {


//        getFilePath();

//        getFilePath2();

        getFilePath3();

    }


    private static void getFilePath3() throws IOException {

        String pfx = ".pfx";
        String machId = "szfsC00000000X77";
        String path = "/hqcverify/" + machId;
        String pfxPath = path + "/"+machId +pfx;


        System.out.println(pfxPath);



        ClassPathResource classPathResource = new ClassPathResource(pfxPath);
        File pfxPathFile = classPathResource.getFile();
        System.out.println("resourceFile:"+pfxPathFile.getPath());

        String classpathStr = pfxPathFile.getPath();

        System.out.println("classpath路径："+classpathStr);

        System.out.println("是否是文件："+pfxPathFile.isFile());

        if (pfxPathFile.exists()){
            System.out.println("exists");
        }else {
            System.out.println("not exists");
        }
//        if(!pfxPathFile.exists()){
//            System.out.println("该商户号对应的证书信息pfx不存在！");
//        }
        System.out.println(pfxPathFile.getName());
        System.out.println(pfxPathFile.getPath());
    }

    private static void getFilePath2() throws IOException {

        String pfx = ".pfx";
        String machId = "szfsC00000000X77";
        String path = "/hqcverify/" + machId;
        String pfxPath = path + "/"+machId +pfx;
        System.out.println(pfxPath);

//        String classpathStr = ResourceUtils.getFile("classpath:").toString();
        String classpathStr = ResourceUtils.getFile("classpath:").toString();

        System.out.println("classpath路径："+classpathStr);

        File pfxPathFile = new File(classpathStr +pfxPath);

        System.out.println("是否是文件："+pfxPathFile.isFile());

        if (pfxPathFile.exists()){
            System.out.println("exists");
        }else {
            System.out.println("not exists");
        }
//        if(!pfxPathFile.exists()){
//            System.out.println("该商户号对应的证书信息pfx不存在！");
//        }
        System.out.println(pfxPathFile.getName());
        System.out.println(pfxPathFile.getPath());
    }

    private static void getFilePath() throws FileNotFoundException {

        String pfx = ".pfx";
//        String crt = ".crt";
        String machId = "szfsC00000000X77";
        String path = "/hqcverify/" + machId;
        String pfxPath = path + "/"+machId +pfx;
//        String crtPath = path + "/"+machId +crt;

        System.out.println(pfxPath);
//        System.out.println(crtPath);

        String classpathStr = ResourceUtils.getURL("classpath:").toString();
        String newClasspathStr = classpathStr.substring(6, classpathStr.length() - 1);
        System.out.println("classpath路径："+classpathStr);
        System.out.println("newClasspath路径："+newClasspathStr);

        File pfxPathFile = new File(newClasspathStr +pfxPath);

        System.out.println("是否是文件："+pfxPathFile.isFile());

        if (pfxPathFile.exists()){
            System.out.println("exists");
        }else {
            System.out.println("not exists");
        }
//        if(!pfxPathFile.exists()){
//            System.out.println("该商户号对应的证书信息pfx不存在！");
//        }
        System.out.println(pfxPathFile.getName());
        System.out.println(pfxPathFile.getPath());
    }



}

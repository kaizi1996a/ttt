package com.example.ttt;

import com.alibaba.fastjson.JSONArray;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipOutputStream;

@RestController
@CrossOrigin
public class TestController {
    @RequestMapping("/qqq")
     public List ttt(){
        List list = new ArrayList();
         for (int i = 1; i <5 ; i++) {
             Stu s= new Stu();
             s.setAge(i+12);
             s.setId(i);
             s.setName("zzz"+i);
             s.setSex("boy");
             list.add(s);
         }
         return list;
     }

    @RequestMapping(value = "/download")
    public String FDownload(HttpServletRequest request, HttpServletResponse response,String name) throws IOException {
        String fileName = name;// 待下载的文件名，可以是其他类型的文件
        int m = name.lastIndexOf(".");
        File ff= new File("D://xiazai/"+name);
        String type=name.substring(m+1);
        if (type.equals("zip")){
            if (!ff.exists()){
                TestController.zip(name);
            }
        }else{
            if (!ff.exists()){
                TestController.Test(name);
            }
        }
        if (fileName != null) {
            String realPath = "D://xiazai/";//待下载的源文件存在地址
            File file =new File(realPath,name);
            if (file.exists()) {
                response.setContentType("application/force-download");
                response.setHeader( "Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1" ) );//设置中文文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }


//    @RequestMapping("/mkdirs")
//    public void asd(String name){
//        File folder = new File("D:\\xiazai\\"+name);
//        if (!folder.exists() && !folder.isDirectory()) {
//            folder.mkdirs();
//            System.out.println("创建文件夹");
//        } else {
//            System.out.println("文件夹已存在");
//
//        }
//
//
//    }


    @RequestMapping("/createNewFile")
    public static void Test(String name) throws IOException {
        List list = new ArrayList();
        File file = new File("D:\\xiazai\\"+name);
        if (!file.exists()) {
            file.createNewFile();//创建文件
            for (int i = 1; i <5 ; i++) {
                Stu s= new Stu();
                s.setAge(i+12);
                s.setId(i);
                s.setName("zzz"+i);
                s.setSex("boy");
                list.add(s);
            }
            File file2 =new File("D:\\xiazai\\"+name);
            Writer out =new FileWriter(file2);
            String l = JSONArray.toJSONString(list);
            out.write(l);
            out.flush();
            out.close();
            System.out.println("测试文件不存在");
        }
    }



    @RequestMapping("/zip")
    public static void zip(String name) throws IOException {
        long start=System.currentTimeMillis();
        List<File> list = new ArrayList();
        ZipOutputStream os=new ZipOutputStream(new FileOutputStream("D:\\xiazai\\"+name));
        if (!new File("D:\\xiazai\\sss.txt").exists()){
            TestController.Test("sss.txt");
        }
        if (!new File("D:\\xiazai\\qqq.txt").exists()){
            TestController.Test("qqq.txt");
        }
        if (!new File("D:\\xiazai\\cccc.txt").exists()){
            TestController.Test("cccc.txt");
        }
        list.add(new File("D:\\xiazai\\sss.txt"));
        list.add(new File("D:\\xiazai\\qqq.txt"));
        list.add(new File("D:\\xiazai\\cccc.txt"));
        Zip.zipFile(os,list);
        long end=System.currentTimeMillis();
        System.out.println("压缩完成，耗时:"+(end-start)+"ms");
    }

}

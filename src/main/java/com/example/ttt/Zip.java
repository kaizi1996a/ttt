package com.example.ttt;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {
    public static void zipFile(ZipOutputStream out, List<File> fileList) throws IOException {
        BufferedOutputStream bo = new BufferedOutputStream(out);
        for (int i = 0; i <fileList.size() ; i++) {
            File fileTemp=fileList.get(i);
            String fileNameTemp=fileTemp.getName();
            out.putNextEntry(new ZipEntry(fileNameTemp));
            System.out.println("被压缩的文件"+fileNameTemp);
            FileInputStream fis = new FileInputStream(fileTemp);
            BufferedInputStream bi = new BufferedInputStream(fis);
            int b;
            while ((b=bi.read())!=-1){
                bo.write(b);
            }
            bo.flush();
            bi.close();
            fis.close();

        }
        bo.close();
    }

}

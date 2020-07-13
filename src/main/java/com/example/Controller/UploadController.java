package com.example.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class UploadController {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
    @PostMapping("/upload")
    public String upload(MultipartFile uploadFile, HttpServletRequest httpServletRequest){
        String realPath = "D://temp/";
        String format = sdf.format(new Date());
        if (uploadFile.isEmpty())
            return "上传失败，请选择文件";
        File folder = new File(realPath + format);
        if(!folder.isDirectory()){
            folder.mkdirs();
        }
        String oldName = uploadFile.getOriginalFilename();
        String newName = UUID.randomUUID().toString()+
                oldName.substring(oldName.lastIndexOf("."));
        try {
            uploadFile.transferTo(new File(folder,newName));

        }catch (Exception e){
            e.printStackTrace();
        }
        return httpServletRequest.getScheme()+"://"+httpServletRequest.getServerName()+":"+httpServletRequest.getServerPort()+"/"+format+newName;
    }

    @PostMapping("/uploads")
    public String[] uploads(MultipartFile[] uploadFiles, HttpServletRequest httpServletRequest){
        String realPath = "D://temp/";
        MultipartFile uploadFile = null;
        String[] allBack = new String[uploadFiles.length];
        String format = sdf.format(new Date());
        for (int i = 0; i <uploadFiles.length ; i++) {
            uploadFile = uploadFiles[i];
            if (uploadFile.isEmpty()){
                allBack[i] = i+" is null";
                continue;
            }
            File folder = new File(realPath + format);
            if(!folder.isDirectory()){
                folder.mkdirs();
            }
            String oldName = uploadFile.getOriginalFilename();
            String newName = UUID.randomUUID().toString()+
                    oldName.substring(oldName.lastIndexOf("."));
            try {
                uploadFile.transferTo(new File(folder,newName));

            }catch (Exception e){
                e.printStackTrace();
            }
            allBack[i] = (httpServletRequest.getScheme()+"://"+httpServletRequest.getServerName()+":"+httpServletRequest.getServerPort()+"/"+format+newName);
            System.out.println(allBack[i]);
        }
        return allBack;
    }
}

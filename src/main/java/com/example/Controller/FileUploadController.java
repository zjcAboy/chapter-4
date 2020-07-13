package com.example.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
@RestController
public class FileUploadController {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
    @PostMapping("/upload")
    public String upload(MultipartFile uploadFile, HttpServletRequest req){
        String realPath =
                req
                        .getSession()
                        .getServletContext()
                        .getRealPath("/uploadFile/");
        String format = sdf.format(new Date());
        System.out.println(req.getRealPath("/uploadFile/"));
        System.out.println(realPath+format);
        File folder = new File(realPath + format);
        if(!folder.isDirectory()){
            folder.mkdirs();
        }
        String oldName = uploadFile.getOriginalFilename();
        System.out.println(oldName);
        String newName = UUID.randomUUID().toString()+
                oldName.substring(oldName.lastIndexOf("."));
        System.out.println(newName);
        //UUID含义是通用唯一识别码
        try {
            uploadFile.transferTo(new File(folder,newName));
            String filePath = req.getScheme() + "://" + req.getServerName() + ":" +
                    req.getServerPort() + "/uploadFile/" + format + newName;
            System.out.println("req.getScheme()  "+req.getScheme());
            System.out.println("req.getServerName()   "+req.getServerName());
            System.out.println("req.getServerPort()  "+req.getServerPort());
            System.out.println("format  "+format);
            System.out.println("newName  "+newName);
            return filePath;

        }catch (IOException e){
            e.printStackTrace();
        }
        return "上传失败";
    }
}

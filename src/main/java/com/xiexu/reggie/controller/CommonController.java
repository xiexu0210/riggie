package com.xiexu.reggie.controller;


import com.xiexu.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/common")
public class CommonController {

    @Value("${reggie.path}")
    private  String basepath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
//        log.info("file:{}", file.toString());
        //file是一个临时文件，需要转储在指定位置，否则完成后临时文件将会自动删除
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        //使用UUID随机生成文件明
        String  filename = UUID.randomUUID().toString() + suffix;

        File dir = new File(basepath);
        if(!dir.exists()){
            dir.mkdirs();
        }


        try {
            file.transferTo(new File(basepath + filename));
        }catch (IOException e){
            e.printStackTrace();
        }

        return R.success(filename);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        //文件下载
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(basepath + name));
            ServletOutputStream outputStream = response.getOutputStream();

            int len=0;
            byte[] bytes = new byte[1024];
            while ((len=fileInputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
            outputStream.close();
            fileInputStream.close();


        }catch (Exception e){
            e.printStackTrace();
        }

    }

}

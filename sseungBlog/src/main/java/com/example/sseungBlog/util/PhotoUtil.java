package com.example.sseungBlog.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class PhotoUtil {
    @Value("${postImgLocation}")
    private String postImgLocation;


    public String ckUpload(MultipartHttpServletRequest request) {
        MultipartFile uploadFile = request.getFile("upload");
        String fileName = getFileName(uploadFile); //저장할 파일이름
        //C:/blog/post/
        String realPath = getPath(request); //파일을 저장할 경로
        //C:/blog/post/ss.jpg
        String savePath = realPath + fileName; //파일을 저장할 실제 서버 경로 + 파일명

        String uploadPath = "/images/" + fileName;
        uploadFile(savePath, uploadFile);
        System.out.println("uploadPath: " + uploadPath);
        return uploadPath;
    }

    ;

    //파일 업로드 메소드
    private void uploadFile(String savePath, MultipartFile uploadFile) {
        File file = new File(savePath); //savePath: 파일을 저장할 경로
        try {
            uploadFile.transferTo(file); //파일이 서버에 저장되는 순간
        } catch (Exception e) {
            throw new RuntimeException("파일 업로드를 실패했습니다.", e);
        }
    }

    //파일 이름 얻는 메소드
    private String getFileName(MultipartFile uploadFile) {
        String originalFileName = uploadFile.getOriginalFilename();
        String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
        return UUID.randomUUID() + ext; //중복되지 않는 이미지명을 return해준다.
    }

    //경로 얻는 메소드
    private String getPath(MultipartHttpServletRequest request) {
        //실제 서버내 파일 저장 경로
        String realPath = postImgLocation + "/";
        System.out.println("realPath:" + realPath);
        Path directoryPath = Paths.get(realPath);

        if (!Files.exists(directoryPath)) {//해당 디렉터리가 존재하지 않는다면
            try {
                Files.createDirectories(directoryPath);
            } catch (Exception e) {
                throw new RuntimeException("업로드할 디렉터리가 존재하지 않습니다.", e);
            }
        }
        return realPath;
    }
}

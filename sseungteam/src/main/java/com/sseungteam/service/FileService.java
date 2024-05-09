package com.sseungteam.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class FileService {
    public String uploadFile(String uploadPath, String originalFileName,
                             byte[] fileData) throws Exception {
        UUID uuid = UUID.randomUUID();

        //. 뒤에있는 확장자명 구하기
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        //파일 이름 생성
        String savedFileName = uuid.toString() + extension;

        //경로
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;

        //파일 업로드
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);
        fos.close();

        return savedFileName;
    }

    //이미지 파일을 서버에서 삭제
    public void deleteFile(String filePath) throws Exception {
        File deleteFile = new File(filePath);

        //파일 삭제
        if(deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일을 삭제하였습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }
}

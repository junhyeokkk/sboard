package com.sboard.Service;

import com.sboard.dto.ArticleDTO;
import com.sboard.dto.FileDTO;
import com.sboard.entity.FileEntity;
import com.sboard.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Service
public class FileService {

    private final FileRepository fileRepository;
    private final ModelMapper modelMapper;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    public List<FileDTO> uploadFile(ArticleDTO articleDTO) {

        // 파일 업로드 경로 파일 객체 생성
        File fileUploadPath = new File(uploadPath);


        // 파일 업로드 경로가 존재하지 않으면 디렉터리 생성
        if(!fileUploadPath.exists()){
            fileUploadPath.mkdirs();
        }

        // 파일 업로드 시스템 경로 구하기
        String path = fileUploadPath.getAbsolutePath();

        // 파일 정보 객체 가져오기
        List<MultipartFile> files = articleDTO.getFiles();

        // 첨부파일 정보 객체 리스트 생성
        List<FileDTO> uploadFiles = new ArrayList<>();

        for (MultipartFile file : files) {

            if(!file.isEmpty()){
                String oName = file.getOriginalFilename();
                String ext = oName.substring(oName.lastIndexOf("."));
                String sName = UUID.randomUUID().toString() + ext;

                // 파일 저장
                try {
                    file.transferTo(new File(path, sName));
                } catch (IOException e) {
                    log.error(e);
                }

                FileDTO fileDTO = FileDTO.builder()
                        .oName(oName)
                        .SName(sName)
                        .build();
                uploadFiles.add(fileDTO);
            }
        }
        return uploadFiles;
    }

    public ResponseEntity<Resource> downloadFile(int fno){
       Optional<FileEntity> opt = fileRepository.findById(fno);
        FileEntity fileEntity = null;
       if(opt.isPresent()){
           fileEntity = opt.get();

           // 파일 다운로드 카운트 +1
           int count = fileEntity.getDownload();
           fileEntity.setDownload(count+1);

           fileRepository.save(fileEntity);

       }


        try {
            Path path = Paths.get(uploadPath + fileEntity.getSName());
            String contentType = Files.probeContentType(path);

           HttpHeaders headers = new HttpHeaders();
           headers.setContentDisposition(
                   ContentDisposition.builder("attachment")
                           .filename(fileEntity.getOName(), StandardCharsets.UTF_8).build());
           headers.add(HttpHeaders.CONTENT_TYPE, contentType);
           Resource resource = new InputStreamResource(Files.newInputStream(path));

           return ResponseEntity
                   .ok()
                   .headers(headers)
                   .body(resource);
        } catch (IOException e) {

            return ResponseEntity
                    .notFound().build();
        }
    }
    public void insertFile(FileDTO fileDTO) {

        FileEntity fileEntity = modelMapper.map(fileDTO, FileEntity.class);

        fileRepository.save(fileEntity);
    }
    public FileDTO selectFile(int fno){

        return null;
    }
    public List<FileDTO> selectFileAll(){

        return null;
    }
    public void updateFile(FileDTO fileDTO) {

    }
    public void deleteFile(int fno){

    }
}

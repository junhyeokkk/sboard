package com.sboard.Service;

import com.sboard.dto.ArticleDTO;
import com.sboard.dto.FileDTO;
import com.sboard.entity.FileEntity;
import com.sboard.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    public void downloadFile(){

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

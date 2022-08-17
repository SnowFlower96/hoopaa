package com.ssafy.api.service;

import com.ssafy.db.dto.RoomInfoDto;
import com.ssafy.db.entity.RoomHashtag;
import com.ssafy.db.entity.RoomInfo;
import com.ssafy.db.repository.HashtagRepository;
import com.ssafy.db.repository.RoomInfoRepositorySupport;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service("RoomInfoService")
public class RoomInfoServiceImpl implements RoomInfoService {

    @Value("${upload.path}")
    private String thumbPath;

    @Autowired
    RoomInfoRepositorySupport roomInfoRepositorySupport;

    @Autowired
    HashtagRepository hashtagRepository;

    @Override
    public List<RoomInfoDto> findAll() throws IOException {
        List<RoomInfo> room = roomInfoRepositorySupport.findAll();

        return roomInfoToDto(room);
    }

    @Override
    public List<RoomInfoDto> searchBy(String keyword) throws IOException {
        List<RoomInfo> room = roomInfoRepositorySupport.searchBy(keyword);

        return roomInfoToDto(room);
    }

    @Override
    public List<RoomInfoDto> findByCate(int cate) throws IOException {
        List<RoomInfo> room = roomInfoRepositorySupport.findByCate(cate);

        return roomInfoToDto(room);
    }

    @Override
    public List<RoomInfoDto> roomInfoToDto(List<RoomInfo> roomInfoList) throws IOException {
        List<RoomInfoDto> roomInfoDtoList = new LinkedList<>();
        RoomInfoDto roomInfoDto;
        for (RoomInfo r : roomInfoList){
            String encodedThumb = r.getThumbUrl() != null ? Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(thumbPath + r.getThumbUrl()))) : null;
            Optional<RoomHashtag> hashtagOptional;
            String[] hashes = new String[3];
            hashtagOptional = r.getRoomDescription().getHash1() != null ? hashtagRepository.findById(r.getRoomDescription().getHash1()) : Optional.empty();
            hashes[0] = hashtagOptional.map(RoomHashtag::getNm).orElse(null);
            hashtagOptional = r.getRoomDescription().getHash2() != null ? hashtagRepository.findById(r.getRoomDescription().getHash2()) : Optional.empty();
            hashes[1] = hashtagOptional.map(RoomHashtag::getNm).orElse(null);
            hashtagOptional = r.getRoomDescription().getHash3() != null ? hashtagRepository.findById(r.getRoomDescription().getHash3()) : Optional.empty();
            hashes[2] = hashtagOptional.map(RoomHashtag::getNm).orElse(null);
            roomInfoDto = RoomInfoDto.builder()
                    .id(r.getId()).hostID(String.valueOf(r.getHostId()))
                    .thumbUrl(encodedThumb).phase(r.getPhase()).maxNum(r.getMaxNum()).curNum(r.getCurNum())
                    .startTime(r.getStartTime()).cate(r.getRoomDescription().getCate())
                    .hash1(hashes[0]).hash2(hashes[1]).hash3(hashes[2])
                    .title(r.getRoomDescription().getTitle()).subtitle(r.getRoomDescription().getSubtitle())
                    .build();
            roomInfoDtoList.add(roomInfoDto);
        }
        return roomInfoDtoList;
    }

}

package com.ssafy.api.service;

import com.ssafy.api.request.RoomOpenReq;
import com.ssafy.db.entity.Hashtag;
import com.ssafy.db.entity.RoomInfo;
import com.ssafy.db.repository.HashtagRepository;
import com.ssafy.db.repository.RoomInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("roomService")
public class RoomServiceImpl implements RoomService{

    @Autowired
    RoomInfoRepository roomInfoRepository;

    @Autowired
    HashtagRepository hashtagRepository;

    @Autowired
    RoomService roomService;



    @Override
    public void openRoom(RoomOpenReq roomOpenInfo) {
        RoomInfo roomInfo = RoomInfo.builder()
                .pwd(roomOpenInfo.getPwd())
                .host_id(roomOpenInfo.getHost_id())
                .is_sys(roomOpenInfo.getIs_sys())
                .thumb_url(roomOpenInfo.getThumb_url())
                .phase(roomOpenInfo.getPhase())
                .max_num(roomOpenInfo.getMax_num())
                .cur_num(roomOpenInfo.getCur_num())
                .cate(roomOpenInfo.getCate())
                .hash_1(roomService.findHashtagId(roomOpenInfo.getHash_1()))
                .hash_2(roomService.findHashtagId(roomOpenInfo.getHash_2()))
                .hash_3(roomService.findHashtagId(roomOpenInfo.getHash_3()))
                .title(roomOpenInfo.getTitle())
                .subtitle(roomOpenInfo.getSubtitle())
                .build();

        roomInfoRepository.save(roomInfo);
    }

    @Override
    public void closeRoomByRoomId(int id) {

    }

    @Override
    public int findHashtagId(String nm) {
        System.out.println("해시태그 아이디 찾기");
        hashtagRepository.findHashtagByNm(nm);

        Optional<Hashtag> hash = hashtagRepository.findHashtagByNm(nm);

        if(hash.isPresent()){
            Hashtag hashtag = hash.get();
            return hashtag.getId();

        }
        Hashtag hashtag = Hashtag.builder()
                .nm(nm)
                .cnt(1L)
                .build();
       hashtagRepository.save(hashtag);

        return findHashtagId(hashtag.getNm());
    }
}

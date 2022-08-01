package com.ssafy.api.service;

import com.ssafy.api.request.RoomOpenReq;
import com.ssafy.db.entity.Hashtag;
import com.ssafy.db.entity.RoomInfo;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.HashtagRepository;
import com.ssafy.db.repository.RoomInfoRepository;
import com.ssafy.db.repository.UserRepository;
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

    @Autowired
    UserRepository userRepository;



    @Override
    public RoomInfo openRoom(RoomOpenReq roomOpenInfo) {
        RoomInfo roomInfo = RoomInfo.builder()
                .pwd(roomOpenInfo.getPwd())
                .is_sys(roomOpenInfo.getIs_sys())
                .thumb_url(roomOpenInfo.getThumb_url())
                .phase(roomOpenInfo.getPhase())
                .max_num(roomOpenInfo.getMax_num())
                .cur_num(roomOpenInfo.getCur_num())
                .cate(roomOpenInfo.getCate())
                .title(roomOpenInfo.getTitle())
                .subtitle(roomOpenInfo.getSubtitle())
                .build();
        System.out.println("host id : " + roomOpenInfo.getHost_id());
        User user = userRepository.findUserById(roomOpenInfo.getHost_id()).get();
        roomInfo.setUser(user);
        if(roomOpenInfo.getHash_1()!=null&&!roomOpenInfo.getHash_1().equals("")){
            roomInfo.setHash_1(roomService.findHashtagId(roomOpenInfo.getHash_1()));
        }
        if(roomOpenInfo.getHash_2()!=null&&!roomOpenInfo.getHash_2().equals("")){
            roomInfo.setHash_2(roomService.findHashtagId(roomOpenInfo.getHash_2()));
        }
        if(roomOpenInfo.getHash_3()!=null&&!roomOpenInfo.getHash_3().equals("")){
            System.out.println(roomOpenInfo.getHash_3());
            roomInfo.setHash_3(roomService.findHashtagId(roomOpenInfo.getHash_3()));
        }
        return roomInfoRepository.save(roomInfo);
    }

    @Override
    public void updateRoomByRoomId(Long id, int phase) {
        RoomInfo roomInfo = roomInfoRepository.findById(id).get();
        roomInfo.setPhase(phase);
        roomInfoRepository.save(roomInfo);
    }

    @Override
    public void closeRoomByRoomId(Long id) {
        roomInfoRepository.findById(id);
    }

    @Override
    public int findHashtagId(String nm) {
        System.out.println("해시태그 아이디 찾기");
        hashtagRepository.findHashtagByNm(nm);

        Optional<Hashtag> hash = hashtagRepository.findHashtagByNm(nm);
        if(hash.isPresent()){ //이미 존재 한다면
            Hashtag hashtag = hash.get();
            hashtag.setCnt(hashtag.getCnt()+1);
            hashtagRepository.save(hashtag);
            return hashtag.getId();
        }
        //존재하지 않던 해시태그라면
        Hashtag hashtag = Hashtag.builder()
                .nm(nm)
                .cnt(0L)
                .build();
        hashtagRepository.save(hashtag);

        return findHashtagId(hashtag.getNm());
    }
}

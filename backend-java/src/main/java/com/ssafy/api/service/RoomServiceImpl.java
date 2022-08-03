package com.ssafy.api.service;

import com.ssafy.api.request.RoomCloseReq;
import com.ssafy.api.request.RoomEnterReq;
import com.ssafy.api.request.RoomOpenReq;
import com.ssafy.db.dto.RoomInfoDto;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.HashtagRepository;
import com.ssafy.db.repository.RoomInfoRepository;
import com.ssafy.db.repository.RoomStatusRepository;
import com.ssafy.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
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

    @Autowired
    RoomStatusRepository roomStatusRepository;


    /*@Autowired
    PasswordEncoder passwordEncoder;*/


    @Override
    public RoomInfoDto createRoom(RoomOpenReq roomOpenInfo) {
        RoomInfo roomInfo = RoomInfo.builder()
                .pwd(/*passwordEncoder.encode(*/roomOpenInfo.getPwd())
                .is_sys(roomOpenInfo.getIs_sys())
                .thumb_url(roomOpenInfo.getThumb_url())
                .phase(roomOpenInfo.getPhase())
                .max_num(roomOpenInfo.getMax_num())
                .cur_num(1)
                .cate(roomOpenInfo.getCate())
                .title(roomOpenInfo.getTitle())
                .subtitle(roomOpenInfo.getSubtitle())
                .build();
        System.out.println("host id : " + roomOpenInfo.getHost_id());
        roomInfo =  roomInfoRepository.save(roomInfo);
        User user = userRepository.findUserById(roomOpenInfo.getHost_id()).get();
        RoomStatus roomStatus = RoomStatus.builder()
                .id(roomInfo.getId())
                .build();
        System.out.println("포지션 : "+roomOpenInfo.getPos() );
        //시스템 모드 , 사회자 모드면 자동으로 호스트는 사회자
        if(roomOpenInfo.getIs_sys()==1){

            if(roomOpenInfo.getPos()==1){ //찬성
                System.out.println("찬성");
                roomStatus.setAgree(1);
                roomStatus.setAgree_1(roomOpenInfo.getHost_id());
                System.out.println(roomStatus.getAgree_1());
            }else if(roomOpenInfo.getPos()==2){ //반대
                roomStatus.setDisagree(1);
                roomStatus.setDisagree_1(roomInfo.getHost_id());
            }else if(roomOpenInfo.getPos()==3){ //방청객

            }
        }
        roomStatusRepository.save(roomStatus);


        roomInfo.setUser(user);
        if(roomOpenInfo.getHash_1()!=null&&!roomOpenInfo.getHash_1().equals("")){
            roomInfo.setHash_1(roomService.findHashtagId(roomOpenInfo.getHash_1()));
        }
        if(roomOpenInfo.getHash_2()!=null&&!roomOpenInfo.getHash_2().equals("")){
            roomInfo.setHash_2(roomService.findHashtagId(roomOpenInfo.getHash_2()));
        }
        if(roomOpenInfo.getHash_3()!=null&&!roomOpenInfo.getHash_3().equals("")){
            roomInfo.setHash_3(roomService.findHashtagId(roomOpenInfo.getHash_3()));
        }
        return new RoomInfoDto(roomInfoRepository.save(roomInfo));
    }

    @Override
    public RoomInfoDto updatePhaseByRoomId(Long id, int phase) {
        RoomInfo roomInfo = roomInfoRepository.findById(id).get();
        if(phase == 1){
            if(roomInfo.getPhase()>=1){
                return null;
            }
        }else if(phase==2){
            if(roomInfo.getPhase()>=2){
                return null;
            }
        }
        roomInfo.setPhase(phase);
        return new RoomInfoDto(roomInfoRepository.save(roomInfo));
    }

    @Override
    public void finishRoom(RoomCloseReq roomCloseReq) {
        RoomInfo roomInfo = roomInfoRepository.findById(roomCloseReq.getId()).get();
        RoomHistory roomHistory = RoomHistory.builder()
                .id(roomCloseReq.getId())
                .log(roomCloseReq.getLog())
                .end_time(LocalDateTime.now())
                .winner(roomCloseReq.getWinner())
                .agree(roomCloseReq.getAgree())
                .disagree(roomCloseReq.getDisagree())
                .invalid(roomCloseReq.getInvalid()).build();
        roomInfo.setPhase(3);
        System.out.println("roomInfo id = " + roomInfo.getId());
        System.out.println("roomHistoy id = "+ roomHistory.getId());
        roomInfo.setRoomHistory(roomHistory);
        roomInfoRepository.save(roomInfo);
    }



    @Override
    public RoomInfoDto enterDebate(RoomEnterReq roomEnterReq) throws NoSuchElementException{
        RoomInfo roomInfo = roomInfoRepository.findById(roomEnterReq.getId()).get();

        System.out.println(roomInfo.getPwd()+" : "+roomEnterReq.getPwd());
        //비밀번호 체크
        if(!roomInfo.getPwd().equals(roomEnterReq.getPwd())){
            return null;
        }

        //방 인원 수 한명 증가
        roomInfo.setCur_num(roomInfo.getCur_num()+1);
        roomInfoRepository.save(roomInfo);

        RoomInfoDto roomInfoDto = new RoomInfoDto(roomInfo);
        return roomInfoDto;


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

package com.ssafy.api.service;

import com.ssafy.api.request.RoomCloseReq;
import com.ssafy.api.request.RoomOpenReq;
import com.ssafy.db.dto.RoomInfoDto;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.HashtagRepository;
import com.ssafy.db.repository.RoomInfoRepository;
import com.ssafy.db.repository.RoomStatusRepository;
import com.ssafy.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
                .isSys(roomOpenInfo.getIs_sys())
                .thumbUrl(roomOpenInfo.getThumb_url())
                .phase(roomOpenInfo.getPhase())
                .maxNum(roomOpenInfo.getMax_num())
                .curNum(1)
                .cate(roomOpenInfo.getCate())
                .title(roomOpenInfo.getTitle())
                .subtitle(roomOpenInfo.getSubtitle())
                .build();

        roomInfo =  roomInfoRepository.save(roomInfo);
        User user = userRepository.findUserByEm(roomOpenInfo.getHost_em()).get();
        RoomStatus roomStatus = RoomStatus.builder()
                .id(roomInfo.getId())
                .build();

        //시스템 모드 , 사회자 모드면 자동으로 호스트는 사회자
        if(roomOpenInfo.getIs_sys()==1){

            if(roomOpenInfo.getPos()==1){ //찬성
                System.out.println("찬성");
                roomStatus.setAgree(1);
                roomStatus.setAgree_1(user.getId());
                System.out.println(roomStatus.getAgree_1());
            }else if(roomOpenInfo.getPos()==2){ //반대
                roomStatus.setDisagree(1);
                roomStatus.setDisagree_1(roomInfo.getHostId());
            }else if(roomOpenInfo.getPos()==3){ //방청객

            }
        }
        roomStatusRepository.save(roomStatus);

        roomInfo.setUser(user);
        if(roomOpenInfo.getHash_1()!=null&&!roomOpenInfo.getHash_1().equals("")){
            roomInfo.setHash1(roomService.findHashtagId(roomOpenInfo.getHash_1()));
        }
        if(roomOpenInfo.getHash_2()!=null&&!roomOpenInfo.getHash_2().equals("")){
            roomInfo.setHash2(roomService.findHashtagId(roomOpenInfo.getHash_2()));
        }
        if(roomOpenInfo.getHash_3()!=null&&!roomOpenInfo.getHash_3().equals("")){
            roomInfo.setHash3(roomService.findHashtagId(roomOpenInfo.getHash_3()));
        }
        return new RoomInfoDto(roomInfoRepository.save(roomInfo));
    }

    @Override
    public RoomInfoDto findRoomByRoomId(Long roomId) {
        Optional<RoomInfo> roomInfo = roomInfoRepository.findRoomInfoById(roomId);
        return roomInfo.map(RoomInfoDto::new).orElse(null);
    }

    @Override
    public RoomInfoDto findRoomBySessionId(String sessionId) {
        Optional<User> user = userRepository.findUserByEm(sessionId);
        if (!user.isPresent()) return null;

        Long hostId = user.get().getId();
        Optional<RoomInfo> roomInfo = roomInfoRepository.findRoomInfoByHostId(hostId);
        return roomInfo.map(RoomInfoDto::new).orElse(null);
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

        roomInfo.setRoomHistory(roomHistory);
        roomInfoRepository.save(roomInfo);
    }



//    @Override
//    public boolean enterRoom(RoomEnterReq roomEnterReq, String userEm) throws NoSuchElementException{
//        RoomInfo roomInfo = roomInfoRepository.findById(roomEnterReq.getId()).get();
//
//        //비밀번호 체크
//        if(!roomInfo.getPwd().equals(roomEnterReq.getPwd())){
//            return false;
//        }

//        if(roomEnterReq.getPos()==1){//찬성
//            int max_num = roomInfo.getMax_num();
//            RoomStatus roomStatus = roomInfo.getRoomStatus();
//            if(roomStatus.getAgree()>=max_num){
//                throw new IllegalArgumentException();
//            }else{
//                int cur_agree = roomStatus.getAgree();
//                if(cur_agree==0){
//                    roomStatus.setAgree_1(user.getId());
//                    roomStatus.setAgree(1);
//                }else if(cur_agree==1){
//                    roomStatus.setAgree_2(user.getId());
//                    roomStatus.setAgree(2);
//                }else if(cur_agree==2){
//                    roomStatus.setAgree_3(user.getId());
//                    roomStatus.setAgree(3);
//                }else if(cur_agree==3){
//                    roomStatus.setAgree_4(user.getId());
//                    roomStatus.setAgree(4);
//                }else if(cur_agree==4){
//                    roomStatus.setAgree_5(user.getId());
//                    roomStatus.setAgree(5);
//                }
//            }
//
//        }else if(roomEnterReq.getPos()==2) {//반대
//            int max_num = roomInfo.getMax_num();
//            RoomStatus roomStatus = roomInfo.getRoomStatus();
//            if (roomStatus.getAgree() >= max_num) {
//                throw new IllegalArgumentException();
//            } else {
//                int cur_agree = roomStatus.getDisagree();
//                if (cur_agree == 0) {
//                    roomStatus.setDisagree_1(user.getId());
//                    roomStatus.setDisagree(1);
//                } else if (cur_agree == 1) {
//                    roomStatus.setDisagree_2(user.getId());
//                    roomStatus.setDisagree(2);
//                } else if (cur_agree == 2) {
//                    roomStatus.setDisagree_3(user.getId());
//                    roomStatus.setDisagree(3);
//                } else if (cur_agree == 3) {
//                    roomStatus.setDisagree_4(user.getId());
//                    roomStatus.setDisagree(4);
//                } else if (cur_agree == 4) {
//                    roomStatus.setDisagree_5(user.getId());
//                    roomStatus.setDisagree(5);
//                }
//            }
//        }
        //방 인원 수 한명 증가
//        roomInfo.setCur_num(roomInfo.getCur_num()+1);
//        roomInfoRepository.save(roomInfo);
//
//        RoomInfoDto roomInfoDto = new RoomInfoDto(roomInfo);
//        return true;
//
//    }

    @Override
    public int findHashtagId(String nm) {
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

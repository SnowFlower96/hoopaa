package com.ssafy.api.service;

import com.ssafy.api.request.RoomCloseReq;
import com.ssafy.api.request.RoomOpenReq;
import com.ssafy.common.data.UserInfo;
import com.ssafy.common.data.VSession;
import com.ssafy.db.dto.RoomInfoDto;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.*;
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

    @Autowired
    UserHistoryRepository userHistoryRepository;

    @Autowired
    UserStatRepository userStatRepository;


    /*@Autowired
    PasswordEncoder passwordEncoder;*/


    @Override
    public RoomInfoDto createRoom(RoomOpenReq roomOpenInfo) {
        System.out.println(roomOpenInfo);
        User user = userRepository.findUserByEm(roomOpenInfo.getHost_em()).get();
        RoomInfo roomInfo = RoomInfo.builder()
                .pwd(/*passwordEncoder.encode(*/roomOpenInfo.getPwd().length() == 0 ? null : roomOpenInfo.getPwd())
                .hostId(user.getId())
                .isSys(roomOpenInfo.getIs_sys())
                .thumbUrl(roomOpenInfo.getThumb_url())
                .phase(roomOpenInfo.getPhase())
                .maxNum(roomOpenInfo.getMax_num())
                .curNum(1)
                .cate(roomOpenInfo.getCate())
                .title(roomOpenInfo.getTitle())
                .subtitle(roomOpenInfo.getSubtitle())
                .build();
        if(roomOpenInfo.getHash_1()!=null&&!roomOpenInfo.getHash_1().equals("")){
            roomInfo.setHash1(roomService.findHashtagId(roomOpenInfo.getHash_1()));
        }
        if(roomOpenInfo.getHash_2()!=null&&!roomOpenInfo.getHash_2().equals("")){
            roomInfo.setHash2(roomService.findHashtagId(roomOpenInfo.getHash_2()));
        }
        if(roomOpenInfo.getHash_3()!=null&&!roomOpenInfo.getHash_3().equals("")){
            roomInfo.setHash3(roomService.findHashtagId(roomOpenInfo.getHash_3()));
        }
        roomInfo =  roomInfoRepository.save(roomInfo);

        RoomStatus roomStatus = RoomStatus.builder()
                .id(roomInfo.getId())
                .build();
        roomStatusRepository.save(roomStatus);
        return new RoomInfoDto(roomInfo);
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
    public void finishRoom( VSession vSession) {
        RoomInfo roomInfo = roomInfoRepository.findById(vSession.getRoomInfo().getId()).get();
        int winner = 0;
        if(vSession.getVote_final_agree()> vSession.getVote_final_disagree())winner=1;
        else if(vSession.getVote_final_agree() < vSession.getVote_final_disagree())winner=2;
        RoomHistory roomHistory = RoomHistory.builder()
                .id(vSession.getRoomInfo().getId())
                .end_time(LocalDateTime.now())
                .winner(winner)
                .agree(vSession.getVote_final_agree())
                .disagree(vSession.getVote_final_disagree())
                .build();
        roomInfo.setPhase(3);

        // 토론왕 확인하기 위한 변수 설정정
        int kingCntMax = -1;
        Long kingId=-1L;
        //user history 업데이트
        //찬성 진영 업데이트
        for(UserInfo userInfo : vSession.getAgree()){
            if(userInfo == null) continue; //
            kingCntMax=userInfo.getKingCnt();
            kingId = userInfo.getId();
            User user = userRepository.findUserById(userInfo.getId()).get();
            UserHistory userHistory = UserHistory.builder()
                    .userId(user.getId()) //id
                    .roomId(roomInfo.getId()) //roomId
                    .userPos(1)//userPos
                    .build();
            if(roomInfo.getHostId()==user.getId()){ //host라면
                userHistory.setHost(true); //isHost
            }
            userHistory.setRoomInfo(roomInfo);
            userHistoryRepository.save(userHistory);

            //user stat 업데이트
            UserStat userStat = userStatRepository.findStatById(user.getId()).get();
            userStat.setTotal(userStat.getTotal()+1);
            //승 , 패 , 무
            if(vSession.getVote_final_agree()> vSession.getVote_final_disagree()) {
                userStat.setWin(userStat.getWin()+1);
            }else if(vSession.getVote_final_agree()< vSession.getVote_final_disagree()){
                userStat.setLose(userStat.getLose()+1);
            }else{
                userStat.setDraw(userStat.getDraw()+1);
            }
            userStatRepository.save(userStat);
        }

        //반대 진영 업데이트
        for(UserInfo userInfo : vSession.getDisagree()){
            if(userInfo == null) continue; //
            if(userInfo.getKingCnt()>kingCntMax){
                kingCntMax=userInfo.getKingCnt();
                kingId = userInfo.getId();
            }
            User user = userRepository.findUserById(userInfo.getId()).get();
            UserHistory userHistory = UserHistory.builder()
                    .userId(user.getId()) //id
                    .roomId(roomInfo.getId()) //roomId
                    .userPos(2)//userPos
                    .build();
            if(roomInfo.getHostId()==user.getId()){ //host라면
                userHistory.setHost(true); //isHost
            }
            userHistory.setRoomInfo(roomInfo);
            userHistoryRepository.save(userHistory);

            //user stat 업데이트
            UserStat userStat = userStatRepository.findStatById(user.getId()).get();
            userStat.setTotal(userStat.getTotal()+1);
            //승 , 패 , 무
            if(vSession.getVote_final_agree()< vSession.getVote_final_disagree()) {
                userStat.setWin(userStat.getWin()+1);
            }else if(vSession.getVote_final_agree()> vSession.getVote_final_disagree()){
                userStat.setLose(userStat.getLose()+1);
            }else{
                userStat.setDraw(userStat.getDraw()+1);
            }
            userStatRepository.save(userStat);

        }
        //토론왕 설정
        UserStat userStat = userStatRepository.findStatById(kingId).get();
        UserHistory userHistory = userHistoryRepository.findUserHistoryByUserIdAndRoomId(kingId, vSession.getRoomInfo().getId()).get();
        userStatRepository.save(userStat);
        userHistoryRepository.save(userHistory);
        roomInfo.setRoomHistory(roomHistory);
        roomInfoRepository.save(roomInfo);
    }

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

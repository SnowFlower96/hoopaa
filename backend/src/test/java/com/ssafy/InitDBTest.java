package com.ssafy;

import com.ssafy.api.request.RoomOpenReq;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.service.RoomService;
import com.ssafy.api.service.UserService;
import com.ssafy.db.entity.RoomInfo;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.RoomInfoRepository;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@SpringBootTest
public class InitDBTest {

    @Autowired
    UserService userService;

    @Autowired
    RoomService roomService;

    @Autowired
    RoomInfoRepository roomInfoRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    String[] testEms = {"test", "admin"};
    String[] dummyEms = {"efasf12@gmail.com", "zxawdd32@gmail.com", "asdf@gmail.com",
            "njkadn@gmail.com", "qwdnj2@gmail.com", "njnk@gmail.com",
            "sdfa@naver.com", "anjkzxc@naver.com", "ajnwd123@naver.com",
            "asdfm@kakao.com", "asnjz124@kakao.com", "nkjxczv55@kakao.com"};
    String[] dummyNnms = {"대전", "3반", "공통",
            "2팀", "닉네임", "비그만와",
            "후파", "배고파", "커피",
            "스프링", "젠킨스", "인간집사"};
    String[] ems = {"8x15yz@gmail.com", "jhno96@gmail.com", "wjdgus9596@naver.com", "jhr991108@naver.com", "wj5295@naver.com"};
    String[] nnms = {"Jay", "Back", "정현", "혜령", "원재"};

    @Test
    public void Initialize() throws IOException {
        // test, admin 계정
        for (String testEm : testEms) {
            UserRegisterPostReq user = UserRegisterPostReq.builder()
                    .em(testEm)
                    .nnm(testEm)
                    .pwd(testEm)
                    .build();
            userService.createUser(user);
            userService.updateEmailAuth(user.getEm());
        }

        // 더미 계정
        for (int i = 0; i < dummyEms.length; i++) {
            UserRegisterPostReq user = UserRegisterPostReq.builder()
                    .em(dummyEms[i])
                    .nnm(dummyNnms[i])
                    .pwd("1234")
                    .build();
            userService.createUser(user);
            userService.updateEmailAuth(user.getEm());
        }

        // 조원 계정
        for (int i = 0; i < ems.length; i++) {
            UserRegisterPostReq user = UserRegisterPostReq.builder()
                    .em(ems[i])
                    .nnm(nnms[i])
                    .pwd("1234")
                    .build();
            userService.createUser(user);
            userService.updateEmailAuth(user.getEm());
        }

        // --------------------------------

        int[] cateCnts = new int[8];
        String[] pwds = {"", "", "1234",
                "", "", "",
                "", "", "1234",
                "", "", ""};
        String[] hashs = {"#깻잎논쟁", "#핫이슈#찬반", "#후파#나락#해시",
                "#스프링#핫이슈#Vue", "#9시#뉴스#우와", "#뭘#써야#될까",
                "#아이디어#고갈", "#살려줘", "",
                "#핫식스#카페인#ㅁ나ㅣㅇ", "#카페인#타우린", ""};
        String[] titles = {"깻잎논쟁", "박카스vs핫식스", "짜장 대 짬뽕",
                "바다 vs 산", "Java vs Python", "자취 vs 기숙사",
                "카페인 과다섭취 이대로 괜찮은가", "악법도 법이다", "담배값 인상",
                "심청이는 효녀인가", "버스 vs 지하철", "빈공간"};
        String[] subtitles = {"요즘 핫한 깻잎논쟁", "당신의 부스터는?", "먹고싶다",
                "여름 휴가 장소", "프로그래밍 입문 언어는?", "뭐가 좋을까",
                "현대인의 필수아이템", "테스형", "얼마로?",
                "전래동화", "뭐가 편한가", "널널"};
        File dir = new File("./app/dump");
        String[] encFiles = new String[12];
        File[] files = dir.listFiles();
        assert files != null;
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            byte[] bt = new byte[(int) file.length()];

            try (FileInputStream fis = new FileInputStream(file)) {
                fis.read(bt);
                encFiles[i] = new String(Base64.encodeBase64(bt));
            }
        }

        for (int i = 0; i < pwds.length; i++) {
            String em = dummyEms[i];
            User user = userService.getUserByEm(em);
            int cate = (int) ((Math.random() * 10000) % 5 + 1);
            while (true) {
                if (cateCnts[cate] > 3) {
                    cate = (int) ((Math.random() * 10000) % 5 + 1);
                } else {
                    cateCnts[cate]++;
                    break;
                }
            }
            RoomOpenReq roomOpenReq = RoomOpenReq.builder()
                    .pwd(pwds[i]).max_num((int) ((Math.random() * 10000) % 5 + 1)).cate(cate)
                    .hashtags(hashs[i]).title(titles[i]).subtitle(subtitles[i]).file(encFiles[i])
                    .build();
            roomService.createRoom(String.valueOf(user.getId()), roomOpenReq);

            RoomInfo roomInfo = roomInfoRepository.findRoomInfoByHostId(user.getId()).get();
            roomInfo.setCurNum((int) ((Math.random() * 10000) % 50 + 1));
            roomInfo.setPhase((int) ((Math.random() * 10000) % 3));
            roomInfoRepository.save(roomInfo);
        }
    }

}

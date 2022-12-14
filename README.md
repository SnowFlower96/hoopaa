![hoopaa](./frontend/src/assets/images/071414.png)

# HooPaa 
- 온라인 토론 플랫폼 서비스 HooPaa

<!-- 필수 항목 -->

## 카테고리

| Application | Domain | Language | Framework |
| ---- | ---- | ---- | ---- |
| :white_check_mark: Desktop Web | :black_square_button: AI | :white_check_mark: JavaScript | :white_check_mark: Vue.js |
| :white_check_mark: Mobile Web | :black_square_button: Big Data | :black_square_button: TypeScript | :black_square_button: React |
| :white_check_mark: Responsive Web | :black_square_button: Blockchain | :black_square_button: C/C++ | :black_square_button: Angular |
| :black_square_button: Android App | :black_square_button: IoT | :black_square_button: C# | :black_square_button: Node.js |
| :black_square_button: iOS App | :black_square_button: AR/VR/Metaverse | :black_square_button: Python | :black_square_button: Flask/Django |
| :black_square_button: Desktop App | :black_square_button: Game | :white_check_mark: Java | :white_check_mark: Spring/Springboot |
| | | :black_square_button: Kotlin | |

<!-- 필수 항목 -->

## 프로젝트 소개
* **진행 기간** : 2022.07.11~2022.08.19 
* **프로젝트명** : 온라인 토론 플랫폼 서비스 HooPaa
* **프로젝트 설명**
  - 온라인으로 즐기는 완벽한 토론. 코로나 19 시대가 도래하며 대부분의 수업들이
온오프라인을 반복하는 가운데 대한민국 교육에서 빠지지 않는 토론 수업은
온라인으로 진행하기 까다로운 형태의 수업 중 하나입니다.
HOOPAA 는 이러한 문제를 해결하고자 개발되었습니다. 이제 토론 맞춤 UI 와
사회자의 컨트롤로 원활한 토론을 진행할 수 있습니다. 토론 왕 선정과 다양한
애니메이션 효과는 토론에 재미를 느껴 더 집중할 수 있게 하죠. 많은 사람들과
다양한 주제로 토론하고 토론 왕이 되어 보세요. 당신의 도전을 응원합니다!
* **프로젝트 주요 기능 및 특징**
  - **역할 별 분리**
    - 찬성 진영과 반대 진영이 분리되어 화면에 나타나므로 찬성과 반대의 구분이 명확해집니다.
  - **공정한 토론 진행** 
    - 각 진영에게 발언 시간을 부여하고 타이머를 제공하므로써 공정하게 발언권을 제공할 수 있습니다.
    - 상대 진영이 발언하는 동안 음소거가 되기 때문에 방해 받지 않고 토론을 진행할 수 있습니다.
  - **팀 채팅**
    - 토론 중 팀원 간의 채팅을 통해서 의견 공유를 원활히 할 수 있습니다. 
  - **쉬는 시간 및 세부 세션**
    - 쉬는 시간 동안 각 진영에게 제공되는 독립된 세부 세션 공간에서 활발한 의견 공유를 할 수 있습니다.
  - **토론 승/패 결정**
    - 방청객의 투표로 토론의 승리와 패배를 결정할 수 있습니다. 
  - **토론왕 선정 기능** 
    - 방청객의 투표로 가장 우수한 토론 능력을 가진 토론왕을 선정할 수 있습니다.
  - **방청객 참여 기능**
    - 방청객의 토론의 승자와 토론왕을 결정하는 투표를 할 수 있습니다.
    - 방청객이 호응을 보낼 수 있습니다.

* **주요 기술**
  - WebRTC
  - WebSocket
  - JWT Authentication
  - REST API

* **배포 환경**
  - URL: [https://hoopaa.site](https://hoopaa.site)
  - 테스트 계정
    - 아이디 : test 패스워드 : test
    - 아이디 : admin 패스워드 : admin

<!-- 자유 양식 -->
## 팀원 소개
| 이름  | 역할  |
| ---- | ---- | 
| **김현주** | 프론트엔드 개발 & UI/UX| 
| **노정형** | 백엔드 개발 & CI/CD|
| **박정현** | 프론트엔드 개발 & UI/UX |
| **주혜령** | 팀장 & 백엔드 개발|
| **최원재** | 백엔드 개발 |


## 프로젝트 기술 스택
#### 이슈 관리 : Jira
#### 형상관리 : GitLab
#### 커뮤니케이션 : Mattermost, [Notion](https://www.notion.so/Team-B302-5db87fcb7871413a875b4e5c2511e2f0)
#### 개발 환경
- OS : Window 10
- IDE
  - IntelliJ IDEA Community Edition 2022.1.3
  - Visual Studio Code 1.70.0
  - UI/UX : Adobe Photoshop, Figma
- Database : MySQL Workbench 8.0 CE
- Server : AWS EC2 (MobaXterm)
  - Ubuntu 20.04.4 LTS
  - Docker 20.10.17
  - Jenkins 2.346.2 LTS
  - Nginx 1.18.0
#### 개발 환경 상세 
- Backend
  - Java (Open JDK (Zulu 8.33.0.1))
  - Spring Boot Gradle 6.7
  - Lombok 1.18.20, OpenVidu 2.22.0, Swagger3.0.0, QueryDSL, JPA
- Frontend
  - HTML5, CSS, JavaScript (ES6)
  - Vue 3.0.0, Vuex 4.0.2
  - Node.js 16.16.0

## 포트
| 포트 번호| 설명 |
| --- | --- |
| 80 | http (redirect to 443) |
| 443 | https |
| 3306 | DB |
| 3478 | STUN/TURN |
| 5443 | OpenVidu  |
| 8080 | REST API |
| 40000 - 57000 | Kurento  |
| 57001 - 65535 | TURN |

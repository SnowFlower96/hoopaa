<template>
  <div class="make-room-bg">
    <div class="make-room-outer">
    <div class="make-room-blank"></div>
      <div class="make-room-container">
        <div class="make-room-label">방 만들기</div>
          <div class="select-wrap">
            <div class="select-inner">
              <!-- 추가 -->
              <div v-if="preview==''|| preview==null" class="displayFlex">
                <div>
                  <div style="font-size: 30px;">썸네일 미리보기</div>
                  <div style="margin: 10px;">썸네일을 넣어주세요</div>
                </div>
              </div>
              <div v-else><img :src="preview" img class="thumbnail"></div>
            </div>
          </div>
      <div class="select-status" v-if="isSys == '0'">👨‍⚖️사회자모드👩‍⚖️</div>

          <div class="options-wrap">
            <div class="inner-wrap">
              <div class="label-container "><p>방 이름 입력</p></div>
              <div class="input-container">
                <div class="name-input"><input class="name-input" v-model="roomName" type="text" placeholder="방 이름을 입력해주세요"/></div>
              </div>
            </div>

            <div class="inner-wrap">
              <div class="label-container "><p>비밀방 여부</p></div>
              <div class="input-container">
                <div class="pw-check" ><input class="pw-check" type="checkbox"></div>
                <div class="pw-input"><input class="pw-input" v-model="roomPwd" placeholder="비밀번호를 정해주세요" /></div>
              </div>
            </div>

            <div class="inner-wrap">
                <div class="label-container "><p>썸네일</p></div>
                <div class="input-container">
                  <div class="thumbnail-input">
                    <input @change="fileChange" type="file" id="file" class="inputfile" accept="image/*" />
                    <label for="file" class="input-plus">+</label>
                  </div>
                </div>
            </div>

            <div class="inner-wrap">
              <div class="label-container "><p>주제 설정</p></div>
              <div class="input-container">
                <div class="hash-combo">
                  <select v-model="selectedMenu">
                    <option v-for="(item, index) in menus" :key="index" :value="item">{{item.name}}</option>
                  </select>
                </div>
                <div class="hash-wrap-inner">
                  <div class="hash"><input class="hash" v-model="hashTag1" type="text" placeholder="해쉬태그1"></div>
                  <div class="hash"><input class="hash hash-center" v-model="hashTag2" type="text" placeholder="해쉬태그2"></div>
                  <div class="hash"><input class="hash hash-end" v-model="hashTag3" type="text" placeholder="해쉬태그3"></div>
                </div>
              </div>
            </div>

            <div class="inner-wrap">
                <div class="label-container"><p>토론인원</p></div>
              <div class="input-container">
                <div>1<input type="radio" name="maxNum" value="1" @change="getNum($event)" checked="checked" /></div>
                <div>2<input type="radio" name="maxNum" value="2" @change="getNum($event)"/></div>
                <div>3<input type="radio" name="maxNum" value="3" @change="getNum($event)"/></div>
                <div>4<input type="radio" name="maxNum" value="4" @change="getNum($event)"/></div>
                <div>5<input type="radio" name="maxNum" value="5" @change="getNum($event)"/></div>
              </div>
            </div>
          </div>
          <div class="start-btn" @click="makeRoom"><span>토론방 생성</span></div>
          <router-link style=text-decoration:none; to="/"><div class="pp-goto-home-sys"><span>홈으로 돌아가기</span><img :src="require(`@/assets/images/favi6.png`)" alt="home-favi"></div></router-link>
      </div>
    </div>
  </div>
    <div :class="{'make-room-outer-mod':isSys === '0', 'make-room-outer-sys':isSys === '1'}">
    </div>
</template>

<script>

import { mapState } from 'vuex';


export default {
  components : {
  },
  data () {
    return {
      menuData : require('@/views/main/category.json'),
      dropdownSortTF : false,
      host: '',
      mySessionId: 'SessionA',
      myUserName: '',
      roomName : '',
      roomPwd : '',
      meuns : '',
      roomTitle : '',
      roomCate : '',
      hashTag1 : '',
      hashTag2 : '',
      hashTag3 : '',
      maxNum : '1',
      isSys : '0',
      selectedMenu: null,
      file : '',
      preview : '',
      cate: ''

    }
  },
   computed : {
    ...mapState(["user", "roomCode"])
  },
  created() {
    this.menus = this.menuData;
    this.host = this.$store.state.user.em;
    this.myUserName = this.$store.state.user.nnm;
  },
  methods : {
     dropdownSortBtnTF() {
      this.dropdownSortTF = !this.dropdownSortTF
    },
    getNum(event) {
        this.maxNum = event.target.value;

    },
    async makeRoom() {
      var room = {
        cate : this.roomCate,
        hashtags : '#' + this.hashTag1 + '#' + this.hashTag2 + '#' + this.hashTag3,
        max_num : this.maxNum,
        pwd : this.roomPwd,
        title : this.roomName,
        cate: this.selectedMenu.idx,
        file: this.file,
      }
      await this.$store.dispatch("makeRoom",room);
        let data = {
          pwd : room["pwd"],
          sessionId : this.roomCode,
        }
      this.$store.dispatch("enterRoom", data);

    },
    isSysStatus0() {
      this.isSys = '0'
    },
    isSysStatus1() {
      this.isSys = '1'
    },
     fileChange(e) {
      const files = e.target.files;
      if(files.length>0){


        let validation = true;
        let message = '';

        if(files.length > 1){
          validation = false;
          message = `하나의 이미지 파일만 업로드 가능합니다.`;
        }

        if(files[0].size > 1024 * 1024 * 3){
          validation = false;
          message = `3MB이하의 파일만 업로드 가능합니다.`;
        }

        if(files[0].type.indexOf('image') < 0) {
          validation = false;
          message = `이미지 파일만 업로드 가능합니다.`;
        }

        if(validation) {
          const reader = new FileReader();
          //reader가 이미지 로드할 시 이벤트
          reader.addEventListener("load", ()=>{
            const dataIndex = reader.result.indexOf(',')+1
            const base64 = reader.result.substring(
              dataIndex,
              reader.result.length
            )

            this.file = base64
          })
          reader.readAsDataURL(files[0])
          this.file = this.encodedFile;
          this.preview = URL.createObjectURL(files[0])
        }else{
          this.file = '';
          alert(message);
        }
      }else{
        this.preview='';
      }
    }
  //추가 끝
  }
}

</script>
<style>
.start-btn {
  display: flex;
  justify-content: center;
  right: 0px;
  margin-top: 30px;
}
.start-btn > span:hover {
  background-color: rgba(255, 255, 255, 0.232);
  cursor: pointer;
}
.start-btn > span {
  padding: 10px;
  border-radius: 10px;
  outline: 1px white solid;
}
.make-room-label {
  font-size: 30px;
  margin: 20px;
}

.select-deactivate {
  filter: brightness(40%);
  width: 180px;
  height: 180px;
  cursor: pointer;
  border-radius: 10px;

}
.select-deactivate:hover {
  cursor: pointer;
  outline: 4px dashed #FFDB65;
  filter: brightness(70%);
}
.select-deactivate:active {
  border: 2px solid #FFDB65;
  filter: brightness(80%);
}
.select-activate {
  width: 180px;
  height: 180px;
  outline: 4px solid #FFDB65;
  cursor: pointer;
  border-radius: 10px;
  filter: brightness(100%);
}
.pp-goto-home-sys {
    padding-top: 10px;
    display: flex;
    justify-content: end;
    color: white;
    height: 30px;
    margin-right: 20px;
    margin-bottom: 10px;
}
.pp-goto-home-sys > img {
    height: 20px;
    width: 25px;
}
.goto-home-sys {
  display: flex;
  justify-content: end;
  margin-bottom: 20px;
}
.make-room-container {
  width: 500px;
  background-color: #0000009d;
  /* outline: 3px solid  #E1CAFF; */
  box-shadow: 3px 10px 10px 3px  rgba(0, 0, 0, 0.112);
  border-radius: 10px;
  overflow: hidden;
}
.inner-wrap {
  margin: 5px;
}
.select-status{
  overflow: hidden;
  animation: select-status 1s ease-in-out;
  font-size: 30px;
  margin-bottom: 20px;
  margin-top: 20px;
}
@keyframes select-status{
  0% {
    opacity: 0;
    transform: translateY(10px);
  }
  100% {
    opacity: 1;
    transform: none;
  }
}
.make-room-outer-mod {
  height: 100vh;
  background-image: linear-gradient( rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.3) ), url(../../assets/images/makeroombg.jpg);
  animation: outer-bg 1s ease-in-out;
  background-size : cover;

}
.make-room-blank {
  width: 100px;
  height: 90px;
}
.make-room-bg {
  display: flex;
  justify-content: center;
  color: white;
}
.make-room-outer {
  z-index: 5;
  position: absolute;
  text-align: center;
  height: 100vh;
  width: 500px;
}
@keyframes outer-bg{
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}
.make-room-black-space {
  height: 100px;
}

.label-container > p {
  margin: 0px;
}

.select-inner > div > img {
  margin: 10px;
}
.inner-wrap, .hash-wrap-inner{
  display: flex;
  justify-content: center;
  margin: 7px;
  margin-left: 25px;
  margin-right: 25px;
}
.inner-wrap {
  border-radius: 5px;
  /* outline: solid white 3px; */
  overflow: hidden;
}

.hash-wrap-inner {
  margin: 0px;
  height: 100%;
}
.label-container {
  width: 170px;
  height: 30px;
  background-color: #667799;
  display: flex;
  align-items: center;
  justify-content: center;
}
.input-container {
  width: 280px;
  height: 30px;
  background-color: #667799a1;
  display: flex;
  align-items: center;
  justify-content: space-evenly;
}
.name-input {
  width: 100%;
  height: 100%;
}
.pw-input {
  width: 100%;
  height: 100%;
}
.hash {
  width: 73px;
  height: 100%;
}
input:focus {
  outline:none
}
.select-inner {
  width: auto;
  height: 180px;
  display: flex;
  justify-content: center;
  border: 2px solid #FFDB65;
  outline: 2px solid #FFDB65;
  margin-left: 10px;
  margin-right: 10px;
  border-radius: 10px;
}
.thumbnail{
  margin: 0px !important;
  width: 100%;
  height: 100%;
  cursor: pointer;
}
</style>

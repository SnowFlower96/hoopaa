<template>
  <div class="make-room-bg">
    <div class="make-room-outer">
    <div class="make-room-blank"></div>
      <div class="make-room-container">
        <div class="make-room-label">방 만들기</div>
          <div class="select-wrap">
            <div class="select-inner">
              <!-- 추가 -->
              <div v-if="preview==''|| preview==null"><img class="thumbnail" @click="isSysStatus0" src="https://user-images.githubusercontent.com/87743473/182767796-d6da026b-e704-4870-828e-acfaa287a18d.png" alt=""></div>
              <div v-else><img :src="preview" img class="thumbnail"></div>
            </div>
          </div>
      <div class="select-status" v-if="isSys == '0'">👨‍⚖️사회자모드👩‍⚖️</div>
      <div class="select-status" v-if="isSys == '1'">🤖시스템모드🤖</div>

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
                  <select v-model="roomCate">
                    <option v-for="(item, index) in menus" :key="index" :value="item.idx">{{item.name}}</option>
                  </select>
                </div>
                <div class="hash-wrap-inner">
                  <div class="hash"><input class="hash" v-model="hashTags" type="text" placeholder="#태그1태그2#태그3"></div>
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
      menuData : require('@/views/main/menu.json'),
      dropdownSortTF : false,
      host: '',
      mySessionId: 'SessionA',
      myUserName: '',
      roomName : '',
      roomPwd : '',
      meuns : '',
      roomTitle : '',
      roomCate : '',
      hashTags : '',
      maxNum : '',
      isSys : '0',
      file : '',
      preview : ''
    }
  },
   computed : {
    ...mapState(["user"])
  },
  created() {
    this.menus = this.menuData;
    this.host = this.$store.state.userStat.em;
    this.myUserName = this.$store.state.userStat.nnm;
  },
  methods : {
     dropdownSortBtnTF() {
      this.dropdownSortTF = !this.dropdownSortTF
    },
    getNum(event) {
        this.maxNum = event.target.value;
        console.log(this.maxNum)
    },
    async makeRoom() {
      var room = {
        cate : this.roomCate,
        hashtags : this.hashTags,
        host_em : this.user.em,
        is_sys : this.isSys,
        max_num : this.maxNum,
        pwd : this.roomPwd,
        subtitle : this.roomName,
        title : this.roomTitle,
        file: this.file,
      }
        let data = {
          pwd : room["pwd"],
          sessionId : this.user.id,
        }
      await this.$store.dispatch("makeRoom",room);
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

        console.log(files[0].size);
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
            console.log("base64"+base64);
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
.select-inner {
  display: flex;
  justify-content: center;
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
.make-room-outer-sys {
  height: 100vh;
  /* background-image: url(https://user-images.githubusercontent.com/87743473/182767796-d6da026b-e704-4870-828e-acfaa287a18d.png); */
  background-image: linear-gradient( rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.3) ), url(https://user-images.githubusercontent.com/87743473/182761383-205f241e-0fad-4d7e-bf74-544966eb2570.png);
  animation: outer-bg 1s ease-in-out;
  background-size : cover;
}
.make-room-outer-mod {
  height: 100vh;
  background-image: linear-gradient( rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.3) ), url(https://user-images.githubusercontent.com/87743473/182767796-d6da026b-e704-4870-828e-acfaa287a18d.png);
  /* background-image: linear-gradient( rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.3) ), url(https://user-images.githubusercontent.com/87743473/182761383-205f241e-0fad-4d7e-bf74-544966eb2570.png); */
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
  width: 100%;
  height: 100%;
}
input:focus {
  outline:none
}
.thumbnail{
  border: 2px solid #FFDB65;
  width: 180px;
  height: 180px;
  outline: 4px solid #FFDB65;
  cursor: pointer;
  border-radius: 10px;
  filter: brightness(100%);
}
</style>

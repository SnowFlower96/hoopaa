<template>
<h1>session</h1>
<h1>session</h1>
<h1>session</h1>
	<div id="main-container" class="container">
		<div id="join">
			<div id="join-dialog" class="jumbotron vertical-center">
				<div class="form-group">
					<p>
						<label>Participant</label>
						<input v-model="myUserName" class="form-control" type="text" required>
					</p>
					<p>
						<label>Session</label>
						<input v-model="mySessionId" class="form-control" type="text" required>
					</p>
					<p class="text-center">
						<button class="btn btn-lg btn-success" @click="role=0">사회자</button>
						<button class="btn btn-lg btn-success" @click="role=1">찬성</button>
						<button class="btn btn-lg btn-success" @click="role=-1">반대</button>
            <button class="btn btn-lg btn-success" @click="role=2">입장</button>
						role:{{this.role}}
					</p>
				</div>
			</div>
		</div>

		<div id="session">
			<div id="session-header">
        <!-- <div v-if="session && !detailSession"> -->
				<h1 id="session-title">{{ mySessionId }}</h1>
				<input class="btn btn-large btn-danger" type="button" id="buttonLeaveSession" @click="role=2" value="나가기">
        <div v-if="session">
        <button class="btn btn-large btn" type="button" id="buttonDetailSession" @click="goDetailSession">세부세션 가기</button>

			<div id="video-container" class="col-md-6">
				<!-- <div><h4>me {{this.myUserName}} {{this.role}}</h4><user-video :stream-manager="publisher" @click.native="updateMainVideoStreamManager(publisher)"/></div> -->
					<div v-if="this.role===0"><user-video :stream-manager="publisher" @click.native="updateMainVideoStreamManager(publisher)"/></div>
				<div v-for="s in subscribers" :key="s.stream.connection.connectionId">
          <div v-if="s.stream.connection.dataObject.role===0">
						<h4>HOST {{s.stream.connection.client.clientData}}</h4>
						<user-video :stream-manager="s" @click.native="updateMainVideoStreamManager(sub)"/>
					</div></div>
          <div v-if="this.role===1"><user-video :stream-manager="publisher" @click.native="updateMainVideoStreamManager(publisher)"/></div>
          <div v-for="s in subscribers" :key="s.stream.connection.connectionId">
					<div v-if="s.stream.connection.dataObject.role===1">
						<h4>AGREE {{s.stream.connection.client.clientData}}</h4>
						<user-video :stream-manager="s" @click.native="updateMainVideoStreamManager(sub)"/>
					</div></div>
          <div v-if="this.role===-1"><user-video :stream-manager="publisher" @click.native="updateMainVideoStreamManager(publisher)"/></div>
          <div v-for="s in subscribers" :key="s.stream.connection.connectionId">
					<div v-if="s.stream.connection.dataObject.role===-1">
						<h4>DISAGREE {{s.stream.connection.client.clientData}}</h4>
						<user-video :stream-manager="s" @click.native="updateMainVideoStreamManager(sub)"/>
					</div></div>
				</div>
				<!-- {{s.stream.connection.client.clientData}}&&{{s.stream.connection.dataObject.role}} -->
				<!-- <user-video v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub" @click.native="updateMainVideoStreamManager(sub)"/> -->
			</div>
		</div>
	<!-- </div> -->
  <!-- <div v-if="session&&detailSession">detailsession!!!!!
    <input class="btn btn-large btn" type="button" id="buttonDetailSession" @click="detailSession=false" value="돌아 가기">
    <div v-if="role===1"><h1>찬성</h1>
    <h4>me {{this.myUserName}} {{this.role}}</h4><user-video :stream-manager="publisher" @click.native="updateMainVideoStreamManager(publisher)"/>
      <div v-for="s in subscribers" :key="s.stream.connection.connectionId">
        <div v-if="s.stream.connection.dataObject.role===1">
						<h4>AGREE {{s.stream.connection.client.clientData}}</h4>
						<user-video :stream-manager="s" @click.native="updateMainVideoStreamManager(sub)"/>
			  </div>
      </div>
    </div>
    <div v-if="role===-1"><h1>반대</h1>
    <h4>me {{this.myUserName}} {{this.role}}</h4><user-video :stream-manager="publisher" @click.native="updateMainVideoStreamManager(publisher)"/>
      <div v-for="s in subscribers" :key="s.stream.connection.connectionId">
        <div v-if="s.stream.connection.dataObject.role===-1">
						<h4>AGREE {{s.stream.connection.client.clientData}}</h4>
						<user-video :stream-manager="s" @click.native="updateMainVideoStreamManager(sub)"/>
			  </div>
      </div>
    </div>
  </div> -->
  </div></div>
</template>

<script>
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import UserVideo from './openvidu/UserVideo.vue';

axios.defaults.headers.post['Content-Type'] = 'application/json';

const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {
	name: 'App',

	components: {
		UserVideo,
	},

	data () {
		return {
			OV: undefined,
			session: undefined,
			mainStreamManager: undefined,
			publisher: undefined,
			subscribers: [],
			role:-5,
      detailSession:false,

			mySessionId: 'SessionA',
			myUserName: 'Participant' + Math.floor(Math.random() * 100),
		}
	},

	watch:{
		role:function(){

			this.leaveSession();
			// if(this.role!=2)
				this.joinSession();

			console.log(this.role+"!!!!!!!!!!!!!!!!!!!")
		}
	},

	methods: {
		streamCreated(){
			console.log("streamCreated")
			// On every new Stream received...
			this.session.on('streamCreated', ({ stream }) => {
				const subscriber = this.session.subscribe(stream);
				subscriber.stream.connection.client=JSON.parse(subscriber.stream.connection.data)[0];
				subscriber.stream.connection.dataObject=JSON.parse(subscriber.stream.connection.data)[1];
				this.subscribers.push(subscriber);
			});
		},
		streamDestroyed(){
			console.log("streamDestroyed")
			// On every Stream destroyed...
			this.session.on('streamDestroyed', ({ stream }) => {
				const index = this.subscribers.indexOf(stream.streamManager, 0);
				if (index >= 0) {
					this.subscribers.splice(index, 1);
				}
			});
		},
    goDetailSession(){
      console.log("detail")
      this.leaveSession();
      this.session=true;
      this.$router.push('/detailSession?role='+this.role)
    },
		joinSession () {
			console.log("joinSession")
			// --- Get an OpenVidu object ---
			this.OV = new OpenVidu();

			// --- Init a session ---
			this.session = this.OV.initSession();

			// --- Specify the actions when events take place in the session ---

			this.streamCreated();
			this.streamDestroyed();


			// On every asynchronous exception...
			this.session.on('exception', ({ exception }) => {
				console.warn(exception);
			});

			// --- Connect to the session with a valid user token ---

			// 'getToken' method is simulating what your server-side should do.
			// 'token' parameter should be retrieved and returned by your own backend
			this.getToken(this.mySessionId).then(token => {
				this.session.connect(token, [{ clientData: this.myUserName }, { role:this.role }])
					.then(() => {

						// --- Get your own camera stream with the desired properties ---

						let publisher = this.OV.initPublisher(undefined, {
							audioSource: undefined, // The source of audio. If undefined default microphone
							videoSource: undefined, // The source of video. If undefined default webcam
							publishAudio: true,  	// Whether you want to start publishing with your audio unmuted or not
							publishVideo: true,  	// Whether you want to start publishing with your video enabled or not
							resolution: '640x480',  // The resolution of your video
							frameRate: 30,			// The frame rate of your video
							insertMode: 'APPEND',	// How the video is inserted in the target element 'video-container'
							mirror: false       	// Whether to mirror your local video or not
						});

						this.mainStreamManager = publisher;
						this.publisher = publisher;

						// --- Publish your stream ---

						this.session.publish(this.publisher);
					})
					.catch(error => {
						console.log('There was an error connecting to the session:', error.code, error.message);
					});
			});

			window.addEventListener('beforeunload', this.leaveSession)
		},

		leaveSession () {
			console.log("leaveSession")
			// --- Leave the session by calling 'disconnect' method over the Session object ---
			if (this.session) this.session.disconnect();

			this.session = undefined;
			this.mainStreamManager = undefined;
			this.publisher = undefined;
			this.subscribers = [];
			this.OV = undefined;

			window.removeEventListener('beforeunload', this.leaveSession);
		},

		updateMainVideoStreamManager (stream) {
			console.log("updateMainVideoStreamManager")
			if (this.mainStreamManager === stream) return;
			this.mainStreamManager = stream;
		},

		/**
		 * --------------------------
		 * SERVER-SIDE RESPONSIBILITY
		 * --------------------------
		 * These methods retrieve the mandatory user token from OpenVidu Server.
		 * This behavior MUST BE IN YOUR SERVER-SIDE IN PRODUCTION (by using
		 * the API REST, openvidu-java-client or openvidu-node-client):
		 *   1) Initialize a Session in OpenVidu Server	(POST /openvidu/api/sessions)
		 *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
		 *   3) The Connection.token must be consumed in Session.connect() method
		 */

		getToken (mySessionId) {
			console.log("getToken")
			return this.createSession(mySessionId).then(sessionId => this.createToken(sessionId));
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-session
		createSession (sessionId) {
			console.log("createSession")
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, JSON.stringify({
						customSessionId: sessionId,
					}), {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.id))
					.catch(error => {
						if (error.response.status === 409) {
							console.log("not host!!!!!")
							resolve(sessionId);
						} else {
							console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`);
							if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`)) {
								location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
							}
							reject(error.response);
						}
					});
			});
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-connection
		createToken (sessionId) {
			console.log("createToken")
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`, {}, {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.token))
					.catch(error => reject(error.response));
			});
		},
	}
}
</script>

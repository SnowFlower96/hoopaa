import { createRouter, createWebHistory } from 'vue-router'
import list from '@/views/home/home'
import ConferencesDetail from '@/views/conferences/conference-detail'
import History from '@/views/history/history'
import SignUp from '@/views/user/sign-up'
import Login from '@/views/user/login'
import reSign from '@/views/user/resign'
import mainPage from '@/views/main-page/main-page'
import Participate from '@/views/participate/participating-code'
import participatingPage from '@/views/participate/participating-page'
import homeToParticipate from '@/views/home/homeToParticipate'

import debateRoom from '@/views/participate/debateRoom'
import detailSessionView from '@/views/participate/detailSessionView'

import test from '@/views/participate/test'

import endDebate from '@/views/participate/end/endDebate'

import signUpEmail from '@/views/user/email'
import makeRoom from '@/views/debate-hosting/make-room'
import store from '@/store'
// const routerComponent = require('@/views/main/router-components.json')


import myPage from '@/views/user/mypage'
import checkPwd from '@/views/user/checkPwd'
import myPageInfo from '@/views/user/mypage-info'

const fullMenu = require('@/views/main/menu.json')

const checkLogin = () => (from, to, next) => {
  if (store.state.isLogin) {
    next();
  } else {
    if (
      confirm(
        "로그인이 필요한 서비스입니다.\n로그인 페이지로 이동하시겠습니까?"
      )
    ) {
      next(`/login?call=${from.fullPath}`);
    }
  }
};

function makeRoutesFromMenu () {
  let routes = Object.keys(fullMenu).map((key) => {
    if (key === 'home') {
      return { path: fullMenu[key].path, name: key, component: Home  }
    } else if (key === 'history') {
      return { path: fullMenu[key].path, name: key, component: History }
    } else { // menu.json 에 들어있는 로그아웃 메뉴
      return null
    }
  })
  // 로그아웃 파싱한 부분 제거
  routes = routes.filter(item => item)
  // menu 자체에는 나오지 않는 페이지 라우터에 추가(방 상세보기)

  routes.push({
    path: '/conferences/:conferenceId',
    name: 'conference-detail',
    component: ConferencesDetail
  },
  {path: '/signUp',
  name: 'sign-up',
  component: SignUp},
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/',
    name: 'main-page',
    component: mainPage
  },
  {
    path: '/list',
    name: 'list',
    component: list
  },
  {
    path: '/myPage',
    name: 'myPage',
    component : myPage,
    beforeEnter: checkLogin(),

  },

  {
  path : '/myPage/info',
  name : 'myPage-info',
  component : myPageInfo,
},
{
  path : '/checkPwd',
  name : 'checkPwd',
  component : checkPwd, 
},
{
  path: '/participatingPage',
  name: 'participating-page',
  component: participatingPage,
  // beforeEnter: checkLogin(),
},
{
  path: '/startWithCode',
  name: 'Participate',
  component: Participate,
  beforeEnter: checkLogin(),
},
{
  path: '/debateRoom',
  name: 'debateRoom',
  component: debateRoom
},
{
  path : '/makeRoomPage',
  name: 'make-room',
  component: makeRoom,
  beforeEnter: checkLogin(),
},
{
  path : '/email',
  name : 'email',
  component : signUpEmail,
},
{
  path : '/testmyPage',
  name: 'testmyPage',
  component: myPageInfo
},
{
  path : '/myPage/reSign',
  name : 'reSign',
  component : reSign
},
{
  path : '/detailSessionView',
  name: 'detailSessionView',
  component: detailSessionView
},
{
  path : '/homeToParticipate',
  name: 'homeToParticipate',
  component: homeToParticipate
},

{
  path : '/test',
  name: 'test',
  component: test,
},

{
  path : '/endDebate',
  name: 'endDebate',
  component: endDebate,
},

)

  return routes
}

const routes = makeRoutesFromMenu()

const router = createRouter({
  history: createWebHistory(),
  routes
})

// router.afterEach((to) => {
//   console.log('이거냐?', to.fullPath, $state.headerVisible)

// })

export default router

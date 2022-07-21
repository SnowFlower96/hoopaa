import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/views/home/home'
import ConferencesDetail from '@/views/conferences/conference-detail'
import History from '@/views/history/history'
import SignUp from '@/views/main/components/sign-up'
import Login from '@/views/main/components/login'

import mainPage from '@/views/main-page/main-page'
import Participate from '@/views/participate/participating-code'
import participatingPage from '@/views/participate/participating-page'
import waitingPage from '@/views/participate/waiting-page'

import gameSetPage from '@/views/debate-hosting/game-set-page'
import makeRoom from '@/views/debate-hosting/make-room'

// const routerComponent = require('@/views/main/router-components.json')

const fullMenu = require('@/views/main/menu.json')
function makeRoutesFromMenu () {
  let routes = Object.keys(fullMenu).map((key) => {
    if (key === 'home') {
      return { path: fullMenu[key].path, name: key, component: Home  }
    } else if (key === 'history') {
      return { path: fullMenu[key].path, name: key, component: History }
    } else if (key === 'make-room') { // 미팅 시작하기
      return { path: fullMenu[key].path, name: key, component: makeRoom }
    } else if (key === 'participate') { // 미팅 참여하기
      return { path: fullMenu[key].path, name: key, component: Participate }
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
  })
  routes.push(
    {path: '/signUp',
    name: 'sign-up',
    component: SignUp}
  )
  routes.push({
    path: '/login',
    name: 'Login',
    component: Login
  }
  )
  routes.push({
    path: '/',
    name: 'Home',
    component: Home
  })
  routes.push({
    path: '/mainPage',
    name: 'main-page',
    component: mainPage
  })
  routes.push({
    path: '/participatingPage',
    name: 'participating-page',
    component: participatingPage
  })
  routes.push({
    path: '/waitingPage',
    name: 'waiting-page',
    component: waitingPage
  })
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

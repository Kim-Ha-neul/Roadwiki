import DashboardNavbar from '@/views/Layout/DashboardNavbar.vue';
import AuthLayout from '@/views/Pages/AuthLayout.vue';
import NothingMain from '@/views/NothingMain.vue';

import NotFound from '@/views/NotFoundPage.vue';

const routes = [
  // 도입부 페이지
  // {
  //   path: '/intro',
  //   name: 'intro',
  //   component: () => import(/* webpackChunkName: "demo" */ '../views/Intro/Intro.vue')
  // },
  // {
  //   path: '/main',
  //   name: 'main',
  //   component: NothingMain,
  // },
  {
    path: '/',
    // redirect: 'mainpage',
    component: DashboardNavbar,
    children: [
      // {
      //   path: '/icons',
      //   name: 'icons',
      //   component: () => import(/* webpackChunkName: "demo" */ '../views/Icons.vue')
      // },
      {
        path: '/profile/',
        name: '프로필',
        component: () => import(/* webpackChunkName: "demo" */ '../views/Pages/UserProfile.vue')
      },
      {
        path: '/profile-update',
        name: 'profile-update',
        component: () => import(/* webpackChunkName: "demo" */ '../views/Pages/ProfileUpdate.vue')
      },
      {
        path: '/maps',
        name: 'maps',
        component: () => import(/* webpackChunkName: "demo" */ '../views/GoogleMaps.vue')
      },
      {
        path: '/detail-contents',
        name: 'Detail Content',
        props: true,
        component: () => import(/* webpackChunkName: "demo" */ '../components/Board/DetailContent.vue')
      },
      {
        path: '/tmp_board',
        name: '게시판',
        component: () => import('../views/Board/Board.vue')
      },
      {
        path: '/create-board',
        name: '글쓰기',
        component: () => import('../views/Board/CreateBoard.vue'),
        props: true
      },
      {
        path: '/detail-board',
        name: '게시글',
        props: true,
        component: () => import('../views/Board/DetailBoard.vue')
      },
      {
        path: '/update-board/',
        name: '글 수정하기',
        component: () => import('../views/Board/UpdateBoard.vue')
      },
      {
        path: '/read-user-roadmap',
        name: "나의 로드맵",
        component: () => import(/* webpackChunkName: "demo" */ '../views/Roadmap/ReadUserRoadmap.vue')
      },
      {
        path: '/create-user-roadmap',
        name: '로드맵 생성하기',
        component: () => import('../views/Roadmap/CreateUserRoadmap.vue'),
        props: true
      },
      {
        path: '/update-user-roadmap',
        name: '로드맵 수정하기',
        component: () => import('../views/Roadmap/UpdateUserRoadmap.vue'),
        props: true
      },
      {
        path: '/official-roadmap',
        name: '공식 로드맵',
        component: () => import('../views/Roadmap/OfficialRoadmap.vue'),
        props: true
      },
      {
        path: '/admin',
        name: '로드맵 관리자',
        component: () => import('../views/Roadmap/AdminRoadmap.vue'),
      },
      {
        path: '/calendar',
        name: '캘린더',
        component: () => import('../views/Calendar/Calendar.vue'),
        props: true
      },
      {
        path: '/shareboard',
        name: '공유로드맵\'s',
        component: () => import('../views/ShareRoadmap/ShareBoard.vue'),
      },
      // chatting 
      {
        path: '/chatting-main',
        name: '채팅카테고리',
        component: () => import('../views/Chatting/ChattingMain.vue'),
      },
      {
      path: '/chatting',
      name: '채팅',
      props: true,
      component: () => import('../views/Chatting/Chatting.vue'),
      },
      {
        path: '/sharedetail',
        name: '공유로드맵',
        component: () => import('../views/ShareRoadmap/ShareDetail.vue'),
        props: true,
      },
      {
        path: '/searchlist',
        name: '검색결과',
        component: () => import('../views/Search/SearchList.vue'),
      },
      {
        path: '/createshareboard',
        name: '로드맵 공유하기',
        component: () => import('../views/ShareRoadmap/CreateShareBoard.vue'),
        props: true,
      },
      {
        path: '/roadback',
        name: 'roadback',
        component: () => import('../views/ShareRoadmap/RoadBack.vue'),
        props: true,
      },
      {
        path: '/mainpage',
        name: "MAINPAGE",
        component: () => import('../views/Mainpage/Mainpage.vue'),
      },
    ]
  },
  {
    path: '/',
    // redirect: 'login',
    component: AuthLayout,
    children: [
      // {
      //   path: '/login',
      //   name: 'login',
      //   component: () => import(/* webpackChunkName: "demo" */ '../views/Pages/Login.vue')
      // },
      {
        path: '/register',
        name: 'register',
        component: () => import(/* webpackChunkName: "demo" */ '../views/Pages/Register.vue')
      },
      {
        path: '/find-password',
        name: '비밀번호찾기',
        component: () => import(/* webpackChunkName: "demo" */ '../components/Login/FindPassword.vue')
      },
      {
        path: '/reset-password',
        name: '비밀번호재설정',
        component: () => import(/* webpackChunkName: "demo" */ '../components/Login/ResetPassword.vue')
      },
      { path: '*', component: NotFound },
    ]
  },
];

export default routes;
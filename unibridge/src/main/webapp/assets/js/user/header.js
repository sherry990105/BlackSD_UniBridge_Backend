(function () {
  // 1. 컨텍스트 루트 추출
  const getBase = () => {
    const { origin, pathname } = window.location;
    const contextName = 'unibridge'; // 프로젝트명
    if (pathname.includes(`/${contextName}`)) {
      return origin + `/${contextName}`;
    }
    return origin;
  };

  const base = getBase();

  // 2. 초기화 함수 (이벤트 바인딩)
  function initHeaderEvents() {
    // JSP에서 생성된 로그아웃 버튼(ID 또는 Class)을 찾습니다.
    const logoutBtn = document.getElementById('headerBtnLogout') || document.querySelector('.logout');

    if (logoutBtn) {
      logoutBtn.addEventListener('click', (e) => {
        e.preventDefault();
        
        if (confirm('로그아웃 하시겠습니까?')) {
          // 서버의 로그아웃 처리 컨트롤러로 이동
          window.location.href = `${base}/logout.mem`; 
        }
      });
    }
  }

  // DOM 로드 완료 시 실행
  if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', initHeaderEvents);
  } else {
    initHeaderEvents();
  }
})();
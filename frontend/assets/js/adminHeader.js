document.addEventListener("DOMContentLoaded", () => {

  /* ========================
     헤더 위치: /header/adminHeader.html
     루트(frontend/) 기준 상대경로
     로고:  ../assets/img/UniBridge.png
     페이지: ../html/admin/adminXxx/xxx.html
  ======================== */

  const toAssets = "../assets";
  const toAdmin  = "../html/admin";

  /* 로고 이미지 */
  const logoImg = document.getElementById("header-logo");
  if (logoImg) {
    logoImg.src = `${toAssets}/img/UniBridge.png`;
  }

  /* 로고 클릭 → 메인 */
  const logoLink = document.querySelector(".admin-header__logo");
  if (logoLink) {
    logoLink.href = `${toAdmin}/adminMain/main.html`;
  }

  /* ========================
     메뉴 경로 매핑
  ======================== */
  const menuMap = {
    main:     `${toAdmin}/adminMain/main.html`,
    notice:   `${toAdmin}/adminNotice/noticeList.html`,
    board:    `${toAdmin}/adminBoard/menteeBoard/menteeBoardList.html`,
    report:   `${toAdmin}/adminReport/reportList.html`,
    user:     `${toAdmin}/adminUserManagement/userList.html`,
    matching: `${toAdmin}/adminMatching/matchingList.html`,
    logout:   `${toAdmin}/adminLogin/login.html`,
  };

  /* ========================
     현재 페이지 → 활성 메뉴 판별
  ======================== */

  const pageKeyMap = {
    notice:   ["noticeList.html", "noticeDetail.html", "noticeWrite.html", "noticeEdit.html"],
    board:    ["mentorBoardList.html", "menteeBoardList.html", "mentorBoardDetail.html", "menteeBoardDetail.html", "mentorBoardWrite.html", "menteeBoardWrite.html", "mentorBoardEdit.html", "menteeBoardEdit.html"],
    report:   ["reportList.html", "reportDetail.html"],
    user:     ["userList.html", "userDetail.html", "userDetailWaiting.html"],
    matching: ["matchingList.html", "matchingDetail.html"],
  };

  const currentPath = window.location.pathname;
  const getCurrentMenuKey = () => {
    for (const [key, pages] of Object.entries(pageKeyMap)) {
      if (pages.some(name => currentPath.includes(name))) return key;
    }
    return "";
  };

  const currentMenuKey = getCurrentMenuKey();

  document.querySelectorAll("[data-link]").forEach(btn => {
    const target = btn.dataset.link;

    if (target === currentMenuKey) btn.classList.add("is-active");

    btn.addEventListener("click", e => {
      e.preventDefault();
      if (target === "logout") {
        if (!window.confirm("로그아웃 하시겠습니까?")) return;
      }
      const path = menuMap[target];
      if (path) window.location.href = path;
    });
  });

});

// 공지사항 페이지네이션
document.addEventListener('DOMContentLoaded', () => {
    const pageBtns = document.querySelectorAll('.announcePageBtn');
  
    pageBtns.forEach((btn) => {
      btn.addEventListener('click', () => {
        // active 상태 이동 (숫자 버튼만)
        if (btn.classList.contains('announcePageNext')) return;
  
        pageBtns.forEach((b) => b.classList.remove('active'));
        btn.classList.add('active');
  
        const pageNum = btn.dataset.page;
        // 페이지 데이터 로드 로직 추가 가능
        // loadAnnouncePage(pageNum);
      });
    });
  
    // 행 클릭 이벤트
    announceRows.forEach((row) => {
      row.addEventListener('click', () => {
        const announceId = row.dataset.announceId;
        window.location.href = `/frontend/announceBoardDetail.html?announceId=${announceId}`;
      });
    });
  });

// announceBoard.js - 수정 버전

document.addEventListener("DOMContentLoaded", function () {
    // 1. 상세 페이지 이동
    const announceRows = document.querySelectorAll('.announceRow');
    announceRows.forEach((row) => {
        row.addEventListener('click', () => {
            const announceId = row.dataset.announceId;
            if (announceId) {
                window.location.href = `/frontend/announceBoardDetail.html?announceId=${announceId}`;
            }
        });
    });

    // 2. 페이지네이션 (기존 로직 유지)
    const pageBtns = document.querySelectorAll('.announcePageBtn');
    pageBtns.forEach((btn) => {
        btn.addEventListener('click', () => {
            if (btn.classList.contains('announcePageNext')) return;
            pageBtns.forEach((b) => b.classList.remove('active'));
            btn.classList.add('active');
        });
    });
});


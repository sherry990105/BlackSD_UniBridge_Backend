document.addEventListener('DOMContentLoaded', () => {

  // 글목록 버튼
  const backBtn = document.getElementById('mentorBoardModifyBackBtn');
  if (backBtn) {
    backBtn.addEventListener('click', () => {
      window.location.href = `${contextPath}/mentor/mentorBoard/MentorBoardList.mob`;
    });
  }

  // 수정 버튼
  const submitBtn = document.getElementById('mentorBoardModifySubmitBtn');
  if (submitBtn) {
    submitBtn.addEventListener('click', (e) => {
      e.preventDefault();
      const subject = document.getElementById('mentorBoardModifySubject').value.trim();
      const content = document.getElementById('mentorBoardModifyContent').value.trim();

      if (!subject) { alert('제목을 입력해주세요.'); return; }
      if (!content) { alert('내용을 입력해주세요.'); return; }

      document.getElementById('modify-form').submit();
    });
  }

  // 삭제 버튼
  const deleteBtn = document.getElementById('mentorBoardModifyDeleteBtn');
  if (deleteBtn) {
    deleteBtn.addEventListener('click', async () => {
      if (!confirm('게시글을 삭제하시겠습니까?')) return;

      const boardNumber = document.querySelector('input[name="MentorBoardNumber"]')?.value;
      if (!boardNumber) return alert("게시글 번호를 찾을 수 없습니다.");

      try {
        const res = await fetch(`${contextPath}/mentor/mentorBoard/MentorBoardDelete.mob?MentorBoardNumber=${encodeURIComponent(boardNumber)}`, {
          method: "POST",
          headers: { "X-Requested-With": "fetch" },
        });
        if (!res.ok) throw new Error("삭제 요청 실패");

        alert("게시글이 삭제되었습니다.");
        window.location.href = `${contextPath}/mentor/mentorBoard/MentorBoardList.mob`;
      } catch (err) {
        console.error("게시글 삭제 실패 : ", err);
        alert("게시글 삭제에 실패했습니다.");
      }
    });
  }
});

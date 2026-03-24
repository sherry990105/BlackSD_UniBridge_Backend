document.addEventListener("DOMContentLoaded", () => {

  const writeForm = document.getElementById("writeForm");
    writeForm.addEventListener("submit", () => {
      const title = document.getElementById("inputTitle").value.trim();
      const content = document.getElementById("inputContent").value.trim();
      if (!title) { alert("제목을 입력해주세요."); return; }
      if (!content) { alert("내용을 입력해주세요."); return; }

	  writeForm.submit();
	  
	  
    });
  

  const btnEditCancel = document.getElementById("btnEditCancel");
    btnEditCancel.addEventListener("click", () => {
		if (confirm("게시글 작성을 취소하시겠습니까?")) {
        location.href = `noticeDetail.admin?boardNumber=${boardNumber}`;
		}
    });
})

document.addEventListener("DOMContentLoaded", () => {


document.getElementById("btnList").addEventListener("click", () => {
      location.href = "noticeList.admin";
    });
  
  

document.getElementById("btnEdit").addEventListener("click", () => {
      location.href = "noticeEdit.admin";
    });
  


document.getElementById("btnDelete").addEventListener("click", () => {
  if (confirm("공지사항을 삭제하시겠습니까?")) {
    location.href = "noticeDeleteOk.admin";
  }
});
  

});

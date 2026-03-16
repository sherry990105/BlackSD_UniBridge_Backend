<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
  <title>게시판 관리 - 게시글 수정</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin/adminBoard/menteeBoard/boardEdit.css" />
</head>
<body>

  <div id="header-wrap"></div>

  <div class="form-wrap">
    <h1 class="page-title">게시판 관리</h1>

    <!-- 수정하기 제목 행 -->
    <div class="form-title-row" style="margin-top: 28px;">
      <span class="form-title-label">수정하기</span>
      <input
        type="text"
        id="inputTitle"
        class="form-title-input"
        placeholder="글제목"
      />
    </div>

    <!-- 내용 입력 -->
    <textarea
      id="inputContent"
      class="form-content-area"
      placeholder="수정할 글 내용"
    ></textarea>

    <!-- 버튼 -->
    <div class="form-actions">
      <button type="button" class="btn btn-blue" id="btnSave">저장</button>
      <button type="button" class="btn btn-red" id="btnEditDelete">삭제</button>
    </div>
  </div>

<script>
  fetch("${pageContext.request.contextPath}/header/adminHeader.jsp")
    .then(res => res.text())
    .then(html => {
      document.getElementById("header-wrap").innerHTML = html;
      const s = document.createElement("script");
      s.src = "${pageContext.request.contextPath}/header/assets/adminHeader.js";
      document.body.appendChild(s);
    });
</script>
  <script src="${pageContext.request.contextPath}/assets/js/admin/adminBoard/boardData.js"></script>
  <script src="${pageContext.request.contextPath}/assets/js/admin/adminBoard/menteeBoard/boardEdit.js"></script>
</body>
</html>

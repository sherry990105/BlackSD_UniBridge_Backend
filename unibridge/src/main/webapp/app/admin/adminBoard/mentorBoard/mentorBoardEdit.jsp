<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>게시판 관리 - 멘티 게시글</title>
  <link rel="stylesheet" href="/frontend/assets/css/admin/adminBoard/mentorBoard/boardEdit.css" />
</head>
<body>

  <div id="header-wrap"></div>

  <div class="form-wrap">
    <h1 class="page-title">ê²ìí ê´ë¦¬</h1>

    <!-- ìì íê¸° ì ëª© í -->
    <div class="form-title-row" style="margin-top: 28px;">
      <span class="form-title-label">ìì íê¸°</span>
      <input
        type="text"
        id="inputTitle"
        class="form-title-input"
        placeholder="ê¸ì ëª©"
      />
    </div>

    <!-- ë´ì© ìë ¥ -->
    <textarea
      id="inputContent"
      class="form-content-area"
      placeholder="ìì í  ê¸ ë´ì©"
    ></textarea>

    <!-- ë²í¼ -->
    <div class="form-actions">
      <button type="button" class="btn btn-blue" id="btnSave">ì ì¥</button>
      <button type="button" class="btn btn-red" id="btnEditDelete">ì­ì </button>
    </div>
  </div>

<script>
  fetch("/frontend/header/adminHeader.html")
    .then(res => res.text())
    .then(html => {
      document.getElementById("header-wrap").innerHTML = html;
      const s = document.createElement("script");
      s.src = "/frontend/header/assets/adminHeader.js";
      document.body.appendChild(s);
    });
</script>
  <script src="/frontend/assets/js/admin/adminBoard/boardData.js"></script>
  <script src="/frontend/assets/js/admin/adminBoard/mentorBoard/boardEdit.js"></script>
</body>
</html>

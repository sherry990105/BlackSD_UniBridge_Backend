<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>게시판 관리 - 멘티</title>
  <link rel="stylesheet" href="/frontend/assets/css/admin/adminBoard/mentorBoard/boardList.css" />
</head>
<body>

  <div id="header-wrap"></div>

  <div class="page-wrap">
    <h1 class="page-title">ê²ìí ê´ë¦¬</h1>

    <!-- í­ -->
    <div class="board-tabs">
      <a id="tab-mentee" href="#" class="board-tab">ë©í° ê²ìí</a>
      <a id="tab-mentor" href="#" class="board-tab">ë©í  ê²ìí</a>
    </div>

    <!-- íí° í -->
    <div class="filter-row">
      <div class="filter-date">
        <input type="date" class="input-date" id="dateFrom" />
        <span class="tilde">~</span>
        <input type="date" class="input-date" id="dateTo" />
      </div>
      <button class="btn" id="btnSearch">ì¡°í</button>
      <button class="btn btn-primary" id="btnWrite">+ ê²ìê¸ ìì±</button>
    </div>

    <!-- íì´ë¸ -->
    <div class="board-table">
      <div class="board-table-head">
        <div class="col-no">ë²í¸</div>
        <div class="col-title">ì ëª©</div>
        <div class="col-author">ìì±ì</div>
        <div class="col-date">ìì±ì¼</div>
        <div class="col-views">ì¡°íì</div>
      </div>
      <div id="boardTableBody"></div>
    </div>

    <!-- íì´ì§ë¤ì´ì -->
    <div class="pagination" id="pagination"></div>
  </div>

<script>
  fetch("/frontend/header/adminHeader.html")
    .then(res => res.text())
    .then(html => {
      document.getElementById("header-wrap").innerHTML = html;
      const s = document.createElement("script");
      s.src = "/frontend/header/adminHeader.js";
      document.body.appendChild(s);
    });
</script>
  <script src="/frontend/assets/js/admin/adminBoard/boardData.js"></script>
  <script src="/frontend/assets/js/admin/adminBoard/mentorBoard/boardList.js"></script>
</body>
</html>
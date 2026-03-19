<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>UniBridge - 관리자</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/adminHeader.css" />
</head>

<body>

  <header class="admin-header">
    <a href="${pageContext.request.contextPath}/app/admin/adminMain/main.jsp" class="admin-header__logo" data-link="main">
      <img id="header-logo" src="${pageContext.request.contextPath}/assets/img/UniBridge.png" alt="UniBridge" />
    </a>

    <nav class="admin-header__nav">
      <a href="${pageContext.request.contextPath}/app/admin/adminNotice/noticeList.jsp" class="admin-header__menu" data-link="notice">공지사항</a>
      <a href="${pageContext.request.contextPath}/app/admin/adminBoard/menteeBoard/menteeBoardList.jsp" class="admin-header__menu" data-link="board">게시판</a>
      <a href="${pageContext.request.contextPath}/app/admin/adminReport/report.jsp" class="admin-header__menu" data-link="report">학습보고서</a>
      <a href="${pageContext.request.contextPath}/app/admin/adminUserManagement/userList.jsp" class="admin-header__menu" data-link="user">유저관리</a>
      <a href="${pageContext.request.contextPath}/app/admin/adminMatching/matching.jsp" class="admin-header__menu" data-link="matching">매칭</a>
      <a href="${pageContext.request.contextPath}/app/admin/adminLogin/login.jsp" class="admin-header__menu admin-header__menu--logout" data-link="logout">로그아웃</a>
    </nav>
  </header>

  <script src="${pageContext.request.contextPath}/assets/js/adminHeader.js"></script>
</body>

</html>

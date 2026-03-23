<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>공지사항 관리 - 상세</title>

  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin/adminNotice/noticeDetail.css" />
</head>
<body>

  <div id="header-wrap"></div>

  <div class="detail-wrap">
    <h1 class="page-title">공지사항 관리</h1>

    <!-- 제목 + 조회수 -->
    <div class="detail-header">
      <div class="detail-title-row">
        <span class="detail-title-label">제목 : </span>
        <span class="detail-title-text">${board.getBoardTitle()}</span>
      </div>
      <div class="detail-meta">조회수 ${board.getBoardClick()}</div>
    </div>

    <!-- 본문 -->
    <div class="detail-content-box">${board.getBoardContent()}</div>

    <!-- 수정 / 삭제 버튼 -->
    <div class="detail-actions">
      <button class="btn" id = "btnList">목록</button>
      <div>
      	<button class="btn btn-blue" id="btnEdit">수정</button>
      	<button class="btn btn-red" id="btnDelete">삭제</button>
      </div>
    </div>

    <!-- 첨부파일 박스 -->
    <div class="attach-box">
     	<c:if test="${not empty board.file}">
       		<div class="img-box">        			
      			<!--  다운로드 받기 위해서는 시스템 이름이 필요하고 사용자에게 파일을 줄 때는 오리지널 네임으로 줘야한다 -->
      			<a href="${pageContext.request.contextPath}/file/download.file?fileNumber=${board.file.fileNumber}" download="${file.fileOriginalName}">
       				<c:out value="${board.file.fileOriginalName}"/>
       			</a>
       		</div>
        </c:if>
    </div>
  </div>

  <script>
  fetch("${pageContext.request.contextPath}/header/adminHeader.jsp")
    .then(res => res.text())
    .then(html => {
      document.getElementById("header-wrap").innerHTML = html;
      const s = document.createElement("script");
      s.src = "/frontend/header/adminHeader.js";
      document.body.appendChild(s);
    });
  </script>
  
  <script src="${pageContext.request.contextPath}/assets/js/admin/adminNotice/noticeDetail.js"></script>
</body>
</html>

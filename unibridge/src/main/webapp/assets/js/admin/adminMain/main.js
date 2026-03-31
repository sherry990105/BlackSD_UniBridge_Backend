document.addEventListener("DOMContentLoaded", () => {


  const totalJoinCount = document.getElementById("totalJoinCount");
  const joinSubText    = document.getElementById("joinSubText");
  const boardCount = document.getElementById("boardCount");
  const matchingCount  = document.getElementById("matchingCount");

	totalJoinCount.textContent = `${data.totalUsers} 명`;
	joinSubText.textContent    = `멘토 ${data.mentorCount}  멘티 ${data.menteeCount}`;
	boardCount.textContent = `${data.boardCount}개`;
	boardSubText.textContent    = `멘토 ${data.mentorBoardCount}  멘티 ${data.menteeBoardCount} 공지 ${data.noticeCount}`;
	matchingCount.textContent  = `${data.matchingCount} 명`;

  /*최근 게시글 출력*/
  const recentBoardList = document.getElementById("recentBoardList");
	
    recentBoardList.innerHTML = recentBoards.map((element, index) => {
		
		const date = element.boardDate.substring(2, 10)
		
		return (`<div class="table-row">
          <span>${index + 1}</span>
          <span>${element.boardTitle}</span>
          <span>${date}</span>
        </div>`)
	}).join("");

  /* 최근 가입자 출력*/
  const recentUserList = document.getElementById("recentUserList");
  
  
    recentUserList.innerHTML = recentUsers.map(element => {
		let typeText = "";
		if (element.memberType === 'NODECIDED') {
		        typeText = "미정";
		    } else if (element.memberType === 'MENTOR') {
		        typeText = "멘토";
		    } else if (element.memberType === 'MENTEE') {
		        typeText = "멘티";
		    };
		
		const date = element.memberDate.substring(2, 10);
		
		
        return (`<div class="table-row">
          <span>${element.memberName}</span>
		  <span>${typeText}</span>
          <span>${date}</span>
        </div>`)
      }).join("");
	  
	  
});
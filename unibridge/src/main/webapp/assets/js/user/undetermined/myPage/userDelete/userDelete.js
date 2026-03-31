document.addEventListener('DOMContentLoaded', () => {
    const sendBtn = document.getElementById('sendSms');
    const verifyBtn = document.getElementById('verifySms');
    const submitBtn = document.getElementById('submitBtn');
    const phoneInput = document.getElementById('userPhone');
    const authInput = document.getElementById('authCode');
    const modifyForm = document.getElementById('goUserModify'); // 폼 ID 확인 필요
    const authErrorArea = document.getElementById('authError');

    // 인증 완료 여부를 추적하는 상태 변수
    let isAuthVerified = false; 

    // 1. 인증번호 전송 클릭 시
    sendBtn.addEventListener('click', () => {
        const phone = phoneInput.value.trim();
        
        if (!/^010\d{8}$/.test(phone)) {
            alert("하이픈(-) 없이 숫자 11자리를 입력해주세요.");
            return;
        }

        // fetch 경로에 contextPath 사용 (jsp 상단 선언 확인)
        fetch(`${contextPath}/mvc/auth/undecided/delete.my?mode=send`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: `phoneNumber=${encodeURIComponent(phone)}`
        })
        .then(res => res.text())
        .then(data => {
            if (data.trim() === "success") {
                alert("인증번호가 발송되었습니다.");
                authInput.focus(); // 발송 후 입력창으로 포커스 이동
            } else {
                alert("인증번호 발송에 실패하였습니다. 다시 시도해주세요.");
            }
        })
        .catch(err => {
            console.error("Error:", err);
            alert("서버 통신 중 오류가 발생했습니다.");
        });
    });

    // 2. 인증 확인 클릭 시 (AJAX)
    verifyBtn.addEventListener('click', () => {
        const code = authInput.value.trim();
        if (!code) {
            alert("인증번호를 입력해주세요.");
            return;
        }

        fetch(`${contextPath}/mvc/auth/undecided/delete.my?mode=check`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ authCode: code })
        })
        .then(res => res.text())
        .then(data => {
            if (data.trim() === "verified") {
                alert("인증에 성공하였습니다!");
                
                // 상태 변경 및 UI 처리
                isAuthVerified = true;
                authErrorArea.innerText = "";
                authErrorArea.style.color = "blue"; // 성공 메시지 색상 변경(선택사항)
                
                // 입력 제어
                submitBtn.disabled = false;    // 제출 버튼 활성화
                verifyBtn.disabled = true;     // 확인 버튼 비활성화
                authInput.readOnly = true;      // 입력창 수정 불가
                phoneInput.readOnly = true;     // 번호 수정 불가
            } else {
                isAuthVerified = false;
                authErrorArea.innerText = "인증번호가 일치하지 않습니다.";
                authErrorArea.style.color = "red";
            }
        });
    });

    // 3. 최종 제출 전 체크 (폼 submit 이벤트 가로채기)
    // 폼 객체가 존재할 경우에만 작동
    if (modifyForm) {
        modifyForm.addEventListener('submit', (e) => {
            if (!isAuthVerified) {
                e.preventDefault(); // 서버로 데이터 전송 중단
                alert('휴대폰 인증을 먼저 완료해주세요.');
            }
        });
    }
});
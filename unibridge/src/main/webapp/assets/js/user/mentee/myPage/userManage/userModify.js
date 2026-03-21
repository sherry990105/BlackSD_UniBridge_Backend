document.addEventListener("DOMContentLoaded", () => {
    // ── 공통 설정 ──
    // contextPath는 이미 JSP 하단 <script>에서 선언되었으므로 여기서 다시 선언하지 않습니다.
    // 만약 선언이 안 되어 있다면 아래 한 줄만 남기세요.
    const cPath = window.contextPath || ""; 

    // ── 1. 프로필 사진 관리 ──
    const profileImg = document.querySelector(".userImg > img");
    const photoBtn = document.querySelector("#imgBtn");
    const photoError = document.querySelector(".userImg .errorMsg");
    let isPhotoAttached = !profileImg.src.includes("ex1.png");

    const fileInput = document.createElement("input");
    fileInput.type = "file";
    fileInput.accept = "image/*";
    
    photoBtn.addEventListener("click", (e) => {
        e.preventDefault(); // 버튼 클릭 시 폼 제출 방지
        fileInput.click();
    });

    fileInput.addEventListener("change", (e) => {
        const file = e.target.files; // 인덱스 추가
        if (file) {
            const reader = new FileReader();
            reader.onload = (event) => {
                profileImg.src = event.target.result;
                isPhotoAttached = true;
                photoError.textContent = ""; 
            };
            reader.readAsDataURL(file);
        }
    });

    // ── 2. 전화번호 인증 ──
    window.sendSms = function() {
        const phoneInput = document.getElementById("memberPhone");
        const phone = phoneInput.value.trim();

        if (!phone) {
            alert("전화번호를 입력해주세요.");
            return;
        }

        // URL 확인: contextPath가 중복되지 않도록 주의
        const url = `${cPath}/mvc/auth/mentee/verify.my?mode=send&phoneNumber=${phone}`;
        
        fetch(url)
            .then(res => res.text())
            .then(data => {
                if (data.trim() === "success") {
                    alert("인증번호가 발송되었습니다.");
                } else if (data.trim() === "invalid_format") {
                    alert("올바른 전화번호 형식이 아닙니다. (010으로 시작하는 11자리)");
                } else {
                    alert("발송 실패: " + data);
                }
            })
            .catch(err => console.error("Error:", err));
    };

    window.verifyCode = function() {
        const code = document.getElementById("authCodeInput").value.trim();
        if(!code) {
            alert("인증번호를 입력하세요.");
            return;
        }
        
        fetch(`${cPath}/mvc/auth/mentee/verify.my?mode=check`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ authCode: code })
        })
        .then(res => res.text())
        .then(data => {
            if (data.trim() === "verified") {
                alert("인증에 성공하였습니다.");
            } else {
                alert("인증번호가 일치하지 않거나 만료되었습니다.");
            }
        });
    };

    // ── 3. 완료 버튼 ──
    document.querySelector(".userModifyBtn").addEventListener("click", () => {
        if (!isPhotoAttached) {
            photoError.style.color = "red";
            photoError.textContent = "사진 첨부는 필수입니다.";
            window.scrollTo({ top: 0, behavior: 'smooth' });
            return;
        }
        location.href = `${cPath}/mvc/auth/mentee/myPage.my`;
    });
});
document.addEventListener("DOMContentLoaded", () => {
    // ── 공통 설정 ──
    const cPath = window.contextPath || ""; 
    
    // 상태 체크 플래그 (확인 버튼을 눌렀는지 여부)
    let isNickChecked = false;
    let isPwChecked = false;

    // ── 1. 프로필 사진 관리 ──
    const profileImg = document.querySelector(".userImg > img");
    const photoBtn = document.querySelector("#imgBtn");
    const photoError = document.querySelector(".userImg .errorMsg");
    let isPhotoAttached = !profileImg.src.includes("ex1.png");

    const fileInput = document.createElement("input");
    fileInput.type = "file";
    fileInput.accept = "image/*";
    
    photoBtn.addEventListener("click", (e) => {
        e.preventDefault();
        fileInput.click();
    });

    fileInput.addEventListener("change", (e) => {
        const file = e.target.files;
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

    // ── 2. 닉네임 중복 확인 ──
    window.checkNick = function() {
        const nickInput = document.getElementById("memberNickname");
        const nick = nickInput.value.trim();
        const msg = document.getElementById("nickErrorMsg");

        if (!nick) {
            msg.style.color = "red";
            msg.textContent = "닉네임을 입력해주세요.";
            isNickChecked = false;
            return;
        }

        // 서버와 통신 (실제 컨트롤러 경로에 맞게 수정 필요)
        fetch(`${cPath}/mvc/auth/mentor/checkNick.my?nickname=${encodeURIComponent(nick)}`)
            .then(res => res.text())
            .then(data => {
                if (data.trim() === "available") {
                    msg.style.color = "blue";
                    msg.textContent = "사용 가능한 닉네임입니다.";
                    isNickChecked = true;
                } else {
                    msg.style.color = "red";
                    msg.textContent = "닉네임이 중복됩니다.";
                    isNickChecked = false;
                }
            })
            .catch(err => {
                console.error(err);
                alert("중복 확인 중 오류가 발생했습니다.");
            });
    };

    // 닉네임 입력값이 변경되면 다시 중복확인 하도록 리셋
    document.getElementById("memberNickname").addEventListener("input", () => {
        isNickChecked = false;
        document.getElementById("nickErrorMsg").textContent = "";
    });

    // ── 3. 비밀번호 일치 확인 ──
    window.checkPwMatch = function() {
        const pw = document.getElementById("newPw").value;
        const pwConfirm = document.getElementById("newPwConfirm").value;
        const msg = document.getElementById("pwErrorMsg");

        if (!pw || !pwConfirm) {
            msg.style.color = "red";
            msg.textContent = "비밀번호를 모두 입력해주세요.";
            isPwChecked = false;
            return;
        }

        if (pw === pwConfirm) {
            msg.style.color = "blue";
            msg.textContent = "확인 완료";
            isPwChecked = true;
        } else {
            msg.style.color = "red";
            msg.textContent = "비밀번호가 서로 일치하지 않습니다.";
            isPwChecked = false;
        }
    };

    // 비밀번호 입력값이 변경되면 다시 확인 하도록 리셋
    document.getElementById("newPw").addEventListener("input", () => { isPwChecked = false; document.getElementById("pwErrorMsg").textContent = ""; });
    document.getElementById("newPwConfirm").addEventListener("input", () => { isPwChecked = false; document.getElementById("pwErrorMsg").textContent = ""; });

    // ── 4. 전화번호 인증 (SMS) ──
    window.sendSms = function() {
        const phone = document.getElementById("memberPhone").value;
        const msg = document.getElementById("phoneSendError");
        
        if (!phone) {
            alert("전화번호를 입력해주세요.");
            return;
        }

        const url = `${cPath}/mvc/auth/mentor/verifyAction.my?mode=send&phoneNumber=${phone}`;
        
        fetch(url)
            .then(res => res.text())
            .then(data => {
                if (data.trim() === "success") {
                    alert("인증번호가 발송되었습니다.");
                    msg.style.color = "blue";
                    msg.textContent = "인증번호 발송 완료";
                } else {
                    alert("발송 실패: " + data);
                }
            });
    };

    window.verifyCode = function() {
        const code = document.getElementById("authCodeInput").value;
        const msg = document.getElementById("authCodeError");
        const url = `${cPath}/mvc/auth/mentor/verifyAction.my?mode=check`;

        fetch(url, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ authCode: code })
        })
        .then(res => res.text())
        .then(data => {
            if (data.trim() === "verified") {
                alert("인증에 성공하였습니다.");
                msg.style.color = "blue";
                msg.textContent = "전화번호 인증 완료";
            } else {
                msg.style.color = "red";
                msg.textContent = "인증번호가 일치하지 않습니다.";
            }
        });
    };

    // ── 5. 각 Form의 '변경' 버튼 클릭 시 최종 검증 ──
    const nickForm = document.getElementById("nickForm");
    const pwForm = document.getElementById("pwForm");

    if(nickForm) {
        nickForm.addEventListener("submit", function(e) {
            if (!isNickChecked) {
                e.preventDefault();
                alert("닉네임 중복 확인을 먼저 해주세요.");
            } else {
                alert("닉네임 변경이 완료되었습니다.");
            }
        });
    }

    if(pwForm) {
        pwForm.addEventListener("submit", function(e) {
            if (!isPwChecked) {
                e.preventDefault();
                alert("비밀번호 일치 확인을 먼저 해주세요.");
            } else {
                alert("비밀번호 변경이 완료되었습니다.");
            }
        });
    }

    // ── 6. 하단 '완료' 버튼 (최종 페이지 이동) ──
    document.querySelector(".userModifyBtn").addEventListener("click", () => {
        if (!isPhotoAttached) {
            photoError.style.color = "red";
            photoError.textContent = "사진 첨부는 필수입니다.";
            window.scrollTo({ top: 0, behavior: 'smooth' });
            return;
        }
        alert("모든 변경사항이 저장되었습니다.");
        location.href = `${cPath}/mvc/auth/mentee/myPage.my`;
    });

    // ── 7. 서버 사이드 에러 메시지 처리 (수정 후 리다이렉트 시) ──
    if (window.SERVER_UPDATE_STATUS === "fail") {
        alert("데이터 업데이트 중 오류가 발생했습니다. 입력을 다시 확인해주세요.");
    }
});
const modal = document.getElementById("surveyModal"); // HTML ID와 일치시킴
const openBtn = document.getElementById("userWriteBtn");
const xBtn = document.querySelector(".closeBtn");
const closeBtn = document.getElementById("closeModalBtn");
const submitBtn = document.getElementById("submitBtn"); // ID submitBtn으로 수정

const roleRadios = document.querySelectorAll('.radioUserType');
const mentorContent = document.getElementById('mentorContent');
const menteeContent = document.getElementById('menteeContent');

// 모달 열기
openBtn.onclick = () => {
    modal.style.display = "block";
}

// 모달 닫기
[closeBtn, xBtn].forEach(btn => {
    btn.onclick = () => {
        modal.style.display = "none";
    }
});

// 배경 클릭 시 닫기
window.onclick = (event) => {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

function updateFileName() {
    const fileInput = document.getElementById('surveyFile');
    const fileSelector = document.getElementById('fileSelector');
    const fileInfoDisplay = document.getElementById('fileInfoDisplay');
    const fileNameDisplay = document.getElementById('fileNameDisplay');
    
    if (fileInput.files.length > 0) {
        fileSelector.classList.add('hidden');
        fileInfoDisplay.style.display = 'flex';
        fileNameDisplay.textContent = fileInput.files[0].name;
    } else {
        fileSelector.classList.remove('hidden');
        fileInfoDisplay.style.display = 'none';
    }
}

submitBtn.onclick = (event) => {
    event.preventDefault();

    // 2. 원하는 경로를 직접 지정하여 이동합니다.
    // 예: "userModify.html" 또는 "../../main.html" 등
    const targetPath = "/frontend/html/user/undetermined/myPage/myPage.html"; 
    window.location.href = targetPath;
};

roleRadios.forEach(radio => {
    radio.addEventListener('change', (e) => {
        if (e.target.value === 'mentor') {
            mentorContent.style.display = 'block';
            menteeContent.style.display = 'none';
        } else if (e.target.value === 'mentee') {
            mentorContent.style.display = 'none';
            menteeContent.style.display = 'block';
        }
    });
});
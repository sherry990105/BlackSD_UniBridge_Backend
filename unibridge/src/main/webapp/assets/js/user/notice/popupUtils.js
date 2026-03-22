const STORAGE_KEYS = {
  SUBJECTS: "learningReportSubjects",
  REPORTS: "learningReportDummyResponse",
  USER_NUMBER: "userNumber",
};

const ENDPOINTS = {
  WRITE: `${window.contextPath}/api/user/lr/newLearningReport.rep`,
  MODIFY: `${window.contextPath}/api/user/lr/modifyLearningReport.rep`,
};

const POPUP_TYPES = {
  WRITE: "write",
  MODIFY: "modify",
  DETAIL: "detail",
};

function getPopupContainer() {
  return document.querySelector(".popup-container");
}

function openPopupContainer(container) {
  container.classList.add("active");
}

function closePopup(container, popup) {
  container.classList.remove("active");
  popup.remove();
}

function parseSessionJSON(key) {
  const raw = sessionStorage.getItem(key);
  return raw ? JSON.parse(raw) : null;
}

function getLearningReportSubjects() {
  return parseSessionJSON(STORAGE_KEYS.SUBJECTS) ?? [];
}

function getLearningReportData() {
  return parseSessionJSON(STORAGE_KEYS.REPORTS);
}

function getReportIdFromElement(element) {
  return [...element.classList]
    .find((className) => className.startsWith("rid-"))
    .replace("rid-", "");
}

function getReportIdFromEvent(event) {
  const parentElement = event.target.closest(".list-learning-content");
  return getReportIdFromElement(parentElement);
}

function findReportById(reportId) {
  const reportData = getLearningReportData();
  if (!reportData) {
    return null;
  }

  for (const group of Object.values(reportData)) {
    const foundReport = group.find(
      (report) => String(report.lrReportNumber) === String(reportId)
    );

    if (foundReport) {
      return foundReport;
    }
  }

  return null;
}

function getSubjectNumberByName(subjects, subjectName) {
  return Number(
    subjects.find(({ subjectName: name }) => name === subjectName).subjectNumber
  );
}

function getSubjectNameByReport(report, subjects) {
  if (report.lrSubjectName) {
    return report.lrSubjectName;
  }

  const foundSubject = subjects.find(
    ({ subjectNumber }) =>
      String(subjectNumber) === String(report.lrSubjectNumber ?? report.subjectNumber)
  );

  return foundSubject?.subjectName ?? "과목명을 선택해주세요.";
}

function getPopupFieldElements(container) {
  return {
    subjectNameEl: container.querySelector(
      ".select-subject-container > .select-subject-placeholder"
    ),
    subjectTitleEl: container.querySelector(
      ".config-subject-topic-container > span + div"
    ),
    subjectSummaryEl: container.querySelector(".config-subject-summary-textbox"),
    subjectContentEl: container.querySelector(".content-text-box"),
  };
}

function getPopupFieldValues(container, subjects) {
  const {
    subjectNameEl,
    subjectTitleEl,
    subjectSummaryEl,
    subjectContentEl,
  } = getPopupFieldElements(container);

  return {
    subjectNumber: getSubjectNumberByName(subjects, subjectNameEl.innerText),
    subjectTitle: subjectTitleEl.innerText,
    subjectSummary: subjectSummaryEl.innerText,
    subjectContent: subjectContentEl.innerText,
  };
}

function applyReportToModifyPopup(popup, report, subjects) {
  if (!popup || !report) {
    return;
  }

  const {
    subjectNameEl,
    subjectTitleEl,
    subjectSummaryEl,
    subjectContentEl,
  } = getPopupFieldElements(popup);

  if (subjectNameEl) {
    subjectNameEl.textContent = getSubjectNameByReport(report, subjects);
  }

  if (subjectTitleEl) {
    subjectTitleEl.textContent = report.lrSubjectTitle ?? "";
  }

  if (subjectSummaryEl) {
    subjectSummaryEl.textContent = report.lrSubjectSummary ?? "";
  }

  if (subjectContentEl) {
    subjectContentEl.textContent = report.lrSubjectContent ?? "";
  }
}

function createSubjectItems(subjects) {
  return subjects
    .map((subject) => `<li class="select-subject-item">${subject}</li>`)
    .join("");
}

function createButtonContainer(type) {
  switch (type) {
    case POPUP_TYPES.WRITE:
      return `
        <div class="button-container">
          <div class="button-cancel">취소</div>
          <div class="button-write">작성</div>
        </div>
      `;

    case POPUP_TYPES.MODIFY:
      return `
        <div class="button-container">
          <div class="button-cancel">취소</div>
          <div class="button-modify">수정</div>
        </div>
      `;

    default:
      return ``;
  }
}

function createPopupTitle(type) {
  switch (type) {
    case POPUP_TYPES.WRITE:
      return "학습일지 작성";
    case POPUP_TYPES.MODIFY:
      return "학습일지 수정";
    case POPUP_TYPES.DETAIL:
      return "학습일지 상세보기";
    default:
      return "";
  }
}

function createTextareaOrDiv(type, placeHolder, extraClsName = "") {
  switch (type) {
    case POPUP_TYPES.WRITE:
    case POPUP_TYPES.MODIFY:
      return `
        <div 
          class="editor ${extraClsName}" 
          role="textbox"
          contenteditable="true" 
          aria-label="내용 입력" 
          aria-placeholder="${placeHolder}"
          data-placeholder="제목을 입력해주세요."
        ></div>
      `;

    case POPUP_TYPES.DETAIL:
      return `
        <div class="viewer ${extraClsName}"></div>
      `;

    default:
      return "";
  }
}

function createPopupElement(html) {
  const temp = document.createElement("div");
  temp.innerHTML = html.trim();
  return temp.firstElementChild;
}

function attachPopupCloseEvents(container, popup) {
  popup.querySelector(".popup-close-button")?.addEventListener("click", () => {
    closePopup(container, popup);
  });

  popup.querySelector(".button-cancel")?.addEventListener("click", () => {
    closePopup(container, popup);
  });
}

function attachSubjectDropdownEvents(popup) {
  const subjectContainer = popup.querySelector(".select-subject-container");
  const dropdown = popup.querySelector(".select-subject-dropdown");
  const placeholder = popup.querySelector(".select-subject-placeholder");

  subjectContainer.addEventListener("click", (event) => {
    if (
      event.target.classList.contains("select-subject-placeholder") ||
      event.target.classList.contains("select-subject-selector")
    ) {
      dropdown.classList.toggle("disabled");
    }
  });

  popup.querySelectorAll(".select-subject-item").forEach((item) => {
    item.addEventListener("click", () => {
      placeholder.textContent = item.textContent;
      dropdown.classList.add("disabled");
    });
  });
}

export function createModifyPopup(e) {
  const popupContainer = getPopupContainer();
  openPopupContainer(popupContainer);

  const learningReportSubjects = getLearningReportSubjects();

  const popup = appendStudyLogPopup({
    container: popupContainer,
    type: POPUP_TYPES.MODIFY,
    e,
    subjects: learningReportSubjects.map(({ subjectName }) => subjectName),
    subjectMeta: learningReportSubjects,
  });

  const modifyButton = popupContainer.querySelector(".button-modify");

  modifyButton.addEventListener("click", async () => {
    try {
      const parentElement = e.target.closest(".list-learning-content");
      const reportNumber = getReportIdFromElement(parentElement);

      const {
        subjectNumber,
        subjectTitle,
        subjectSummary,
        subjectContent,
      } = getPopupFieldValues(popupContainer, learningReportSubjects);

      await fetch(ENDPOINTS.MODIFY, {
        method: "POST",
        body: JSON.stringify({
          reportNumber: reportNumber,
          subjectNumber: subjectNumber,
          subjectTitle: subjectTitle,
          subjectSummary: subjectSummary,
          subjectContent: subjectContent,
        }),
      });
	  
	  closePopup(popupContainer, popup);
	  location.href = `${window.contextPath}/mvc/auth/report.rep`;
    } catch {
      closePopup(popupContainer, popup);
	  alert("학습데이터 변경에 실패했습니다.");
    }
  });
}

export function createDetailPopup(e) {
  const popupContainer = getPopupContainer();
  openPopupContainer(popupContainer);

  insertLrViewerPopup({
    target: popupContainer,
    event: e,
  });
}

export function createWritePopup() {
  const popupContainer = getPopupContainer();
  openPopupContainer(popupContainer);

  const learningReportSubjects = getLearningReportSubjects();

  appendStudyLogPopup({
    container: popupContainer,
    type: POPUP_TYPES.WRITE,
    subjects: learningReportSubjects.map(({ subjectName }) => subjectName),
    subjectMeta: learningReportSubjects,
  });

  const submitBtnEl = popupContainer.querySelector(".button-write");

  submitBtnEl.addEventListener("click", async () => {
    const mentorNumber = Number(sessionStorage.getItem(STORAGE_KEYS.USER_NUMBER));

    const {
      subjectNumber,
      subjectTitle,
      subjectSummary,
      subjectContent,
    } = getPopupFieldValues(popupContainer, learningReportSubjects);

    await fetch(ENDPOINTS.WRITE, {
      method: "POST",
      body: JSON.stringify({
        mentorNumber: mentorNumber,
        subjectNumber: subjectNumber,
        subjectTitle: subjectTitle,
        subjectSummary: subjectSummary,
        subjectContent: subjectContent,
      }),
    });
	
	closePopup(popupContainer, popup);
	location.href = `${window.contextPath}/mvc/auth/report.rep`;
  });
}

export function appendStudyLogPopup({
  container,
  type,
  e = null,
  subjects = [],
  subjectMeta = [],
} = {}) {
  const subjectItems = createSubjectItems(subjects);

  let reportNumber = null;
  let reportInfo = null;

  if (type === POPUP_TYPES.DETAIL || type === POPUP_TYPES.MODIFY) {
    reportNumber = getReportIdFromEvent(e);
    reportInfo = findReportById(reportNumber);
  }

  const html = `
    <div class="popup rid-${reportNumber}">
      <div class="popup-head">
        <div class="popup-title">${createPopupTitle(type)}</div>
        <div class="popup-close-button"></div>
      </div>
      <div class="popup-body">
        <div class="popup-body-config">
          <div class="config-title">학습 주제 및 목표</div>
          <div class="config-subject-container">
            <div class="config-subject-name-container">
              <span class="config-subject-title__inner">학습과목</span>
              <div class="select-subject-container" ${type === POPUP_TYPES.DETAIL ? 'style="cursor: auto;"' : ""}>
                <div class="select-subject-placeholder select-placeholder">과목명을 선택해주세요.</div>
                <div class="select-subject-selector">${type !== POPUP_TYPES.DETAIL ? "▼" : ""}</div>
                <div class="select-subject-dropdown disabled">
                  <ul class="select-subject-dropdown__inner">
                    ${subjectItems}
                  </ul>
                </div>
              </div>
            </div>
            <div class="config-subject-topic-container">
              <span class="config-subject-title__inner">학습일지 주제</span>
              ${createTextareaOrDiv(type, "제목을 입력해주세요.")}
            </div>
            <div class="config-subject-summary-container">
              <span class="config-subject-title__inner">학습일지 요약</span>
              ${createTextareaOrDiv(type, "내용을 입력해주세요.", "config-subject-summary-textbox")}
            </div>
          </div>
        </div>
        <div class="popup-body-content">
          <div class="content-title">학습 내용</div>
          ${createTextareaOrDiv(type, "내용을 입력해주세요.", "content-text-box")}
        </div>
        ${createButtonContainer(type)}
      </div>
    </div>
  `;

  const popup = createPopupElement(html);
  container.appendChild(popup);

  attachPopupCloseEvents(container, popup);

  if (type === POPUP_TYPES.WRITE || type === POPUP_TYPES.MODIFY) {
    attachSubjectDropdownEvents(popup);
  }

  if (type === POPUP_TYPES.MODIFY) {
    applyReportToModifyPopup(popup, reportInfo, subjectMeta);
  }

  return popup;
}

export function insertLrViewerPopup({ target, event }) {
  const reportID = getReportIdFromEvent(event);
  const lrInfoIfDetail = findReportById(reportID);

  if (!lrInfoIfDetail) {
    return "";
  }

  const html = `
    <div class="popup rid-${reportID}">
      <div class="popup-head">
        <div class="popup-title">학습일지 상세보기</div>
        <div class="popup-close-button"></div>
      </div>
      <div class="popup-body">
        <div class="popup-body-config">
          <div class="config-title">학습 주제 및 목표</div>
          <div class="config-subject-container">
            <div class="config-subject-name-container">
              <span class="config-subject-title__inner">학습과목</span>
              <div class="select-subject-container" style="cursor: auto;">
                <div class="select-subject-placeholder select-placeholder">${lrInfoIfDetail.lrSubjectName}</div>
                <div class="select-subject-selector"></div>
                <div class="select-subject-dropdown disabled">
                  <ul class="select-subject-dropdown__inner">
                  </ul>
                </div>
              </div>
            </div>
            <div class="config-subject-topic-container">
              <span class="config-subject-title__inner">학습일지 주제</span>
              <div class="viewer">${lrInfoIfDetail.lrSubjectTitle}</div>
            </div>
            <div class="config-subject-summary-container">
              <span class="config-subject-title__inner">학습일지 요약</span>
              <div class="viewer config-subject-summary-textbox">${lrInfoIfDetail.lrSubjectSummary}</div>
            </div>
          </div>
        </div>
        <div class="popup-body-content">
          <div class="content-title">학습 내용</div>
          <div class="viewer content-text-box">${lrInfoIfDetail.lrSubjectContent}</div>
        </div>
      </div>
    </div>
  `;

  const popup = createPopupElement(html);
  target.appendChild(popup);

  popup.querySelector(".popup-close-button")?.addEventListener("click", () => {
    closePopup(target, popup);
  });

  return popup;
}
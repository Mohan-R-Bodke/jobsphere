// ========================================
// RECRUITER DASHBOARD
// ========================================

const token =
    localStorage.getItem("jwtToken");

const userRole =
    localStorage.getItem("userRole");

const userEmail =
    localStorage.getItem("userEmail");


// ========================================
// AUTHENTICATION CHECK
// ========================================

if (!token || userRole !== "RECRUITER") {

    window.location.href =
        "login.html";
}


// ========================================
// DISPLAY USER INFORMATION
// ========================================

const emailElement =
    document.getElementById("userEmail");

if (emailElement && userEmail) {

    emailElement.textContent =
        `Logged in as: ${userEmail}`;
}


// ========================================
// CREATE JOB
// ========================================

const createJobBtn =
    document.getElementById("createJobBtn");

if (createJobBtn) {

    createJobBtn.addEventListener(
        "click",
        function () {

            window.location.href =
                "create-job.html";

        }
    );
}


// ========================================
// MY JOBS
// ========================================

const myJobsBtn =
    document.getElementById("myJobsBtn");

if (myJobsBtn) {

    myJobsBtn.addEventListener(
        "click",
        function () {

            window.location.href =
                "recruiter-jobs.html";

        }
    );
}


// ========================================
// APPLICANTS
// ========================================

const applicantsBtn =
    document.getElementById("applicantsBtn");

if (applicantsBtn) {

    applicantsBtn.addEventListener(
        "click",
        function () {

            window.location.href =
                "recruiter-applicants.html";

        }
    );
}


// ========================================
// COMPANY PROFILE
// ========================================

const profileBtn =
    document.getElementById("profileBtn");

if (profileBtn) {

    profileBtn.addEventListener(
        "click",
        function () {

            window.location.href =
                "recruiter-profile.html";

        }
    );
}


// ========================================
// LOGOUT
// ========================================

const logoutBtn =
    document.getElementById("logoutBtn");

if (logoutBtn) {

    logoutBtn.addEventListener(
        "click",
        function (event) {

            event.preventDefault();

            localStorage.clear();

            window.location.href =
                "index.html";

        }
    );
}
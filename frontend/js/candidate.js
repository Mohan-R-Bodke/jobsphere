// ========================================
// CANDIDATE DASHBOARD
// ========================================

const token = localStorage.getItem("jwtToken");

const userRole = localStorage.getItem("userRole");

const userEmail = localStorage.getItem("userEmail");


// ========================================
// AUTHENTICATION CHECK
// ========================================

if (!token || userRole !== "CANDIDATE") {

    window.location.href = "login.html";
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
// VIEW JOBS
// ========================================

const viewJobsBtn =
    document.getElementById("viewJobsBtn");

if (viewJobsBtn) {

    viewJobsBtn.addEventListener(
        "click",
        function () {

            window.location.href =
                "jobs.html";

        }
    );
}


// ========================================
// MY APPLICATIONS
// ========================================

const applicationsBtn =
    document.getElementById("applicationsBtn");

if (applicationsBtn) {

    applicationsBtn.addEventListener(
        "click",
        function () {

            window.location.href =
                "candidate-applications.html";

        }
    );
}


// ========================================
// PROFILE
// ========================================

const profileBtn =
    document.getElementById("profileBtn");

if (profileBtn) {

    profileBtn.addEventListener(
        "click",
        function () {

            window.location.href =
                "candidate-profile.html";

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

            localStorage.removeItem("jwtToken");
            localStorage.removeItem("userId");
            localStorage.removeItem("userEmail");
            localStorage.removeItem("userRole");

            window.location.href =
                "index.html";
        }
    );
}
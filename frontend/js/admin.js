const token = localStorage.getItem("jwtToken");
const userRole = localStorage.getItem("userRole");

if (!token || userRole !== "ADMIN") {
    window.location.href = "login.html";
}


document.getElementById("usersBtn")
    .addEventListener("click", function () {

        window.location.href =
            "admin-users.html";
    });


document.getElementById("jobsBtn")
    .addEventListener("click", function () {

        window.location.href =
            "admin-jobs.html";
    });


document.getElementById("applicationsBtn")
    .addEventListener("click", function () {

        window.location.href =
            "admin-applications.html";
    });


document.getElementById("logoutBtn")
    .addEventListener("click", function (event) {

        event.preventDefault();

        localStorage.clear();

        window.location.href =
            "index.html";
    });
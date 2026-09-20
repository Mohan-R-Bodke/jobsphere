const API_BASE_URL =
    "https://jobsphere-production-a956.up.railway.app/api";

const token =
    localStorage.getItem("jwtToken");

const userRole =
    localStorage.getItem("userRole");


// ========================================
// AUTHENTICATION CHECK
// ========================================

if (!token || userRole !== "RECRUITER") {

    window.location.href =
        "login.html";
}


// ========================================
// FORM
// ========================================

const createJobForm =
    document.getElementById("createJobForm");

const jobMessage =
    document.getElementById("jobMessage");


// ========================================
// CREATE JOB
// ========================================

createJobForm.addEventListener(
    "submit",
    async function (event) {

        event.preventDefault();


        const jobData = {

            title:
                document.getElementById("title")
                    .value.trim(),

            company:
                document.getElementById("company")
                    .value.trim(),

            description:
                document.getElementById("description")
                    .value.trim(),

            location:
                document.getElementById("location")
                    .value.trim(),

            jobType:
                document.getElementById("jobType")
                    .value,

            skills:
                document.getElementById("skills")
                    .value.trim(),

            salary:
                document.getElementById("salary")
                    .value.trim()

        };


        jobMessage.textContent =
            "Creating job...";


        try {

            const response =
                await fetch(
                    `${API_BASE_URL}/jobs`,
                    {
                        method: "POST",

                        headers: {

                            "Content-Type":
                                "application/json",

                            "Authorization":
                                `Bearer ${token}`

                        },

                        body:
                            JSON.stringify(jobData)
                    }
                );


            const data =
                await response.json();


            if (!response.ok) {

                jobMessage.textContent =
                    data.message ||
                    "Unable to create job.";

                return;
            }


            jobMessage.textContent =
                "Job created successfully!";


            createJobForm.reset();


        } catch (error) {

            console.error(error);

            jobMessage.textContent =
                "Unable to connect to the server.";
        }

    }
);


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
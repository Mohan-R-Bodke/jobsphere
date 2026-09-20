const API_BASE_URL = "https://jobsphere-production-a956.up.railway.app/api";

const token = localStorage.getItem("jwtToken");
const userRole = localStorage.getItem("userRole");

if (!token || userRole !== "RECRUITER") {
    window.location.href = "login.html";
}

const params = new URLSearchParams(window.location.search);
const jobId = params.get("id");

const form = document.getElementById("editJobForm");
const message = document.getElementById("jobMessage");

if (!jobId) {
    message.textContent = "Invalid job ID.";
    form.style.display = "none";
}


// Load existing job
async function loadJob() {

    message.textContent = "Loading job...";

    try {

        const response = await fetch(
            `${API_BASE_URL}/jobs/${jobId}`,
            {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            }
        );

        const job = await response.json();

        if (!response.ok) {
            message.textContent =
                job.message || "Unable to load job.";
            return;
        }

        document.getElementById("title").value =
            job.title || "";

        document.getElementById("company").value =
            job.company || "";

        document.getElementById("description").value =
            job.description || "";

        document.getElementById("location").value =
            job.location || "";

        document.getElementById("jobType").value =
            job.jobType || "";

        document.getElementById("skills").value =
            job.skills || "";

        document.getElementById("salary").value =
            job.salary || "";

        message.textContent =
            "Job loaded successfully.";

    } catch (error) {

        console.error(error);

        message.textContent =
            "Unable to connect to the server.";
    }
}


// Update job
form.addEventListener(
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

        message.textContent =
            "Updating job...";

        try {

            const response = await fetch(
                `${API_BASE_URL}/jobs/${jobId}`,
                {
                    method: "PUT",

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

                message.textContent =
                    data.message ||
                    "Unable to update job.";

                return;
            }

            message.textContent =
                "Job updated successfully!";

        } catch (error) {

            console.error(error);

            message.textContent =
                "Unable to connect to the server.";
        }
    }
);


// Logout
document.getElementById("logoutBtn")
    .addEventListener("click", function (event) {

        event.preventDefault();

        localStorage.clear();

        window.location.href = "index.html";
    });


if (jobId) {
    loadJob();
}
const API_BASE_URL =
    "https://jobsphere-production-a956.up.railway.app/api";

const token =
    localStorage.getItem("jwtToken");

const userRole =
    localStorage.getItem("userRole");

const userId =
    localStorage.getItem("userId");

if (!token || userRole !== "RECRUITER") {
    window.location.href = "login.html";
}

const jobsContainer =
    document.getElementById("jobsContainer");

const jobsMessage =
    document.getElementById("jobsMessage");


async function loadMyJobs() {

    jobsMessage.textContent =
        "Loading your jobs...";

    jobsContainer.innerHTML = "";

    try {

        const response =
            await fetch(
                `${API_BASE_URL}/jobs`,
                {
                    method: "GET",
                    headers: {
                        "Authorization":
                            `Bearer ${token}`
                    }
                }
            );

        const jobs =
            await response.json();

        if (!response.ok) {

            jobsMessage.textContent =
                jobs.message ||
                "Unable to load jobs.";

            return;
        }

        // Show only jobs created by this recruiter
        const myJobs =
            jobs.filter(
                job =>
                    String(job.recruiterId) ===
                    String(userId)
            );

        jobsMessage.textContent =
            `${myJobs.length} job(s) posted`;

        if (myJobs.length === 0) {

            jobsContainer.innerHTML = `
                <div class="empty-state">
                    <h3>No Jobs Posted</h3>
                    <p>You have not created any jobs yet.</p>

                    <button
                        class="btn primary"
                        onclick="window.location.href='create-job.html'">
                        Create Job
                    </button>
                </div>
            `;

            return;
        }

        myJobs.forEach(job => {

            const card =
                document.createElement("div");

            card.className = "job-card";

            card.innerHTML = `
                <h2>${job.title}</h2>

                <h3>${job.company}</h3>

                <p>
                    <strong>Job ID:</strong>
                    ${job.id}
                </p>

                <p>
                    <strong>Location:</strong>
                    ${job.location}
                </p>

                <p>
                    <strong>Job Type:</strong>
                    ${job.jobType}
                </p>

                <p>
                    <strong>Skills:</strong>
                    ${job.skills}
                </p>

                <p>
                    <strong>Salary:</strong>
                    ${job.salary || "Not specified"}
                </p>

                <p class="job-description">
                    ${job.description}
                </p>

                <div class="hero-buttons">

                    <button
                        class="btn secondary edit-btn"
                        data-job-id="${job.id}">
                        Edit
                    </button>

                    <button
                        class="btn primary delete-btn"
                        data-job-id="${job.id}">
                        Delete
                    </button>

                </div>
            `;

            jobsContainer.appendChild(card);
        });


        // Edit buttons
        document
            .querySelectorAll(".edit-btn")
            .forEach(button => {

                button.addEventListener(
                    "click",
                    function () {

                        const jobId =
                            this.dataset.jobId;

                        window.location.href =
                            `edit-job.html?id=${jobId}`;
                    }
                );
            });


        // Delete buttons
        document
            .querySelectorAll(".delete-btn")
            .forEach(button => {

                button.addEventListener(
                    "click",
                    function () {

                        const jobId =
                            this.dataset.jobId;

                        deleteJob(jobId);
                    }
                );
            });

    } catch (error) {

        console.error(error);

        jobsMessage.textContent =
            "Unable to connect to the server.";
    }
}


async function deleteJob(jobId) {

    const confirmed =
        confirm(
            "Are you sure you want to delete this job?"
        );

    if (!confirmed) {
        return;
    }

    try {

        const response =
            await fetch(
                `${API_BASE_URL}/jobs/${jobId}`,
                {
                    method: "DELETE",

                    headers: {
                        "Authorization":
                            `Bearer ${token}`
                    }
                }
            );

        // DELETE may return an empty response body
        const responseText =
            await response.text();

        if (!response.ok) {

            let errorMessage =
                "Unable to delete job.";

            if (responseText) {

                try {

                    const errorData =
                        JSON.parse(responseText);

                    errorMessage =
                        errorData.message ||
                        errorMessage;

                } catch (error) {

                    errorMessage =
                        responseText;
                }
            }

            alert(errorMessage);

            return;
        }

        alert(
            "Job deleted successfully."
        );

        loadMyJobs();

    } catch (error) {

        console.error(error);

        alert(
            "Unable to connect to the server."
        );
    }
}


// Logout
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


loadMyJobs();
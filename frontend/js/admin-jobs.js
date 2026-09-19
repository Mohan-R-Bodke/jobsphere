const API_BASE_URL = "http://localhost:8080/api";

const token = localStorage.getItem("jwtToken");
const userRole = localStorage.getItem("userRole");

if (!token || userRole !== "ADMIN") {
    window.location.href = "login.html";
}

const jobsContainer =
    document.getElementById("jobsContainer");

const jobsMessage =
    document.getElementById("jobsMessage");


async function loadJobs() {

    jobsMessage.textContent = "Loading jobs...";
    jobsContainer.innerHTML = "";

    try {

        const response = await fetch(
            `${API_BASE_URL}/jobs`,
            {
                headers: {
                    "Authorization":
                        `Bearer ${token}`
                }
            }
        );

        const jobs = await response.json();

        if (!response.ok) {

            jobsMessage.textContent =
                jobs.message ||
                "Unable to load jobs.";

            return;
        }

        jobsMessage.textContent =
            `${jobs.length} job(s) found`;

        if (jobs.length === 0) {

            jobsContainer.innerHTML = `
                <div class="empty-state">
                    <h3>No Jobs Found</h3>
                    <p>No jobs have been posted yet.</p>
                </div>
            `;

            return;
        }

        jobs.forEach(job => {

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
                    <strong>Recruiter ID:</strong>
                    ${job.recruiterId}
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
            `;

            jobsContainer.appendChild(card);
        });

    } catch (error) {

        console.error(error);

        jobsMessage.textContent =
            "Unable to connect to the server.";
    }
}


document.getElementById("logoutBtn")
    .addEventListener("click", function (event) {

        event.preventDefault();

        localStorage.clear();

        window.location.href =
            "index.html";
    });


loadJobs();
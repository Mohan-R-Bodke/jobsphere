const API_BASE_URL = "http://localhost:8080/api";

const token = localStorage.getItem("jwtToken");

const userRole = localStorage.getItem("userRole");


// ========================================
// AUTHENTICATION CHECK
// ========================================

if (!token || userRole !== "CANDIDATE") {

    window.location.href = "login.html";
}


// ========================================
// ELEMENTS
// ========================================

const jobsContainer =
    document.getElementById("jobsContainer");

const jobsMessage =
    document.getElementById("jobsMessage");

const searchBtn =
    document.getElementById("searchBtn");


// ========================================
// LOAD JOBS
// ========================================

async function loadJobs() {

    jobsMessage.textContent =
        "Loading jobs...";

    jobsContainer.innerHTML = "";


    const title =
        document.getElementById("titleSearch")
            .value.trim();

    const location =
        document.getElementById("locationSearch")
            .value.trim();

    const company =
        document.getElementById("companySearch")
            .value.trim();

    const jobType =
        document.getElementById("jobTypeSearch")
            .value;


    const params =
        new URLSearchParams();


    if (title) {
        params.append("title", title);
    }

    if (location) {
        params.append("location", location);
    }

    if (company) {
        params.append("company", company);
    }

    if (jobType) {
        params.append("jobType", jobType);
    }


    try {

        const url =
            `${API_BASE_URL}/jobs` +
            (params.toString()
                ? `?${params.toString()}`
                : "");


        const response =
            await fetch(url, {

                method: "GET",

                headers: {
                    "Authorization":
                        `Bearer ${token}`
                }
            });


        const jobs =
            await response.json();


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
                    <h3>No jobs found</h3>
                    <p>
                        Try changing your search filters.
                    </p>
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

                <button
                    class="btn primary apply-btn"
                    data-job-id="${job.id}">
                    Apply Now
                </button>
            `;


            jobsContainer.appendChild(card);

        });


        // Attach Apply buttons

        document
            .querySelectorAll(".apply-btn")
            .forEach(button => {

                button.addEventListener(
                    "click",
                    () => applyForJob(
                        button.dataset.jobId
                    )
                );

            });


    } catch (error) {

        console.error(error);

        jobsMessage.textContent =
            "Unable to connect to the server.";
    }
}


// ========================================
// APPLY FOR JOB
// ========================================

async function applyForJob(jobId) {

    try {

        const response =
            await fetch(
                `${API_BASE_URL}/applications/apply`,
                {
                    method: "POST",

                    headers: {
                        "Content-Type":
                            "application/json",

                        "Authorization":
                            `Bearer ${token}`
                    },

                    body: JSON.stringify({
                        jobId: Number(jobId)
                    })
                }
            );


        const data =
            await response.json();


        if (!response.ok) {

            alert(
                data.message ||
                "Unable to apply for this job."
            );

            return;
        }


        alert(
            "Application submitted successfully!"
        );

    } catch (error) {

        console.error(error);

        alert(
            "Unable to connect to the server."
        );
    }
}


// ========================================
// SEARCH
// ========================================

searchBtn.addEventListener(
    "click",
    loadJobs
);


// ========================================
// INITIAL LOAD
// ========================================

loadJobs();


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
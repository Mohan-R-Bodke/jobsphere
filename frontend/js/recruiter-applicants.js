const API_BASE_URL = "http://localhost:8080/api";

const token = localStorage.getItem("jwtToken");
const userRole = localStorage.getItem("userRole");

if (!token || userRole !== "RECRUITER") {
    window.location.href = "login.html";
}

const applicantsContainer =
    document.getElementById("applicantsContainer");

const applicantsMessage =
    document.getElementById("applicantsMessage");


async function loadApplicants() {

    applicantsMessage.textContent =
        "Loading applicants...";

    applicantsContainer.innerHTML = "";

    try {

        const jobsResponse = await fetch(
            `${API_BASE_URL}/jobs`,
            {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            }
        );

        const jobs = await jobsResponse.json();

        if (!jobsResponse.ok) {
            applicantsMessage.textContent =
                jobs.message ||
                "Unable to load jobs.";
            return;
        }

        const userId =
            localStorage.getItem("userId");

        const myJobs = jobs.filter(
            job =>
                String(job.recruiterId) ===
                String(userId)
        );

        let allApplicants = [];

        for (const job of myJobs) {

            const response = await fetch(
                `${API_BASE_URL}/applications/job/${job.id}`,
                {
                    method: "GET",
                    headers: {
                        "Authorization":
                            `Bearer ${token}`
                    }
                }
            );

            if (!response.ok) {
                continue;
            }

            const applications =
                await response.json();

            applications.forEach(application => {

                allApplicants.push({
                    ...application,
                    jobTitle: job.title,
                    company: job.company
                });

            });
        }

        applicantsMessage.textContent =
            `${allApplicants.length} applicant(s) found`;

        if (allApplicants.length === 0) {

            applicantsContainer.innerHTML = `
                <div class="empty-state">
                    <h3>No Applicants Yet</h3>
                    <p>No candidates have applied to your jobs.</p>
                </div>
            `;

            return;
        }

        allApplicants.forEach(application => {

            const card =
                document.createElement("div");

            card.className = "job-card";

            const status =
                application.status || "APPLIED";

            card.innerHTML = `

                <h2>${application.jobTitle}</h2>

                <h3>${application.company}</h3>

                <p>
                    <strong>Application ID:</strong>
                    ${application.id}
                </p>

                <p>
                    <strong>Candidate:</strong>
                    ${application.candidateName || "Candidate"}
                </p>

                <p>
                    <strong>Email:</strong>
                    ${application.candidateEmail || "Not available"}
                </p>

                <p>
                    <strong>Applied On:</strong>
                    ${formatDate(application.appliedAt)}
                </p>

                <p>
                    <strong>Current Status:</strong>

                    <span class="status-badge status-${status.toLowerCase()}">
                        ${status}
                    </span>
                </p>

                <div class="form-group">

                    <label for="status-${application.id}">
                        Change Status
                    </label>

                    <select
                        id="status-${application.id}"
                        class="status-select">

                        <option value="APPLIED"
                            ${status === "APPLIED" ? "selected" : ""}>
                            Applied
                        </option>

                        <option value="REVIEWING"
                            ${status === "REVIEWING" ? "selected" : ""}>
                            Reviewing
                        </option>

                        <option value="SHORTLISTED"
                            ${status === "SHORTLISTED" ? "selected" : ""}>
                            Shortlisted
                        </option>

                        <option value="REJECTED"
                            ${status === "REJECTED" ? "selected" : ""}>
                            Rejected
                        </option>

                        <option value="HIRED"
                            ${status === "HIRED" ? "selected" : ""}>
                            Hired
                        </option>

                    </select>

                </div>

                <button
                    class="btn primary update-status-btn"
                    data-application-id="${application.id}">

                    Update Status

                </button>
            `;

            applicantsContainer.appendChild(card);
        });


        document
            .querySelectorAll(".update-status-btn")
            .forEach(button => {

                button.addEventListener(
                    "click",
                    function () {

                        const applicationId =
                            this.dataset.applicationId;

                        const select =
                            document.getElementById(
                                `status-${applicationId}`
                            );

                        updateStatus(
                            applicationId,
                            select.value
                        );
                    }
                );
            });

    } catch (error) {

        console.error(error);

        applicantsMessage.textContent =
            "Unable to connect to the server.";
    }
}


async function updateStatus(
    applicationId,
    status
) {

    try {

        const response = await fetch(
            `${API_BASE_URL}/applications/${applicationId}/status`,
            {
                method: "PUT",

                headers: {
                    "Content-Type":
                        "application/json",

                    "Authorization":
                        `Bearer ${token}`
                },

                body: JSON.stringify({
                    status: status
                })
            }
        );

        const data =
            await response.json();

        if (!response.ok) {

            alert(
                data.message ||
                "Unable to update application status."
            );

            return;
        }

        alert(
            "Application status updated successfully."
        );

        loadApplicants();

    } catch (error) {

        console.error(error);

        alert(
            "Unable to connect to the server."
        );
    }
}


function formatDate(dateValue) {

    if (!dateValue) {
        return "Not available";
    }

    const date =
        new Date(dateValue);

    if (isNaN(date.getTime())) {
        return dateValue;
    }

    return date.toLocaleString();
}


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


loadApplicants();
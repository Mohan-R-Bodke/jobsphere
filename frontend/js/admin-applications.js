const API_BASE_URL = "https://jobsphere-production-a956.up.railway.app/api";

const token = localStorage.getItem("jwtToken");
const userRole = localStorage.getItem("userRole");

if (!token || userRole !== "ADMIN") {
    window.location.href = "login.html";
}

const applicationsContainer =
    document.getElementById(
        "applicationsContainer"
    );

const applicationsMessage =
    document.getElementById(
        "applicationsMessage"
    );


async function loadApplications() {

    applicationsMessage.textContent =
        "Loading applications...";

    applicationsContainer.innerHTML = "";

    try {

        const response = await fetch(
            `${API_BASE_URL}/admin/applications`,
            {
                headers: {
                    "Authorization":
                        `Bearer ${token}`
                }
            }
        );

        const applications =
            await response.json();

        if (!response.ok) {

            applicationsMessage.textContent =
                applications.message ||
                "Unable to load applications.";

            return;
        }

        applicationsMessage.textContent =
            `${applications.length} application(s) found`;


        if (applications.length === 0) {

            applicationsContainer.innerHTML = `
                <div class="empty-state">
                    <h3>No Applications Found</h3>
                    <p>No applications have been submitted yet.</p>
                </div>
            `;

            return;
        }


        applications.forEach(application => {

            const card =
                document.createElement("div");

            card.className = "job-card";

            const status =
                application.status || "APPLIED";

            card.innerHTML = `

                <h2>
                    ${application.jobTitle}
                </h2>

                <h3>
                    ${application.company}
                </h3>

                <p>
                    <strong>Application ID:</strong>
                    ${application.id}
                </p>

                <p>
                    <strong>Candidate ID:</strong>
                    ${application.candidateId}
                </p>

                <p>
                    <strong>Job ID:</strong>
                    ${application.jobId}
                </p>

                <p>
                    <strong>Applied On:</strong>
                    ${formatDate(application.appliedAt)}
                </p>

                <p>
                    <strong>Status:</strong>

                    <span
                        class="status-badge status-${status.toLowerCase()}">

                        ${status}

                    </span>

                </p>
            `;

            applicationsContainer.appendChild(card);
        });

    } catch (error) {

        console.error(error);

        applicationsMessage.textContent =
            "Unable to connect to the server.";
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


document.getElementById("logoutBtn")
    .addEventListener("click", function (event) {

        event.preventDefault();

        localStorage.clear();

        window.location.href =
            "index.html";
    });


loadApplications();
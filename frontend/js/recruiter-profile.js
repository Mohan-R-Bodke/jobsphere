const API_BASE_URL = "http://localhost:8080/api";

const token = localStorage.getItem("jwtToken");
const userRole = localStorage.getItem("userRole");

if (!token || userRole !== "RECRUITER") {
    window.location.href = "login.html";
}

const form = document.getElementById("profileForm");
const message = document.getElementById("profileMessage");

// LOAD PROFILE
async function loadProfile() {

    message.textContent = "Loading company profile...";

    try {

        const response = await fetch(
            `${API_BASE_URL}/recruiter/profile`,
            {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            }
        );

        const profile = await response.json();

        if (!response.ok) {
            message.textContent =
                profile.message || "Unable to load profile.";
            return;
        }

        document.getElementById("companyName").value =
            profile.companyName || "";

        document.getElementById("description").value =
            profile.companyDescription || "";

        document.getElementById("website").value =
            profile.website || "";

        document.getElementById("location").value =
            profile.location || "";

        document.getElementById("industry").value =
            profile.industry || "";

        document.getElementById("companySize").value =
            profile.companySize || "";

        message.textContent =
            "Company profile loaded successfully.";

    } catch (error) {

        console.error(error);

        message.textContent =
            "Unable to connect to the server.";
    }
}


// SAVE PROFILE
form.addEventListener("submit", async function (event) {

    event.preventDefault();

    const profileData = {

        companyName:
            document.getElementById("companyName").value.trim(),

        companyDescription:
            document.getElementById("description").value.trim(),

        website:
            document.getElementById("website").value.trim(),

        location:
            document.getElementById("location").value.trim(),

        industry:
            document.getElementById("industry").value.trim(),

        companySize:
            document.getElementById("companySize").value.trim()
    };

    message.textContent = "Saving company profile...";

    try {

        const response = await fetch(
            `${API_BASE_URL}/recruiter/profile`,
            {
                method: "POST",

                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                },

                body: JSON.stringify(profileData)
            }
        );

        const data = await response.json();

        if (!response.ok) {

            if (data.fields) {
                const errors = Object.values(data.fields).join(" ");
                message.textContent = errors;
            } else {
                message.textContent =
                    data.message || "Unable to save profile.";
            }

            return;
        }

        message.textContent =
            "Company profile saved successfully.";

    } catch (error) {

        console.error(error);

        message.textContent =
            "Unable to connect to the server.";
    }
});


// LOGOUT
const logoutBtn = document.getElementById("logoutBtn");

if (logoutBtn) {

    logoutBtn.addEventListener("click", function (event) {

        event.preventDefault();

        localStorage.clear();

        window.location.href = "index.html";
    });
}


// INITIAL LOAD
loadProfile();
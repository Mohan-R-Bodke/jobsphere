const API_BASE_URL = "https://jobsphere-production-a956.up.railway.app/api";

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

const profileForm =
    document.getElementById("profileForm");

const profileMessage =
    document.getElementById("profileMessage");


// ========================================
// LOAD PROFILE
// ========================================

async function loadProfile() {

    profileMessage.textContent =
        "Loading profile...";


    try {

        const response = await fetch(
            `${API_BASE_URL}/candidate/profile`,
            {
                method: "GET",

                headers: {
                    "Authorization":
                        `Bearer ${token}`
                }
            }
        );


        const profile =
            await response.json();


        if (!response.ok) {

            profileMessage.textContent =
                profile.message ||
                "Unable to load profile.";

            return;
        }


        // ========================================
        // FILL FORM
        // ========================================

        document.getElementById("phone").value =
            profile.phone || "";

        document.getElementById("education").value =
            profile.education || "";

        document.getElementById("skills").value =
            profile.skills || "";

        document.getElementById("experience").value =
            profile.experience || "";

        document.getElementById("resumeUrl").value =
            profile.resumeUrl || "";


        profileMessage.textContent =
            "Profile loaded successfully.";


    } catch (error) {

        console.error(error);

        profileMessage.textContent =
            "Unable to connect to the server.";
    }
}


// ========================================
// SAVE PROFILE
// ========================================

profileForm.addEventListener(
    "submit",
    async function (event) {

        event.preventDefault();


        const profileData = {

            phone:
                document.getElementById("phone").value.trim(),

            education:
                document.getElementById("education").value.trim(),

            skills:
                document.getElementById("skills").value.trim(),

            experience:
                document.getElementById("experience").value.trim(),

            resumeUrl:
                document.getElementById("resumeUrl").value.trim()
        };


        profileMessage.textContent =
            "Saving profile...";


        try {

            const response = await fetch(
                `${API_BASE_URL}/candidate/profile`,
                {
                    method: "POST",

                    headers: {
                        "Content-Type":
                            "application/json",

                        "Authorization":
                            `Bearer ${token}`
                    },

                    body: JSON.stringify(profileData)
                }
            );


            const data =
                await response.json();


            if (!response.ok) {

                profileMessage.textContent =
                    data.message ||
                    "Unable to save profile.";

                return;
            }


            profileMessage.textContent =
                "Profile saved successfully.";


        } catch (error) {

            console.error(error);

            profileMessage.textContent =
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


// ========================================
// INITIAL LOAD
// ========================================

loadProfile();
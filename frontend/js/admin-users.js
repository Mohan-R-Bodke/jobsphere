const API_BASE_URL = "https://jobsphere-production-a956.up.railway.app/api";

const token = localStorage.getItem("jwtToken");
const userRole = localStorage.getItem("userRole");

if (!token || userRole !== "ADMIN") {
    window.location.href = "login.html";
}

const usersContainer =
    document.getElementById("usersContainer");

const usersMessage =
    document.getElementById("usersMessage");


async function loadUsers() {

    usersMessage.textContent =
        "Loading users...";

    usersContainer.innerHTML = "";

    try {

        const response = await fetch(
            `${API_BASE_URL}/users`,
            {
                headers: {
                    "Authorization":
                        `Bearer ${token}`
                }
            }
        );

        const users =
            await response.json();

        if (!response.ok) {

            usersMessage.textContent =
                users.message ||
                "Unable to load users.";

            return;
        }

        usersMessage.textContent =
            `${users.length} user(s) found`;


        if (users.length === 0) {

            usersContainer.innerHTML = `
                <div class="empty-state">
                    <h3>No Users Found</h3>
                </div>
            `;

            return;
        }


        users.forEach(user => {

            const card =
                document.createElement("div");

            card.className =
                "job-card";

            card.innerHTML = `

                <h2>${user.name}</h2>

                <p>
                    <strong>User ID:</strong>
                    ${user.id}
                </p>

                <p>
                    <strong>Email:</strong>
                    ${user.email}
                </p>

                <p>
                    <strong>Role:</strong>
                    ${user.role}
                </p>
            `;

            usersContainer.appendChild(card);
        });

    } catch (error) {

        console.error(error);

        usersMessage.textContent =
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


loadUsers();
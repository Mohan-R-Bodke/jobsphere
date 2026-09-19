const API_BASE_URL = "http://localhost:8080/api";


// ========================================
// LOGIN
// ========================================

const loginForm = document.getElementById("loginForm");

if (loginForm) {

    loginForm.addEventListener("submit", async function (event) {

        event.preventDefault();

        const email =
            document.getElementById("email").value.trim();

        const password =
            document.getElementById("password").value;

        const message =
            document.getElementById("loginMessage");

        message.textContent = "Logging in...";


        try {

            const response = await fetch(
                `${API_BASE_URL}/auth/login`,
                {
                    method: "POST",

                    headers: {
                        "Content-Type": "application/json"
                    },

                    body: JSON.stringify({
                        email: email,
                        password: password
                    })
                }
            );


            const data = await response.json();


            if (!response.ok) {

                message.textContent =
                    data.message ||
                    "Invalid email or password.";

                return;
            }


            // ========================================
            // STORE JWT AND USER INFORMATION
            // ========================================

            localStorage.setItem(
                "jwtToken",
                data.token
            );

            localStorage.setItem(
                "userId",
                data.user.id
            );

            localStorage.setItem(
                "userEmail",
                data.user.email
            );

            localStorage.setItem(
                "userRole",
                data.user.role
            );


            message.textContent =
                "Login successful. Redirecting...";


            // ========================================
            // REDIRECT ACCORDING TO ROLE
            // ========================================

            if (data.user.role === "CANDIDATE") {

                window.location.href =
                    "candidate-dashboard.html";

            } else if (data.user.role === "RECRUITER") {

                window.location.href =
                    "recruiter-dashboard.html";

            } else if (data.user.role === "ADMIN") {

                window.location.href =
                    "admin-dashboard.html";

            } else {

                window.location.href =
                    "index.html";
            }


        } catch (error) {

            console.error(error);

            message.textContent =
                "Unable to connect to the server.";
        }

    });
}


// ========================================
// REGISTER
// ========================================

const registerForm =
    document.getElementById("registerForm");

if (registerForm) {

    registerForm.addEventListener(
        "submit",
        async function (event) {

            event.preventDefault();


            const name =
                document.getElementById("name").value.trim();

            const email =
                document.getElementById("email").value.trim();

            const password =
                document.getElementById("password").value;

            const role =
                document.getElementById("role").value;


            const message =
                document.getElementById("registerMessage");


            message.textContent =
                "Creating your account...";


            try {

                const response = await fetch(
                    `${API_BASE_URL}/auth/register`,
                    {
                        method: "POST",

                        headers: {
                            "Content-Type": "application/json"
                        },

                        body: JSON.stringify({
                            name: name,
                            email: email,
                            password: password,
                            role: role
                        })
                    }
                );


                const data =
                    await response.json();


                if (!response.ok) {

                    message.textContent =
                        data.message ||
                        "Registration failed.";

                    return;
                }


                message.textContent =
                    "Registration successful. Redirecting to login...";


                setTimeout(function () {

                    window.location.href =
                        "login.html";

                }, 1200);


            } catch (error) {

                console.error(error);

                message.textContent =
                    "Unable to connect to the server.";
            }

        }
    );
}
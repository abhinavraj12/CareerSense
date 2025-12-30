// ===============================
// Load Logged-in User
// ===============================
async function loadUser() {
    try {
        const res = await fetch("/api/user/me", {
            credentials: "include"
        });

        // Not authenticated → redirect to login
        if (!res.ok) {
            window.location.replace("/index.html");
            return;
        }

        const user = await res.json();

        // Populate user info
        document.getElementById("user-name").textContent =
            user.name && user.name.trim() ? user.name : "User";

        const emailEl = document.getElementById("user-email");
        emailEl.textContent = user.email || "";
        emailEl.title = user.email || "";

        // Avatar handling
        const avatar = document.getElementById("profile-pic");

        // STEP 1: Always reset to default avatar
        avatar.src = "/img/default-avatar.png";

        // STEP 2: Use Google profile picture if available
        if (user.picture && user.picture.trim().length > 0) {
            avatar.src = user.picture;
        }

        // STEP 3: Fallback if image fails
        avatar.onerror = () => {
            avatar.src = "/img/default-avatar.png";
        };

    } catch (error) {
        // Any unexpected error → force logout
        window.location.replace("/index.html");
    }
}

// ===============================
// Logout
// ===============================
async function logout() {
    try {
        await fetch("/api/logout", {
            method: "POST",
            credentials: "include"
        });
    } finally {
        window.location.replace("/index.html");
    }
}

// ===============================
// Navigation
// ===============================
function goToResumeScore() {
    window.location.href = "/resume-score.html";
}

function goToJobMatch() {
    // 🔐 Spring Security will enforce authentication
    window.location.href = "/job-match.html";
}

// ===============================
// DOM Ready
// ===============================
document.addEventListener("DOMContentLoaded", () => {

    // Load user info
    loadUser();

    // Logout button
    const logoutBtn = document.getElementById("logout-btn");
    if (logoutBtn) {
        logoutBtn.addEventListener("click", logout);
    }

    // Resume Score card
    const resumeCard = document.getElementById("resume-score-card");
    if (resumeCard) {
        resumeCard.addEventListener("click", goToResumeScore);
    }

    // Job Matching card
    const jobMatchCard = document.getElementById("job-match-card");
    if (jobMatchCard) {
        jobMatchCard.addEventListener("click", goToJobMatch);
    }
});

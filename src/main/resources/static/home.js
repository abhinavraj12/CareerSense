async function loadUser() {
    const res = await fetch("/api/user/me", { credentials: "include" });

    if (!res.ok) {
        window.location.replace("/index.html");
        return;
    }

    const user = await res.json();

    document.getElementById("user-name").textContent = user.name || "User";
    document.getElementById("user-email").textContent = user.email || "";
    document.getElementById("user-email").title = user.email || "";

    const avatar = document.getElementById("profile-pic");

    // STEP 1: Always reset to default first
    avatar.src = "/img/default-avatar.png";

    // STEP 2: Apply Google profile pic only if valid
    if (user.picture && user.picture.trim().length > 0) {
        avatar.src = user.picture;
    }

    // STEP 3: Hard fallback if Google image fails
    avatar.onerror = () => {
        avatar.src = "/img/default-avatar.png";
    };
}

// Logout
document.getElementById("logout-btn").addEventListener("click", async () => {
    await fetch("/api/logout", {
        method: "POST",
        credentials: "include"
    });
    window.location.replace("/index.html");
});

// Load user once DOM is ready
document.addEventListener("DOMContentLoaded", loadUser);

document
    .getElementById("resume-score-card")
    .addEventListener("click", () => {
        window.location.href = "/resume-score.html";
    });


async function loadUser() {
    const res = await fetch("/api/user/me", { credentials: "include" });

    if (!res.ok) {
        window.location.replace("/index.html");
        return;
    }

    const user = await res.json();

    document.getElementById("user-name").textContent = user.name;
    document.getElementById("user-email").textContent = user.email;
    document.getElementById("user-email").title = user.email;

    const avatar = document.getElementById("profile-pic");

    // ✅ SINGLE assignment – no flicker, no duplicate
    avatar.src = user.picture && user.picture.trim().length > 0
        ? user.picture
        : "/img/default-avatar.png";
}

// Logout
document.getElementById("logout-btn").addEventListener("click", async () => {
    await fetch("/api/logout", { method: "POST", credentials: "include" });
    window.location.replace("/index.html");
});

loadUser();
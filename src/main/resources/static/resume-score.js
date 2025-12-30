const form = document.getElementById("resume-form");
const resultBox = document.getElementById("result");

form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const file = document.getElementById("resume-file").files[0];
    const experience = document.getElementById("experience").value;

    if (!file || experience === "") {
        alert("Please upload resume and enter experience");
        return;
    }

    const formData = new FormData();
    formData.append("resume", file);
    formData.append("experience", experience);

    try {
        const res = await fetch("/api/resume-score/analyze", {
            method: "POST",
            credentials: "include",
            body: formData
        });

        if (!res.ok) throw new Error();

        const data = await res.json();
        renderResult(data);

    } catch {
        alert("Something went wrong while analyzing your resume.");
    }
});

function renderResult(data) {
    resultBox.classList.remove("hidden");

    document.getElementById("score").textContent = data.score;
    document.getElementById("level").textContent = data.experienceLevel;
    document.getElementById("ats").textContent = data.atsFriendly ? "Yes ✅" : "No ❌";
    document.getElementById("roles").textContent =
        data.suggestedRoles.join(", ");

    renderList("matched-skills", data.matchedSkills);
    renderList("missing-skills", data.missingSkills);
    renderList("suggestions", data.ruleBasedSuggestions);

    document.getElementById("ai-suggestions").textContent =
        data.aiSuggestions || "AI suggestions unavailable.";
}

function renderList(id, items) {
    const ul = document.getElementById(id);
    ul.innerHTML = "";

    if (!items || items.length === 0) {
        ul.innerHTML = "<li>None</li>";
        return;
    }

    items.forEach(item => {
        const li = document.createElement("li");
        li.textContent = item;
        ul.appendChild(li);
    });
}

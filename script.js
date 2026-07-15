const usernameInput = document.getElementById("usernameInput");
const searchBtn = document.getElementById("searchBtn");

const message = document.getElementById("message");
const profileContainer = document.getElementById("profileContainer");

searchBtn.addEventListener("click", searchProfile);

usernameInput.addEventListener("keypress", function (event) {

    if (event.key === "Enter") {
        searchProfile();
    }

});

function searchProfile() {

    let username = usernameInput.value.trim();

    if (username === "") {

        message.innerText = "Please enter a GitHub username.";

        profileContainer.innerHTML = "";

        return;
    }

    message.innerText = "Loading profile...";

    profileContainer.innerHTML = "";

    fetch("http://localhost:8080/github?username=" + encodeURIComponent(username))

        .then(response => {

            if (!response.ok) {
                throw new Error("User not found");
            }

            return response.json();

        })

        .then(profile => {

            message.innerText = "";

            displayProfile(profile);

        })

        .catch(error => {

            message.innerText = "GitHub user not found.";

            profileContainer.innerHTML = "";

        });

}

function displayProfile(profile) {

    profileContainer.innerHTML = `

        <div class="profile-card">

            <div class="profile-top">

                <img
                    src="${profile.avatar_url}"
                    class="profile-image"
                    alt="Profile Image">

                <div class="profile-info">

                    <h2>${profile.name || "Name not available"}</h2>

                    <h3>@${profile.login}</h3>

                    <p>
                        ${profile.bio || "No bio available"}
                    </p>

                </div>

            </div>

            <div class="profile-stats">

                <div class="stat-card">

                    <h3>${profile.followers}</h3>

                    <p>Followers</p>

                </div>

                <div class="stat-card">

                    <h3>${profile.following}</h3>

                    <p>Following</p>

                </div>

                <div class="stat-card">

                    <h3>${profile.public_repos}</h3>

                    <p>Repositories</p>

                </div>

            </div>

            <div class="profile-details">

                <p>
                    📍 ${profile.location || "Location not available"}
                </p>

                <p>
                    🏢 ${profile.company || "Company not available"}
                </p>

                <p>
                    📅 Joined: ${new Date(profile.created_at).toDateString()}
                </p>

            </div>

            <a
                href="${profile.html_url}"
                target="_blank"
                class="profile-button">

                View GitHub Profile

            </a>

        </div>

    `;

}
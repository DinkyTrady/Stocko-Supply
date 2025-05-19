<script>
    let isLoggedIn = false;

    function toggleDropdown() {
        document.getElementById("dropdown").style.display =
            document.getElementById("dropdown").style.display === "block"
                ? "none"
                : "block";
    }

    function toggleLogin() {
        isLoggedIn = !isLoggedIn;
        const authAction = document.getElementById("authAction");

        authAction.textContent = isLoggedIn ? "Logout" : "Login";
        alert(isLoggedIn ? "You are now logged in!" : "You have logged out.");

        document.getElementById("dropdown").style.display = "none";
    }

    // Close dropdown if clicked outside
    window.onclick = function (event) {
        if (!event.target.matches('.profile-icon')) {
            const dropdown = document.getElementById("dropdown");
            if (dropdown.style.display === "block") {
                dropdown.style.display = "none";
            }
        }
    };
</script>

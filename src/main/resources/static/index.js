document.addEventListener('DOMContentLoaded', function() {
    const dropdown = document.getElementById('custom-select');
    const hiddenInput = document.getElementById('selectedOption');
    const errorTooltip = document.getElementById('dropdown-error');
    const form = document.getElementById('uploadForm');
    const confirmationMessage = document.getElementById('confirmation-message'); // New message div

    function selectOption(optionText) {
        const selectBtn = document.querySelector('.select-btn');
        selectBtn.textContent = optionText;
        hiddenInput.value = optionText;

        if (optionText !== "(Select One)") {
            errorTooltip.style.display = 'none';
            selectBtn.classList.remove('invalid');
        }
    }

    document.querySelectorAll('.submenu-content a').forEach(item => {
        item.addEventListener('click', function(event) {
            event.preventDefault();
            selectOption(event.target.textContent.trim());
        });
    });

    form.addEventListener('submit', async function(event) {
        event.preventDefault(); // Prevent default form submission

        // Check if a valid option is selected
        if (!hiddenInput.value || hiddenInput.value === "(Select One)") {
            errorTooltip.style.display = 'block';
            dropdown.classList.add('invalid');
            return; // Exit if validation fails
        }

        // Prepare form data for submission
        const formData = new FormData(form);

        try {
            const response = await fetch(form.action, {
                method: 'POST',
                body: formData,
            });

            if (response.ok) {
                const data = await response.json(); // Parse the JSON response
                confirmationMessage.textContent = `Weighted Relic Score: ${data.result}`; // Display the confirmation message
                confirmationMessage.style.display = 'block'; // Show the message
                confirmationMessage.style.color = 'green'; // Optional: Change text color to green
            } else {
                const errorData = await response.json(); // Parse the error response
                confirmationMessage.textContent = errorData.message || "An error occurred."; // Display error
                confirmationMessage.style.display = 'block'; // Show the message
                confirmationMessage.style.color = 'red'; // Optional: Change text color to red
            }
        } catch (error) {
            console.error('Error:', error);
            confirmationMessage.textContent = "An unexpected error occurred."; // Display unexpected error
            confirmationMessage.style.display = 'block'; // Show the message
            confirmationMessage.style.color = 'red'; // Optional: Change text color to red
        }
    });

    screenshot.onchange = evt => {
        const [file] = screenshot.files
        if (file) {
            preview.src = URL.createObjectURL(file)
            var img = document.getElementById('preview');
            img.style.visibility = 'visible';
        }
      }
});
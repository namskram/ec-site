document.addEventListener('DOMContentLoaded', function() {
    // Function to handle option selection
    function selectOption(optionText) {
        // Find the select button and replace its text with the chosen sub-option
        document.querySelector('.select-btn').textContent = optionText;
    }

    // Add event listeners to all sub-options (inside the submenu-content)
    document.querySelectorAll('.submenu-content a').forEach(item => {
        item.addEventListener('click', function(event) {
            // Prevent the default link behavior
            event.preventDefault();
            // Update the select button with the clicked sub-option's text
            selectOption(event.target.textContent);
        });
    });

    // Disable click for main options by adding an event listener that does nothing
    document.querySelectorAll('.dropdown-content > a').forEach(item => {
        item.addEventListener('click', function(event) {
            event.preventDefault(); // Prevent any action for main options
        });
    });
});

const dropdown = document.getElementById('custom-select');  // Your dropdown div
const hiddenInput = document.getElementById('selectedOption');  // Hidden input field

// Function to update the hidden input with the selected option
document.getElementById('uploadForm').addEventListener('submit', function (event) {
    hiddenInput.value = dropdown.textContent.trim();  // Set the hidden input to the dropdown's current text
});

// Form submission (for reference if you need it)
document.getElementById('uploadForm').onsubmit = async (e) => {
    e.preventDefault();

    const formData = new FormData(e.target);

    try {
        const response = await fetch('/upload', {
            method: 'POST',
            body: formData,
        });

        if (response.ok) {
            const result = await response.text();
            alert("File and selected option submitted successfully!");
        } else {
            alert("Submission failed.");
        }
    } catch (error) {
        console.error('Error:', error);
    }
};
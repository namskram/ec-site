document.addEventListener('DOMContentLoaded', function() {
    const dropdown = document.getElementById('custom-select');  // Dropdown div
    const hiddenInput = document.getElementById('selectedOption');  // Hidden input field
    const errorTooltip = document.getElementById('dropdown-error');  // Error tooltip div
    const form = document.getElementById('uploadForm');

    // Function to handle option selection
    function selectOption(optionText) {
        const selectBtn = document.querySelector('.select-btn');
        selectBtn.textContent = optionText;
        hiddenInput.value = optionText;  // Update hidden input with selected option

        // If a valid option is selected, hide the error tooltip and remove the invalid class
        if (optionText !== "(Select One)") {
            errorTooltip.style.display = 'none';  // Hide error message
            selectBtn.classList.remove('invalid');
        }
    }

    // Add event listeners to all sub-options (inside the submenu-content)
    document.querySelectorAll('.submenu-content a').forEach(item => {
        item.addEventListener('click', function(event) {
            event.preventDefault();  // Prevent default link behavior
            selectOption(event.target.textContent.trim());  // Select the option and update hidden input
        });
    });

    // Form submission validation
    form.addEventListener('submit', function(event) {
        // Check if a valid option is selected
        if (!hiddenInput.value || hiddenInput.value === "(Select One)") {
            event.preventDefault();  // Prevent form submission

            // Show the error tooltip and mark the dropdown as invalid
            errorTooltip.style.display = 'block';
            dropdown.classList.add('invalid');
        } else {
            // Optional: For debugging purposes, log the selected option
            console.log("Submitting with selected option:", hiddenInput.value);
        }
    });
});
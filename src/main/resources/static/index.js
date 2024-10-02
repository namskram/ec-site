document.addEventListener('DOMContentLoaded', function() {
    const dropdown = document.getElementById('custom-select');
    const hiddenInput = document.getElementById('selectedOption');
    const errorTooltip = document.getElementById('dropdown-error');

    function selectOption(optionText) {
        const selectBtn = document.querySelector('.select-btn');
        selectBtn.textContent = optionText;
        hiddenInput.value = optionText;

        // Log the hidden input value to the console for debugging
        console.log("Hidden Input Value: ", hiddenInput.value);

        // Hide the error tooltip if a valid option is selected
        if (optionText !== "(Select One)") {
            errorTooltip.style.display = 'none';
            selectBtn.classList.remove('invalid');
        }
    }

    // Attach click event listeners to options
    document.querySelectorAll('.submenu-content a').forEach(item => {
        item.addEventListener('click', function(event) {
            event.preventDefault();
            selectOption(event.target.textContent.trim());
        });
    });
});
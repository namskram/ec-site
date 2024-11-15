document.addEventListener('DOMContentLoaded', function() {
    const dropdown = document.getElementById('custom-select');
    const hiddenInput = document.getElementById('selectedOption');
    const errorTooltip = document.getElementById('dropdown-error');
    const form = document.getElementById('uploadForm');
    const confirmationMessage = document.getElementById('confirmation-message'); // New message div
    const charImage = document.getElementById('char-img')

    function selectOption(optionText) {
        // console.log("Selected option text:", optionText);  // Check if this logs correctly
        const selectBtn = document.querySelector('.select-btn');
        const charImgFile = "images/char-img-full/" + optionText.toLowerCase() + ".webp";
        // console.log("Character Image Path:", charImgFile);  // Log image path for debugging
        selectBtn.textContent = optionText;
        hiddenInput.value = optionText;
        charImage.src = charImgFile;
        charImage.style.height = '300px';
        charImage.style.width = 'auto';
        charImage.style.marginTop = '0';
        

        if (optionText !== "(Select One)") {
            errorTooltip.style.display = 'none';
            selectBtn.classList.remove('invalid');
        }
    }

    document.querySelectorAll('.submenu-content a').forEach(item => {
        item.addEventListener('click', function(event) {
            event.preventDefault();
            setTimeout(() => {
                const optionText = event.target.dataset.value; // Using dataset
                selectOption(optionText);
            }, 50); // Adjust delay if needed
        });
    });

    form.addEventListener('submit', async function(event) {
        event.preventDefault();
    
        if (!hiddenInput.value || hiddenInput.value === "(Select One)") {
            errorTooltip.style.display = 'block';
            dropdown.classList.add('invalid');
            return;
        }
    
        const formData = new FormData(form);
    
        try {
            const response = await fetch(form.action, {
                method: 'POST',
                body: formData,
            });
    
            if (response.ok) {
                const data = await response.json();
    
                // Update the confirmation message
                confirmationMessage.textContent = `Weighted Relic Score: ${data.result}`;
                confirmationMessage.style.display = 'block';
                confirmationMessage.style.color = `${data.color}`;
    
                // Update the calculation history
                addCalculationToHistory(data);
            } else {
                const errorData = await response.json();
                confirmationMessage.textContent = errorData.message || "An error occurred.";
                confirmationMessage.style.display = 'block';
                confirmationMessage.style.color = 'red';
            }
        } catch (error) {
            console.error('Error:', error);
            confirmationMessage.textContent = "An unexpected error occurred.";
            confirmationMessage.style.display = 'block';
            confirmationMessage.style.color = 'red';
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

// Function to add the new calculation to the sidebar
function addCalculationToHistory(calc) {
    const sidebar = document.getElementById("calculation-history");
    const listItem = document.createElement("div");
    listItem.innerHTML = `
        <strong>${calc.characterName}</strong> - Score: ${calc.result}<br>
        <img src="/uploads/${calc.filePath}" alt="${calc.filePath}" 
             style="width: 200px; height: auto; margin: 5px 0; border-radius: 5px; border: 1px solid #ccc;">
        <br>
        <small>${new Date(calc.date).toLocaleDateString()}</small>
        <br><br>
    `;
    sidebar.prepend(listItem); // Add to the top of the history
}

document.addEventListener("DOMContentLoaded", async function() {
    const form = document.getElementById("uploadForm");

    // Load recent calculations on page load
    await fetchRecentCalculations();

    form.addEventListener('submit', async function(event) {
        event.preventDefault();
        // Your existing form submission logic
    });
});

async function fetchRecentCalculations() {
    try {
        const response = await fetch("http://localhost:8080/api/calculations/recent");
        if (response.ok) {
            const calculations = await response.json();
            displayCalculations(calculations);
        } else {
            console.error("Failed to fetch recent calculations.");
        }
    } catch (error) {
        console.error("Error:", error);
    }
}

function displayCalculations(calculations) {
    const sidebar = document.getElementById("calculation-history");
    sidebar.innerHTML = "";  // Clear existing entries

    calculations.forEach(calc => {
        const listItem = document.createElement("div");
        listItem.classList.add("history-item"); // Match styling from previous setup

        listItem.innerHTML = `
            <strong>${calc.characterName}</strong> - Score: ${calc.result}<br>
            <img src="/uploads/${calc.filePath}" alt="Equipment screenshot" 
                 style="width: 200px; height: auto; margin: 5px 0; border-radius: 5px; border: 1px solid #ccc;">
            <br>
            <small>${new Date(calc.date).toLocaleDateString()}</small>
            <br><br>
        `;
        sidebar.appendChild(listItem);
    });
}
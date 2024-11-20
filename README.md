# Equipment Calculator Website

![Demo](https://github.com/namskram/ec-site/blob/main/uploads/demo.png)

## Overview

This equipment calculator uses the Tesseract OCR software to parse an image for data, and applies weighted values to the stats to calculate a min-max rating for the equipment.

## Usage

After you finish rolling your equipment, take a screenshot containing only the equipment level and stats.

![Screenshot](https://github.com/namskram/ec-site/blob/main/uploads/test-image.png)


Run the application and use the drop-down to select a character.

![Dropdown](https://github.com/namskram/ec-site/blob/main/uploads/select.png)

Then click the `Browse` button to upload the screenshot and click `Calculate`.

![Upload](https://github.com/namskram/ec-site/blob/main/uploads/upload.png)

After a few moments, a score will appear with a color-coordinated grade:

|        |       |          |
| ------ | ----- | -------- |
| <span style="color:red">Red</span> | <span style="color:red">0-5</span> | <span style="color:red">Terrible</span> |
| <span style="color:orange">Orange</span> | <span style="color:orange">5-9</span> | <span style="color:orange">Bad</span> |
| <span style="color:yellow">Yellow</span> | <span style="color:yellow">9-12</span> | <span style="color:yellow">Okay</span> |
| <span style="color:green">Green</span> | <span style="color:green">12-15</span> | <span style="color:green">Great</span> |
| <span style="color:cyan">Cyan</span> | <span style="color:cyan">15+</span> | <span style="color:cyan">Perfect</span> |

Finally, each score is saved in a sidebar list containing the calculation history.

![History](https://github.com/namskram/ec-site/blob/main/uploads/history.png)

---

Last update: November 20, 2024
## /vanilla_display create_video

Displays a video by showing frames from a folder. The image names must have the following format `n.jpg`, where `n` is a number from `1` to `frames`.

    /vanilla_display create_video <pos> <folder_path> <frames> [scale] [tpf] [nbt]

- pos: world coordinates (e.g. ~ ~ ~)
- folder_path: folder containing frame images (**MUST END IN /**)
- frames: total number of frames
- scale: optional float (default: 0.1)
- tpf: ticks per frame (default: 2)
- nbt: optional NBT data for the entity

---

## /vanilla_display create_image

Displays a static image from a file.

    /vanilla_display create_image <pos> <path> [scale] [nbt]

- pos: world coordinates
- path: image file path
- scale: optional float (default: 0.1)
- nbt: optional NBT data

---

## /vanilla_display create_source

Displays frames from a dynamic source (an image that changes regularly).

    /vanilla_display create_source <pos> <path> [scale] [tpf] [nbt]

- pos: world coordinates
- path: path to the frame source
- scale: optional float (default: 0.1)
- tpf: ticks per frame (default: 2)
- nbt: optional NBT data

### Screen recording example

Python script:

```python
import time
from PIL import ImageGrab, Image

# Path to save the screenshot
save_path = "screenshot.png"

# Time interval in seconds (e.g., 5 seconds)
interval = 1


def capture_screenshot():
    # Capture the entire screen
    screenshot = ImageGrab.grab()
    return screenshot


def save_screenshot(screenshot, path):
    # Save the screenshot to the specified path
    screenshot.save(path)


def main():
    while True:
        # Capture a screenshot
        screenshot = capture_screenshot()

        screenshot = screenshot.resize((256, 144), Image.LANCZOS)

        # Save the screenshot to the path
        save_screenshot(screenshot, save_path)

        # Wait for the next interval
        time.sleep(interval)


if __name__ == "__main__":
    main()

```

Command:

    /vanilla_display create_source ~ ~ ~ "/absolute/path/to/screenshot.png"

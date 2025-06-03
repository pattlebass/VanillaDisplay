A proof-of-concept server-side mod that uses Text Display entities to show images, videos, and dynamic sources to vanilla clients.

**Needs a resource pack.** ([Link](https://github.com/pattlebass/VanillaDisplay/releases/download/v1.0/VanillaDisplayFont.zip))

It's recommended to require it on the server side. See [https://minecraft.wiki/w/Server.properties](https://minecraft.wiki/w/Server.properties).

## Images
![A screenshot of 4 different images in an art gallery in Minecraft](https://cdn.modrinth.com/data/cached_images/283a556bc62d50663f5db709a8a24ec8827dbba6.jpeg)

## Videos
https://github.com/user-attachments/assets/2b872112-f564-4b59-84f3-458baaf357cc

## Video Sources
You can write scripts that output a source (such as your screen or webcam) to a file and then point Vanilla Display to it. - See [commands.md](https://github.com/pattlebass/VanillaDisplay/blob/main/docs/commands.md).

![A display that shows the screen, creating a feedback loop](https://cdn.modrinth.com/data/cached_images/81414c68d2c4eb8e80b0bc742122244e5acb64cc.jpeg)


## How to use

See [commands.md](https://github.com/pattlebass/VanillaDisplay/blob/main/docs/commands.md).

## Features

- No client-side mods required
- Display static images, looping videos, and dynamic sources
- Versatile - See [Video Sources](#Video-Sources)

## Limitations

- Supported format: JPG
- Max resolution: 256x256
- Max FPS: 20 (recommended: 10)
- Performance drops when using many displays at once

## Note

**Not ready for production.** If you’re a server admin interested in supporting or sponsoring further development, get in touch: [@pattlebass.bsky.social](https://bsky.app/profile/pattlebass.bsky.social).

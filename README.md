# [Let's Do] Every Compat

[![Modrinth](https://img.shields.io/badge/Modrinth-Available-00AF5C?logo=modrinth&logoColor=white)](https://modrinth.com/mod/letsdoeverycompat)
[![CurseForge](https://img.shields.io/badge/CurseForge-Available-F16436?logo=curseforge&logoColor=white)](https://www.curseforge.com/minecraft/mc-mods/lets-do-every-compat)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](LICENSE)

An [Every Compat](https://modrinth.com/mod/every-compat) addon that dynamically generates wood variants for furniture and decorative blocks from the **[Let's Do Collection]**.

## Requirements

* **[Every Compat](https://modrinth.com/mod/every-compat)**
* **[Moonlight Lib](https://modrinth.com/mod/moonlight)**
* *Any combination of supported [Let's Do] mods below.*

## Features

Generates wood variants for any of the following installed mods:

| Mod | Cloned Variants |
|---|---|
| **[[Let's Do] Furniture](https://modrinth.com/mod/lets-do-furniture)** | Bench, desk, desk chair, mirror, shutter, cabinet, clock, grandfather clock, dresser, wardrobe *(+ bamboo, crimson, warped)* |
| **[[Let's Do] Candlelight](https://modrinth.com/mod/lets-do-candlelight-farmcharm-compat)** | Cabinet, drawer, table, chair, shelf, big table |
| **[[Let's Do] Vinery](https://modrinth.com/mod/lets-do-vinery)** | Wine racks (small/mid/big), lattice, barrel + `{shared items}` |
| **[[Let's Do] Beachparty](https://modrinth.com/mod/lets-do-beachparty)** | Bar, bar stool + `{chair, table, cabinet}` |
| **[[Let's Do] Meadow](https://modrinth.com/mod/lets-do-meadow)** | Cheese rack, wall cabinet, bench |
| **[[Let's Do] Hearth & Timber](https://modrinth.com/mod/lets-do-hearth-timber)** | Beam, board, pillar, railing, support, shingles (+ slab, stairs), window (+ casing, pane) |

> **Note on Shared Items (`cabinet`, `drawer`, `table`, `chair`, `shelf`, `big table`):**  
> Priority order is **Candlelight $\rightarrow$ Vinery $\rightarrow$ Beachparty**. Items in `{shared items}` are registered by Candlelight first; if absent, Vinery provides them. Beachparty only provides `chair`, `table`, and `cabinet` if both Candlelight and Vinery are uninstalled.

## License

This project is licensed under the [GNU General Public License v3.0](LICENSE).
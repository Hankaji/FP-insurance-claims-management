# Insurance Claims Management System 
An insurance claims management console application project built with java for Further Programming course

## Features
- Easy to uses
- Customizable color scheme for the appication through json
- Friendly TUI
- Fast and responsive
- Switch between panels with ease

## Dependencies
- [Lanterna3](https://github.com/mabe02/lanterna)
- [Commons IO](https://commons.apache.org/proper/commons-io/)
- [Gson](https://github.com/google/gson)

## Requirement
- Java 21 (Runtime)
  - *Only java 21 has been tested as of now, older or newer version might work however is not guaranteed*
- Maven 3.9.6

## Themes (Only color is supported as of now)
- Color:
  - Change the hex color in side file config.json to any color of your choices. Reload the app to see the changes
  - Current color is heavily inspired from Tokyo-Night theme

## Installation guide

### Run from source

First clone this git repository:
```bash
$ git clone https://github.com/Hankaji/FP-insurance-claims-management.git
```

Install dependencies
```bash
$ mvn install
```

Run the source file
```bash
$ mvn exec:java
```

## Usage

| Keybinds  | Actions                           |
| --------  | --------------------------------: |
| `Q`       | Quit application                  |
| `1 - 4`   | Switch panels/view                |
| `↑` `↓`   | Select row                        |
| `i`       | Display row info                  |
| `p`       | Populate data with default values |
| `h`       | Display help                      |
| `a`       | Add data                          |
| `d`       | Delete data                       |
| `e`       | Edit data                         |
| `s`       | Set status to `DONE`              |

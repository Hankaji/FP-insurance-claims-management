# Insurance Claims Management System 
An insurance Claim management console application project built with java for Further Programming course

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

First clone this git repository:
```bash
$ git clone https://github.com/Hankaji/FP-insurance-Claim-management.git
```

Then `cd` into the project folder

```bash
$ cd ./FP-insurance-Claim-management/
```

Install dependencies
```bash
$ mvn clean install  # With maven installed
$ ./mvnw clean install  # Without maven installed (Use ./mvnw.cmd if you're on Window)
```

### Run source code

```bash
$ mvn exec:java # With maven installed
$ ./mvnw exec:java # Without maven installed (Use ./mvnw.cmd if you're on Window)
```

### Build executeable jar

First bundle source code using maven assembly

```bash
$ mvn clean compile assembly:single # With maven installed
$ ./mvnw clean compile assembly:single # Without maven installed (Use ./mvnw.cmd if you're on Window)
```

Run executable jar

```bash
$ cd ./target
$ java -jar ./insurance-Claim-management-1.0-jar-with-dependencies.jar
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

## Note

For best experience, it is suggested to run this program in a separated terminal window and use terminal that support 256bit colors. Please also make terminal size as big as possible (or better, full screen)

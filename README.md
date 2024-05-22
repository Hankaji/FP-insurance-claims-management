# Insurance Claims Management System 
An insurance claims management console application project built with java for Further Programming course

## Features
- Easy to uses
- Friendly UI
- Switch between views

## Dependencies
- [JavaFX](https://openjfx.io/)
- [JavaFX - FXML](https://openjfx.io/)
- [MaterialFX](https://github.com/palexdev/MaterialFX)
- [Hibernate](https://hibernate.org/)
- [Postgresql](https://jdbc.postgresql.org/)
- [Bcrypt](https://github.com/patrickfav/bcrypt)

## Requirement
- Java 21 (Runtime)
  - *Only java 21 has been tested as of now, older or newer version might work however is not guaranteed*
- Maven 3.9.6

## Installation guide

First clone this git repository:
```bash
$ git clone https://github.com/Hankaji/FP-insurance-claims-management.git
```

Then `cd` into the project folder

```bash
$ cd ./FP-insurance-claims-management/
```

Install dependencies
```bash
$ mvn clean install  # With maven installed
$ ./mvnw clean install  # Without maven installed (Use ./mvnw.cmd if you're on Window)
```

### Run source code

```bash
$ mvn clean javafx:run # With maven installed
$ ./mvnw clean javafx:run # Without maven installed (Use ./mvnw.cmd if you're on Window)
```

## Usage

There are 5 Roles ADMIN, DEPENDENT, POLICY_HOLDER, POLICY_OWNER, PROVIDER, and different accounts can be associated with different roles.

Different Roles give differents actions to the objects data, with admin being the highest.

### Fake accounts

```
--email : password--
admin : admin (ADMIN)
pope@gmail.com : qwertyuiop1 (DEPENDENT)
mdumingo0@csmonitor.com : password123 (PROVIDER)
teaglestone3@surveymonkey.com : sondepchai10 (POLICY_HOLDER)
jgommey25@geocities.jp : KFBNASJBASLJBF (OWNER)
```

### Functionalities

#### Account

Create account with ease as well as logged in using created accounts to use the application.

Passwords are encypted to provide user a more secured protection without the fear of losing data.

Moreover, user logged into the application will have their account stored in a local session, meaning that they won't have to spend through the hassle retyping their email and password again.

Users can also logout of the application using the 'logout' button in the side bar.

#### Claim

View all claims of DEPENDENT and HOLDER, can also delete it and view detailed version of it.

For PROVIDER, displayed claim will be claims whose provider's company is the same as the logged in provider

#### Dashboard

Display current card detail to the HOLDER and DEPENDENT

#### Dependents

Display all the dependents that the HOLDER is currently having. If the logged account is a DEPENDENT, this will instead not displaying dependent list but ask them to choose a holder they want to become dependent of, or if not yet have a holder they can also become the holder.

## Note

For best experience, it is suggested to run this program in a separated terminal window and use terminal that support 256bit colors. Please also make terminal size as big as possible (or better, full screen)

## Video Link

https://rmiteduau.sharepoint.com/:v:/s/vruh/Ec8iHQf4nTdKgqd9rLFDU6MBCadcMZfNZxBjx45-NknHVw?e=ExesIZ 

## Contribution
- Hoang Thai Phuc (s3978081): 4.5
- Le Thien Son (s3977955): 4.2
- Nguyen Vo Truong Toan (s3979056): 1.8
- Tran Nguyen Anh Minh (s3979367): 1.5

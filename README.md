<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

* This project was made with SpringBoot 2.3.6.RELEASE.
* Using openjdk 14.
* Using Spring Data Jpa connect to a Postgres database.
* Also using JWT with Spring Security for user management.
* Swagger for the documentation.


<!-- GETTING STARTED -->
## Getting Started

Instructions for setting up the project locally or deploy

### Prerequisites

You need the following tools to use the software and how to install them.
* OpenJDK 14
* Maven 3.+
* Docker for deploy

### Installation

1. Clone the repo
   ```sh
   git clone {{GIT_REPO_URL}}
   ```
2. Access to folder project
   ```sh
   cd movie-rent/
   ```
3. Add the configurations to `src\main\resources\application.properties`:
   ```JS
   #DATABASE CONFIGURATION
   spring.datasource.username=YOUR DB USERNAME
   spring.datasource.password=PASSWORD FOR DB USERNAME
   spring.datasource.url=jdbc:postgresql://IP:PORT/DATABASE_NAME
   
   #MAIL CONFIGURATION
   spring.mail.username=YOUR MAIL USERNAME
   spring.mail.password=PASSWORD FOR MAIL USERNAME
   spring.mail.host=HOST OF SMTP SERVER - Can be smtp.gmail.com
   spring.mail.port=PORT OF SMTP SERVER - Can be 465 for gmail ssl
   
   #OPTIONAL
   server.servlet.context-path=PATH FOR PUBLISH APP
   ```
4. Executing the sql instructions shared by email
   ```sh
   schema-movie-rent-instructions.sql
   ```
5. Review or create your owner admin users by default into  `src\main\resources\import.sql`
   ```sh
   IMPORTANT: The password for users must be a string encode with B-CRYPT, 
   You must have add password string before continue with the configurations.
   ```
6. Compile the project
   ```sh
   mvn clean package
   or
   mvn clean compile package
   ```
7. Run the project in your local machine
   ```sh
   java -jar target/movie-rent-1.0.0-RELEASE.jar
   ```
8. Deploy in Docker
   * Build image
     ```sh
     docker build -tag movie-rent:1.0.0-RELEASE .
     ```
   * Run image 
     ```sh
     docker run -d -p 8080:8080 -t movie-rent:1.0.0-RELEASE
     ```
     NOTE:you change the output port by any!
9. Access to project deployed
   ```sh
   {URL:PORT}/movie-rent
   ```
<!-- USAGE EXAMPLES -->
## Usage

You can read the RESTFull API documentation in:
{URL_DEPLOYED}/swagger-ui.html

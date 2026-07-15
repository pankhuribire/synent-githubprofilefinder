# 🔍 GitHub Profile Finder

A responsive web application that allows users to search for GitHub profiles and view their public profile information using the GitHub REST API.

## 📌 Project Overview

The GitHub Profile Finder allows users to enter any valid GitHub username and retrieve profile information directly from GitHub.

The application dynamically fetches data from the GitHub API and displays the user's profile details in a clean and user-friendly interface.

The project also includes a Java backend server that handles the application request flow and demonstrates backend integration with the frontend.

## ✨ Features

- 🔍 Search GitHub users by username
- 👤 Display GitHub profile information
- 🖼️ Display profile avatar
- 📊 Show public repositories
- 👥 Show followers and following
- 📝 Display user bio
- 🔗 Open the user's GitHub profile
- ⏳ Loading state while fetching data
- ⚠️ Error handling for invalid usernames
- 📱 Responsive user interface

## 🛠️ Technologies Used

### Frontend

- HTML5
- CSS3
- JavaScript
- Fetch API

### Backend

- Java
- Java HTTP Server
- HTTP request handling

### API

- GitHub REST API

## 🔄 How It Works

1. The user enters a GitHub username.
2. The application sends a request to the backend.
3. The backend communicates with the GitHub API.
4. GitHub profile data is received.
5. The profile information is displayed dynamically on the webpage.
6. If the username does not exist, an appropriate error message is displayed.

## 📊 Profile Information Displayed

- Profile picture
- Name
- Username
- Bio
- Location
- Public repositories
- Followers
- Following
- GitHub profile link

## ⚙️ API Integration

The project uses the GitHub REST API to retrieve public user profile information.

The application uses JavaScript's Fetch API to communicate with the backend and dynamically update the user interface with the received data.

## 🚀 How to Run the Project

### Frontend

1. Clone or download this repository.
2. Open the project folder in VS Code.
3. Start the frontend using Live Server.

### Backend

1. Open the backend folder in the terminal.
2. Compile the Java server:

```bash
javac GitHubProfileServer.java

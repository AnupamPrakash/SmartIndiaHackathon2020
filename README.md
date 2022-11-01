# News Eye
<img width="625" alt="news-eye" src="https://user-images.githubusercontent.com/31325068/199163739-d984cf97-910f-49a4-9d93-23d3e3182ddf.png">

# Problem Statement
Designing a mobile information application for "The Department of Financial Services (DFS)” to allow the broadcast of financial information by senior officials, and reporting of issues by all other officials to respective authorities."
# Inspiration
Inspiration for this project came from the Smart India Hackathon 2020 problem statement. The idea behind this project was to build a mobile application which could enable concerned officials about financial news updates.
# Working Model
The working model of this project is divided into five sections.
- Maintaining user details and authentication.
- Fetching current financial news as soon as they bubble up.
- Managing news articles and storing them on the server.
- Broadcasting news articles to concerned people.
- Maintaining shared and favourite articles as per user for a personalised experience.
# Implementation
The implementation of this project is done using Android, NewsAPI and Google Firebase. 
Following is the explanation at each stage of how the implementation is done.
## Maintaining user details and authentication
For maintaining user details and authentication, we have used Google Firebase as our server backend, For the front end, we have used native Android. For authentication, we are providing email-password sign-in using Firebase Authentication. For user details, we are storing the data in the NoSQL database of the Firebase Database.
## Fetching current financial news as soon as they bubble up.
For fetching current financial news on the go, we are using an Open Source API, `NewsAPI` which provides the latest news bubbling over the surface and gets automatically updated every 30 minutes. The API provides data in JSON format and the data is then framed into respective classes and objects for our use. For Asynchronous data fetching and handling, we use HTTP Client “Volley” for Android.
## Managing news articles and storing them on the server
As soon as articles are fetched into the Android system and framed accordingly, it is listed in the user app and the user gets notified about the incoming news as an alert notification. Using that notification, the user can access the article and mark it as a favourite or can broadcast it as per required. The actions performed by the user over the article are stored as an entry to our real-time backend server and can be further used for providing a personalised experience.
## Broadcasting news articles to concerned people.
For broadcasting news articles, currently, we are using Android’s implicit sharing intent. Using this intent, the concerned user can share the article with the recipient by various sharing means. The shared articles are stored on the server for future reference. In future updates, we will try to implement in-app article broadcasting.
## Maintaining shared and favourite articles as per user for a personalised experience
Personalised experience in the app increases user involvement and user reliability. Keeping this in mind, we tried to provide a personalised experience to the user by maintaining its profile with a display picture, listing of shared articles and also a facility to mark articles as favourites such that they can be accessed in future also. All profile maintenance is done in our backend server with Firebase Realtime Database.
# Future Updates
The current version of the app is just an attempt to create a prototype in the limited timeframe of a hackathon.
There are a lot of further updates possible and we are looking forward to achieving those. Some of the future updates we will surely be working on are:
- Providing an organisational authentication feature such that no person from outside the department can get into the system.
- We are providing an in-app broadcasting feature for better security and end-to-end user coverage.
- Providing a better news fetching mechanism to getting track of particular topics and the ability to follow a trend.

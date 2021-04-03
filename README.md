This applications demonstrate consuming multiple rest API's asynchronously using HttpClient API and collecting all API responses to improve response time of overall transaction.

For user id 1, get the user details, all the post made by the user and all the albums belonging to the user.

- API for user personal detail - https://jsonplaceholder.typicode.com/users/1 : Takes 10s to process
- API for all post made by the user - https://jsonplaceholder.typicode.com/users/1/posts : Takes 5s to process
- API for all albums of user - https://jsonplaceholder.typicode.com/users/1/albums : Takes 2s to process.


fetchUserInfo() method takes 10s to process entire transaction.
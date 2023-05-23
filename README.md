# Contact-Management

This project is for contact management.

<h1>Tech Used</h1>

* Spring boot and hibernate<br>
* MySql <br>
* Java 8 <br>
* Docker <br>

<h1> Functionalities of the Project </h1>

* Implemented Jwt token generation, validation. <br>
* Create contact "contact number should be unique". <br>
* Get all contact details list and have search functionality on first name, last name and email. <br>
* Delete contact details (Its a soft delete as data is crucial). <br>
* Update contact details.

<h1> Routes </h1>

* For JWT Token Generation (unsecured) : "http://localhost:9000/token" <br>
  Request type : "POST" <br>
  Request body : {
  "password": "1234",
  "username": "jatin-rawat"
  }<br><br>

* For create contact (secured) : "http://localhost:9000/contact/import"  <br>
  Request type : "POST" <br>
  Request body : {
  "firstName": "Mohit",
  "lastName" : "Rathi",
  "phoneNumber" : "8826017410",
  "email" : "jatin.rawat@gmail.com"
  }<br><br>

* For Delete contact (secured) : "http://localhost:9000/contact/{mobileNumber}"  <br>
  Request type : "DELETE" <br><br>

* For Contact List (secured) : "http://localhost:9000/contact/list" <br>
  Request type : "GET" <br>
  Query parameters : pageNumber : not required<br>
                     pageSize : not required<br>
                     search : not required<br>
                     sort : not required<br>
                     sortDir : not required<br><br>

* For updating contact (secured) : "http://localhost:9000/contact/{mobileNumber}" <br>
  Request type : "PUT" <br>
  Request body : {
  "firstName": "Mohit",
  "lastName" : "Rathi",
  "phoneNumber" : "8826017410",
  "email" : "jatin.rawat@gmail.com"
  }<br><br>
  
 
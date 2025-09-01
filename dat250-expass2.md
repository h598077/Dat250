I have created a simple REST API (CRUD interface) for the domain model with Spring.

**technical problems that you encountered during installation and how you resolved:**

I encountered the “nested depth 1001” error in user, poll  and voteoption, poll. It was later fixed with annotation @JsonBackReference and @JsonManagedReference. 

I had problems with bruno http requests and resolved them with using correct params for the spesific requests.

I had problems with some of the JUnit tests, did solve most of them by correcting the requests.

Had problems with permission for github action, resolved it with a quick google search to add executable permission and working directory


**any pending issues with this assignment that you did not manage to solve:**

My last JUnit test is interfering with one of the other tests

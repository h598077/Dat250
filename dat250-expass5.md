Implemented caching of voteCounts using Jedis, the Java client for Redis.

Can be seen in http://localhost:8080/polls/0/votes for example when we create a new poll

<img width="426" height="227" alt="{33F5B55B-5EBD-46A9-8B4E-0BAC4690B090}" src="https://github.com/user-attachments/assets/cdcccd76-a2ca-4081-beb9-0075057da37d" />

**Technical problems that you encountered during installation  and how you resolved**

Problem with installing redis for windows, fixed it by installing Memurai for windows.


**Any pending issues with this assignment which you did not manage to solve**

Just that the cached version does not delete the alleredy voted user if he votes again.

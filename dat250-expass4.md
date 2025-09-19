**technical problems that you encountered during installation and use of Java Persistence Architecture (JPA) and how you resolved**

I had problems with annotation like i needed the @Entity for each class i was using with an   @Id and automatic generated value like this @GeneratedValue(strategy = GenerationType.IDENTITY).

I had problems with linking FK and PK with @ManyToOne and joining columns with  @JoinColumn, @OneToMany. Just by watching the picture from md1 it was much easier.

I had problems with using reserved names like user and needed to use @Table(name = "users") to fix that.


**a link to your code for experiment 2 above. Make sure the included test case passes!**

https://github.com/h598077/Dat250 


**an explanation of how you inspected the database tables and what tables were created. For the latter, you may provide screenshots.**

By adding some lines in the application.properties

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE

and some other properties in the test itself

 .property("hibernate.show_sql", "true")
                .property("hibernate.format_sql", "true")
                .property("hibernate.use_sql_comments", "true")

Then we get the whole  table

<img width="616" height="650" alt="{F13E3DF1-F352-44EE-A51F-5C501332861A}" src="https://github.com/user-attachments/assets/753b7700-aa2e-46a0-ad18-f126bccd616f" />


**any pending issues with this assignment that you did not manage to solve**

None

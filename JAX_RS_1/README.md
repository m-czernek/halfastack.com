# Enter into the RESTful world!

The following is a project focused on exploring the JAX-RX specification. I have used the following technologies:

* RESTEasy and RESTEasy client
* Jackson
* AssertJ

In addition, I have used technologies from the previous project, halfastack.com/JPA_I, namely Hibernate and 
frontend technologies though that is not the main focus of this project. 

This project demonstrates the ability to expose methods on specified endpoints, as well as consuming the methods using 
the Resteasy client. 

## Target Audience

The target audience is you. I have no requirements, everything here is free and readily available to everyone. 
If you cannot understand the code here, or you'd prefer some deeper knowledge on the backround of JAX-RS, visit:

* [[REST services with JAX-RS] Enter the RESTFul world](https://www.halfastack.com/rest-services-with-jax-rs-enter-the-restful-world/)

I will be very glad for any comments and critique that you have.

### Prerequisites

You will need:

1. An application server
2. Maven

Note that though this project is extending my halfastack.com/JPA_I project, you do not need a separate database server. I have
bundled an in-memory server inside of the app simply because it is not the focus of this project to work with a DB server. 
If that interests you, follow my [previous](https://www.halfastack.com/jpa-setup-deploying-a-database-for-jee-environment/)
article.

### Deploying

In a command line, change into this project such that when you issue `ls` (or `DIR` on Windows), you see `pom.xml` among other files.
Then, issue the following command:

```
mvn clean package
```

With that, you will create the `target` directory. Copy the resulting `JPA_I.war` into your application server's deploy directory. At this point, you should be able to visit, for example, the following endpoint:

`http://localhost:8080/JAX_RS_1/api/authors/getAuthors`

## Authors

* **Marek Czernek** - halfastack author, find me at www.halfastack.com

# Get to know JPA and Hibernate

The following is a project focused on a simple JPA and Hibernate application. It is a very simple mock library application
that utilizes:

* CDI
* EJB
* Hibernate and JPA
* JPA annotations and mappings
* SQL

## Target Audience

The target audience is you. I have no requirements, everything here is free and readily available to everyone. 
If you cannot understand code here, you can read my articles on JPA:

* [JPA Introduction I](https://www.halfastack.com/java-ee-jpa-introduction-i/)
* [JPA Introduction II](https://www.halfastack.com/java-ee-jpa-introduction-ii/)

I will be very glad for any comments that you have.

## Getting Started

### Prerequisites

You will need:

1. A database server
2. An application server
3. Maven

The precise setup for step 1. is described [here](https://www.halfastack.com/jpa-setup-deploying-a-database-for-jee-environment/). 
If you have no application server or Maven, check out my [Getting Started](https://www.halfastack.com/java-ee-series-getting-started/) article.

### Deploying

In a command line, change into this project such that when you issue `ls` (or `DIR` on Windows), you see `pom.xml` among other files.
Then, issue the following command:

```
mvn clean package
```

With that, you will create the `target` directory. Copy the resulting `JPA_I.war` into your application server's deploy directory
and access `localhost:8080/JPA_I` (if your server runs on a different address or port, please change accordingly).

## Authors

* **Marek Czernek** - [halfastack author](www.halfastack.com)

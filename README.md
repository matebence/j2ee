## Hibernate (JPA/JTA)

- Java Persistence API (aka JPA) its part of data manipulation
- Persistence refers to information that continues to exist even after the process or application that created it is no longer running

Options for storing data:
- SQL
- NoSQL
- File systems
- Disk Storage

JPA is a specification and its implemented by Hibernate OpenJPA etc ...

Object Relational Mapping (aka ORM)
- ORM connects relation database and Java Application together
- Its like a bridge
- Data is organized in a relational database via one or more tables with columns and unique id
- Rows in the table represents instances of that type of data
- Two tables are linked together via FK

- Databases uses tables and columns but java application uses objects and classes
- The Java persistence API (JPA) is a Java specification for accessing, persisting and managing data between Java objects and a relational database
- JPA is a abstractaction on JDBC that's makes eazy to map objects to relational databases
- The mapping between Java Objects and database tables is defined via persistence metadata

JPA configuration can be done via:
- annotations (recommended)
- persistence.xml file (legacy)

The data can be queried:
- Criteria API
- Native Query (Named native quries)
- JPQL/HQL (Named queries)


> #### JPA configuration

```properties
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/hibernate_examples
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=hibernate
spring.datasource.password=examples

spring.jpa.properties.javax.persistence.schema-generation.scripts.action=drop-and-create

spring.jpa.properties.javax.persistence.schema-generation.scripts.create-target=create.sql
spring.jpa.properties.javax.persistence.schema-generation.scripts.drop-target=drop.sql

spring.jpa.properties.javax.persistence.schema-generation.scripts.create-source=metadata
spring.jpa.properties.javax.persistence.schema-generation.scripts.drop-source=metadata
```


> #### Credential entity

```java
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="CREDENTIAL")
public class Credential {

    @Id
    @Getter
    @Setter
    @Column(name="CREDENTIAL_ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long credentialId;

    @Getter
    @Setter
    @Column(name="USERNAME")
    private String username;

    @Getter
    @Setter
    @Column(name="PASSWORD")
    private String password;
}
```


> #### Persisting objects

```java
    @Transactional
    public void persist(Credential credential) {
        credentialRepository.save(credential);
    }
```

```bash
curl -XPOST -H 'Content-type: application/json' -d '{"username":"John","password":"Doe"}' 'http://localhost:8080/credential-resource/api/persist'
```


> #### Reading objects

```java
    @Transactional
    public Optional<Credential> search(Long id) {
        return credentialRepository.findById(id);
    }
```

```bash
curl -XPOST -H 'Content-type: application/json' 'http://localhost:8080/credential-resource/api/search/2'
```


> #### Updating objects

```java
    @Transactional
    public void update(Credential credential, String username, String password) {
        credential.setUsername(username);
        credential.setPassword(password);
        credentialRepository.save(credential);
    }
```

```bash
curl -XPUT -H 'Content-type: application/json' -d '{"username":"Doe","password":"John"}' 'http://localhost:8080/credential-resource/api/update/2'
```


> #### Deleting objects

```java
    @Transactional
    public void delete(Long id) {
        credentialRepository.deleteById(id);
    }
```

```bash
curl -XDELETE -H 'Content-type: application/json' 'http://localhost:8080/credential-resource/api/delete/2'
```


> #### Transaction management

Entity life cycle:
- Persisting
- Updateting
- Removing
- Loading

Entity states:
- Managed
- Detached

States by example:
- When an entity is created with the new keyword, then it is a regular Java Objects
- Before it is persistet it is in Transient or new State
- Once its persistet with the EntityManager, then its in the managed state
- An entity is also in managed state when its loaded from the database
- When a entity is in a managed state under the control of the EntityManager any updates made to the entity using its setter methods automatically synchronizes with the database (method call flush)
- An entity can be in the remove state if its marked for deletion via the EntityManager remove method
- An objects becomes detached when its removed, flushed or commiteted. Once an entity is detached it is going to live in memory until the garbage collector removes from memory

API action table:

|Initial State 	|Action 	    |End State 	  |
|---------------|---------------|-------------|
|None    	    |get()	 	    |Persistent   |
|None  		    |load()	 	    |Persistent   |
|Transient      |save	        |Persistent   |
|Transient      |saveOrUpdate() |Persistent   |
|Persistent     |delete() 		|Removed      |
|Detached       |update() 		|Persistent   |
|Detached       |saveOrUpdate() |Persistent   |
|Persistent     |evict() 		|Detached     |

- Transactions helps us to mange our application (to make sure all the data is consistent)
- JTA stands for Java Transaction API
- We use the @Transactional annoation (class or method level)


> #### Relationship Mapping in JPA

Directions:
```
(Entity A) --Unidirectional--> B(Entity B)
```
```
(Entity A) <--Bidirectional--> B(Entity B)
```

Cardinality:

|Type 			|Strategy 	|Description 																			  |
|---------------|-----------|-----------------------------------------------------------------------------------------|
|@OneToOne      |JoinColumn |Each entity instance is related to a single instance of another entity     			  |
|@OneToMany     |JoinColumn |An entity instance can be related to multiple instances of the other entities     		  |
|@ManyToOne     |JoinTable 	|Multiple instances of an entity can be related to a single instance of the other entity  |
|@ManyToMany    |JoinTable 	|The entity instances can be related to multiple instances of each other     			  |

JPA Cascade Type

|Type 		|Description 																			  																				    |
|-----------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|ALL      	|Propagates all operations — including Hibernate-specific ones — from a parent to a child entity   			  																|
|PERSIST    |Propagates the persist operation from a parent to a child entity    			  																							|
|MERGE      |The merge operation copies the state of the given object onto the persistent object with the same identifier     			  											    |
|REMOVE     |propagates the remove operation from parent to child entity. Similar to JPA's CascadeType.REMOVE, we have CascadeType.DELETE, which is specific to Hibernate   			|
|REFRESH    |Refresh operations reread the value of a given instance from the database     			  																					|
|DETACH     |The detach operation removes the entity from the persistent context. When we use CascadeType.DETACH, the child entity will also get removed from the persistent context    |

Hibernate Cascade Type

|Type 			 |Description 																			  			  |
|----------------|----------------------------------------------------------------------------------------------------|
|REPLICATE       |The replicate operation is used when we have more than one data source and we want the data in sync |
|SAVE_UPDATE     |Propagates the same operation to the associated child entity.     			  			          |
|LOCK      	     |Reattaches the entity and its associated child entity with the persistent context again  			  |


> #### @OneToOne Unidirectional

```java
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="CREDENTIAL")
public class Credential {

    @Id
    @Getter
    @Setter
    @Column(name="CREDENTIAL_ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long credentialId;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="USER_ID")
    public User user;
    .
    .
    .
```

```java
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="FINANCES_USER")
public class User {

    @Id
    @Getter
    @Setter
    @Column(name = "USER_ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long userId;
    .
    .
    .
```

```bash
curl -XPOST -H 'Content-type: application/json' -d '{"username":"John","password":"Doe", "user": {"firstName": "John", "lastName": "Doe"}}' 'http://localhost:8080/credential-resource/api/persist'

curl -XPOST -H 'Content-type: application/json' 'http://localhost:8080/credential-resource/api/search/1'
```


> #### @OneToOne Bidirectional

```java
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="CREDENTIAL")
public class Credential {

    @Id
    @Getter
    @Setter
    @Column(name="CREDENTIAL_ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long credentialId;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="USER_ID")
    public User user;
    .
    .
    .
```

```java
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="FINANCES_USER")
public class User {

    @Id
    @Getter
    @Setter
    @Column(name = "USER_ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long userId;

    @Getter
    @Setter
    @OneToOne(mappedBy="user")
    private Credential credential;
    .
    .
    .
```

```bash
curl -XPOST -H 'Content-type: application/json' -d '{"emailAddress": "john.doe@gmail.com", "credential": {"username": "johny", "password": "test"}}' 'http://localhost:8080/user-resource/api/persist'

curl -XPOST -H 'Content-type: application/json' 'http://localhost:8080/user-resource/api/search/1'
```

> #### @OneToMany Unidirectional

```java
```


> #### @OneToMany Bidirectional

```java
```


> #### @ManyToOne Unidirectional

```java
```


> #### @ManyToOne Bidirectional

```java
```


> #### @ManyToMany Unidirectional

```java
```


> #### @ManyToMany Bidirectional

```java
```

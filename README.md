## Hibernate (JPA/JTA)

- Java Persistence API (aka JPA) its part of data manipulation
- Persistence refers to information that continues to exist even after the process or application that created it is no longer running

**Options for storing data**
- SQL
- NoSQL
- File systems
- Disk Storage

JPA is a specification and its implemented by Hibernate OpenJPA etc ...

**Object Relational Mapping (aka ORM)**
- ORM connects relation database and Java Application together
- Its like a bridge
- Data is organized in a relational database via one or more tables with columns and unique id
- Rows in the table represents instances of that type of data
- Two tables are linked together via FK

- Databases uses tables and columns but java application uses objects and classes
- The Java persistence API (JPA) is a Java specification for accessing, persisting and managing data between Java objects and a relational database
- JPA is a abstractaction on JDBC that's makes eazy to map objects to relational databases
- The mapping between Java Objects and database tables is defined via persistence metadata

**JPA configuration can be done via**
- annotations (recommended)
- persistence.xml file (legacy)

**The data can be queried**
- Criteria API
- Native Query (Named native quries)
- JPQL/HQL (Named queries)


> #### Building A Session Factory

> With hibernate.properties
```properties
hibernate.connection.username=infinite
hibernate.connection.password=skills
hibernate.connection.url=jdbc:mysql://localhost:3306/ifinances
hibernate.connection.driver_class=com.mysql.jdbc.Driver
hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
```

> Or with hibernate.cfg.xml
```xml
<?xml version='1.0' encoding='utf-8'?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">

<hibernate-configuration>

    <session-factory>

        <!-- Database connection settings -->
        <property name="connection.driver_class">com.mysql.jdbc.Driver</property>
        <property name="connection.url">jdbc:mysql://localhost:3306/ifinances</property>
        <property name="connection.username">infinite</property>
        <property name="connection.password">skills</property>

        <!-- SQL dialect -->
        <property name="dialect">org.hibernate.dialect.MySQL5Dialect</property>


        <!-- Echo all executed SQL to stdout -->
        <property name="show_sql">true</property>

        <mapping class="com.infiniteskills.data.entities.User"/>

    </session-factory>

</hibernate-configuration>
```

```java
public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			Configuration configuration = new Configuration();
			configuration.addAnnotatedClass(User.class);
			return configuration
					.buildSessionFactory(new StandardServiceRegistryBuilder()
							.build());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("There was an error building the factory");
		}
	}
	
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
}
```

```java
public class Application {

		public static void main(String[] args) {

			//New Session with transaction
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.getTransaction().begin();
			
			//Creating new Entity
			User user = new User();
			user.setBirthDate(new Date());
			user.setCreatedBy("kevin");
			user.setCreatedDate(new Date());
			user.setEmailAddress("kmb385@yahoo.com");
			user.setFirstName("Kevin");
			user.setLastName("Bowersox");
			user.setLastUpdatedBy("kevin");
			user.setLastUpdatedDate(new Date());
			
			//Save entity
			session.save(user);
			
			//Commiting transaction
			session.getTrasaction().commit();

			//Closing the session
			session.close();
		}
	}
```


> #### JPA configuration (Spring)

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


> #### Entity creation

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


> #### Field Versus Property Access

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Long userId;
}
```

```java
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="FINANCES_USER
public class User {

	private Long userId;

    @Id
	@Getter
    @Setter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	public Long getUserId() {
		return userId;
	}
}
```


> #### List of annotations


|Annoation 						 |Description 																																																															    |
|--------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|@AccessType 				     |The @AccessType annotation is deprecated. You should use either the JPA @Access or the Hibernate native @AttributeAccessor annotation 																																	| 
|@Any 				   			 |The @Any annotation is used to define the any-to-one association which can point to one one of several entity types.																																						|
|@AnyMetaDef 				     |The @AnyMetaDef annotation is used to provide metadata about an @Any or @ManyToAny mapping. 																																												|
|@AnyMetaDefs 				     |The @AnyMetaDefs annotation is used to group multiple @AnyMetaDef annotations.																																															|
|@AttributeAccessor 			 |The @AttributeAccessor annotation is used to specify a custom PropertyAccessStrategy. Should only be used to name a custom PropertyAccessStrategy. For property/field access type, the JPA @Accessannotation should be preferred.											|
|@BatchSize 				     |The @BatchSize annotation is used to specify the size for batch loading the entries of a lazy collection. 																																								|
|@Cache 				     	 |The @Cache annotation is used to specify the CacheConcurrencyStrategy of a root entity or a collection.																																									|
|@Cascade 				     	 |The @Cascade annotation is used to apply the Hibernate specific CascadeType strategies (e.g. CascadeType.LOCK, CascadeType.SAVE_UPDATE, CascadeType.REPLICATE) on a given association.																					|
|@Check 				     	 |The @Check annotation is used to specify an arbitrary SQL CHECK constraint which can be defined at the class level.																																						|
|@CollectionId 				     |The @CollectionId annotation is used to specify an identifier column for an idbag collection. You might want to use the JPA @OrderColumn instead.																															|
|@CollectionType 				 |The @CollectionType annotation is used to specify a custom collection type. The collection can also name a @Type, which defines the Hibernate Type of the collection elements.																							|
|@ColumnDefault 				 |The @ColumnDefault annotation is used to specify the DEFAULT DDL value to apply when using the automated schema generator.																																				|
|@Columns 				     	 |The @Columns annotation is used to group multiple JPA @Column annotations.																																																|
|@ColumnTransformer 			 |The @ColumnTransformer annotation is used to customize how a given column value is read from or write into the database.																																					|
|@ColumnTransformers 			 |The @ColumnTransformers annotation is used to group multiple @ColumnTransformer annotations.																																												|
|@CreationTimestamp 			 |The @CreationTimestamp annotation is used to specify that the currently annotated temporal type must be initialized with the current JVM timestamp value.																													|
|@DiscriminatorFormula 			 |The @DiscriminatorFormula annotation is used to specify a Hibernate @Formula to resolve the inheritance discriminator value.																																				|
|@DiscriminatorOptions 			 |The @DiscriminatorOptions annotation is used to provide the force and insert Discriminator properties.																																									|
|@DynamicInsert 				 |The @DynamicInsert annotation is used to specify that the INSERT SQL statement should be generated whenever an entity is to be persisted.																																	|
|@DynamicUpdate 				 |The @DynamicUpdate annotation is used to specify that the UPDATE SQL statement should be generated whenever an entity is modified.																																		|
|@Entity  				         |The @Entity annotation is deprecated. Use the JPA @Entity annotation instead.																																																|
|@Fetch 				     	 |The @Fetch annotation is used to specify the Hibernate specific FetchMode (e.g. JOIN, SELECT, SUBSELECT) used for the currently annotated association:																													|
|@FetchProfile  				 |The @FetchProfile annotation is used to specify a custom fetching profile, similar to a JPA Entity Graph.																																									|
|@FetchProfile.FetchOverride  	 |The @FetchProfile.FetchOverride annotation is used in conjunction with the @FetchProfile annotation, and it’s used for overriding the fetching strategy of a particular entity association.																				|
|@FetchProfiles  				 |The @FetchProfiles annotation is used to group multiple @FetchProfile annotations.																																														|
|@Filter  				         |The @Filter annotation is used to add filters to an entity or the target entity of a collection.																																											|
|@FilterDef  				     |The @FilterDef annotation is used to specify a @Filter definition (name, default condition and parameter types, if any).																																					|
|@FilterDefs  				     |The @FilterDefs annotation is used to group multiple @FilterDef annotations.																																																|
|@FilterJoinTable  				 |The @FilterJoinTable annotation is used to add @Filter capabilities to a join table collection.																																											|
|@FilterJoinTables  			 |The @FilterJoinTables annotation is used to group multiple @FilterJoinTable annotations.																																													|
|@Filters  				         |The @Filters annotation is used to group multiple @Filter annotations.																																																	|
|@ForeignKey  				     |The @ForeignKey annotation is deprecated. Use the JPA 2.1 @ForeignKey annotation instead.																																													|
|@Formula 				         |The @Formula annotation is used to specify an SQL fragment that is executed in order to populate a given entity attribute.																																				|
|@Generated 				     |The @Generated annotation is used to specify that the currently annotated entity attribute is generated by the database.																																					|
|@GeneratorType 				 |The @GeneratorType annotation is used to provide a ValueGenerator and a GenerationTime for the currently annotated generated attribute.																																	|
|@GenericGenerator 				 |The @GenericGenerator annotation can be used to configure any Hibernate identifier generator.																																												|
|@GenericGenerators 			 |The @GenericGenerators annotation is used to group multiple @GenericGenerator annotations. The @Immutable annotation is used to specify that the annotated entity, attribute, or collection is Immutable.																	|
|@Index 				     	 |The @Index annotation is deprecated. Use the JPA @Index annotation instead.																																																|
|@IndexColumn 				     |The @IndexColumn annotation is deprecated. Use the JPA @OrderColumn annotation instead.																																													|
|@JoinColumnOrFormula 			 |The @JoinColumnOrFormula annotation is used to specify that the entity association is resolved either through a FOREIGN KEY join (e.g. @JoinColumn) or using the result of a given SQL formula (e.g. @JoinFormula).														|
|@JoinColumnsOrFormulas 		 |The @JoinColumnsOrFormulas annotation is used to group multiple @JoinColumnOrFormula annotations.																																											|
|@JoinFormula 		 			 |The @JoinFormula annotation is used as a replacement for @JoinColumn when the association does not have a dedicated FOREIGN KEY column.																																	|
|@LazyCollection 				 |The @LazyCollection annotation is used to specify the lazy fetching behavior of a given collection. The TRUE and FALSE values are deprecated since you should be using the JPA FetchType attribute of the @ElementCollection, @OneToMany, or @ManyToMany collection.		|
|@LazyGroup 				 	 |The @LazyGroup annotation is used to specify that an entity attribute should be fetched along with all the other attributes belonging to the same group.																													|
|@LazyToOne 				     |The @LazyToOne annotation is used to specify the laziness options, represented by LazyToOneOption, available for a @OneToOne or @ManyToOne association.																													|
|@ListIndexBase 				 |The @ListIndexBase annotation is used to specify the start value for a list index, as stored in the database.																																								|
|@Loader 				         |The @Loader annotation is used to override the default SELECT query used for loading an entity loading.																																									|
|@ManyToAny 				     |The @ManyToAny annotation is used to specify a many-to-one association when the target type is dynamically resolved.																																						|
|@MapKeyType 				     |The @MapKeyType annotation is used to specify the map key type.																																																			|
|@MetaValue 				     |The @MetaValue annotation is used by the @AnyMetaDef annotation to specify the association between a given discriminator value and an entity type.																														|
|@NamedNativeQueries 			 |The @NamedNativeQueries annotation is used to group multiple @NamedNativeQuery annotations. The @NamedNativeQuery annotation extends the JPA @NamedNativeQuery with Hibernate specific features.																			|
|@NamedQueries 				     |The @NamedQueries annotation is used to group multiple @NamedQuery annotations.																																															|
|@NamedQuery 				     |The @NamedQuery annotation extends the JPA @NamedQuery with Hibernate specific features.																																													|
|@Nationalized 				     |The @Nationalized annotation is used to specify that the currently annotated attribute is a character type (e.g. String, Character, Clob) that is stored in a nationalized column type (NVARCHAR, NCHAR, NCLOB).															|
|@NaturalId 				     |The @NaturalId annotation is used to specify that the currently annotated attribute is part of the natural id of the entity.																																				|
|@NaturalIdCache 				 |The @NaturalIdCache annotation is used to specify that the natural id values associated with the annotated entity should be stored in the second-level cache.																												|
|@NotFound 				         |The @NotFound annotation is used to specify the NotFoundAction strategy for when an element is not found in a given association.																																			|
|@OnDelete 				         |The @OnDelete annotation is used to specify the delete strategy employed by the currently annotated collection, array or joined subclasses. This annotation is used by the automated schema generation tool to generate the appropriate FOREIGN KEY DDL cascade directive.|
|@OptimisticLock 				 |The @OptimisticLock annotation is used to specify if the currently annotated attribute will trigger an entity version increment upon being modified.																														|
|@OptimisticLocking 			 |The @OptimisticLocking annotation is used to specify the currently annotated an entity optimistic locking strategy.																																						|
|@OrderBy 				         |The @OrderBy annotation is used to specify a SQL ordering directive for sorting the currently annotated collection.																																						|
|@ParamDef 				         |The @ParamDef annotation is used in conjunction with @FilterDef so that the Hibernate Filter can be customized with runtime-provided parameter values.																													|
|@Parameter 				     |The @Parameter annotation is a generic parameter (basically a key/value combination) used to parametrize other annotations, like @CollectionType, @GenericGenerator, and @Type, @TypeDef.																					|
|@Parent 				         |The @Parent annotation is used to specify that the currently annotated embeddable attribute references back the owning entity.																																			|
|@Persister 				     |The @Persister annotation is used to specify a custom entity or collection persister. For entities, the custom persister must implement the EntityPersister interface. For collections, the custom persister must implement the CollectionPersister interface.			|
|@Polymorphism 				     | The @Polymorphism annotation is used to define the PolymorphismType Hibernate will apply to entity hierarchies.																																							|
|@Proxy 				         | The @Proxy annotation is used to specify a custom proxy implementation for the currently annotated entity.																																								|
|@RowId 				         | The @RowId annotation is used to specify the database column used as a ROWID pseudocolumn. For instance, Oracle defines the ROWID pseudocolumn which provides the address of every table row.																			|
|@SelectBeforeUpdate 			 | The @SelectBeforeUpdate annotation is used to specify that the currently annotated entity state be selected from the database when determining whether to perform an update when the detached entity is reattached.														|
|@Sort 				             | The @Sort annotation is deprecated. Use the Hibernate specific @SortComparator or @SortNatural annotations instead.																																						|
|@SortComparator 				 | The @SortComparator annotation is used to specify a Comparator for sorting the Set/Map in-memory.																																										|
|@SortNatural 				     | The @SortNatural annotation is used to specify that the Set/Map should be sorted using natural sorting.																																									|
|@Source 				         | The @Source annotation is used in conjunction with a @Version timestamp entity attribute indicating the SourceTypeof the timestamp value.																																|
|@SQLDelete 				     | The @SQLDelete annotation is used to specify a custom SQL DELETE statement for the currently annotated entity or collection.																																				|
|@SQLDeleteAll 				     | The @SQLDeleteAll annotation is used to specify a custom SQL DELETE statement when removing all elements of the currently annotated collection.																															|
|@SqlFragmentAlias 				 | The @SqlFragmentAlias annotation is used to specify an alias for a Hibernate @Filter.																																													|
|@SQLInsert 				     | The @SQLInsert annotation is used to specify a custom SQL INSERT statement for the currently annotated entity or collection.																																				|
|@SQLUpdate 				     | The @SQLUpdate annotation is used to specify a custom SQL UPDATE statement for the currently annotated entity or collection.																																				|
|@Subselect 				     | The @Subselect annotation is used to specify an immutable and read-only entity using a custom SQL SELECT statement.																																						|
|@Synchronize 				     | The @Synchronize annotation is usually used in conjunction with the @Subselect annotation to specify the list of database tables used by the @Subselect SQL query.																										|
|@Table 				         | The @Table annotation is used to specify additional information to a JPA @Table annotation, like custom INSERT, UPDATE or DELETE statements or a specific FetchMode.																										|
|@Tables 				         | The @Tables annotation is used to group multiple @Table annotations.																																																		|
|@Target 				         | The @Target annotation is used to specify an explicit target implementation when the currently annotated association is using an interface type.																															|
|@Tuplizer 				         | The @Tuplizer annotation is used to specify a custom tuplizer for the currently annotated entity or embeddable.																																							|
|@Tuplizers 				     | The @Tuplizers annotation is used to group multiple @Tuplizer annotations.																																																|
|@Type 				             | The @Type annotation is used to specify the Hibernate @Type used by the currently annotated basic attribute.																																								|
|@TypeDef 				         | The @TypeDef annotation is used to specify a @Type definition which can later be reused for multiple basic attribute mappings.																																			|
|@TypeDefs 				         | The @TypeDefs annotation is used to group multiple @TypeDef annotations.																																																	|
|@UpdateTimestamp 				 | The @UpdateTimestamp annotation is used to specify that the currently annotated timestamp attribute should be updated with the current JVM timestamp whenever the owning entity gets modified.																			|
|@ValueGenerationType 			 | The @ValueGenerationType annotation is used to specify that the current annotation type should be used as a generator annotation type.																													                |
|@Where 				         | The @Where annotation is used to specify a custom SQL WHERE clause used when fetching an entity or a collection.																																						    |
|@WhereJoinTable 				 | The @WhereJoinTable annotation is used to specify a custom SQL WHERE clause used when fetching a join collection table.																																                    |


> #### Most used annoations

> @Column
```java
@Entity
@NoArgumentsContrustor
@AllArgumentsConstructor
@Table(name="FINANCES_USER")
public class User {

	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="USER_ID")
	private Long userId;

	@Getter
	@Setter
	@Column(name="FIRST_NAME", nullable=false)
	private String firstName;

	@Getter
	@Setter
	@Column(name="BIRTH_DATE", nullable=true)
	private Date birthDate;
}
```

> @GeneratedValue

|Type 					 |Description 	 																																														 |
|------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|AUTO Generation         |If we're using the default generation type, the persistence provider will determine values based on the type of the primary key attribute. This type can be numerical or UUID.						 |
|IDENTITY Generation     |This type of generation relies on the IdentityGenerator, which expects values generated by an identity column in the database. This means they are auto-incremented. 								     |
|SEQUENCE Generation     |To use a sequence-based id, Hibernate provides the SequenceStyleGenerator class. This generator uses sequences if our database supports them. It switches to table generation if they aren't supported.|
|TABLE Generation        |The TableGenerator uses an underlying database table that holds segments of identifier generation values.																								 |
|CUSTOM Generation       |We can define our custom generator by implementing the IdentifierGenerator interface.																													 |

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
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="USER_ID")
	private Long userId;
}
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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="USER_ID")
	private Long userId;
}
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
	@GeneratedValue(strategy=GenerationType.TABLE, generator="user_table_generator")
	@TableGenerator(name = "user_table_generator", table="IFINACES_KEYS", pkColumnName = "PK_NAME", valueColumnName="PK_VALUE")
	@Column(name="USER_ID")
	private Long userId;
}
```


> @Transient

```java
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="FINANCES_USER")
public class User {


	@Getter
	@Setter
	@Transient
	private boolean valid;
}
```


> @Temporal

```java
@Entity
@NoArgumentsContrustor
@AllArgumentsConstructor
@Table(name = "TIME_TEST")
public class TimeTest {

	@Id
	@Getter
	@Setter
	@GeneratedValue
	@Column(name = "TIME_TEST_ID")
	private Long timeTestId;

	@Temporal(TemporalType.TIMESTAMP)
	@Getter
	@Setter
	@Column(name = "DATETIME_COLUMN")
	private Date datetimeColumn;

	@Temporal(TemporalType.TIMESTAMP)
	@Getter
	@Setter
	@Column(name = "TIMESTAMP_COLUMN")
	private Date timestampColumn;

	@Temporal(TemporalType.DATE)
	@Getter
	@Setter
	@Column(name = "DATE_COLUMN")
	private Date dateColumn;

	@Temporal(TemporalType.TIME)
	@Column(name = "TIME_COLUMN")
	@Getter
	@Setter
	private Date timeColumn;

	@Column(name = "SQL_DATETIME_COLUMN")
	@Getter
	@Setter
	private java.sql.Timestamp sqlDatetimeColumn;

	@Column(name = "SQL_TIMESTAMP_COLUMN")
	@Getter
	@Setter
	private java.sql.Timestamp sqlTimestampColumn;

	@Column(name = "SQL_DATE_COLUMN")
	@Getter
	@Setter
	private java.sql.Date sqlDateColumn;

	@Column(name = "SQL_TIME_COLUMN")
	@Getter
	@Setter
	private java.sql.Time sqlTimeColumn;
}
```


> @Formula

```java
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="FINANCES_USER")
public class User {

	@Getter
	@Setter
	@Column(name="AGE")
	@Formula("lower(datediff(curdate(), birth_date)/365)")
	private int age;
}
```


> #### Value Types

- Hibernate types
	- Entities (Corresponds with a database row)
- Value types
	- Basic (String, BigDecimal)
	- Composite (Another Java Object Addresses)
	- Collection (Lists, Sets)

```java
@Entity
public class User {

	//Basic value type
	@Id
	private Long userId; 		

	//Basic value type
	private String lastName;

	//Entity value type
	private User referredBy; 

	//Collection value type
	private List<String> aliases; 

	//Collection value type
	private Address address; 
}
```

> Mapping Composite Value Types

```java
@Entity
@Table(name="BANK")
@NoArgumentsContrustor
@AllArgumentsConstructor
public class Bank {
	
	@Getter
	@Setter
	@Embedded
	@AttributeOverrides({@AttributeOverride(name="addressLine1", column=@Column(name="USER_ADDRESS_LINE_1"))})
	private Address address = new Address();
}
```

```java
@Embeddable
@NoArgumentsContrustor
@AllArgumentsConstructor
public class Address {

	@Getter
	@Setter
	@Column(name="USER_ADDRESS_LINE_1")
	private String addressLine1;

	@Getter
	@Setter
	@Column(name="ADDRESS_LINE_2")
	private String addressLine2;
	
}
```

> Mapping Collections Of Basic Value Types

```java
@Entity
@Table(name = "BANK")
@NoArgumentsContrustor
@AllArgumentsConstructor
public class Bank {

	@Getter
	@Setter
	@ElementCollection
	@Column(name="NAME")
	@CollectionTable(name="BANK_CONTACT", joinColumns=@JoinColumn(name="BANK_ID"))
	private Set<String> contacts = new HashSet<String>();
}
```

> Mapping A Map Of Basic Values

```java
@Entity
@Table(name = "BANK")
@NoArgumentsContrustor
@AllArgumentsConstructor
public class Bank {

	@Getter
	@Setter
	@Column(name="NAME")
	@MapKeyColumn(name="POSITION_TYPE")
	@CollectionTable(name="BANK_CONTACT", joinColumns=@JoinColumn(name="BANK_ID"))
	private Map<String, String> contacts = new HashMap<String, String>();
}
```

> Mapping A Collection Of Composite Values

```java
@Entity
@Table(name="BANK")
@NoArgumentsContrustor
@AllArgumentsConstructor
public class Bank {
	
	@Getter
	@Setter
	@ElementCollection
	@CollectionTable(name="USER_ADDRESS", joinColumns=@JoinColumn(name="USER_ID"))
	@AttributeOverrides({@AttributeOverride(name="addressLine1", column=@Column(name="USER_ADDRESS_LINE_1"))})
	private List<Address> address = new ArrayList<Address>();
}
```

```bash
curl -XPOST -H 'Content-type: application/json' -d '{"name": "test", "address": {"addressLine1": "test"}, "address2": [{"addressLine1": "test2"}], "contacts": ["ecneb"], "contacts2": {"ecneb": "SK"}}' 'http://localhost:8080/hibernate/bank-resource/api/persist'

curl -XPOST -H 'Content-type: application/json' 'http://localhost:8080/hibernate/bank-resource/api/search/1'
```


> #### Attribute converters

```java
@Converter
public class PersonNameConverter implements AttributeConverter<PersonName, String> {

    @Override
    public String convertToDatabaseColumn(PersonName personName) {
    }

    @Override
    public PersonName convertToEntityAttribute(String dbPersonName) {
    }
}
```

```java
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="FINANCES_USER")
public class User {

    @Getter
    @Setter
    @Column(name = "PERSON_NAME")
    @Convert(converter = PersonNameConverter.class)
    private PersonName personName;
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
curl -XPOST -H 'Content-type: application/json' -d '{"username":"John","password":"Doe"}' 'http://localhost:8080/hibernate/credential-resource/api/persist'
```


> #### Reading objects

```java
@Transactional
public Optional<Credential> search(Long id) {
    return credentialRepository.findById(id);
}
```

```bash
curl -XPOST -H 'Content-type: application/json' 'http://localhost:8080/hibernate/credential-resource/api/search/2'
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
curl -XPUT -H 'Content-type: application/json' -d '{"username":"Doe","password":"John"}' 'http://localhost:8080/hibernate/credential-resource/api/update/2'
```


> #### Deleting objects

```java
@Transactional
public void delete(Long id) {
    credentialRepository.deleteById(id);
}
```

```bash
curl -XDELETE -H 'Content-type: application/json' 'http://localhost:8080/hibernate/credential-resource/api/delete/2'
```


> #### Transaction management

**Entity life cycle**
- Persisting
- Updateting
- Removing
- Loading

**Entity states**
- Managed
- Detached

**States by example**
- When an entity is created with the new keyword, then it is a regular Java Objects
- Before it is persistet it is in Transient or new State
- Once its persistet with the EntityManager, then its in the managed state
- An entity is also in managed state when its loaded from the database
- When a entity is in a managed state under the control of the EntityManager any updates made to the entity using its setter methods automatically synchronizes with the database (method call flush)
- An entity can be in the remove state if its marked for deletion via the EntityManager remove method
- An objects becomes detached when its removed, flushed or commiteted. Once an entity is detached it is going to live in memory until the garbage collector removes from memory

**API action table**

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

**Directions**
```
(Entity A) --Unidirectional--> B(Entity B)
```
```
(Entity A) <--Bidirectional--> B(Entity B)
```

**Cardinality**

|Type 			|Strategy 	|Description 																			  |
|---------------|-----------|-----------------------------------------------------------------------------------------|
|@OneToOne      |JoinColumn |Each entity instance is related to a single instance of another entity     			  |
|@OneToMany     |JoinColumn |An entity instance can be related to multiple instances of the other entities     		  |
|@ManyToOne     |JoinTable 	|Multiple instances of an entity can be related to a single instance of the other entity  |
|@ManyToMany    |JoinTable 	|The entity instances can be related to multiple instances of each other     			  |

**JPA Cascade Type**

|Type 		|Description 																			  																				    |
|-----------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|ALL      	|Propagates all operations — including Hibernate-specific ones — from a parent to a child entity   			  																|
|PERSIST    |Propagates the persist operation from a parent to a child entity    			  																							|
|MERGE      |The merge operation copies the state of the given object onto the persistent object with the same identifier     			  											    |
|REMOVE     |propagates the remove operation from parent to child entity. Similar to JPA's CascadeType.REMOVE, we have CascadeType.DELETE, which is specific to Hibernate   			|
|REFRESH    |Refresh operations reread the value of a given instance from the database     			  																					|
|DETACH     |The detach operation removes the entity from the persistent context. When we use CascadeType.DETACH, the child entity will also get removed from the persistent context    |

**Hibernate Cascade Type**

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

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="USER_ID")
    public User user;
```

```bash
curl -XPOST -H 'Content-type: application/json' -d '{"username":"John","password":"Doe", "user": {"personName": {"firstName": "John", "lastName": "Doe"}}}' 'http://localhost:8080/hibernate/credential-resource/api/persist'

curl -XPOST -H 'Content-type: application/json' 'http://localhost:8080/hibernate/credential-resource/api/search/1'
```


> #### @OneToOne Bidirectional

```java
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="CREDENTIAL")
public class Credential {

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="USER_ID")
    public User user;
```

```java
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="FINANCES_USER")
public class User {

    @Getter
    @Setter
    @OneToOne(mappedBy="user")
    private Credential credential;
```

```bash
curl -XPOST -H 'Content-type: application/json' -d '{"personName": {"firstName": "John", "lastName": "Doe"}, "emailAddress": "john.doe@gmail.com", "credential": {"username": "johny", "password": "test"}}' 'http://localhost:8080/hibernate/user-resource/api/persist'

curl -XPOST -H 'Content-type: application/json' 'http://localhost:8080/hibernate/user-resource/api/search/1'
```

> #### @OneToMany Unidirectional

```java
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ACCOUNT")
public class Account {

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ACCOUNT_ID", nullable = false)
    private List<Transaction> transactions = new ArrayList<>();
}
```

```bash
curl -XPOST -H 'Content-type: application/json' -d '{"name": "ecneb", "initialBalance": 15, "currentBalance": 15, "transactions": [{"transactionType": "TEST", "amount": 15}]}' 'http://localhost:8080/hibernate/account-resource/api/persist'

curl -XPOST -H 'Content-type: application/json' 'http://localhost:8080/hibernate/account-resource/api/search/1'
```


> #### @OneToMany Bidirectional

```java
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ACCOUNT")
public class Account {

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ACCOUNT_ID", nullable = false)
    private List<Transaction> transactions = new ArrayList<>();
}
```

```java
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TRANSACTION")
public class Transaction {

    @Getter
    @Setter
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="ACCOUNT_ID", insertable = false, updatable = false)
    private Account account;
}
```

```bash
curl -XPOST -H 'Content-type: application/json' -d '{"name": "ecneb", "initialBalance": 15, "currentBalance": 15, "transactions": [{"transactionType": "TEST", "amount": 15}]}' 'http://localhost:8080/hibernate/account-resource/api/persist'

curl -XPOST -H 'Content-type: application/json' 'http://localhost:8080/hibernate/account-resource/api/search/1'
```


> #### @ManyToOne with joinTable
- One 'Transaction' has one 'Budget Transaction'
- One 'Budget' has many 'Budget Transactions'

```java
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BUDGET")
public class Budget {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "BUDGET_TRANSACTION", joinColumns = @JoinColumn(name = "BUDGET_ID"), inverseJoinColumns = @JoinColumn(name = "TRANSACTION_ID"))
    private List<Transaction> transactions = new ArrayList<>();
}
```

```bash
curl -XPOST -H 'Content-type: application/json' -d '{"name": "cpu", "goalAmount": 15, "transactions": [{"transactionType": "TEST", "amount": 15, "account": {"name": "national bank"}}]}' 'http://localhost:8080/hibernate/budget-resource/api/persist'

curl -XPOST -H 'Content-type: application/json' 'http://localhost:8080/hibernate/budget-resource/api/search/1'
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


> #### @ManyToMany with joinTable

```java
```


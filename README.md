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

spring.jpa.hibernate.ddl-auto=create-drop

spring.jpa.properties.javax.persistence.schema-generation.scripts.action=drop-and-create

spring.jpa.properties.javax.persistence.schema-generation.scripts.create-target=create.sql
spring.jpa.properties.javax.persistence.schema-generation.scripts.drop-target=drop.sql

spring.jpa.properties.javax.persistence.schema-generation.scripts.create-source=metadata
spring.jpa.properties.javax.persistence.schema-generation.scripts.drop-source=metadata
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
@Table(name="FINANCES_USER")
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


> @Enumerated

```java
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="FINANCES_USER")
public class User {

    @Enumerated(EnumType.STRING)
    @Column(name="ACCOUNT_TYPE")
    // if dont use the EnumType.STRING then the table values wil be the order of enums
    private AccountType accountType;
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

curl -XGET -H 'Content-type: application/json' 'http://localhost:8080/hibernate/bank-resource/api/search/1'
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


> #### CRUD

- Similarities between Hibernate session and JPA EntityManager

|Operation 			|Session 	    				  |EntitiyManager(Tx)	  			   |Description 		 																										  |
|-------------------|---------------------------------|------------------------------------|------------------------------------------------------------------------------------------------------------------------------|
|Saving    			|se.save(account)	 	     	  |em.persist(account)    		       |It saves the entity 																										  |
|Saving or updating |se.saveOrUpdate(account)	 	  |    		       					   |It saves or update the entity 																								  |
|Retrieving 		|se.get(Account.class, 1L)	 	  |em.find(Account.class, 1L)    	   |If it doesn't not find it, it will throw a NullPointerException (It has cache) 												  |
|Retrieving 		|se.load(Account.class, 123L)	  |em.getReference(Account.class, 1L)  |With load the select will run only if a property is accest. If it doesn't not find it, it will throw a ObjectNotFoundException|
|Deleting  			|se.delete(account)	 	    	  |em.remove(account);   			   |It removes the entity 																										  |
|Modifying 			|se.flush()			 	    	  |tx.commit();		   				   |Take all the changes in persistence context and sync to db, this is also true for transaction commit 			 			  |
|Detaching			|se.update(account)	 	   		  |em.merge(account);   			   |It detaches the entity 																										  |
|Reattaching		|se.close(account)	 	  	      |em.detach(account);   			   |It attaches the entity 																										  |

> Persisting objects

```java
@Transactional
public void persist(Credential credential) {
    credentialRepository.save(credential);
}
```

```bash
curl -XPOST -H 'Content-type: application/json' -d '{"username":"John","password":"Doe"}' 'http://localhost:8080/hibernate/credential-resource/api/persist'
```


> Reading objects

```java
@Transactional
public Optional<Credential> search(Long id) {
    return credentialRepository.findById(id);
}
```

```bash
curl -XGET -H 'Content-type: application/json' 'http://localhost:8080/hibernate/credential-resource/api/search/2'
```


> Updating objects

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


> Deleting objects

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

    @Getter
    @Setter
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="USER_ID")
    public User user;
```

```bash
curl -XPOST -H 'Content-type: application/json' -d '{"username":"John","password":"Doe", "user": {"personName": {"firstName": "John", "lastName": "Doe"}}}' 'http://localhost:8080/hibernate/credential-resource/api/persist'

curl -XGET -H 'Content-type: application/json' 'http://localhost:8080/hibernate/credential-resource/api/search/1'
```


> #### @OneToOne Bidirectional

```java
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="CREDENTIAL")
public class Credential {

    @Getter
    @Setter
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

curl -XGET -H 'Content-type: application/json' 'http://localhost:8080/hibernate/user-resource/api/search/1'
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

curl -XGET -H 'Content-type: application/json' 'http://localhost:8080/hibernate/account-resource/api/search/1'
```


> #### @OneToMany & ManyToOne Bidirectional

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

curl -XGET -H 'Content-type: application/json' 'http://localhost:8080/hibernate/account-resource/api/search/1'
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

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "BUDGET_TRANSACTION", joinColumns = @JoinColumn(name = "BUDGET_ID"), inverseJoinColumns = @JoinColumn(name = "TRANSACTION_ID"))
    private List<Transaction> transactions = new ArrayList<>();
}
```

```bash
curl -XPOST -H 'Content-type: application/json' -d '{"name": "cpu", "goalAmount": 15, "transactions": [{"transactionType": "TEST", "amount": 15, "account": {"name": "national bank"}}]}' 'http://localhost:8080/hibernate/budget-resource/api/persist'

curl -XGET -H 'Content-type: application/json' 'http://localhost:8080/hibernate/budget-resource/api/search/1'
```


> #### @ManyToMany Unidirectional

```java
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ACCOUNT")
public class Account {

    @Getter
    @Setter
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="USER_ACCOUNT", joinColumns=@JoinColumn(name="ACCOUNT_ID"),
            inverseJoinColumns=@JoinColumn(name="USER_ID"))
    private Set<User> users = new HashSet<>();
}
```


> #### @ManyToMany Bidirectional

```java
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="FINANCES_USER")
public class User {

    @Getter
    @Setter
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private Set<Account> accounts = new HashSet<>();
}
```

```bash
curl -XPOST -H 'Content-type: application/json' -d '{"name": "ecneb", "initialBalance": 15, "currentBalance": 15, "transactions": [{"transactionType": "TEST", "amount": 15}], "users": [{"personName": {"firstName": "John", "lastName": "Doe"}}]}' 'http://localhost:8080/hibernate/account-resource/api/persist'

curl -XGET -H 'Content-type: application/json' 'http://localhost:8080/hibernate/account-resource/api/search/1'
```

```java
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ACCOUNT")
public class Account {

    @Getter
    @Setter
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="USER_ACCOUNT", joinColumns=@JoinColumn(name="ACCOUNT_ID"),
            inverseJoinColumns=@JoinColumn(name="USER_ID"))
    private Set<User> users = new HashSet<>();
}
```

```bash
curl -XPOST -H 'Content-type: application/json' -d '{"name": "ecneb", "initialBalance": 15, "currentBalance": 15, "transactions": [{"transactionType": "TEST", "amount": 15}], "users": [{"personName": {"firstName": "John", "lastName": "Doe"}}]}' 'http://localhost:8080/hibernate/account-resource/api/persist'

curl -XGET -H 'Content-type: application/json' 'http://localhost:8080/hibernate/account-resource/api/search/1'
```

> #### Compound Primary Keys

```java
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
public class CurrencyId implements Serializable {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String countryName;
}
```

```java
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(CurrencyId.class)
public class Currency {

    @Id
    @Getter
    @Setter
    @Column(name="NAME")
    private String name;

    @Id
    @Getter
    @Setter
    @Column(name="COUNTRY_NAME")
    private String countryName;

    @Getter
    @Setter
    @Column(name="SYMBOL")
    private String symbol;
}
```

```bash
curl -XPOST -H 'Content-type: application/json' -d '{"name": "EUR", "countryName": "SK", "symbol": "eu"}' 'http://localhost:8080/hibernate/currency-resource/api/persist'

curl -XGET -H 'Content-type: application/json' 'http://localhost:8080/hibernate/currency-resource/api/search/EUR/SK'
```

> #### Compound Join Columns

```java
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MARKET")
public class Market {

    @Getter
    @Setter
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name="CURRENCY_NAME", referencedColumnName="NAME"),
            @JoinColumn(name="COUNTRY_NAME", referencedColumnName="COUNTRY_NAME")
    })
    //CURRENCY_NAME and COUNTRY_NAME will be places inside here (MARKET TABLE)
    private Currency currency;
}
```

```java
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(CurrencyId.class)
public class Currency {

    @Id
    @Getter
    @Setter
    @Column(name="NAME")
    private String name;

    @Id
    @Getter
    @Setter
    @Column(name="COUNTRY_NAME")
    private String countryName;

    @Getter
    @Setter
    @Column(name="SYMBOL")
    private String symbol;
}
```

```bash
curl -XPOST -H 'Content-type: application/json' -d '{"marketName": "Github", "currency": {"name": "EUR", "countryName": "SK", "symbol": "eu"}}' 'http://localhost:8080/hibernate/market-resource/api/persist'

curl -XGET -H 'Content-type: application/json' 'http://localhost:8080/hibernate/market-resource/api/search/1'
```





> #### Inheritance

- There are four types of mapping strategies
	- Mapped superclass
	- Single table
	- Joined table
	- Table per class

> Mapped superclass

- Simplest approach
- MappedSuperclass is not an entity
- Tables created for each subclass, but for parent there is no table
- Ticket shares state and behavior with its child classes but its not considered an entity and can't be managed by the EntityManager
- Cant use polymorphic queries (that selects all ticket entities)

```java
@Builder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @Getter
    @Setter
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Getter
    @Setter
    @Column(name = "TITLE")
    private String title;

    @Getter
    @Setter
    @Column(name = "DESCRIPTION")
    private String description;

    @Getter
    @Setter
    @Column(name = "STATUS")
    private String status;
}
```

```java
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="MAPPED_SUPER_CLASS_ENHANCEMENT")
public class Enhancement extends Ticket {

    @Getter
    @Setter
    @Column(name = "DUPLICATE")
    private Boolean duplicate;

    @Getter
    @Setter
    @Column(name = "PRIORITY")
    private String priority;
}
```

```java
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="MAPPED_SUPER_CLASS_BUG")
public class Bug extends Ticket {

    @Getter
    @Setter
    @Column(name = "SEVERITY")
    private Integer severity;

    @Getter
    @Setter
    @Column(name = "ROOT_CAUSE")
    private String rootCause;
}
```

```sql
create table mapped_super_class_bug (
   id integer not null,
    description varchar(255),
    status varchar(255),
    title varchar(255),
    root_cause varchar(255),
    severity integer,
    primary key (id)
) engine=InnoDB;

create table mapped_super_class_enhancement (
   id integer not null,
    description varchar(255),
    status varchar(255),
    title varchar(255),
    duplicate bit,
    priority varchar(255),
    primary key (id)
) engine=InnoDB;
```

> Single Table

- The single table strategy is the default strategy chosen by JPA if we dont specify one explicitly
- We will have three classes stored in one table
- For this reason JPA also generated a discriminator Column(DTYPE)
- Cant use not null contrains (its the nullable = false attribute)
- Polymorphic queries offecient

```java
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="SINGLE_TABLE_TASK")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Task {

    @Id
    @Getter
    @Setter
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Getter
    @Setter
    @Column(name = "TITLE")
    private String title;

    @Getter
    @Setter
    @Column(name = "DESCRIPTION")
    private String description;

    @Getter
    @Setter
    @Column(name = "STATUS")
    private String status;
}
```

```java
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorColumn(name="STORY_TYPE")
public class Story extends Task {

    @Getter
    @Setter
    @Column(name = "LEVEL")
    private Integer level;
}
```

```java
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorColumn(name="INCIDENT_TYPE")
public class Incident extends Task {

    @Getter
    @Setter
    @Column(name = "TEAM")
    private String team;
}
```

```sql
create table single_table_task (
   dtype varchar(31) not null,
    id integer not null,
    description varchar(255),
    status varchar(255),
    title varchar(255),
    team varchar(255),
    level integer,
    primary key (id)
) engine=InnoDB;
```

> Table per class

- Its similar to the mapper super class strategy, but here the parent class is also a table
- It create's three tables in DB
- All subclass records returned via UNION state in background
- Complexity polymorphic queries and performance issue

```java
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="TABLE_PER_CLASS_ITEM")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Item {

    @Id
    @Getter
    @Setter
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Getter
    @Setter
    @Column(name = "TITLE")
    private String title;

    @Getter
    @Setter
    @Column(name = "DESCRIPTION")
    private String description;

    @Getter
    @Setter
    @Column(name = "STATUS")
    private String status;
}
```

```java
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="TABLE_PER_CLASS_TEST")
public class Test extends Item {

    @Getter
    @Setter
    @Column(name = "DUPLICATE")
    private Boolean duplicate;

    @Getter
    @Setter
    @Column(name = "PRIORITY")
    private String priority;
}
```

```java
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="TABLE_PER_CLASS_ACTIVITY")
public class Activity extends Item {

    @Getter
    @Setter
    @Column(name = "SEVERITY")
    private Integer severity;

    @Getter
    @Setter
    @Column(name = "ROOT_CAUSE")
    private String rootCause;
}
```

```sql
create table table_per_class_activity (
   id integer not null,
    description varchar(255),
    status varchar(255),
    title varchar(255),
    root_cause varchar(255),
    severity integer,
    primary key (id)
) engine=InnoDB;

create table table_per_class_item (
   id integer not null,
    description varchar(255),
    status varchar(255),
    title varchar(255),
    primary key (id)
) engine=InnoDB;

create table table_per_class_test (
   id integer not null,
    description varchar(255),
    status varchar(255),
    title varchar(255),
    duplicate bit,
    priority varchar(255),
    primary key (id)
) engine=InnoDB;
```

> Join table

- It create's three tables in DB
- But the sub tables doest hold the parent tables properties
- The subclasses also contain a primery key field the same value as the matching record in the table of the parent class
- Join of the tables to select the columns (between Job and Feature or Job and Defect)
- Increases the complexity of each query
- Use of NOT NULL contstrains are allowed

```java
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="JOINED_JOB")
@Inheritance(strategy = InheritanceType.JOINED)
public class Job {

    @Id
    @Getter
    @Setter
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Getter
    @Setter
    @Column(name = "TITLE")
    private String title;

    @Getter
    @Setter
    @Column(name = "DESCRIPTION")
    private String description;

    @Getter
    @Setter
    @Column(name = "STATUS")
    private String status;
}
```

```java
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="JOINED_FEATURE")
public class Feature extends Job {

    @Getter
    @Setter
    @Column(name = "LEVEL")
    private Integer level;
}
```

```java
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="JOINED_DEFECT")
public class Defect extends Job {

    @Getter
    @Setter
    @Column(name = "SEVERITY")
    private Integer severity;

    @Getter
    @Setter
    @Column(name = "ROOT_CAUSE")
    private String rootCause;
}
```

```sql
create table joined_defect (
   root_cause varchar(255),
    severity integer,
    id integer not null,
    primary key (id)
) engine=InnoDB;

create table joined_feature (
   level integer,
    id integer not null,
    primary key (id)
) engine=InnoDB;

create table joined_job (
   id integer not null,
    description varchar(255),
    status varchar(255),
    title varchar(255),
    primary key (id)
) engine=InnoDB;
```





> #### Performance - FetchType (EAGER & LAZY)

- We use the fetch attribute in the association annotations

```java
@OneToOne(fetch=FetchType.LAZY)
@OneToMany(fetch=FetchType.LAZY)
@ManyToOne(fetch=FetchType.LAZY)
@ManyToMany(fetch=FetchType.LAZY)
```

```java
public static void main(String[] args) {
	SessionFactory factory = null;
	Session session = null;
	org.hibernate.Transaction tx = null;

	try {
		factory = HibernateUtil.getSessionFactory();
		session = factory.openSession();
		tx = session.beginTransaction();
		
		Query query = session.getNamedQuery("Account.largeDeposits");
		List<Account> accounts = query.list();
		System.out.println("Query has been executed.");
		
		//because we set it to lazy the bank gets selected only when we call getBank();
		for(Account a:accounts){
			System.out.println(a.getName());
			System.out.println(a.getBank().getName());
		}
		
		tx.commit();
	} catch (Exception e) {
		e.printStackTrace();
		tx.rollback();
	} finally {
		session.close();
		factory.close();
	}
}
```

> LazyInitializationException

- Hibernate throws the LazyInitializationException when it needs to initialize a lazily fetched association to another entity without an active session context

How to NOT fix the LazyInitializationException:
- Don’t use FetchType.EAGER
	- When you set the FetchType to EAGER, Hibernate will always fetch the association, even if you don’t use it in your use case
- Avoid the Open Session in View anti-pattern
	- When using the Open Session in View anti-patter, you open and close the EntityManager or Hibernate Session in your view layer. You then call the service layer, which opens and commits a database transaction. Because the Session is still open after the service layer returned the entity, the view layer can then initialize the lazily fetched association
- Don’t use hibernate.enable_lazy_load_no_trans
	- Another suggestion you should avoid is to set the hibernate.enable_lazy_load_no_trans configuration parameter in the persistence.xml file to true. This parameter tells Hibernate to open a temporary Session when no active Session is available

How to fix the LazyInitializationException:
- Initializing associations with a LEFT JOIN FETCH clause
	- SELECT a FROM Author a LEFT JOIN FETCH a.books
- Use a @NamedEntityGraph to initialize an association
	- You can define the graph by annotating one of your entity classes with a @NamedEntityGraph annotation. Within this annotation, you can provide multiple @NamedAttributeNode annotations to specify the attributes that Hibernate shall fetch
- Using a DTO projection
	- SELECT new org.dto.AuthorDto(a.name,b.title) FROM Author a JOIN a.books b





> #### Queries

- JPQL is a subset of HQL

> Writing

```java
public class HqlApplication {
	public static void main(String[] args) {
		Query query = session.createQuery("select t from Transaction t");
		List<Transaction> transactions = query.list();
	}
}
```

```java
public class JpqlApplication {
	public static void main(String[] args) {
		TypedQuery<Transaction> query = em.createQuery("from Transaction t order by t.title", Transaction.class);
		List<Transaction> transactions = query.getResultList();
	}
}
```

> Expressions And Operators

```java
public class HqlApplication {
	public static void main(String[] args) {
		Query query = session.createQuery("select t from Transaction t "
				+ "where t.amount > 75 and t.transactionType = 'Withdrawl'");
		
		List<Transaction> transactions = query.list();
	}
}
```

```java
public class JpqlApplication {
	public static void main(String[] args) {
		TypedQuery<Transaction> query = em.createQuery(
				"from Transaction t"
				+ " where (t.amount between 75 and 100) and t.title like '%s'"
				+ " order by t.title", Transaction.class);
		
		List<Transaction> transactions = query.getResultList();
	}
}
```

> Parameters

```java
public class HqlApplication {
	public static void main(String[] args) {
		Query query = session.createQuery("select t from Transaction t "
				+ "where t.amount > :amount and t.transactionType = 'Withdrawl'");
		System.out.println("Please specify an amount:");
		
		query.setParameter("amount", new BigDecimal(scanner.next()));
		List<Transaction> transactions = query.list();
	}
}
```

```java
public class JpqlApplication {
	public static void main(String[] args) {
		TypedQuery<Transaction> query = em.createQuery(
				"from Transaction t"
				+ " where (t.amount between ?1 and ?2) and t.title like '%s'"
				+ " order by t.title", Transaction.class);
		
		System.out.println("Please provide the first amount:");
		query.setParameter(1, new BigDecimal(scanner.next()));
		System.out.println("Please provide the second amount:");
		query.setParameter(2, new BigDecimal(scanner.next()));
	}
}
```

> Joins

```java
public class HqlApplication {
	public static void main(String[] args) {
		Query query = session.createQuery("select distinct t.account from Transaction t"
				+ " where t.amount > 500 and t.transactionType = 'Deposit'");
		
		List<Account> accounts = query.list();
	}
}
```

```java
public class JpqlApplication {
	public static void main(String[] args) {
		TypedQuery<Account> query = em.createQuery("select distinct a from Transaction t"
				+ " join t.account a "
				+ "where t.amount > 500 and t.transactionType = 'Deposit'",Account.class);
		
		List<Account> accounts = query.getResultList();
	}
}
```

> Functions

```java
public class HqlApplication {
	public static void main(String[] args) {
		Query query = session.createQuery("select distinct t.account from Transaction t"
				+ " where t.amount > 500 and lower(t.transactionType) = 'deposit'");
		
		List<Account> accounts = query.list();
	}
}
```

```java
public class JpqlApplication {
	public static void main(String[] args) {
		Query query = em.createQuery("select distinct t.account.name, "
				+ "concat(concat(t.account.bank.name, ' '),t.account.bank.address.state)"
				+ " from Transaction t"
				+ " where t.amount > 500 and t.transactionType = 'Deposit'");
		
		List<Object[]> accounts = query.getResultList();
	}
}
```

> Query using streams

- Provide an efficient way to move through a result set
- Scroll trought the results without fetching all the data at once

```java
public class TicketDAO implements ITicketDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Ticket> getAllTickets() {
        String query = "select t from Ticket t order by t.title";
        return (List<Ticket>) entityManager.createQuery(query).getResultStream().collect(Collectors.toList())
    }
}
```

> Views

- If we have a complex query then we write first the query in plain SQL and then we put it in a view

```java
public interface Schema {

	String VIEW = "view."
	String TABLE = "table."
}
```

```java
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Schema.VIEW + "USER_CREDENTIAL")
public class UserCredentialView {

	@Id
	@Getter
	@Setter
	@Column(name="USER_ID")
	private Long userId;

	@Getter
	@Setter
	@Column(name="FIRST_NAME")
	private String firstName;

	@Getter
	@Setter
	@Column(name="LAST_NAME")
	private String lastName;

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

> Named queries

```java
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ACCOUNT")
@NamedQueries({
	@NamedQuery(name="Account.largeDeposits", query="select distinct t.account from Transaction t"
				+ " where t.amount > 500 and lower(t.transactionType) = 'deposit'"),
	@NamedQuery(name="Account.byWithdrawlAmount", query="select distinct t.account.name, "
					+ "concat(concat(t.account.bank.name, ' '),t.account.bank.address.state)"
					+ " from Transaction t"
					+ " where t.amount > :amount and t.transactionType = 'withdrawl'")
})
public class Account {
}
```

```java
public class HqlApplication {
	public static void main(String[] args) {
		Query query = session.getNamedQuery("Account.largeDeposits");
		List<Account> accounts = query.list();
	}
}
```

```java
public class JpqlApplication {
	public static void main(String[] args) {
		Query query = em.createNamedQuery("Account.byWithdrawlAmount");
		query.setParameter("amount", new BigDecimal("99"));
		
		List<Object[]> accounts = query.getResultList();
	}
}
```

> Criteria API (Simple selection)

```java
public class HqlApplication {
	public static void main(String[] args) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Transaction> criteriaQuery = cb.createQuery(Transaction.class);
				
		Root<Transaction> root = criteriaQuery.from(Transaction.class);
		criteriaQuery.select(root);
		
		TypedQuery<Transaction> query = em.createQuery(criteriaQuery);
		List<Transaction> transactions = query.getResultList();
	}
}
```

```java
public class JpqlApplication {
	public static void main(String[] args) {
		List<Transaction> transactions = session
				.createCriteria(Transaction.class)
				.addOrder(Order.desc("title")).list();
	}
}
```

> Criteria API (Restriction)

```java
public class HqlApplication {
	public static void main(String[] args) {
		Criterion criterion1 = Restrictions.le("amount", new BigDecimal(
						"20.00"));
		Criterion criterion2 = Restrictions.eq("transactionType",
				"Withdrawl");

		List<Transaction> transactions = session
				.createCriteria(Transaction.class).add(Restrictions.and(criterion1, criterion2))
				.addOrder(Order.desc("title")).list();
	}
}
```

```java
public class JpqlApplication {
	public static void main(String[] args) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Transaction> criteriaQuery = cb
				.createQuery(Transaction.class);

		Root<Transaction> root = criteriaQuery.from(Transaction.class);
		Path<BigDecimal> amountPath = root.get("amount");
		Path<String> transactionTypePath = root.get("transactionType");

		criteriaQuery.select(root).where(
				cb.and(cb.le(amountPath, new BigDecimal("20.00")),
						cb.equal(transactionTypePath, "Withdrawl")));
		
		TypedQuery<Transaction> query = em.createQuery(criteriaQuery);
		List<Transaction> transactions = query.getResultList();
	}
}
```

> Criteria API (Paging)

```java
public class HqlApplication {
	public static void main(String[] args) {
		Criteria criteria = session.createCriteria(Transaction.class)
				.addOrder(Order.asc("title"));
		criteria.setFirstResult((pageNumber - 1) * pageSize);
		criteria.setMaxResults(pageSize);

		List<Transaction> transactions = criteria.list();
	}
}
```

```java
public class JpqlApplication {
	public static void main(String[] args) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Transaction> criteriaQuery = cb
				.createQuery(Transaction.class);

		Root<Transaction> root = criteriaQuery.from(Transaction.class);
		criteriaQuery.select(root);

		TypedQuery<Transaction> query = em.createQuery(criteriaQuery);
		query.setFirstResult((pageNumber - 1) * pageSize);
		query.setMaxResults(pageSize);

		List<Transaction> transactions = query.getResultList();

	}
}
```

> Store procedures

```java
@Entity
@Builder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedStoredProcedureQuery(
    name = "findByRelease",
    procedureName = "FIND_TICKET<BY_RELEASE",
    resultClasses = { Ticket.class },
    parameters = {
        @StoredProcedureParameter(
            name = "p_id",
            type = Integer.class,
            mode = ParameterMode.IN)
    })
public class Ticket {
}
```

```java
public class TicketDAO implements ITicketDAO {
	
    @PersistenceContext
    private EntityManager entityManager;

    public List<Ticket> findByReleaseId(int releaseId){
        return (List<Ticket>) entityManager.createNamedStoredProcedureQuery("findByRelease")
    }
}
```

> Native queries

```java
public class EnhancementDAO implements IEnhancementDAO{

    @PersistenceContext
    private EntityManager entityManager;

    public List<Enhancement> getTicketsWithApps() {
        String jpql = "SELECT t.id, t.description, t.status, a.app_name " +
         "FROM APPLICATIONS a, TICKET t " +
         "WHERE a.application_id = t.application_id";

        return (List<Enhancement>) entityManager.createNativeQuery(jpql).getResultList();
    }
}
```





> #### equals() and hashCode()

- You need to implement the equals() and hashCode() methods for primary key classes if you map composite primary keys.
- If you map an association to a Map, your map key needs to implement the equals() and hashCode() methods. So, if use an entity as the key, it needs to provide both methods.
- You can map one-to-many and many-to-many associations to different sub-types of Collection. If you use a Set, your entities have to have equals() and hashCode() methods.
- Independent of the available keys, all equals() and hashCode() implementations need to pass the following tests:

```java
// 2 transient entities need to be NOT equal
MyEntity e1 = new MyEntity("1");
MyEntity e2 = new MyEntity("2");
Assert.assertFalse(e1.equals(e2));
 
// 2 managed entities that represent different records need to be NOT equal
e1 = em.find(MyEntity.class, id1);
e2 = em.find(MyEntity.class, id2);
Assert.assertFalse(e1.equals(e2));
 
// 2 managed entities that represent the same record need to be equal
e1 = em.find(MyEntity.class, id1);
e2 = em.find(MyEntity.class, id1);
Assert.assertTrue(e1.equals(e2));
 
// a detached and a managed entity object that represent the same record need to be equal
em.detach(e1);
e2 = em.find(MyEntity.class, id1);
Assert.assertTrue(e1.equals(e2));
 
// a re-attached and a managed entity object that represent the same record need to be equal
e1 = em.merge(e1);
Assert.assertTrue(e1.equals(e2));
```

> Using a Business Key or Natural Key

```java
@Entity
@Builder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class MyEntity {
 
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    @Getter
    @Setter
    @NaturalId
    private String businessKey;
 
    @Override
    public int hashCode() {
        return Objects.hashCode(businessKey);
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MyEntity other = (MyEntity) obj;
        return Objects.equals(businessKey, other.getBusinessKey());
    }
}
```

> Using a Business Key with a Parent Reference

```java
@Entity
@Builder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class MyEntity {
 
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    @Getter
    @Setter
    @NaturalId
    private String businessKey;
     
    @Getter
    @Setter
    @ManyToOne
    private MyParent parent;
 
     
    @Override
    public int hashCode() {
        return Objects.hash(parent, businessKey);
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MyEntity other = (MyEntity) obj;
        return Objects.equals(parent, other.getParent())
                && Objects.equals(businessKey, other.getBusinessKey());
    }
}
```

> Using a Programmatically Managed Primary Key

- If you manage your primary key values programmatically, you can implement your equals() and hashCode() methods in almost the same way as I showed you in the previous example. The only requirement here is that you set the primary key value in the constructor or immediately after you instantiated a new entity object

```java
@Entity
@Builder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class MyEntity {
 
    @Id
    @Getter
    @Setter
    private Long id;
 
    public MyEntity(Long id) {
        this.id = id;
    }
     
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MyEntity other = (MyEntity) obj;
        return Objects.equals(id, other.getId());
    }
}
```

> Using a Generated Primary Key

- As I teased earlier, generated primary keys create a problem for the implementation of your equals() and hashCode() methods. That’s because the primary key value gets set when the entity gets persisted. So, your entity object can exist with and without a primary key value

```java
@Entity
@Builder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class MyEntity {
 
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    @Override
    public int hashCode() {
        return 13;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MyEntity other = (MyEntity) obj;
        return id != null && id.equals(other.getId());
    }
}
```





> ### Java Transaction API

- A transaction is a single unit of work items, which follows the ACID properties. ACID stands for Atomic, Consistent, Isolated, and Durable: 
	- Atomic − If any of the work item fails, the whole unit will be considered failed. Success meant, all items execute successfully.
	- Consistent − A transaction must keep the system in consistent state.
	- Isolated − Each transaction executes independent of any other transaction.
	- Durable − Transaction should survive system failure if it has been executed or committed.

- EJB Container/Servers are transaction servers and handles transactions context propagation and distributed transactions. Transactions can be managed by the container or by custom code handling in bean's code
	- Container Managed Transactions − In this type, the container manages the transaction states.
	- Bean Managed Transactions − In this type, the developer manages the life cycle of transaction states.

> Container Managed Transactions

- EJB 3.0 has specified following attributes of transactions, which EJB containers implement:
	- REQUIRED − Indicates that business method has to be executed within transaction, otherwise a new transaction will be started for that method.
	- REQUIRES_NEW − Indicates that a new transaction, is to be started for the business method.
	- SUPPORTS − Indicates that business method will execute as part of transaction.
	- NOT_SUPPORTED − Indicates that business method should not be executed as part of transaction.
	- MANDATORY − Indicates that business method will execute as part of transaction, otherwise exception will be thrown.
	- NEVER − Indicates if business method executes as part of transaction, then an exception will be thrown.

- Examples:
	- MANDATORY
		Scenario: Invoking outside transaction. Should get an error \
		Got TransactionRequiredException for transctionalException.getCause() as expected \
		Scenario: Invoking within a transaction \
		ObjectId for this beans is transactional.BeanManadtory$Proxy$_$$_WeldSublcass@278ca 

	- NEVER
		Scenario: Invoking outside transaction \
		ObjectId for this beans is transactional.BeanNever$Proxy$_$$_WeldSublcass@278ca \
		Scenario: Invoking within a transaction. Should get an error. \
		Got InvalidTransactionException for transctionalException.getCause() as expected 

	- NOT_SUPPORTED
		Scenario: Invoking outside transaction \
		ObjectId for this beans is transactional.BeanNotSupported$Proxy$_$$_WeldSublcass@278ca \
		Scenario: Invoking within a transaction. Transaction is suspedent during the method call \
		ObjectId for this beans is transactional.BeanNotSupported$Proxy$_$$_WeldSublcass@278ca 

	- REQUIRED
		Scenario: Invoking outside transaction. Transaction would be started automatcailly for the method call \
		ObjectId for this beans is transactional.BeanRequired$Proxy$_$$_WeldSublcass@278ca \
		Scenario: Invoking within a transaction. Should get an error. \
		ObjectId for this beans is transactional.BeanRequired$Proxy$_$$_WeldSublcass@278ca 

	- REQUIRED_NEW
		Scenario: Invoking outside transaction. Transaction would be started automatically for the method call \
		ObjectId for this beans is transactional.BeanRequiredNew$Proxy$_$$_WeldSublcass@278ca \
		Scenario: Invoking within a transaction. NEW Transaction would be started automatically for the method call \
		ObjectId for this beans is transactional.BeanRequiredNew$Proxy$_$$_WeldSublcass@278ca 

	- SUPPORTS
		Scenario: Invoking outside transaction. Method is executed outside transaction \
		ObjectId for this beans is transactional$Proxy$_$$_WeldSublcass@278ca \
		Scenario: Invoking within a transaction. Method is Executed within transaction context \
		ObjectId for this beans is transactional.Supports$Proxy$_$$_WeldSublcass@278ca 

```java
import javax.ejb.*
 
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UserDetailBean implements UserDetailRemote {
	
   private UserDetail;

   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public void createUserDetail(User user) {
   }
}
```

```java
@Stateless
public class UserSessionBean implements UserRemote {
	
   private User;

   @EJB
   private UserDAO userDao;


   @EJB
   private UserDetailRemote userDetail;

   public void createUser(User user) {
   		userDao.create(user)
    	userDetail.createUserDetail(user);
   }
}
```

- createUser() business method is using createUserDetail(). If exception occurred during createUser() call and User object is not created then UserDetail object will also not be created.

> Bean Managed Transactions

- In Bean Managed Transactions, Transactions can be managed by handling exceptions at application level.
- Following are the key points to be considered
	- Start − When to start a transaction in a business method.
	- Sucess − Identify success scenario when a transaction is to be committed.
	- Failed − Identify failure scenario when a transaction is to be rollback.

- Examples:
	- Scenario 1: Starting transaction and calling beans. Bean is called twice but object id should be the same
		ObjectId for Bean1 is transactionScoped.Bean1@288ca \
		ObjectId for Bean1 is transactionScoped.Bean1@288ca \
		ObjectId for Bean2 is transactionScoped.Bean2@377ca 
	- Scenario 2: Repeat of scenrio 1 with new transaction. Should see different object ids
		ObjectId for Bean1 is transactionScoped.Bean1@444ca \
		ObjectId for Bean1 is transactionScoped.Bean1@444ca \
		ObjectId for Bean2 is transactionScoped.Bean2@465ca 
	- Scenario 3: Calling TransactionScoped bean outside transaction
		Got a ContextNotActiveException as expected. WELD-001303 No active context for scope type TransactionScoped 

```java
@Stateless
@TransactionManagement(value=TransactionManagementType.BEAN)
public class AccountBean implements AccountBeanLocal {
 
   @Resource
   private UserTransaction userTransaction;

   public void transferFund(Account fromAccount, double fund , 
      Account toAccount) throws Exception{

      try{
         userTransaction.begin();

         confirmAccountDetail(fromAccount);
         withdrawAmount(fromAccount,fund);

         confirmAccountDetail(toAccount);
         depositAmount(toAccount,fund);

         userTransaction.commit();
      }catch (InvalidAccountException exception) {
         userTransaction.rollback();
      }catch (InsufficientFundException exception) {
         userTransaction.rollback();
      }catch (PaymentException exception) {
         userTransaction.rollback();
      }
   }
}
```

> @Transactional annotation
	
- Provides declarative transaction demarcation via simple annoatation
- Provides abilty to specify exception handling behavior for marking transaction for rollback declaratively
- Decouples CMT from EJB with broadened support via CDI Beans, so we dont have to rely on the EJB Transaction management

```java
@Transactional(value = Transactional.TxType.REQUIRED,
	rollbackOn = {SQLException.class},
	dontRollbackOn= {SQLWarning.class})
```

> @TransactionScoped annotation

- Provides ability to declaratively scoped a managed bean to current transaction via simple annoatation (Bean Managed Transactions)
- It is used with UserTransaction
- Provides support in CDI Beans

```java
@TransactionScoped
private void someMethodHere(String param1 String param2)
```





> #### Logging

- The simplest way to dump the queries to standard out is to add the following to application.properties:

```properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

- Via Loggers

```properties
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

- P6Spy

```xml
<dependency>
    <groupId>com.github.gavlyukovskiy</groupId>
    <artifactId>p6spy-spring-boot-starter</artifactId>
    <version>1.8.0</version>
</dependency>
```
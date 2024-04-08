package hellojpa;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@SequenceGenerator( // CREATE SEQUENCE
        name = "member_seq_generator", sequenceName = "member_seq"
)
public class Member0 {
    @Id // ⭐권장하는 식별자 전략 : Long형 + 대체키(sequence나 UUID) + 키 생성 전략 사용 = Auto-increment나 Sequence-Object 권장!
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // IDENTITY는 기본 키 생성을 데이터베이스에 위임(문제는 DB에 들어가서 insert를 실행한 후에 ID을 알 수 있음)
    private Long id;
                                                            // 데이터베이스 컬럼 정보를 직접 줄 수 있다
    // @Column(insertable=true, updatable=true, unique=true, columnDefinition="varchar(100) default 'EMPTY'")
    @Column(name = "name", nullable = false) // 데이터베이스 컬럼명은 name, not null 제약조건
    private String username;

//    private BigDecimal age; // 숫자나 소수점 줄 때
    private int age;

//    @Enumerated(EnumType.ORDINAL) // Enum 순서를 데이터베이스에 저장
    @Enumerated(EnumType.STRING) // Enum 타입 만들고 Enum이름을 데이터베이스에 저장 하고 싶을때
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob
    private  String description;

    @Transient // 메모리 내에서만 쓰겠다
    private int temp;
    
    public Member0() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }
}

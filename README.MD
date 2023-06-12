# Security JWT Example

### JPA LocalDateTime 자동으로 생성하는 방법
- @EnableJpaAuditing (Main 클래스)
- @EntityListeners(AuditingEntityListener.class) (Entity 클래스)
```java
    @CreatedDate // Insert
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // Insert, Update
    @Column(nullable = false)
    private LocalDateTime updatedAt;
```

### Validation 체크 하는 법

```java
@Getter
@Setter
public class JoinReqDto{
    @NotEmpty // null 이거나 공백일 수 없다.
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    private String email;
    @NotEmpty
    private String fullname;

    public User toEntity(BCryptPasswordEncoder passwordEncoder){
        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .fullname(fullname)
                .role(UserRoleType.CUSTOMER)
                .build();
    }
}
```
```java
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid JoinReqDto joinReqDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }

            return new ResponseEntity<>(new ResponseDto<>(-1, "유효성 검사 실패" ,errorMap), HttpStatus.BAD_REQUEST);
        }

        JoinRespDto joinRespDto = userService.save(joinReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "회원가입 완료", joinRespDto), HttpStatus.CREATED);
    }
```
유효성 검사할 dto 앞에 @Valid를 추가하고 바로 뒤에 BindingResult를 쓰면 BindingResult에 error가 담긴다.

### Long Test
- 2^8 (-126 ~ 127) 범위에서는 == 으로 비교가 된다.
- 그 밖의 범위에서는 비교가 안된다. 그래서 isEquals나 longValue를 이용하여 비교하여야 한다.
```java
    @Test
    public void long_test2() throws Exception {
        // given
        Long v1 = 128L;
        Long v2 = 128L;

        // when
        if(v1 < v2){
            System.out.println("테스트 : v1이 작습니다");
        } else if(v1 == v2){
            System.out.println("테스트 : 같습니다");
        } else if(v1 > v2){
            System.out.println("테스트 : v1이 큽니다");
        } else if(v1.longValue() == v2.longValue()){
            System.out.println("테스트 : longValue()를 이용하여 비교 v1과 v2는 같습니다");
        }
    }

    @Test
    public void long_test3() throws  Exception {
        // given
        Long v1 = 128L;
        Long v2 = 128L;

        assertThat(v1).isEqualTo(v2);
    }
```
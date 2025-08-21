# repository
데이터베이스(DB)와 직접 통신하여 데이터를 저장하고 조회하는 역할

## JpaRepository - 쿼리 메서드 작성 규칙


* **핵심 원칙**
    * 메소드 이름을 분석하여 JPQL 쿼리를 자동으로 생성함
    * 엔티티의 필드명을 기반으로 조합

-----

### **1. 기본 구조**

* `[접두사][엔티티 속성][조건절]` 형태로 구성

-----

### **2. 주요 접두사 (Action)**

* **조회:** `find...By...`, `read...By...`, `get...By...`

    * 가장 일반적인 조회 기능 수행
    * 예: `findByNickname(String nickname)`
  

* **존재 여부 확인:** `exists...By...`

    * 결과가 존재하는지 `boolean` 타입으로 반환
    * 예: `existsByEmail(String email)`
  

* **개수 확인:** `count...By...`

    * 결과의 개수를 `long` 타입으로 반환
    * 예: `countByCategory(String category)`
  

* **삭제:** `delete...By...`, `remove...By...`

    * 조회된 엔티티를 삭제
    * `@Transactional` 필요
    * 예: `deleteByEmail(String email)`
  

-----

### **3. 조건절 (Criteria)**

* `By` 키워드 뒤에 엔티티의 필드명과 연산자를 조합


* **기본 조건:** 필드명 그대로 사용

    * `findByEmail(String email)` → `WHERE email = ?`
  

* **논리 연산:** `And`, `Or`

    * `findByProviderAndProvider1d(String p, String pId)` → `WHERE provider = ? AND provider_id = ?`


* **비교 연산:**

    * `GreaterThan`, `LessThan`: 초과/미만
    * `GreaterThanEqual`, `LessThanEqual`: 이상/이하
    * `Between`: 사이 값
        * 예: `findByAmountGreaterThan(int amount)` → `WHERE amount > ?`


* **문자열 연산:**

    * `Like`: 특정 문자열 포함 (`%` 와일드카드 필요)
    * `Containing`: 양방향 `Like` (`%word%`)
    * `StartingWith`: `word%`
    * `EndingWith`: `%word`
        * 예: `findByDescriptionContaining(String keyword)` → `WHERE description LIKE '%keyword%'`


* **기타 조건:**

    * `IsNull`, `IsNotNull`: `null` 값 여부
    * `In`: 여러 값 중 하나에 포함
    * `True`, `False`: `boolean` 필드 조건


* **속성 탐색 (Nested Properties):**

    * 연관 관계를 맺은 엔티티의 속성을 `_`(언더스코어)로 연결하여 접근
    * 예: `Diary` 엔티티에서 `User`의 `id`로 검색
        * `findByLedger_User_Id(Long userId)`


-----

### **4. 결과 제한 및 정렬**

* **결과 개수 제한:** `find`와 `By` 사이에 `First<숫자>` 또는 `Top<숫자>` 삽입

    * `findTop5By...`: 상위 5개 결과만 반환 (`LIMIT 5`)
    * `findFirstBy...`: 첫 번째 결과 1개만 반환 (`LIMIT 1`)

* **정렬:** `OrderBy` 키워드와 필드명, 정렬 방향(`Asc`, `Desc`) 조합

    * `...OrderByCreatedAtDesc`: `createdAt` 필드로 내림차순 정렬 (`ORDER BY createdAt DESC`)
    * 여러 필드 조합 가능: `...OrderByTransactionDateDesc, IdAsc`


-----

### **5. 종합 예시 (Pigmint 프로젝트 실제 코드 분석)**

* **메소드명:** `findTop3ByLedger_User_IdOrderByCreatedAtDesc(Long userId)`
* **자동 생성되는 쿼리 (JPQL):**
  ```sql
  SELECT d 
  FROM Diary d 
  WHERE d.ledger.user.id = :userId 
  ORDER BY d.createdAt DESC 
  LIMIT 3
  ```
* **분석:**
    * `findTop3`: 결과를 **3개**로 제한
    * `ByLedger_User_Id`: `Diary`의 `ledger` 필드를 거쳐, `Ledger`의 `user` 필드를 거쳐, 최종적으로 `User`의 `id`를 조건으로 검색
    * `OrderByCreatedAtDesc`: `createdAt` 필드를 기준으로 **내림차순(최신순)** 정렬
# MySQL Docker Setup Guide

## 1. MySQL Docker container indítása

Futtasd az alábbi parancsot a terminálban:

```bash
docker run --name kristof-mysql -e MYSQL_ROOT_PASSWORD=admin -e MYSQL_DATABASE=school -p 3306:3306 -d mysql:8.0
```

**Paraméterek magyarázata:**

| Paraméter | Érték | Leírás |
|---|---|---|
| `--name` | `kristof-mysql` | A container neve |
| `MYSQL_ROOT_PASSWORD` | `admin` | A root felhasználó jelszava |
| `MYSQL_DATABASE` | `school` | Automatikusan létrehozott adatbázis neve |
| `-p 3306:3306` | port | Host 3306 → Container 3306 |
| `-d` | – | Háttérben futtatja a containert |
| `mysql:8.0` | – | MySQL 8.0 image |

---

## 2. Container státusz ellenőrzése

```bash
docker ps
```

Ha fut, látni fogod a `kristof-mysql` containert a listában.

---

## 3. Maven dependency hozzáadása (`pom.xml`)

Add hozzá a MySQL JDBC drivert a `pom.xml` `<dependencies>` blokkjába:

```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

> A Spring Boot BOM automatikusan kezeli a verziót, nem kell külön megadni.

---

## 4. Spring Boot konfiguráció (`application.yaml`)

Az `src/main/resources/application.yaml` fájl tartalma:

```yaml
spring:
  application:
    name: nyeuni
  datasource:
    url: jdbc:mysql://localhost:3306/school
    username: root
    password: admin
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        format_sql: true
```

> `ddl-auto: update` → Hibernate automatikusan létrehozza/frissíti a táblákat (`courses`, `students`) indításkor.

---

## 5. Alkalmazás indítása

```bash
./mvnw spring-boot:run
```

Az alkalmazás elérhető: [http://localhost:8080](http://localhost:8080)

---

## 6. Elérhető végpontok

### Courses
| Method | URL | Leírás |
|---|---|---|
| `POST` | `/api/courses` | Új kurzus létrehozása |
| `GET` | `/api/courses` | Összes kurzus lekérdezése |
| `GET` | `/api/courses/{id}` | Kurzus lekérdezése ID alapján |
| `PUT` | `/api/courses/{id}` | Kurzus módosítása |
| `DELETE` | `/api/courses/{id}` | Kurzus törlése |

### Students
| Method | URL | Leírás |
|---|---|---|
| `POST` | `/api/students` | Új hallgató létrehozása |
| `GET` | `/api/students` | Összes hallgató lekérdezése |
| `GET` | `/api/students/{id}` | Hallgató lekérdezése ID alapján |
| `PUT` | `/api/students/{id}` | Hallgató módosítása |
| `DELETE` | `/api/students/{id}` | Hallgató törlése |

---

## 7. Példa kérések

### Kurzus létrehozása
```bash
curl -X POST http://localhost:8080/api/courses \
  -H "Content-Type: application/json" \
  -d '{"name": "Matematika", "teacherName": "Nagy János"}'
```

### Hallgató létrehozása (előbb kell egy kurzus!)
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{"name": "Kiss Péter", "age": 21, "courseId": 1}'
```

---

## 8. Container leállítása / újraindítása

```bash
# Leállítás
docker stop kristof-mysql

# Újraindítás (adatok megmaradnak)
docker start kristof-mysql

# Teljes törlés
docker rm -f kristof-mysql
```

> ⚠️ Ha törlöd a containert (`docker rm`), az adatbázis adatai elvesznek, mert nincs volume csatolva.
> Ha perzisztens tárolót szeretnél, add hozzá a parancshoz: `-v mysql_data:/var/lib/mysql`


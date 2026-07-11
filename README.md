# GelatoGo 🍦

GelatoGo je full-stack web aplikacija razvijena u okviru seminarskog rada iz predmeta **Napredne Java tehnologije**. Aplikacija omogućava pregled poslastičarnica, online naručivanje sladoleda i administraciju sistema.

## Tehnologije

### Backend
- Java 21
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA (Hibernate)
- MySQL
- Maven

### Frontend
- React
- React Router
- Axios
- HTML
- CSS

## Funkcionalnosti

### Korisnik
- Registracija korisnika
- Verifikacija naloga putem e-maila
- JWT autentifikacija
- Pregled poslastičarnica i sladoleda
- Dodavanje proizvoda u korpu
- Kreiranje narudžbine
- Pregled istorije narudžbina

### Administrator
- Pregled svih narudžbina
- Promena statusa narudžbina
- Dodavanje, izmena i brisanje poslastičarnica
- Dodavanje, izmena i brisanje sladoleda

---

# Pokretanje projekta

## 1. Kloniranje repozitorijuma

```bash
git clone https://github.com/MilaMladenovic/GelatoGo.git
cd GelatoGo
```

## 2. Kreiranje baze

Kreirati MySQL bazu pod nazivom:

```sql
CREATE DATABASE ice_cream_delivery_njt;
```

Zatim izvršiti SQL skriptu:

```
database/database.sql
```

## 3. Podešavanje backend-a

Po potrebi izmeniti fajl:

```
src/main/resources/application.properties
```

Posebno:

- MySQL korisničko ime i lozinku
- Mailtrap SMTP kredencijale (ili sopstveni SMTP server)

## 4. Pokretanje backend-a

Iz glavnog direktorijuma projekta:

```bash
mvn spring-boot:run
```

Backend će biti dostupan na:

```
http://localhost:8080
```

## 5. Pokretanje frontend-a

Otvoriti novi terminal:

```bash
cd ice-cream-delivery-front
npm install
npm start
```

Frontend će biti dostupan na:

```
http://localhost:3000
```

---

## Napomena

Za funkcionalnosti registracije, verifikacije naloga i resetovanja lozinke potrebno je podesiti SMTP parametre u `application.properties`.

---

## Autori

**Mila Mladenović**

**Maša Mladenović**

Fakultet organizacionih nauka  
Softversko inženjerstvo

Seminarski rad iz predmeta **Napredne Java tehnologije**

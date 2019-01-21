# Wypożyczalnia video
## Wymagania:
- Java Development Kit 1.8
- Maven
- Node.js
- NPM
- NPM, Maven oraz JDK w PATH
## Instrukcja uruchomienia:
1. Wymagania wstępne:
#### Linux
```
sudo apt update
sudo apt install node
npm install -g @vue/cli
```
#### Windows
```
npm install -g @vue/cli
```
2. Uruchomienie w folderze głównym kolejno:
```
mvn clean build
mvn --projects backend spring-boot:run
cd frontend
npm run serve
```
3. Przejdź do adresu http://localhost:8080/ w przeglądarce.
## Autorzy:
- Bartosz Żywicki
- Tomasz Pućka

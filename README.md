# Counter
# Link to docker image: 
https://hub.docker.com/layers/dsavitsky96/counter/1.0.1/images/sha256:add5921f589c51d18852968db3efe113966b372ddce647294b6e72c58731dcec

# Для запуска контейнера:
docker run -p {port}:8080 -t dsavitsky96/counter:1.0.3

# API

- для создания счетчика с уникальным именем {{NAME}} необходимо выполнить запрос:
POST /counters?name={{NAME}}
- для инкремента значения счетчика с именем {{NAME}} необходимо выполнить запрос:
PUT /counters?name={{NAME}}
- для получения значения счетчика с именем {{NAME}} необходимо выполнить запрос:
GET /counters?name={{NAME}}
- для удаления счетчика с именем {{NAME}} необходимо выполнить запрос:
DELETE /counters?name={{NAME}}
- для получения суммарного значения всех счетчиков необходимо выполнить запоос:
GET /counters/value
- для получения списка имен всех счетчиков необходимо выполнить запоос:
GET /counters/names

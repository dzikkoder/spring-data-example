# POST continents

curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"name":"Europe"}' \
  http://localhost:8080/continents/

curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"name":"Africa"}' \
  http://localhost:8080/continents/

curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"name":"Asia"}' \
  http://localhost:8080/continents/

sleep 1

# POST countries in Europe

curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"name":"Poland"}' \
  http://localhost:8080/continents/1/countries

curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"name":"Finland"}' \
  http://localhost:8080/continents/1/countries

sleep 1

# POST countries in Africa

curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"name":"Kongo"}' \
  http://localhost:8080/continents/2/countries

curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"name":"Egypt"}' \
  http://localhost:8080/continents/2/countries

sleep 1

# POST cities in Europe

curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"name":"Wrocław"}' \
  http://localhost:8080/countries/4/cities

curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"name":"Tampere"}' \
  http://localhost:8080/countries/5/cities

sleep 1

# POST Russia

curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"name":"Russia"}' \
  http://localhost:8080/continents/1/countries/

curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"name":"Russia"}' \
  http://localhost:8080/continents/3/countries

sleep 1

# DELETE Russia

curl --header "Content-Type: application/json" \
  --request DELETE \
  http://localhost:8080/countries/10

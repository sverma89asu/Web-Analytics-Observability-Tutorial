# Run the given file
1. Unzip the given files
2. Create a docker network <br />
`docker create network flask_net`
3. Pull redis image <br />
`docker pull redis`
4. Run redis container on the above created network <br />
`docker run -d --name my_redis --network flask_net redis`
5. Make sure redis_host is `my_redis` (the name of the redis docker container)
`def __init__(self, redis_host='my_redis', redis_port=6379):`
6. Build the flask service docker image <br />
`docker build -t task .`
7. Run the flask microservice <br />
`docker run -d --name task --network flask_net -p 5000:5000 task`
8. Test the Good and Bad request APIs from Postman

# Run the good files with exception handling and logging
1. Unzip the good files
2. Create a docker network (Use the same as above if already created) <br />
`docker create network flask_net`
3. Pull redis image <br />
`docker pull redis`
4. Run redis container on the above created network <br />
`docker run -d --name my_redis --network flask_net redis`
5. Make sure redis_host is `my_redis` (the name of the redis docker container)
`def __init__(self, redis_host='my_redis', redis_port=6379):`
6. Build the flask service docker image <br />
`docker build -t task_new .`
7. Run the flask microservice <br />
`docker run -d --name task_new --network flask_net -p 5001:5000 --env-file .env task_new`
8. Test the Good and Bad request APIs from Postman
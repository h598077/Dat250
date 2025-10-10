I have made docker container for rabbitmq, redis and spring with Dockerfile and docker-compose.

1. I started writing the Dockerfile using gradle alpine for builder and build the jar with grandle bootJar
2. Then using eclipse temurin alpine to add workingdirectory, group and user to that group.
3. Made an docker-compose where i spesify rabbit, redis and spring and network in between, ports and some kind of healthcheck so redis and rabbit starts before spring
4. Needed to fix my hostname in java from localhost to rabbitmq and redis
5. Launch multiple containers with docker compose build and then docker compose up



<img width="1612" height="776" alt="{9415AEAE-15F0-4401-90BE-794CF6004DCF}" src="https://github.com/user-attachments/assets/f2562f26-ab53-4b81-b2df-779d118c7efa" />

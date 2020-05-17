# 도커를 활용한 CI/CD 구축기 2
> Jenkins는 Docker 컨테이너 기반으로 돌아가고 Docker.sock을 통해 타 Docker container들과 통신한다.
## 
- Dockerfile 생성하기

```bash
minkj1992@minkj1992-900X5L:~/code/blue-jeju$ docker build -t jenkins:v1 .
docker run -p 8080:8080 -v /var/run/docker.sock:/var/run/docker.sock -v /home/minkj1992/jenkins:/var/jenkins_home:rw --name jenkins jenkins:v1
```

- mount 해준 볼륨에 jdk 파일 넣어주기
- maven 자동 다운 설정하기
- git webhook 설정하기
- new item on jenkins && github 설정 및 ssh 키 설정

> Failed to connect to repository : Error performing command: git ls-remote -h https://github.com/micdoodle8/Crossbow_Mod_2.git HEAD
- docker images 상에 존재하는 git 위치 (/usr/bin/git)을 jenkins 설정에 추가

> chomd 777 docker.sock

> Docker Builder

- Docker URL 설정
  - `http://localhost:8080/configure`의 Docker Builder
  - `unix:///var/run/docker.sock` 설정
  - 
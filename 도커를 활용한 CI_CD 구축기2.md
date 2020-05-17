# 1. 도커를 활용한 CI/CD 구축기 2
> Jenkins는 Docker 컨테이너 기반으로 돌아가고 Docker.sock을 통해 타 Docker container들과 통신한다.
> [Ngrok을 활용한 jenkins 세팅법](https://github.com/alreadyJ/create-CI)
## 1.1.  
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


> Maven 세팅 / Dockerize 세팅

## 1.2. 특이점 (20/05/17 5:50 PM)
현재 특이점이 온것은 내가 협업 관계가 아니다 보니, CI/CD를 하는 범위와 목적이 그저 CI/CD를 체험해 보기 위함일 뿐이라는 것을 깨달았다. AWS 서버를 지속적으로 사용할 서비스가 있는 것도 아니고, 그렇다고 merge conflict가 일어날 경우도 현재 존재하지 않는다. 

단지 내가 spring 서비스를 remote에 push 하면 webhook이 동작하여 Docker Image로 구성한 Jenkins가 동작하도록 trigger가 발생하고 이후 unit test 및 통합테스트를 진행한 뒤 docker image로 나의 서비스를 build해주면 종료가 되도록 진행하는것이 최선의 상황인 것 같다.

## 1.3. 이후 설정
1. Maven3 세팅 on Jenkins
2. GLIBC Version이 2.27 필요함(jdk-11용)
  - 시도 실패
  ```bash
  mkdir ~/glibc_install; cd ~/glibc_install
  wget http://ftp.gnu.org/gnu/glibc/glibc-2.27.tar.gz
  tar zxvf glibc-2.27.tar.gz
  cd glibc-2.27
  mkdir build
  cd build
  ../configure --prefix=/opt/glibc-2.27
  make -j4
  sudo make install
  export LD_LIBRARY_PATH=/opt/glibc-2.27/lib
  ```
3. 하지만 현재 버전의 문제로 애당초 Dockerfile이 잘못되었다. 해당 버전보다 **jenkins-jdk11-lst** 버전을 사용해야한다.
```bash
  Distributor ID:	Debian
  Description:	Debian GNU/Linux 9.12 (stretch)
  Release:	9.12
  Codename:	stretch

```
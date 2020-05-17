# 1. 도컬를 활용한 CI/CD 구축하기
> 목표: Docker 기반 Nginx, Jenkins, github webhook +a(AWS)를 활용한 CI/CD 환경 세팅방법을 정리한다.
> 해당 글은 Local 환경에서 ssh 키 기반으로 github server에 존재하는 repository에 webhook trigger가 가능하도록 하는 것을 1차적 목표로 구현한다.

- Nginx (무중단 배포)
- Jenkins, Github (for CI)
- Docker Container

## 1.1. Jenkins with Docker

1. 도커 기능을 사용해 Jenkins 검색
   - **Docker Official Jenkins는 버전이 deprecated되어있다. 이를 수정해주어야 한다.**
   - 만약 아래 수정 부분을 하고 싶지 않다면, [latest Jenkins](https://batmat.net/2018/09/07/how-to-run-and-upgrade-jenkins-using-the-official-docker-image/)를 참조하여 세팅한다.
   - 또는 `docker pull jenkins/jenkins:lts`을 통해 latest를 받아준다. (설정하기가 귀찮다면)

   ```bash
      sudo docker search jenkins
   ```

2. Jenkins를 사용하여 설치
   - **local의 jenkins mount backup 파일은 /app/jenkins가 더 현명하다고 생각한다.**
   - rw를 줄 필요가 있는가?

   ```bash
      sudo docker pull jenkins
      sudo docker inspect jenkins
      # 아래를 진행하기 이전에 rw 권한 에러 핸들링을 해주어야 한다.
      docker run -p 8081:8080 -v "$PWD"/jenkins:/var/jenkins_home:rw --name jk jenkins
   ```
   - 다음에는 `docker run -p 8080:8080 -v /home/minkj1992/jenkins:/var/jenkins_home:rw --name jk jenkins`

   - `rw 권한 에러 핸들링`
   ```
   touch: cannot touch '/var/jenkins_home/copy_reference_file.log': Permission denied
   Can not write to /var/jenkins_home/copy_reference_file.log. Wrong volume permissions?
   ```
     1. 볼륨으로 사용할 디렉토리를 먼저 생성한 다음
        1. PWD가 원하는 디렉토리가 되도록 cd이후 생성했던 jenkins 폴더에 chown 1000
     2. `sudo chown 1000 <볼륨으로 사용할 디렉터리>`
        - 해당 디렉토리의 권한을 변경해준다음 (강제적으로1000의 UID에 권한 부여)


3. Jenkins 포트로 접속하여 웹 서비스 열기

   ```bash
   chrome 127.0.0.1:8081
   브라우저에 캐시가 남아있는 경우에는 ctl + shift + del
   ```

4. Jenkins의 초기 패스워드 찾아서 로그인하기

    ```bash
    sudo docker exec -it jk cat /var/jenkins_home/secrets/initialAdminPassword
    sudo docker logs jk
    ```
## 1.2. Jenkins Setting
### 1.2.1. jenkins 플러그인 설치
> [Docker + Jenkins + 플러그인 에러 핸드링 참고 url](https://beomseok95.tistory.com/177)
#### 1.2.1.1. 플러그인 설치 에러 핸들링
> Docker Official Jenkins는 버전이 deprecated되어있다. 이를 수정해주어야 한다. 

1. docker container 내부에서 작업해야 하기 때문에 루트권한이 필요하고  ' -u 0 '   이 루트 권한 획득이다.

   ```bash
      minkj1992@minkj1992-900X5L:~/code/blue-jeju$ docer start <container_name>
      minkj1992@minkj1992-900X5L:~/code/blue-jeju$ docker exec -it -u 0 "jk" /bin/bash
      
      root@9dd2eebd138c:/# 
   ```
2. 최신 jenkins .war 파일 update
   - [참조](https://noviceany.tistory.com/55)
   - **혹시 다음에 한다면 ubuntu default mirror server는 속도가 느리기 때문에 kakao mirror로 바꾼뒤 아래를 진행하자**
   ```bash
      root@9dd2eebd138c:/# cd tmp
      root@9dd2eebd138c:/tmp# wget http://mirrors.jenkins-ci.org/war/latest/jenkins.war
   ```
3.  권한 및 war 교체작업
   ```bash
      mv ./jenkins.war /usr/share/jenkins
      chown jenkins:jenkins /usr/share/jenkins/jenkins.war
      exit
      ...
      docker restart <container_name>
   ```

#### github integration with jenkins
- `github integration` 플러그인 다운로드

### jenkins SSH 설정
> [참고 URI](https://jojoldu.tistory.com/442)

1. SSH 키 생성하기
   - 이것을 해주기위해서 local repository에 존재하던 jenkins mount dir를 local 환경의 git staging이 되지 않는 volume으로 이동시켜주었다.
```bash
root@minkj1992-900X5L:/var/lib# cd jenkins/
root@minkj1992-900X5L:/var/lib/jenkins# ls
root@minkj1992-900X5L:/var/lib/jenkins# mkdir .ssh
root@minkj1992-900X5L:/var/lib/jenkins# ls
root@minkj1992-900X5L:/var/lib/jenkins# cd .
./    ../   .ssh/ 
root@minkj1992-900X5L:/var/lib/jenkins# cd .
./    ../   .ssh/ 
root@minkj1992-900X5L:/var/lib/jenkins# cd .ssh/
root@minkj1992-900X5L:/var/lib/jenkins/.ssh# ls
root@minkj1992-900X5L:/var/lib/jenkins/.ssh# ssh-keygen -t rsa -f /var/lib/jenkins/.ssh/github_ansible-in-action

```

1. Add Credentials
   - 젠킨스 메인 화면에서 Credentials -> System -> Global credentials로 차례로 이동
    - 키 등록
2. 젠킨스 Item 등록하기(Credentials 권한 부여)

## Github Webhook with Jenkins
> [참고 URL](https://shmoon.tistory.com/11)

### 플러그인 다운로드
> jenkinsr관리 -> 플러그인 관리-> 고급 -> 플러그인 올리기
- Maven Plugin
- Docker-build-Step
- Docker-plugin




## c.f) 도커 bind-mount 위치 변경하기
> [참고자료](https://stackoverflow.com/questions/28302178/how-can-i-add-a-volume-to-an-existing-docker-container)
> Jenkins 관련 파일 지우고 싶지 않기 때문에, host에서 copy를 진행해줌과 동시에, mount config.json 위치를 변경해준다.
0. docker <container_name> stop
1. `/var/lib/docker/containers/9dd2eebd138ca70be6887b9029695564a62fc19dae2c7d9dd12a957debc9eb40# vim config.v2.json` 변경
```bash
minkj1992@minkj1992-900X5L:/var/lib/docker$ sudo -i
root@minkj1992-900X5L:/var/lib/docker/containers/9dd2eebd138ca70be6887b9029695564a62fc19dae2c7d9dd12a957debc9eb40# vim config.v2.json 
```
   - `/home/minkj1992/code/blue-jeju/jenkins` -> `/home/minkj1992/jenkins` 변경
2. OLD-PATH(/home/minkj1992/code/blue-jeju/jenkins)에 존재하는 Jenkins 파일 mv
```bash
minkj1992@minkj1992-900X5L:~/code/blue-jeju$ mv jenkins ~/jenkins
minkj1992@minkj1992-900X5L:service docker restart
```

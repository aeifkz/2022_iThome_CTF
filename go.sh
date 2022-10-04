docker pull aeifkz/ithome_ctf:v1.0
docker run --rm -p 8080:8080 --security-opt="seccomp=unconfined" --security-opt="apparmor=unconfined" aeifkz/ithome_ctf:v1.0
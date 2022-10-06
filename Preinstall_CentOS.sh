useradd -m $1
cd /home/$1
echo "Install yum tools......"
yum install vim git wget unzip net-tools -y 

echo "Install docker......"
yum install -y yum-utils
yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
yum install docker-ce docker-ce-cli containerd.io docker-compose-plugin -y
systemctl enable docker
systemctl start docker
usermod -aG docker $1  

echo "Install Maven......"
cd /home/$1
wget --no-check-certificate  https://dlcdn.apache.org/maven/maven-3/3.8.6/binaries/apache-maven-3.8.6-bin.zip
unzip apache-maven-3.8.6-bin.zip
rm apache-maven-3.8.6-bin.zip
#echo 'PATH=/home/aeifkz/apache-maven-3.8.6/bin:$PATH' >> /home/aeifkz/.bashrc
export PATH=/home/$1/apache-maven-3.8.6/bin:$PATH

echo "Install openjdk-11-jdk......"
yum install java-11-openjdk.x86_64 -y

echo "Download Git Project and Build WAR......"
cd /home/$1
git clone https://github.com/aeifkz/2022_iThome_CTF.git
cd 2022_iThome_CTF
mvn clean package -DskipTests
cp target/Spring4Shell-0.0.1-SNAPSHOT.war Spring4Shell.war
mv ~/.m2 /home/$1

echo "chown home directory"
chown -R $1:$1 /home/$1

echo "create flag file"
cp /home/$1/2022_iThome_CTF/flag.tar /home/$1
chown root:root /home/$1/flag.tar
chmod 600 /home/$1/flag.tar

echo "Install Linux Kernel 5.16.20-051620......"
cd /home/$1



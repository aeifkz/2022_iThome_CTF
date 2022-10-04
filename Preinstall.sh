useradd -m $1
cd /home/$1
apt update
echo "Install apt tools......"
apt install vim git wget unzip net-tools -y 

echo "Install docker......"
apt install ca-certificates curl gnupg lsb-release -y
mkdir -p /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | gpg --dearmor -o /etc/apt/keyrings/docker.gpg
echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null
apt update
apt install docker-ce docker-ce-cli containerd.io docker-compose-plugin -y
usermod -aG docker $1  

echo "Install Maven......"
cd /home/$1
wget https://dlcdn.apache.org/maven/maven-3/3.8.6/binaries/apache-maven-3.8.6-bin.zip
unzip apache-maven-3.8.6-bin.zip
rm apache-maven-3.8.6-bin.zip
#echo 'PATH=/home/aeifkz/apache-maven-3.8.6/bin:$PATH' >> /home/aeifkz/.bashrc
export PATH=/home/$1/apache-maven-3.8.6/bin:$PATH

echo "Install openjdk-17-jdk......"
apt install openjdk-17-jdk -y

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
cp /home/$1/2022_iThome_CTF/flag.tar /home/$1/2022_iThome_CTF/
chown root:root /home/$1/flag.tar

echo "Install Linux Kernel 5.16.20-051620......"
cd /home/$1
wget https://kernel.ubuntu.com/~kernel-ppa/mainline/v5.16.20/amd64/linux-headers-5.16.20-051620-generic_5.16.20-051620.202204131933_amd64.deb
wget https://kernel.ubuntu.com/~kernel-ppa/mainline/v5.16.20/amd64/linux-headers-5.16.20-051620_5.16.20-051620.202204131933_all.deb
wget https://kernel.ubuntu.com/~kernel-ppa/mainline/v5.16.20/amd64/linux-image-unsigned-5.16.20-051620-generic_5.16.20-051620.202204131933_amd64.deb
wget https://kernel.ubuntu.com/~kernel-ppa/mainline/v5.16.20/amd64/linux-modules-5.16.20-051620-generic_5.16.20-051620.202204131933_amd64.deb
sudo dpkg -i *.deb #不要一個個安裝，會失敗，讓系統自己決定相依性
update-grub
rm -f *.deb
shutdown -r now




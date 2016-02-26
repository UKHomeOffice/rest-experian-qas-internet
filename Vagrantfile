# -*- mode: ruby -*-
# vi: set ft=ruby :

# Vagrantfile API/syntax version. Don't touch unless you know what you're doing!
VAGRANTFILE_API_VERSION = "2"

Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|
  # All Vagrant configuration is done here. The most common configuration
  # options are documented and commented below. For a complete reference,
  # please see the online documentation at vagrantup.com.

  # Every Vagrant virtual environment requires a box to build off of.
  config.vm.box = "ubuntu/trusty64"


  config.vm.define "web" do |web|
    web.vm.network "private_network", ip: "192.168.91.10"

    $script = <<SCRIPT
apt_updated=false

for package in openjdk-7-jdk openjdk-7-jre; do
  if ! dpkg -s $package > /dev/null 2>&1 ; then
    if [ "$apt_updated" = false ] ; then
      echo "Updating APT Repository"
      apt-get update
      apt_updated=true
    fi

    echo "Installing $package"
    apt-get install -y $package
  fi
done

if [ ! -d /opt/apache-maven-3.2.3 ] ; then
  echo "Installing apache-maven-3.2.3"
  wget -qO /tmp/apache-maven-3.2.3-bin.tar.gz http://www.mirrorservice.org/sites/ftp.apache.org/maven/maven-3/3.2.3/binaries/apache-maven-3.2.3-bin.tar.gz
  tar -C /opt -xvzf /tmp/apache-maven-3.2.3-bin.tar.gz
  rm /tmp/apache-maven-3.2.3-bin.tar.gz
  for bin_file in mvn mvnDebug mvnyjp; do
    ln -s /opt/apache-maven-3.2.3/bin/$bin_file /usr/local/bin/$bin_file
  done
fi

if ! grep -q "ws.ondemand.qas.com" /etc/hosts; then
  echo "Preventing Direct Access To "ws.ondemand.qas.com" Via Hosts File"
  echo >> /etc/hosts
  echo "127.0.0.1 ws.ondemand.qas.com" >> /etc/hosts
fi

SCRIPT


    web.vm.provision "shell", inline: $script
  end

  config.vm.define "proxy" do |proxy|
    proxy.vm.network "private_network", ip: "192.168.91.11"

    $script = <<SCRIPT
apt_updated=false

for package in squid3; do
  if ! dpkg -s $package > /dev/null 2>&1 ; then
    if [ "$apt_updated" = false ] ; then
      echo "Updating APT Repository"
      apt-get update
      apt_updated=true
    fi

    echo "Installing $package"
    apt-get install -y $package
  fi
done

if grep -q "#acl localnet" /etc/squid3/squid.conf; then
  echo "Configuring Squid3 As Proxy"
  sed -i 's/#acl localnet src 192.168.0.0/acl localnet src 192.168.0.0/g' /etc/squid3/squid.conf
  sed -i 's/#http_access allow localnet/http_access allow localnet/g' /etc/squid3/squid.conf
  service squid3 restart
fi

SCRIPT


    proxy.vm.provision "shell", inline: $script
  end
end

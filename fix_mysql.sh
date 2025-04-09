#!/bin/bash

echo "📦 停止并卸载 MySQL..."
brew services stop mysql@8.0
brew uninstall --force mysql@8.0

echo "🧹 删除 MySQL 数据目录和残留配置..."
sudo rm -rf /opt/homebrew/var/mysql
sudo rm -rf /tmp/mysql.sock
sudo rm -rf /usr/local/mysql
sudo rm -rf /usr/local/var/mysql
sudo rm -rf /opt/homebrew/etc/my.cnf
sudo rm -rf ~/.my.cnf

echo "🔧 终止残留 mysqld 进程..."
sudo pkill -f mysqld

echo "🍺 重新安装 mysql@8.0..."
brew install mysql@8.0

echo "✅ 配置环境变量..."
echo 'export PATH="/opt/homebrew/opt/mysql@8.0/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc

echo "📂 初始化 MySQL 数据库（不设置 root 密码）..."
/opt/homebrew/opt/mysql@8.0/bin/mysqld --initialize-insecure --user=$(whoami) --basedir=/opt/homebrew/opt/mysql@8.0 --datadir=/opt/homebrew/var/mysql

echo "🚀 启动 MySQL 服务..."
brew services start mysql@8.0

echo "📝 创建默认连接配置 ~/.my.cnf ..."
cat <<EOF > ~/.my.cnf
[client]
user=root
socket=/opt/homebrew/var/mysql/mysql.sock
EOF
chmod 600 ~/.my.cnf

echo "✅ 现在你可以通过 mysql 命令直接连接："
echo "    mysql"
echo "或者执行 mysql_secure_installation 设置 root 密码"

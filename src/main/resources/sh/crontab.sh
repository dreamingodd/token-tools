
mkdir -p /opt/log/geth
chmod 744 /opt/sh/*
vi /etc/rsyslog.d/50-default.conf
service rsyslog restart
service cron restart

# add in crontab
crontab -e

  0  0 * * * /opt/sh/geth-day-log.sh
 15 17 * * * /opt/sh/geth-auto-restart.sh
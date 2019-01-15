# Maven Build
mvn clean test verify
# Copy to tommcat
sudo cp authorization-endpoint/target/authorization-endpoint.war /usr/share/tomcat8/webapps/
sudo cp token-endpoint/target/token-endpoint.war /usr/share/tomcat8/webapps/
sudo cp discovery-endpoint/target/discovery-endpoint.war /usr/share/tomcat8/webapps/
# Restart tomcat
sudo systemctl restart tomcat8

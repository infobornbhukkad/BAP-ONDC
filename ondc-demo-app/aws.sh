git checkout src/
git pull
mvn clean install
cp target/ondc-demo-app-0.0.1-SNAPSHOT.jar test/seller/
#mv src/main/resources/application.yml src/main/resources/application.yml.seller
#mv src/main/resources/application.yml.buyer src/main/resources/application.yml
#mvn clean install
#cp target/ondc-demo-app-0.0.1-SNAPSHOT.jar test/buyer/
#mv src/main/resources/application.yml src/main/resources/application.yml.buyer




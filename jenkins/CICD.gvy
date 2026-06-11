pipeline
{
agent any
tools
{
maven "maven-3.9.16"
}
triggers 
{
  cron '* * * * *'
}

stages
{
stage('github')
{
steps
{
git branch: 'dev', credentialsId: 'siva', url: 'https://github.com/MSivaP/myapp_mithun.git'
}
}
stage ('build')
{
steps
{
sh "mvn clean package"
}
}
stage ('deploy into the container')
{
steps
{
sshagent(['ef75fe83-bfc0-4f51-848f-340e348fa6e5'])
{
 sh "scp -o StrictHostKeyChecking=no target/maven-web-application.war ec2-user@16.176.13.84:/opt/tomcat/webapps"
}
}
}
}
}

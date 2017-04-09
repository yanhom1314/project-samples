#### 创建 

        forge
        $ addon-install-from-git --url https://github.com/forge/wildfly-swarm-addon.git
        $ project-new --named demo --type wildfly-swarm-addon --stack JAVAEE_7
        $ wildfly-swarm-setup
        $ wildfly-swarm-list-fractions
        $ wildfly-swarm-add-fraction --fractions jaxrs
        $ wildfly-swarm-new-main-class
        $ wildfly-swarm-run
        
        
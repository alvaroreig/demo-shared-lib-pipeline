def call(body) {

    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    node {

        try {

            /*
                This library requires mailgunDomainName and mailgunApiKey
                configured as Global properties in Jenkins
            */
            def user="api:${env.mailgunApiKey}"
            def url="https://api.mailgun.net/v3/${env.mailgunDomainName}/messages"
            def from="'Mailgun Helper Jenkins <jenkins@${env.mailgunDomainName}>'"

            println "sending email to ${config.mailTo}"
            sh "curl -s --user $user $url -F from=$from -F to=${config.mailTo} -F subject='${config.mailSubject}' -F text='${config.mailText}'"
        } catch (err) {
            currentBuild.result = 'FAILED'
            throw err
        }
    }
}
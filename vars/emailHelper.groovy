def call(body) {

    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    node {
        // Clean workspace before doing anything
        deleteDir()

        try {
            println "sending email to ${config.mailTo}"
            sh "curl -s --user 'api:${config.apiKey}' https://api.mailgun.net/v3/${config.domainName}/messages -F from='Excited User <mailgun@${config.domainName}>' -F to=${config.mailTo} -F subject='Hello' -F text='Testing some Mailgun awesomness!'"
        } catch (err) {
            currentBuild.result = 'FAILED'
            throw err
        }
    }
}
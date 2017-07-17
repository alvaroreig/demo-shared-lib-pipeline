def call(body) {

    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    node {
        // Clean workspace before doing anything
        //deleteDir()

        try {

            def user="api:${config.apiKey}"
            def url="https://api.mailgun.net/v3/${config.domainName}/messages"
            def from="'Excited User <mailgun@${config.domainName}>'"

            println "sending email to ${config.mailTo}"
            sh "curl -s --user $user $url -F from=$from -F to=${config.mailTo} -F subject='${config.mailSubject}' -F text='${config.mailText}'"
        } catch (err) {
            currentBuild.result = 'FAILED'
            throw err
        }
    }
}
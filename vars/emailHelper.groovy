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
        } catch (err) {
            currentBuild.result = 'FAILED'
            throw err
        }
    }
}
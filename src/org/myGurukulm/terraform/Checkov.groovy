package org.myGurukulm.terraform

class Checkov implements Serializable {

    def steps

    Checkov(steps) {
        this.steps = steps
    }

    def scan() {
        steps.sh '''
          checkov -d . \
            --framework terraform \
            --soft-fail
        '''
    }
}

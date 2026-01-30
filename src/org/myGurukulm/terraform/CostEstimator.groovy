package org.myGurukulm.terraform

class CostEstimator implements Serializable {

    def steps

    CostEstimator(steps) {
        this.steps = steps
    }

    def estimate() {
        steps.sh '''
          infracost breakdown \
            --path . \
            --format table \
            --show-skipped
        '''
    }
}

package org.myGurukulm.terraform

class Terraform implements Serializable {

    def steps

    Terraform(steps) {
        this.steps = steps
    }

    def init() {
        steps.sh 'terraform init -input=false'
    }

    def fmt() {
        steps.sh 'terraform fmt -check'
    }

    def validate() {
        steps.sh 'terraform validate'
    }

    def plan(String planFile) {
        steps.sh "terraform plan -out=${planFile}"
    }

    def apply(String planFile) {
        steps.sh "terraform apply -auto-approve ${planFile}"
    }
}

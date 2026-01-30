def call(Map config) {

    def terraform = new org.myGurukulm.terraform.Terraform(this)
    def checkov = new org.myGurukulm.terraform.Checkov(this)
    def costEstimator = new org.myGurukulm.terraform.CostEstimator(this)

    pipeline {
        agent any

        environment {
            TF_DIR  = "${config.tfDir}"
            TF_PLAN = "tfplan.out"
        }

        stages {

            stage('Checkout') {
                steps {
                    git branch: 'main', url: 'https://github.com/SyedRehanAli25/employee-api.git' 
                }
            }

            stage('Terraform Init') {
                steps {
                    dir(env.TF_DIR) {
                        script {
                            terraform.init()
                        }
                    }
                }
            }

            stage('Validate & Fmt') {
                steps {
                    dir(env.TF_DIR) {
                        script {
                            terraform.fmt()
                            terraform.validate()
                        }
                    }
                }
            }

            stage('Checkov Scan') {
                steps {
                    dir(env.TF_DIR) {
                        script {
                            checkov.scan()
                        }
                    }
                }
            }

            stage('Cost Estimation') {
                steps {
                    dir(env.TF_DIR) {
                        script {
                            costEstimator.estimate()
                        }
                    }
                }
            }

            stage('Terraform Plan (CI)') {
                steps {
                    dir(env.TF_DIR) {
                        script {
                            terraform.plan(env.TF_PLAN)
                        }
                    }
                }
            }

            stage('Archive tfplan') {
                steps {
                    dir(env.TF_DIR) {
                        archiveArtifacts artifacts: env.TF_PLAN, fingerprint: true
                    }
                }
            }

            stage('Approve CD') {
                steps {
                    input message: "Approve Terraform Apply for ${env.TF_DIR}?"
                }
            }

            stage('Terraform Apply (CD)') {
                steps {
                    dir(env.TF_DIR) {
                        script {
                            terraform.apply(env.TF_PLAN)
                        }
                    }
                }
            }
        }
    }
}
